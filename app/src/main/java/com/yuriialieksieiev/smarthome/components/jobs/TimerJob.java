package com.yuriialieksieiev.smarthome.components.jobs;

import android.os.Parcel;
import android.os.Parcelable;
import com.yuriialieksieiev.smarthome.components.Action;
import com.yuriialieksieiev.smarthome.components.time.Date;
import com.yuriialieksieiev.smarthome.components.time.Time;
import com.yuriialieksieiev.smarthome.utils.JsonManager;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

public class TimerJob implements Parcelable {
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

    private TimerJob(Parcel in) {
        date = in.readParcelable(Date.class.getClassLoader());
        time = in.readParcelable(Time.class.getClassLoader());
        actions = in.createTypedArrayList(Action.CREATOR);
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public List<Action> getActions() {
        return actions;
    }


    public static final Creator<TimerJob> CREATOR = new Creator<TimerJob>() {
        @Override
        public TimerJob createFromParcel(Parcel in) {
            return new TimerJob(in);
        }

        @Override
        public TimerJob[] newArray(int size) {
            return new TimerJob[size];
        }
    };

    public static TimerJob parse(JSONObject jsonObject) throws JSONException {
        final Date date = Date.parse(jsonObject.getString(API_EXTRA_DATE));
        final Time time = Time.parse(jsonObject.getString(API_EXTRA_TIME));
        final List<Action> actions = JsonManager.parseActions(jsonObject.getJSONArray(API_EXTRA_ACTIONS));

        return new TimerJob(date, time, actions);
    }

    public static JSONObject toJsonObject(TimerJob timerJob) throws JSONException {
        final JSONObject job = new JSONObject();
        job.put(API_EXTRA_TIME, timerJob.time.toString());
        job.put(API_EXTRA_DATE, (timerJob.date != null ? timerJob.date.toString() : Date.NONE));
        job.put(API_EXTRA_ACTIONS, JsonManager.getJsonArray(timerJob.actions));

        return job;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {

        dest.writeParcelable(date, flags);
        dest.writeParcelable(time, flags);
        dest.writeTypedList(actions);
    }
}
