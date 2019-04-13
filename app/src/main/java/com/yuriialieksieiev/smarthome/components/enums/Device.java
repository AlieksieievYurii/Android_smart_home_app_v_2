package com.yuriialieksieiev.smarthome.components.enums;

public enum Device
{
    TCOD("TCOD"),TWCOD("TWCOD");

    private String inJson;

    Device(String inJson) {
        this.inJson = inJson;
    }

    public static Device getDeviceByName(String name)
    {
        if(name.equals(TCOD.inJson))
            return TCOD;
        else if(name.equals(TWCOD.inJson))
            return TWCOD;
        else
            return null;
    }

    public String getInJson() {
        return inJson;
    }
}
