package com.yuriialieksieiev.smarthome.components.enums;

import com.yuriialieksieiev.smarthome.components.exceptions.TaskModeException;

public enum TaskMode {
    once("once"), always("always");

    private String inJson;

    TaskMode(String inJson) {
        this.inJson = inJson;
    }

    public String getInJson() {
        return inJson;
    }

    public static TaskMode getTaskModeByJson(String json) throws TaskModeException {
        if (once.inJson.equals(json))
            return once;
        else if (always.inJson.equals(json))
            return always;
        else
            throw new TaskModeException(json);
    }
}
