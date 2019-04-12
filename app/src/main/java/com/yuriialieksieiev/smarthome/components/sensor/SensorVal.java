package com.yuriialieksieiev.smarthome.components.sensor;

import com.yuriialieksieiev.smarthome.R;
import com.yuriialieksieiev.smarthome.utils.JsonManager;

import org.json.JSONException;
import org.json.JSONObject;

public class SensorVal {
    public enum Sensors {
        TEMPERATURE("temperature", R.drawable.ic_celsius),
        LIGHT("light",R.drawable.ic_day),
        HUMIDITY("humidity",R.drawable.ic_moon);

        private String inJson;
        private int res;

        Sensors(String inJson,int res) {
            this.inJson = inJson;
            this.res = res;
        }

        public int getRes() {
            return res;
        }

        public static Sensors getByName(String name) {
            switch (name) {
                case "temperature":
                    return TEMPERATURE;
                case "light":
                    return LIGHT;
                case "humidity":
                    return HUMIDITY;
                default:
                    return null;
            }

        }
    }
    private Sensors sensor;
    private int value;

    public SensorVal(Sensors sensor, int value) {
        this.sensor = sensor;
        this.value = value;
    }

    public SensorVal(JSONObject jsonObject) throws JSONException {
        sensor = Sensors.getByName(jsonObject.getString(JsonManager.JSON_EXTRA_SENSOR));
        value = jsonObject.getInt(JsonManager.JSON_EXTRA_SENSOR_VALUE);
    }

    public Sensors getSensor() {
        return sensor;
    }

    public int getValue() {
        return value;
    }
}
