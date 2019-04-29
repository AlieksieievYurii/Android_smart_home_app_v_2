package com.yuriialieksieiev.smarthome.framents;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import com.yuriialieksieiev.smarthome.R;
import com.yuriialieksieiev.smarthome.components.Action;
import com.yuriialieksieiev.smarthome.components.AlertCreateAction;
import com.yuriialieksieiev.smarthome.components.AlertDialogAction;
import com.yuriialieksieiev.smarthome.components.RcActionsAdapter;
import com.yuriialieksieiev.smarthome.components.time.Date;
import com.yuriialieksieiev.smarthome.components.time.Time;
import com.yuriialieksieiev.smarthome.utils.JsonManager;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class FragmentJobTimer extends Fragment implements FragmentCreatorTask.TakerJob,RcActionsAdapter.OnLongPressAction{

    public static final String API_EXTRA_JOB_DATE = "data";
    public static final String API_EXTRA_JOB_TIME = "time";
    public static final String API_EXTRA_JOB_ACTIONS = "actions";

    private View root;
    private RecyclerView rcActions;
    private RcActionsAdapter actionsAdapter;
    private List<Action> actions;
    private TextView tvDate;
    private TextView tvTime;

    private Date date;
    private Time time = new Time(12,0);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_job_timer, container, false);
        init();
        return root;
    }

    private void init() {
        actions = new ArrayList<>();

        tvDate = root.findViewById(R.id.tv_date);

        tvTime = root.findViewById(R.id.tv_time);

        rcActions = root.findViewById(R.id.rc_actions);
        actionsAdapter = new RcActionsAdapter(actions, this);
        rcActions.setAdapter(actionsAdapter);
        rcActions.setLayoutManager(new LinearLayoutManager(getContext()));
        rcActions.setHasFixedSize(true);

        root.findViewById(R.id.btn_add_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAction();
            }
        });

        ((Switch) root.findViewById(R.id.sw_enable_date)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    tvDate.setClickable(true);
                    tvDate.setTextColor(Color.GRAY);
                } else {
                    tvDate.setClickable(false);
                    tvDate.setTextColor(Color.RED);
                    tvDate.setText(getString(R.string.every));
                    date = null;
                }
            }
        });

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        tvDate.setClickable(false);

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });
    }

    private void showTimePicker() {
        new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time = new Time(hourOfDay,minute);
                tvTime.setText(time.toString());

            }
        },12,0,true).show();
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(Objects.requireNonNull(getContext()), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date = new Date(dayOfMonth,month,year);
                tvDate.setText(date.toString());
                tvDate.setTextColor(Color.BLACK);
            }
        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void addAction() {
        new AlertCreateAction(Objects.requireNonNull(getContext()), new AlertCreateAction.CallBack() {
            @Override
            public void applyAction(Action action) {
                actions.add(action);
            }
        },actions).show();
    }

    @Override
    public JSONObject getJob()
    {
        final JSONObject job = new JSONObject();
        try {
            job.put(API_EXTRA_JOB_TIME,time.toString());
            job.put(API_EXTRA_JOB_DATE,(date != null?date.toString():"none"));
            job.put(API_EXTRA_JOB_ACTIONS,JsonManager.getJsonArray(actions));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return job;
    }


    @Override
    public void onLongPressAction(final Action action)
    {
        new AlertDialogAction(getContext(), new AlertDialogAction.CallBack() {
            @Override
            public void edit() {
                new AlertCreateAction(Objects.requireNonNull(getContext()), new AlertCreateAction.CallBack() {
                    @Override
                    public void applyAction(Action newAction) {
                        actions.remove(action);
                        actions.add(newAction);
                        actionsAdapter.notifyDataSetChanged();
                    }
                },actions,action).show();
            }

            @Override
            public void remove() {
                actions.remove(action);
                actionsAdapter.notifyDataSetChanged();

            }
        },action.getPort()).show();
    }
}
