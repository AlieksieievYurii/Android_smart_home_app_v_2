package com.yuriialieksieiev.smarthome.components.enums;

import com.yuriialieksieiev.smarthome.components.exceptions.TypeTaskException;

public enum  TypeTask
{
    timer("timer");

    private String inJson;

    TypeTask(String inJson) {
        this.inJson = inJson;
    }

    public String getInJson() {
        return inJson;
    }

    public static TypeTask getTypeTaskByJson(String inJson) throws TypeTaskException {
        if(timer.inJson.equals(inJson))
            return timer;
        else
            throw new TypeTaskException(inJson);
    }

}
