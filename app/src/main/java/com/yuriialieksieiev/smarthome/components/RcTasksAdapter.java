package com.yuriialieksieiev.smarthome.components;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.yuriialieksieiev.smarthome.R;
import com.yuriialieksieiev.smarthome.components.enums.TaskStatus;

import java.util.List;

public class RcTasksAdapter extends RecyclerView.Adapter<RcTasksAdapter.TaskViewHolder> {
    public interface RcListener {
        void onLongPressTask(Task task);

        void onChangeTaskStatus(Task task);
    }

    private List<Task> tasks;
    private final RcListener rcListener;


    public RcTasksAdapter(List<Task> tasks, RcListener rcListener) {
        this.tasks = tasks;
        this.rcListener = rcListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cv_task, viewGroup, false);
        return new TaskViewHolder(v, tasks, rcListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {
        Task task = tasks.get(i);

        taskViewHolder.tvName.setText(task.getName());
        taskViewHolder.tvDescription.setText(task.getDescription());
        taskViewHolder.swStatus.setChecked(task.getTaskStatus() == TaskStatus.enable);
        taskViewHolder.tvMode.setText(task.getTaskMode().getInJson());
        taskViewHolder.tvTypeTask.setText(task.getTypeTask().getInJson());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvDescription;
        private TextView tvMode;
        private TextView tvTypeTask;
        private Switch swStatus;

        TaskViewHolder(@NonNull View itemView, final List<Task> tasks, final RcListener rcListener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDescription = itemView.findViewById(R.id.tv_description);
            swStatus = itemView.findViewById(R.id.sw_status);
            tvMode = itemView.findViewById(R.id.tv_mode);
            tvTypeTask = itemView.findViewById(R.id.tv_type_task);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    rcListener.onLongPressTask(tasks.get(getAdapterPosition()));
                    return true;
                }
            });

            swStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final Task task = tasks.get(getAdapterPosition());
                    task.setTaskStatus(isChecked?TaskStatus.enable:TaskStatus.disable);
                    rcListener.onChangeTaskStatus(task);
                }
            });
        }
    }
}
