package com.yuriialieksieiev.smarthome.components;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.yuriialieksieiev.smarthome.R;
import com.yuriialieksieiev.smarthome.components.enums.TaskStatus;

import java.util.List;

public class RcTasksAdapter extends RecyclerView.Adapter<RcTasksAdapter.TaskViewHolder>
{
    private List<Task> tasks;


    public RcTasksAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cv_task,viewGroup,false);
        return new TaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {
        Task task = tasks.get(i);

        taskViewHolder.tvName.setText(task.getName());
        taskViewHolder.tvDescription.setText(task.getDescription());
        taskViewHolder.swStatus.setChecked(task.getTaskStatus() == TaskStatus.enable);
        taskViewHolder.tvMode.setText(task.getTaskMode().getInJson());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class  TaskViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvName;
        private TextView tvDescription;
        private TextView tvMode;
        private Switch swStatus;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDescription = itemView.findViewById(R.id.tv_description);
            swStatus = itemView.findViewById(R.id.sw_status);
            tvMode = itemView.findViewById(R.id.tv_mode);
        }
    }
}
