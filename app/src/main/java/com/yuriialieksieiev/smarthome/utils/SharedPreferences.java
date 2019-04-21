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

    public static JSONArray getActionsViewsJson(Context context)
    {
        final android.content.SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        try {
            return new JSONArray(sharedPreferences.getString(ACTIONS_VIEWS_JSON,null));
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    private static final String  URL_SERVER = "com.yuiialieksieiev.smarthome.url_to_server";

    public static void saveUrlToServer(Context context, String url)
    {
        final android.content.SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(URL_SERVER,url);
        editor.apply();
    }

    public static String getUrlToServer(Context context)
    {
        final android.content.SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getString(URL_SERVER,null);
    }

    private static final String NAME_MODULE = "com.yuiialieksieiev.smarthome.name_module";

    public static void saveModuleName(Context context, String moduleName)
    {
        final android.content.SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NAME_MODULE,moduleName);
        editor.apply();
    }

    public static String getServerName(Context context)
    {
        final android.content.SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getString(NAME_MODULE,null);
    }

    private static final String PASSWORD_SERVER = "com.yuiialieksieiev.smarthome.password_server";

    public static void savePasswordToServer(Context context, String password)
    {
        final android.content.SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PASSWORD_SERVER,password);
        editor.apply();
    }

    public static String getPasswordServer(Context context)
    {
        final android.content.SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getString(PASSWORD_SERVER,null);
    }

    private static final String WIDTH_VIEWS = "com.yuiialieksieiev.smarthome.width_views";

    public static void saveWidthViews(Context context, int width)
    {
        final android.content.SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(WIDTH_VIEWS,width);
        editor.apply();
    }

    public static int getWidthViews(Context context)
    {
        final android.content.SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getInt(WIDTH_VIEWS,240);
    }

    private static final String HEIGHT_VIEWS = "com.yuiialieksieiev.smarthome.height_views";

    public static void saveHeightViews(Context context, int height)
    {
        final android.content.SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(HEIGHT_VIEWS,height);
        editor.apply();
    }

    public static int getHeightViews(Context context)
    {
        final android.content.SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getInt(HEIGHT_VIEWS,240);
    }

    private static final String VIBRATION = "com.yuiialieksieiev.smarthome.vibration";

    public static void saveModeVibration(Context context, boolean v)
    {
        final android.content.SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(VIBRATION,v);
        editor.apply();
    }

    public static boolean isVibrated(Context context)
    {
        final android.content.SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getBoolean(VIBRATION,false);
    }
}
