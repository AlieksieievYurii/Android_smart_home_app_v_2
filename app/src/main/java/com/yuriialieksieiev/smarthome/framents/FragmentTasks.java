package com.yuriialieksieiev.smarthome.framents;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.yuriialieksieiev.smarthome.components.RcTasksAdapter;
import com.yuriialieksieiev.smarthome.components.Task;
import com.yuriialieksieiev.smarthome.components.exceptions.TaskException;
import com.yuriialieksieiev.smarthome.utils.UrlUtils;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;

public class FragmentTasks extends Fragment
{
    private RecyclerView.Adapter rcAdapter;
    private Context context;
    private List<Task> tasks = new ArrayList<>();
    private View root;

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
        rcAdapter = new RcTasksAdapter(tasks);
        rcTasks.setLayoutManager( new LinearLayoutManager(getContext()));
        rcTasks.setAdapter(rcAdapter);

        root.findViewById(R.id.btn_add_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });
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
                                    final JSONArray tasks = new JSONArray(response);
                                    for(int i = 0; i < tasks.length(); i++)
                                        FragmentTasks.this.tasks.add(Task.parseTask(tasks.getJSONObject(i)));
                                    rcAdapter.notifyDataSetChanged();
                                } catch (JSONException |TaskException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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
}
