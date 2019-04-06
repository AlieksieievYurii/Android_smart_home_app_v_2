package com.yuriialieksieiev.smarthome.components;

public enum Device
{
    TCOD("TCOD");

    private String inJson;

    Device(String inJson) {
        this.inJson = inJson;
    }

    public static Device getDeviceByName(String name)
    {
        if(name.equals(TCOD.inJson))
            return TCOD;
        else
            return null;
    }
}
