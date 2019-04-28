package com.yuriialieksieiev.smarthome.framents;

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

import com.yuriialieksieiev.smarthome.R;
import com.yuriialieksieiev.smarthome.activity.MakerView;
import com.yuriialieksieiev.smarthome.components.Action;
import com.yuriialieksieiev.smarthome.components.RcTasksAdapter;
import com.yuriialieksieiev.smarthome.components.Task;
import com.yuriialieksieiev.smarthome.components.enums.Device;
import com.yuriialieksieiev.smarthome.components.enums.TaskMode;
import com.yuriialieksieiev.smarthome.components.enums.TaskStatus;
import com.yuriialieksieiev.smarthome.components.enums.TypeTask;
import com.yuriialieksieiev.smarthome.components.jobs.TimerJob;
import com.yuriialieksieiev.smarthome.components.time.Date;
import com.yuriialieksieiev.smarthome.components.time.Time;

import java.util.ArrayList;
import java.util.List;

public class FragmentTasks extends Fragment
{
    private RecyclerView rcTasks;
    private RecyclerView.Adapter rcAdapter;
    private RecyclerView.LayoutManager rcLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_tasks,container,false);
        init(view);
        return view;
    }

    private void init(View view)
    {
        List<Task> tasks = getTestsTasks();
        rcTasks = view.findViewById(R.id.rcv_tasks);
        rcTasks.setHasFixedSize(true);
        rcLayoutManager = new LinearLayoutManager(getContext());
        rcAdapter = new RcTasksAdapter(tasks);

        rcTasks.setLayoutManager(rcLayoutManager);
        rcTasks.setAdapter(rcAdapter);

        view.findViewById(R.id.btn_add_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });
    }

    private List<Task> getTestsTasks()
    {
        List<Task> tasks = new ArrayList<>();

        List<Action> actions = new ArrayList<>();
        actions.add(new Action(Device.TCOD,1, Action.PortStatus.HIGH));
        actions.add(new Action(Device.TCOD,2, 255));
        actions.add(new Action(Device.TCOD,3, Action.PortStatus.LOW));

        TimerJob timerJob = new TimerJob(Date.parse("12.04.2018"), Time.parse("12:00"),actions);
        Task t1 = new Task(1, TypeTask.timer,"test_one","kefe", TaskStatus.enable, TaskMode.always,timerJob);
        Task t2 = new Task(2, TypeTask.timer,"tesretewt254t3tw_two","kfwefek", TaskStatus.disable, TaskMode.always,timerJob);
        Task t3 = new Task(3, TypeTask.timer,"erefefefe","teswerewtrewtretertretertrety54ew6t254tyrtfbvwreg4gwergwertyrewtyqertwfde_two", TaskStatus.disable, TaskMode.once,timerJob);

        tasks.add(t1);
        tasks.add(t2);
        tasks.add(t3);
        tasks.add(t3);
        tasks.add(t3);
        tasks.add(t3);
        tasks.add(t3);
        tasks.add(t3);
        tasks.add(t3);
        tasks.add(t3);
        tasks.add(t3);
        tasks.add(t3);
        tasks.add(t3);
        tasks.add(t3);
        tasks.add(t3);
        tasks.add(t3);
        tasks.add(t3);

        return tasks;
    }

    public void addTask()
    {
        final Intent intent = new Intent(getContext(), MakerView.class);
        intent.putExtra(MakerView.EXTRA_WHAT_VIEW,MakerView.EXTRA_TASK);

        startActivity(intent);
    }
}
