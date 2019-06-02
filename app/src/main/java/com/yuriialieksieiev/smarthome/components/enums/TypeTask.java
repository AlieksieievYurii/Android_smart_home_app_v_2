package com.yuriialieksieiev.smarthome.components.enums;

import com.yuriialieksieiev.smarthome.components.exceptions.TypeTaskException;

public enum TypeTask {
    timer("timer");

    private String inJson;

    TypeTask(String inJson) {
        this.inJson = inJson;
    }

    public String getInJson() {
        return inJson;
    }

    public static TypeTask getTypeTaskByJson(String inJson) throws TypeTaskException {
        for (TypeTask t : TypeTask.class.getEnumConstants())
            if (t.inJson.equals(inJson))
                return t;
        throw new TypeTaskException(inJson);
    }

}
