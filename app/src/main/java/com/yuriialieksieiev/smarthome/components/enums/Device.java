package com.yuriialieksieiev.smarthome.components.enums;

public enum Device
{
    TCOD("tcod"),TWCOD("twcod");

    private String inJson;

    Device(String inJson) {
        this.inJson = inJson;
    }

    public static Device getDeviceByName(String name)
    {

        for(Device d : Device.class.getEnumConstants())
            if(d.inJson.equals(name))
                return d;
        return null;
    }

    public String getInJson() {
        return inJson;
    }
}
