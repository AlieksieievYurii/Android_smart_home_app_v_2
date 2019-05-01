package com.yuriialieksieiev.smarthome.components;

import android.os.Parcel;
import android.os.Parcelable;

import com.yuriialieksieiev.smarthome.components.enums.TaskMode;
import com.yuriialieksieiev.smarthome.components.enums.TaskStatus;
import com.yuriialieksieiev.smarthome.components.enums.TypeTask;
import com.yuriialieksieiev.smarthome.components.exceptions.TaskException;
import com.yuriialieksieiev.smarthome.components.exceptions.TaskModeException;
import com.yuriialieksieiev.smarthome.components.exceptions.TaskStatusException;
import com.yuriialieksieiev.smarthome.components.exceptions.TypeTaskException;
import com.yuriialieksieiev.smarthome.components.jobs.TimerJob;
import org.json.JSONException;
import org.json.JSONObject;

public class Task implements Parcelable
{
    private static final String API_EXTRA_ID = "id";
    private static final String API_EXTRA_TYPE_TASK = "type_task";
    private static final String API_EXTRA_NAME = "name";
    private static final String API_EXTRA_DESCRIPTION = "description";
    private static final String API_EXTRA_STATUS = "status";
    private static final String API_EXTRA_MODE = "mode";
    private static final String API_EXTRA_JOB = "job";

    private int id;
    private TypeTask typeTask;
    private String name;
    private String description;
    private TaskStatus taskStatus;
    private TaskMode taskMode;
    private Object job;

    private Task(int id,
                TypeTask typeTask,
                String name,
                String description,
                TaskStatus taskStatus,
                TaskMode taskMode,
                Object job) {
        this.id = id;
        this.typeTask = typeTask;
        this.name = name;
        this.description = description;
        this.taskStatus = taskStatus;
        this.taskMode = taskMode;
        this.job = job;
    }

    private Task(Parcel in) {

        try {
            id = in.readInt();
            typeTask = TypeTask.getTypeTaskByJson(in.readString());
            name = in.readString();
            description = in.readString();
            taskStatus = TaskStatus.getTaskStatusByJson(in.readString());
            taskMode = TaskMode.getTaskModeByJson(in.readString());
            switch (typeTask)
            {
                case timer:
                    job = in.readParcelable(TimerJob.class.getClassLoader());
                    break;
            }

        } catch (TypeTaskException | TaskStatusException | TaskModeException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public TaskMode getTaskMode() {
        return taskMode;
    }

    public TypeTask getTypeTask() {
        return typeTask;
    }

    public int getId() {
        return id;
    }

    public Object getJob() {
        return job;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(typeTask.getInJson());
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(taskStatus.getInJson());
        dest.writeString(taskMode.getInJson());

        switch (typeTask)
        {
            case timer:
                dest.writeParcelable((TimerJob)job,flags);
                break;
        }
    }

    public static Task parseTask(JSONObject jsonObject) throws TaskException {
        try
        {
            final int id = jsonObject.getInt(API_EXTRA_ID);
            final TypeTask typeTask = TypeTask.getTypeTaskByJson(jsonObject.getString(API_EXTRA_TYPE_TASK));
            final String name = jsonObject.getString(API_EXTRA_NAME);
            final String description = jsonObject.getString(API_EXTRA_DESCRIPTION);
            final TaskStatus taskStatus = TaskStatus.getTaskStatusByJson(jsonObject.getString(API_EXTRA_STATUS));
            final TaskMode taskMode = TaskMode.getTaskModeByJson(jsonObject.getString(API_EXTRA_MODE));

            Object job = null;
            switch (typeTask)
            {
                case timer:
                    job = TimerJob.parse(jsonObject.getJSONObject(API_EXTRA_JOB));
                    break;
            }

            return new Task(id,typeTask,name,description,taskStatus,taskMode,job);

        } catch (JSONException | TypeTaskException | TaskStatusException | TaskModeException e) {
            e.printStackTrace();
            throw new TaskException(e.getMessage());
        }
    }

    public static JSONObject toJson(Task task) throws JSONException {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put(API_EXTRA_ID, task.getId());
        jsonObject.put(API_EXTRA_TYPE_TASK, task.getTypeTask().getInJson());
        jsonObject.put(API_EXTRA_NAME, task.getName());
        jsonObject.put(API_EXTRA_DESCRIPTION, task.getDescription());
        jsonObject.put(API_EXTRA_STATUS, task.getTaskStatus().getInJson());
        jsonObject.put(API_EXTRA_MODE, task.getTaskMode().getInJson());
        switch (task.getTypeTask())
        {
            case timer:
                jsonObject.put(API_EXTRA_JOB, TimerJob.toJsonObject((TimerJob) task.getJob()));
                break;
        }
        return jsonObject;

    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", typeTask=" + typeTask +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", taskStatus=" + taskStatus +
                ", taskMode=" + taskMode +
                ", job=" + job +
                '}';
    }
}
