package com.yuriialieksieiev.smarthome.framents;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.yuriialieksieiev.smarthome.R;
import com.yuriialieksieiev.smarthome.components.Action;
import com.yuriialieksieiev.smarthome.components.enums.TaskMode;
import com.yuriialieksieiev.smarthome.components.enums.TypeTask;

import org.json.JSONException;

import java.util.List;
import java.util.Objects;

public class FragmentCreatorTask extends Fragment {
    public interface TakerActions {
        List<Action> getActions();
    }

    private View root;

    private EditText edtName;
    private EditText edtDescription;
    private Spinner spTaskType;
    private Spinner spMode;
    private TakerActions takerActions;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_creator_task, container, false);
        init();
        return root;
    }

    private void init() {
        edtName = root.findViewById(R.id.tv_name);
        edtDescription = root.findViewById(R.id.tv_description);
        spTaskType = root.findViewById(R.id.sp_type_task);
        spMode = root.findViewById(R.id.sp_mode);

        spMode.setAdapter(new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_dropdown_item, TaskMode.values()));

        spTaskType.setAdapter(new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_dropdown_item, TypeTask.values()));
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

        root.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Action> a  = takerActions.getActions();

                for(Action aqa: a) {
                    try {
                        Log.d("Lol",aqa.toJson().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void setFragmentTimerJob()
    {
        FragmentJobTimer f = new FragmentJobTimer();
        takerActions = f;
        assert getFragmentManager() != null;
        getFragmentManager().beginTransaction().replace(R.id.fl_job,f).commit();
    }

}
