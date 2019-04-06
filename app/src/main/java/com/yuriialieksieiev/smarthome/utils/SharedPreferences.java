package com.yuriialieksieiev.smarthome.utils;

import android.content.Context;
import android.preference.PreferenceManager;

import org.json.JSONArray;

public class SharedPreferences
{
    private static final String ACTIONS_VIEWS_JSON = "com.yuiialieksieiev.smarthome.actions_views_json";

    public static void saveActionsViews(Context context, JSONArray actionsViews)
    {
        final android.content.SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACTIONS_VIEWS_JSON,actionsViews.toString());
        editor.apply();
    }

    public static String getActionsViewsJson(Context context)
    {
        final android.content.SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getString(ACTIONS_VIEWS_JSON,null);
    }

}
