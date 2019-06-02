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
    private Action.TypePin typePin;
    private Device device;
    private String name;
    private String description;

    private Pin(int pin, Action.TypePin typePin, Device device, String name, String description) {
        this.pin = pin;
        this.typePin = typePin;
        this.device = device;
        this.name = name;
        this.description = description;
    }

    public int getPin() {
        return pin;
    }

    public Action.TypePin getTypePin() {
        return typePin;
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
        final Action.TypePin typePin = Action.TypePin.getTypePin(jsonObject.getString(API_TYPE_PORT));
        final Device device = Device.getDeviceByName(jsonObject.getString(API_DEVICE));
        final String name = jsonObject.getString(API_NAME);
        final String description = jsonObject.getString(API_DESCRIPTION);

        return new Pin(pin, typePin,device,name,description);
    }
}
