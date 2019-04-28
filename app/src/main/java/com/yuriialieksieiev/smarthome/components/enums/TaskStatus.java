package com.yuriialieksieiev.smarthome.components.enums;

import com.yuriialieksieiev.smarthome.components.exceptions.TaskStatusException;

public enum TaskStatus
{
    enable("enable"),disable("disable");

    private String inJson;

    TaskStatus(String inJson) {
        this.inJson = inJson;
    }

    public String getInJson() {
        return inJson;
    }

    public static TaskStatus getTaskStatusByJson(String json) throws TaskStatusException {
        if(enable.inJson.equals(json))
            return enable;
        else if(disable.inJson.equals(json))
            return disable;
        else
            throw new TaskStatusException(json);
    }
}
