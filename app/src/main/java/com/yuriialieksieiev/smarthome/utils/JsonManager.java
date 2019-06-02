package com.yuriialieksieiev.smarthome.utils;

import android.content.Context;

import com.yuriialieksieiev.smarthome.Factory;
import com.yuriialieksieiev.smarthome.components.Action;
import com.yuriialieksieiev.smarthome.components.Pin;
import com.yuriialieksieiev.smarthome.components.button.PatternActionButton;
import com.yuriialieksieiev.smarthome.components.enums.Device;
import com.yuriialieksieiev.smarthome.components.enums.Icons;
import com.yuriialieksieiev.smarthome.components.seekbar.PatternActionSeekBar;
import com.yuriialieksieiev.smarthome.components.sensor.SensorVal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonManager {
    public static final String JSON_EXTRA_TYPE = "type";
    public static final String JSON_EXTRA_ICON = "icon";
    public static final String JSON_EXTRA_NAME = "name";
    public static final String JSON_EXTRA_ACTION = "action";
    public static final String JSON_EXTRA_SENSOR = "sensor";
    public static final String JSON_EXTRA_SENSOR_VALUE = "value";

    public static boolean isExist(int oin, Device device, Context context) {
        try {
            JSONArray jsonArray = SharedPreferences.getActionsViewsJson(context);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (isAction(jsonObject)) {
                    Action action = Action.parseFactoryJson(jsonObject.getJSONObject(JSON_EXTRA_ACTION));

                    if (action.getPin() == oin && device == action.getDevice())
                        return true;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean isAction(JSONObject jsonObject) throws JSONException {
        return Factory.TypeView.getTypeViewByName(jsonObject.getString(JSON_EXTRA_TYPE))
                == Factory.TypeView.BUTTON ||
                Factory.TypeView.getTypeViewByName(jsonObject.getString(JSON_EXTRA_TYPE))
                        == Factory.TypeView.SEEK_BAR;
    }


    public static void addActionButton(Context context, String name, int pin, Icons icon, Device device) throws JSONException {
        Action action = new Action(device, pin, Action.PinStatus.LOW);
        PatternActionButton patternActionButton = new PatternActionButton(icon, name, action);

        JSONArray jsonArray = SharedPreferences.getActionsViewsJson(context);
        jsonArray.put(patternActionButton.toJsonObject());
        SharedPreferences.saveActionsViews(context, jsonArray);
    }

    public static void addActionSeekBar(Context context, String name, int pin, Device device) throws JSONException {
        Action action = new Action(device, pin, 0);
        PatternActionSeekBar patternActionSeekBar = new PatternActionSeekBar(name, action);

        JSONArray jsonArray = SharedPreferences.getActionsViewsJson(context);
        jsonArray.put(patternActionSeekBar.toJsonObject());
        SharedPreferences.saveActionsViews(context, jsonArray);
    }

    public static void addSensor(Context context, SensorVal sensorVal) throws JSONException {
        JSONArray jsonArray = SharedPreferences.getActionsViewsJson(context);
        jsonArray.put(sensorVal.toJsonObject());
        SharedPreferences.saveActionsViews(context, jsonArray);
    }

    public static void remove(Context context, Action action) throws JSONException {
        JSONArray jsonArray = SharedPreferences.getActionsViewsJson(context);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            if (isAction(jsonObject)) {
                Action action1 = Action.parseFactoryJson(jsonObject.getJSONObject(JSON_EXTRA_ACTION));
                if (action.equals(action1))
                    jsonArray.remove(i);
            }
        }

        SharedPreferences.saveActionsViews(context, jsonArray);
    }

    public static void remove(Context context, SensorVal sensorVal) throws JSONException {
        JSONArray jsonArray = SharedPreferences.getActionsViewsJson(context);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            if (Factory.TypeView.getTypeViewByName(jsonObject.getString(JSON_EXTRA_TYPE))
                    == Factory.TypeView.SENSOR) {
                SensorVal s = new SensorVal(jsonObject);
                if (s.equals(sensorVal))
                    jsonArray.remove(i);
            }
        }

        SharedPreferences.saveActionsViews(context, jsonArray);
    }

    public static void replaceActionButton(Context context, PatternActionButton patternActionButton, Action oldAction) throws JSONException {
        JSONArray jsonArray = SharedPreferences.getActionsViewsJson(context);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            if (Factory.TypeView.getTypeViewByName(jsonObject.getString(JSON_EXTRA_TYPE))
                    == Factory.TypeView.BUTTON) {
                PatternActionButton p = new PatternActionButton(jsonObject);

                if (p.getAction().equals(oldAction)) {
                    jsonArray.put(i, patternActionButton.toJsonObject());
                    SharedPreferences.saveActionsViews(context, jsonArray);
                    break;
                }
            }
        }
    }

    public static void replaceActionSeekBar(Context context, PatternActionSeekBar patternActionSeekBar, Action oldAction) throws JSONException {
        JSONArray jsonArray = SharedPreferences.getActionsViewsJson(context);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            if (Factory.TypeView.getTypeViewByName(jsonObject.getString(JSON_EXTRA_TYPE))
                    == Factory.TypeView.SEEK_BAR) {
                PatternActionSeekBar p = new PatternActionSeekBar(jsonObject);

                if (p.getAction().equals(oldAction)) {
                    jsonArray.put(i, patternActionSeekBar.toJsonObject());
                    SharedPreferences.saveActionsViews(context, jsonArray);
                    break;
                }
            }
        }
    }

    public static void replaceSensor(Context context, SensorVal sensorVal, SensorVal old) throws JSONException {
        JSONArray jsonArray = SharedPreferences.getActionsViewsJson(context);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            if (Factory.TypeView.getTypeViewByName(jsonObject.getString(JSON_EXTRA_TYPE))
                    == Factory.TypeView.SENSOR) {
                SensorVal s = new SensorVal(jsonObject);

                if (s.equals(old)) {
                    jsonArray.put(i, sensorVal.toJsonObject());
                    SharedPreferences.saveActionsViews(context, jsonArray);
                }
            }
        }
    }

    public static List<Action> parseActions(JSONArray jsonArray) throws JSONException {
        final List<Action> actions = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++)
            actions.add(Action.parseAPI(jsonArray.getJSONObject(i)));

        return actions;
    }

    public static JSONArray getJsonArray(List<Action> actions) {
        final JSONArray jsonArray = new JSONArray();

        try {
            for (final Action action : actions)
                jsonArray.put(Action.toAPI(action));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public static List<Pin> parsePins(JSONArray jsonArray) throws JSONException {
        final List<Pin> pins = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++)
            pins.add(Pin.parse(jsonArray.getJSONObject(i)));
        return pins;
    }

}
