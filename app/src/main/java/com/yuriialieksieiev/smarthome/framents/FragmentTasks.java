package com.yuriialieksieiev.smarthome.framents;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yuriialieksieiev.smarthome.R;
import com.yuriialieksieiev.smarthome.activity.MakerView;
import com.yuriialieksieiev.smarthome.components.dialoges.AlertDialogEdition;
import com.yuriialieksieiev.smarthome.components.RcTasksAdapter;
import com.yuriialieksieiev.smarthome.components.Task;
import com.yuriialieksieiev.smarthome.components.exceptions.TaskException;
import com.yuriialieksieiev.smarthome.utils.UrlUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentTasks extends Fragment
{
    private RecyclerView.Adapter rcAdapter;
    private Context context;
    private List<Task> tasks = new ArrayList<>();
    private View root;
    private Snackbar snackBarErrorConnection;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_tasks,container,false);
        init();
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        setTasks();
    }

    private void init()
    {
        context = getContext();

        final RecyclerView rcTasks = root.findViewById(R.id.rcv_tasks);
        rcTasks.setHasFixedSize(true);
        rcAdapter = new RcTasksAdapter(tasks, new RcTasksAdapter.RcListener() {
            @Override
            public void onLongPressTask(final Task task) {
                new AlertDialogEdition(getContext(), new AlertDialogEdition.CallBack() {
                    @Override
                    public void edit() {
                        editTask(task);
                    }

                    @Override
                    public void remove()
                    {
                        sendRequestForRemovingTask(task);
                    }
                },task.getName()).show();
            }

            @Override
            public void onChangeTaskStatus(Task task) {
                sendTask(task);
            }
        });
        rcTasks.setLayoutManager( new LinearLayoutManager(getContext()));
        rcTasks.setAdapter(rcAdapter);

        root.findViewById(R.id.btn_add_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });
    }

    private void sendTask(final Task task)
    {
        final StringRequest stringRequest =
                new StringRequest(
                        Request.Method.POST,
                        UrlUtils.getUrlForPostTask(context),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    final JSONObject responseJson = new JSONObject(response);
                                    if (responseJson.getString("Response").equals("WRONG"))
                                        Snackbar.make(root, responseJson.getString("description"), Snackbar.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },null){
                    @Override
                    protected Map<String, String> getParams() {

                        Map<String,String> map = new HashMap<>();
                        try {
                            map.put("data",Task.toJson(task).toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return map;
                    }
                };
        Volley.newRequestQueue(context).add(stringRequest);
    }

    private void sendRequestForRemovingTask(final Task task)
    {
        final StringRequest stringRequest =
                new StringRequest(
                        Request.Method.POST,
                        UrlUtils.getUrlForDeleteTask(context),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    final JSONObject responseJson = new JSONObject(response);
                                    if (responseJson.getString("Response").equals("WRONG"))
                                        Snackbar.make(root, responseJson.getString("description"), Snackbar.LENGTH_SHORT).show();
                                    else {
                                        Snackbar.make(root, R.string.removed_successful, Snackbar.LENGTH_SHORT).show();
                                        rcAdapter.notifyDataSetChanged();
                                        tasks.remove(task);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },null){
                    @Override
                    protected Map<String, String> getParams() {
                        final JSONObject data = new JSONObject();
                        try {
                            data.put("id",task.getId());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Map<String,String> map = new HashMap<>();
                        map.put("data",data.toString());
                        return map;
                    }
                };
        Volley.newRequestQueue(context).add(stringRequest);
    }

    private void setTasks()
    {
        final StringRequest stringRequest =
                new StringRequest(
                        Request.Method.GET,
                        UrlUtils.getUrlForGettingTasks(context),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    final JSONArray tasks_json = new JSONArray(response);
                                    tasks.clear();
                                    for(int i = 0; i < tasks_json.length(); i++)
                                        tasks.add(Task.parseTask(tasks_json.getJSONObject(i)));
                                    rcAdapter.notifyDataSetChanged();
                                } catch (JSONException |TaskException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        snackBarErrorConnection = Snackbar.make(root, R.string.error_connection, Snackbar.LENGTH_INDEFINITE);
                        snackBarErrorConnection.setAction(R.string.retry, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setTasks();
                            }
                        });
                        snackBarErrorConnection.show();
                    }
                });
        Volley.newRequestQueue(context).add(stringRequest);
    }



    public void addTask()
    {
        final Intent intent = new Intent(getContext(), MakerView.class);
        intent.putExtra(MakerView.EXTRA_WHAT_VIEW,MakerView.EXTRA_TASK);

        startActivity(intent);
    }

    private void editTask(Task task)
    {
        final Intent intent = new Intent(getContext(), MakerView.class);
        intent.putExtra(MakerView.EXTRA_WHAT_VIEW,MakerView.EXTRA_TASK);
        intent.putExtra(MakerView.EXTRA_OBJECT_TASK,task);
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (snackBarErrorConnection != null && snackBarErrorConnection.isShown()) {
            snackBarErrorConnection.dismiss();
            snackBarErrorConnection = null;
        }
    }
}
