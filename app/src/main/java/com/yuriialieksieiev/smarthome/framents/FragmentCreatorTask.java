package com.yuriialieksieiev.smarthome.framents;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yuriialieksieiev.smarthome.R;
import com.yuriialieksieiev.smarthome.components.enums.TaskMode;
import com.yuriialieksieiev.smarthome.components.enums.TaskStatus;
import com.yuriialieksieiev.smarthome.components.enums.TypeTask;
import com.yuriialieksieiev.smarthome.utils.UrlUtils;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FragmentCreatorTask extends Fragment {
    public interface TakerJob {
        JSONObject getJob();
    }

    private static final String API_EXTRA_TASK_ID = "id";
    private static final String API_EXTRA_TASK_TYPE_TASK = "type_task";
    private static final String API_EXTRA_TASK_NAME = "name";
    private static final String API_EXTRA_TASK_DESCRIPTION = "description";
    private static final String API_EXTRA_TASK_STATUS = "status";
    private static final String API_EXTRA_TASK_MODE = "mode";
    private static final String API_EXTRA_TASK_JOB = "job";

    private View root;
    private EditText edtName;
    private EditText edtDescription;
    private Spinner spTaskType;
    private Spinner spMode;
    private Spinner spStatus;
    private TakerJob takerJob;
    private Context context;
    private Button btnApply;

    private int id = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_creator_task, container, false);
        context = getContext();
        init();
        getTaskIds();
        return root;
    }

    private void init() {
        btnApply = root.findViewById(R.id.btn_save);
        edtName = root.findViewById(R.id.edt_name);
        edtDescription = root.findViewById(R.id.edt_description);
        spTaskType = root.findViewById(R.id.sp_type_task);
        spMode = root.findViewById(R.id.sp_mode);
        spStatus = root.findViewById(R.id.sp_status);

        spMode.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, TaskMode.values()));
        spStatus.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, TaskStatus.values()));
        spTaskType.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, TypeTask.values()));
        spTaskType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch ((TypeTask) spTaskType.getSelectedItem()) {
                    case timer:
                        setFragmentTimerJob();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFields()) {
                    try {
                        applyTask();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }}
        });

    }

    private void getTaskIds() {
        final StringRequest stringRequest =
                new StringRequest(
                        Request.Method.GET,
                        UrlUtils.getUrlForGettingFreeId(context),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    id = new JSONObject(response).getInt("id");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },null);
        Volley.newRequestQueue(context).add(stringRequest);
    }

    private boolean checkFields() {
        if (edtName.getText().toString().isEmpty()) {
            Snackbar.make(root, "Name is empty!", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        if (edtDescription.getText().toString().isEmpty()) {
            Snackbar.make(root, "Description is empty!", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void applyTask() throws JSONException
    {
        if(id == -1)
        {
            Snackbar.make(root,"Can not get free id!",Snackbar.LENGTH_LONG).show();
            return;
        }

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put(API_EXTRA_TASK_ID, id);
        jsonObject.put(API_EXTRA_TASK_TYPE_TASK, ((TypeTask) spTaskType.getSelectedItem()).getInJson());
        jsonObject.put(API_EXTRA_TASK_NAME, edtName.getText().toString());
        jsonObject.put(API_EXTRA_TASK_DESCRIPTION, edtDescription.getText().toString());
        jsonObject.put(API_EXTRA_TASK_STATUS, ((TaskStatus) spStatus.getSelectedItem()).getInJson());
        jsonObject.put(API_EXTRA_TASK_MODE, ((TaskMode) spMode.getSelectedItem()).getInJson());
        jsonObject.put(API_EXTRA_TASK_JOB, takerJob.getJob());
        sendTask(jsonObject);
    }

    private void sendTask(final JSONObject jsonObject) {
        btnApply.setClickable(false);
        btnApply.setTextColor(Color.GRAY);
        final StringRequest stringRequest =
                new StringRequest(
                        Request.Method.POST,
                        UrlUtils.getUrlForPostTask(context),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (response != null) {
                                    try {
                                        JSONObject resp = new JSONObject(response);
                                        if (resp.getString("Response").equals("OK"))
                                            Objects.requireNonNull(getActivity()).finish();
                                        else
                                            errorSendingTask();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                errorSendingTask();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> param = new HashMap<>();
                        param.put("data", jsonObject.toString());
                        return param;
                    }
                };
        Volley.newRequestQueue(context).add(stringRequest);
    }

    private void errorSendingTask() {
        btnApply.setClickable(true);
        btnApply.setTextColor(Color.BLACK);
        Snackbar.make(root, "Error sending Task!", Snackbar.LENGTH_SHORT).show();
    }


    private void setFragmentTimerJob() {
        FragmentJobTimer f = new FragmentJobTimer();
        takerJob = f;
        assert getFragmentManager() != null;
        getFragmentManager().beginTransaction().replace(R.id.fl_job, f).commit();
    }

}
