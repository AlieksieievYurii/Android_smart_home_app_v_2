package com.yuriialieksieiev.smarthome.components.seekbar;

import com.yuriialieksieiev.smarthome.components.Button.Action;
import com.yuriialieksieiev.smarthome.components.JsonExtras;

import org.json.JSONException;
import org.json.JSONObject;

public class PatternActionSeekBar
{
    private String name;
    private Action action;

    public PatternActionSeekBar(JSONObject jsonObject) throws JSONException {
        init(jsonObject);
    }

    public PatternActionSeekBar(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        init(jsonObject);
    }

    private void init(JSONObject jsonObject) throws JSONException {
        this.name = jsonObject.getString(JsonExtras.JSON_EXTRA_NAME);
        this.action = Action.getActionByJSon(jsonObject.getJSONObject(JsonExtras.JSON_EXTRA_ACTION));
    }

    public String getName() {
        return name;
    }

    public Action getAction() {
        return action;
    }
}
