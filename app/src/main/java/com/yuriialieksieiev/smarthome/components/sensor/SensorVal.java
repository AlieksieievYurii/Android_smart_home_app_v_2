package com.yuriialieksieiev.smarthome.components.sensor;

import android.os.Parcel;
import android.os.Parcelable;
import com.yuriialieksieiev.smarthome.Factory;
import com.yuriialieksieiev.smarthome.R;
import com.yuriialieksieiev.smarthome.utils.JsonManager;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Objects;

public class SensorVal implements Parcelable {

    private final static String API_EXTRA_SENSOR = "sensor";
    private final static String API_EXTRA_VALUE = "value";

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

        public String getInJson() {
            return inJson;
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



    int getValue() {
        return value;
    }

    public boolean equals(SensorVal sensorVal) {
        return sensorVal.sensor == this.sensor;
    }

    public JSONObject toJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JsonManager.JSON_EXTRA_TYPE,Factory.TypeView.SENSOR.getInJson());
        jsonObject.put(JsonManager.JSON_EXTRA_SENSOR,sensor.inJson);
        jsonObject.put(JsonManager.JSON_EXTRA_SENSOR_VALUE,value);

        return jsonObject;
    }


    private SensorVal(Parcel in) {
        value = in.readInt();
        sensor = Sensors.getByName(Objects.requireNonNull(in.readString()));
    }

    public static final Creator<SensorVal> CREATOR = new Creator<SensorVal>() {
        @Override
        public SensorVal createFromParcel(Parcel in) {
            return new SensorVal(in);
        }

        @Override
        public SensorVal[] newArray(int size) {
            return new SensorVal[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(value);
        dest.writeString(sensor.inJson);
    }

    public static SensorVal fromAPI(JSONObject jsonObject) throws JSONException {
        Sensors sensors = Sensors.getByName(jsonObject.getString(API_EXTRA_SENSOR));
        int val = jsonObject.getInt(API_EXTRA_VALUE);

        return new SensorVal(sensors,val);
    }

}
