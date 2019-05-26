package com.yuriialieksieiev.smarthome.components;

import com.yuriialieksieiev.smarthome.components.enums.Device;

import org.json.JSONException;
import org.json.JSONObject;

public class Pin
{
    private static final String API_PIN = "pin";
    private static final String API_TYPE_PORT = "type_port";
    private static final String API_DEVICE = "device";
    private static final String API_NAME = "name";
    private static final String API_DESCRIPTION = "description";

    private int pin;
    private Action.TypePort typePort;
    private Device device;
    private String name;
    private String description;

    public Pin(int pin, Action.TypePort typePort, Device device, String name, String description) {
        this.pin = pin;
        this.typePort = typePort;
        this.device = device;
        this.name = name;
        this.description = description;
    }

    public int getPin() {
        return pin;
    }

    public Action.TypePort getTypePort() {
        return typePort;
    }

    public Device getDevice() {
        return device;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static Pin parse(JSONObject jsonObject) throws JSONException {
        final int pin = jsonObject.getInt(API_PIN);
        final Action.TypePort typePort = Action.TypePort.getTypePort(jsonObject.getString(API_TYPE_PORT));
        final Device device = Device.getDeviceByName(jsonObject.getString(API_DEVICE));
        final String name = jsonObject.getString(API_NAME);
        final String description = jsonObject.getString(API_DESCRIPTION);

        return new Pin(pin,typePort,device,name,description);
    }
}
