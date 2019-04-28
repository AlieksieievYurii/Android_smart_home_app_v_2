package com.yuriialieksieiev.smarthome.components.jobs;

import com.yuriialieksieiev.smarthome.components.Action;
import com.yuriialieksieiev.smarthome.components.time.Date;
import com.yuriialieksieiev.smarthome.components.time.Time;
import com.yuriialieksieiev.smarthome.utils.JsonManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class TimerJob
{
    private static final String API_EXTRA_DATE = "date";
    private static final String API_EXTRA_TIME = "time";
    private static final String API_EXTRA_ACTIONS = "actions";

    private Date date;
    private Time time;
    private List<Action> actions;

    public TimerJob(Date date, Time time, List<Action> actions) {
        this.date = date;
        this.time = time;
        this.actions = actions;
    }

    public static TimerJob parse(JSONObject jsonObject) throws JSONException {
        final Date date = Date.parse(jsonObject.getString(API_EXTRA_DATE));
        final Time time = Time.parse(jsonObject.getString(API_EXTRA_TIME));
        final List<Action> actions = JsonManager.parseActions(jsonObject.getJSONArray(API_EXTRA_ACTIONS));

        return new TimerJob(date,time,actions);
    }
}
