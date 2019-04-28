package com.yuriialieksieiev.smarthome.components;

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

public class Task
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

    public Task(int id,
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


}
