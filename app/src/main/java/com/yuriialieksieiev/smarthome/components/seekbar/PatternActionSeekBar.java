package com.yuriialieksieiev.smarthome.components.seekbar;

import android.os.Parcel;
import android.os.Parcelable;

import com.yuriialieksieiev.smarthome.Factory;
import com.yuriialieksieiev.smarthome.components.Action;
import com.yuriialieksieiev.smarthome.utils.JsonManager;

import org.json.JSONException;
import org.json.JSONObject;

public class PatternActionSeekBar implements Parcelable
{
    private String name;
    private Action action;

    public PatternActionSeekBar(String name, Action action) {
        this.name = name;
        this.action = action;
    }

    public PatternActionSeekBar(JSONObject jsonObject) throws JSONException {
        this.name = jsonObject.getString(JsonManager.JSON_EXTRA_NAME);
        this.action = Action.parseFactoryJson(jsonObject.getJSONObject(JsonManager.JSON_EXTRA_ACTION));
    }

    private PatternActionSeekBar(Parcel in) {
        name = in.readString();
        action = in.readParcelable(Action.class.getClassLoader());
    }

    public static final Creator<PatternActionSeekBar> CREATOR = new Creator<PatternActionSeekBar>() {
        @Override
        public PatternActionSeekBar createFromParcel(Parcel in) {
            return new PatternActionSeekBar(in);
        }

        @Override
        public PatternActionSeekBar[] newArray(int size) {
            return new PatternActionSeekBar[size];
        }
    };

    public JSONObject toJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JsonManager.JSON_EXTRA_TYPE, Factory.TypeView.SEEK_BAR.getInJson());
        jsonObject.put(JsonManager.JSON_EXTRA_NAME,name);
        jsonObject.put(JsonManager.JSON_EXTRA_ACTION,Action.toFactoryJson(action));
        return jsonObject;
    }

    public String getName() {
        return name;
    }

    public Action getAction() {
        return action;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeParcelable(action, flags);
    }
}
