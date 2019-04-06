package com.yuriialieksieiev.smarthome.components;

import android.content.Context;
import android.util.Log;

import com.yuriialieksieiev.smarthome.components.Button.Action;
import com.yuriialieksieiev.smarthome.utils.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonExtras
{
    public static final String JSON_EXTRA_TYPE = "type";
    public static final String JSON_EXTRA_ICON = "icon";
    public static final String JSON_EXTRA_NAME = "name";
    public static final String JSON_EXTRA_ACTION = "action";

    public static boolean isExist(int port, Context context)
    {
        try {
            JSONArray jsonArray = new JSONArray(SharedPreferences.getActionsViewsJson(context));

            for(int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Action action = Action.getActionByJSon(jsonObject.getJSONObject(JSON_EXTRA_ACTION));
                if(action.getPort() == port)
                {

                    return true;
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
