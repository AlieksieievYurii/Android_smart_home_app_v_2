package com.yuriialieksieiev.smarthome.utils;

import android.content.Context;

import com.yuriialieksieiev.smarthome.Factory;
import com.yuriialieksieiev.smarthome.components.button.Action;
import com.yuriialieksieiev.smarthome.components.button.PatternActionButton;
import com.yuriialieksieiev.smarthome.components.Device;
import com.yuriialieksieiev.smarthome.components.enums.Icons;
import com.yuriialieksieiev.smarthome.components.seekbar.PatternActionSeekBar;
import com.yuriialieksieiev.smarthome.components.sensor.SensorVal;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonManager
{
    public static final String JSON_EXTRA_TYPE = "type";
    public static final String JSON_EXTRA_ICON = "icon";
    public static final String JSON_EXTRA_NAME = "name";
    public static final String JSON_EXTRA_ACTION = "action";
    public static final String JSON_EXTRA_SENSORS = "sensors";
    public static final String JSON_EXTRA_SENSOR = "sensor";
    public static final String JSON_EXTRA_SENSOR_VALUE = "value";

    public static boolean isExist(int port,Device device ,Context context)
    {
        try {
            JSONArray jsonArray = SharedPreferences.getActionsViewsJson(context);

            for(int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Action action = Action.getActionByJSon(jsonObject.getJSONObject(JSON_EXTRA_ACTION));

                if(action.getPort() == port && device == action.getDevice())
                    return true;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void addActionButton(Context context,String name, int port, Icons icon, Device device) throws JSONException {
        Action action = new Action(device,port, Action.PortStatus.LOW);
        PatternActionButton patternActionButton = new PatternActionButton(icon,name,action);

        JSONArray jsonArray = SharedPreferences.getActionsViewsJson(context);
        jsonArray.put(patternActionButton.toJsonObject());
        SharedPreferences.saveActionsViews(context,jsonArray);
    }

    public static void addActionSeekBar(Context context,String name, int port,Device device) throws JSONException {
        Action action = new Action(device,port,0);
        PatternActionSeekBar patternActionSeekBar = new PatternActionSeekBar(name,action);

        JSONArray jsonArray = SharedPreferences.getActionsViewsJson(context);
        jsonArray.put(patternActionSeekBar.toJsonObject());
        SharedPreferences.saveActionsViews(context,jsonArray);
    }

    public static void addSensor(Context context, SensorVal sensorVal) throws JSONException {
        JSONArray jsonArray = SharedPreferences.getActionsViewsJson(context);
        jsonArray.put(sensorVal.toJsonObject());
        SharedPreferences.saveActionsViews(context,jsonArray);
    }

    public static void remove(Context context,Action action) throws JSONException {
        JSONArray jsonArray = SharedPreferences.getActionsViewsJson(context);

        for(int i = 0; i < jsonArray.length(); i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            if(Factory.TypeView.getTypeViewByName(jsonObject.getString(JSON_EXTRA_TYPE))
                    == Factory.TypeView.BUTTON ||
                    Factory.TypeView.getTypeViewByName(jsonObject.getString(JSON_EXTRA_TYPE))
                            == Factory.TypeView.SEEK_BAR) {

                Action action1 = Action.getActionByJSon(jsonObject.getJSONObject(JSON_EXTRA_ACTION));
                if (action.equals(action1))
                    jsonArray.remove(i);
            }
        }

        SharedPreferences.saveActionsViews(context,jsonArray);
    }

    public static void remove(Context context,SensorVal sensorVal) throws JSONException {
        JSONArray jsonArray = SharedPreferences.getActionsViewsJson(context);

        for(int i = 0; i < jsonArray.length(); i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            if(Factory.TypeView.getTypeViewByName(jsonObject.getString(JSON_EXTRA_TYPE))
                    == Factory.TypeView.SENSOR)
            {
                SensorVal s = new SensorVal(jsonObject);
                if (s.equals(sensorVal))
                    jsonArray.remove(i);
            }
        }

        SharedPreferences.saveActionsViews(context,jsonArray);
    }

    public static void replaceActionButton(Context context, PatternActionButton patternActionButton, Action oldAction) throws JSONException {
        JSONArray jsonArray = SharedPreferences.getActionsViewsJson(context);

        for(int i = 0; i < jsonArray.length(); i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            if(Factory.TypeView.getTypeViewByName(jsonObject.getString(JSON_EXTRA_TYPE))
                    == Factory.TypeView.BUTTON)
            {
                PatternActionButton p = new PatternActionButton(jsonObject);

                if(p.getAction().equals(oldAction))
                {
                    jsonArray.put(i, patternActionButton.toJsonObject());
                    SharedPreferences.saveActionsViews(context,jsonArray);
                    break;
                }
            }
        }
    }

    public static void replaceActionSeekBar(Context context, PatternActionSeekBar patternActionSeekBar, Action oldAction) throws JSONException {
        JSONArray jsonArray = SharedPreferences.getActionsViewsJson(context);

        for(int i = 0; i < jsonArray.length(); i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            if(Factory.TypeView.getTypeViewByName(jsonObject.getString(JSON_EXTRA_TYPE))
                    == Factory.TypeView.SEEK_BAR)
            {
                PatternActionSeekBar p = new PatternActionSeekBar(jsonObject);

                if(p.getAction().equals(oldAction))
                {
                    jsonArray.put(i, patternActionSeekBar.toJsonObject());
                    SharedPreferences.saveActionsViews(context,jsonArray);
                    break;
                }
            }
        }
    }

    public static void replaceSensor(Context context, SensorVal sensorVal,SensorVal old) throws JSONException {
        JSONArray jsonArray = SharedPreferences.getActionsViewsJson(context);

        for(int i = 0; i < jsonArray.length(); i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            if(Factory.TypeView.getTypeViewByName(jsonObject.getString(JSON_EXTRA_TYPE))
                    == Factory.TypeView.SENSOR)
            {
                SensorVal s = new SensorVal(jsonObject);

                if(s.equals(old))
                {
                    jsonArray.put(i,sensorVal.toJsonObject());
                    SharedPreferences.saveActionsViews(context,jsonArray);
                }
            }
        }
    }

}
