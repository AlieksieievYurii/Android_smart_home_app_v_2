package com.yuriialieksieiev.smarthome.utils;

import android.content.Context;

public class UrlUtils
{
    private final static String TYPE_HASH_CODE_ACTIONS = "getHashCodeActions";
    private final static String TYPE_GET_ACTIONS = "getActions";
    private final static String TYPE_GET_SENSORS = "getSensors";
    private final static String TYPE_POST_ACTION = "postAction";
    private final static String TYPE_POST_TASK = "postTask";
    private final static String TYPE_GET_FREE_TASK_ID = "getFreeTaskId";
    private final static String MODULE_LISTENER_TASKS = "listenerTasks";

    public static String getUrlForHashCode(Context context)
    {
        String url = SharedPreferences.getUrlToServer(context);
        String nameModule = SharedPreferences.getServerName(context);
        String password = SharedPreferences.getPasswordServer(context);

        return url +
                "/" +
                nameModule +
                "/" +
                MODULE_LISTENER_TASKS +
                "?p=" +
                password +
                "&type=" +
                TYPE_HASH_CODE_ACTIONS;
    }

    public static String getUrlForGetActions(Context context)
    {
        String url = SharedPreferences.getUrlToServer(context);
        String nameModule = SharedPreferences.getServerName(context);
        String password = SharedPreferences.getPasswordServer(context);

        return url +
                "/" +
                nameModule +
                "/" +
                MODULE_LISTENER_TASKS +
                "?p=" +
                password +
                "&type=" +
                TYPE_GET_ACTIONS;
    }

    public static String getUrlForGetSensors(Context context)
    {
        String url = SharedPreferences.getUrlToServer(context);
        String nameModule = SharedPreferences.getServerName(context);
        String password = SharedPreferences.getPasswordServer(context);

        return url +
                "/" +
                nameModule +
                "/" +
                MODULE_LISTENER_TASKS +
                "?p=" +
                password +
                "&type=" +
                TYPE_GET_SENSORS;
    }

    public static String getUrlForPostAction(Context context)
    {
        String url = SharedPreferences.getUrlToServer(context);
        String nameModule = SharedPreferences.getServerName(context);
        String password = SharedPreferences.getPasswordServer(context);

        return url +
                "/" +
                nameModule +
                "/" +
                MODULE_LISTENER_TASKS +
                "?p=" +
                password +
                "&type=" +
                TYPE_POST_ACTION;
    }

    public static String getUrlForGettingFreeId(Context context)
    {
        String url = SharedPreferences.getUrlToServer(context);
        String nameModule = SharedPreferences.getServerName(context);
        String password = SharedPreferences.getPasswordServer(context);

        return url +
                "/" +
                nameModule +
                "/" +
                MODULE_LISTENER_TASKS +
                "?p=" +
                password +
                "&type=" +
                TYPE_GET_FREE_TASK_ID;
    }

    public static String getUrlForPostTask(Context context)
    {
        final String url = SharedPreferences.getUrlToServer(context);
        String nameModule = SharedPreferences.getServerName(context);
        String password = SharedPreferences.getPasswordServer(context);

        return url +
                "/" +
                nameModule +
                "/" +
                MODULE_LISTENER_TASKS +
                "?p=" +
                password +
                "&type=" +
                TYPE_POST_TASK;
    }
}
