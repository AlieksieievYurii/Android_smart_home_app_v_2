package com.yuriialieksieiev.smarthome.components.seekbar;

import com.yuriialieksieiev.smarthome.Factory;
import com.yuriialieksieiev.smarthome.components.Button.Action;
import com.yuriialieksieiev.smarthome.utils.JsonManager;

import org.json.JSONException;
import org.json.JSONObject;

public class PatternActionSeekBar
{
    private String name;
    private Action action;

    public PatternActionSeekBar(String name, Action action) {
        this.name = name;
        this.action = action;
    }

    public PatternActionSeekBar(JSONObject jsonObject) throws JSONException {
        init(jsonObject);
    }

    public PatternActionSeekBar(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        init(jsonObject);
    }

    public JSONObject toJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JsonManager.JSON_EXTRA_TYPE, Factory.TypeView.SEEK_BAR.getInJson());
        jsonObject.put(JsonManager.JSON_EXTRA_NAME,name);
        jsonObject.put(JsonManager.JSON_EXTRA_ACTION,action.toJson());
        return jsonObject;
    }

    private void init(JSONObject jsonObject) throws JSONException {
        this.name = jsonObject.getString(JsonManager.JSON_EXTRA_NAME);
        this.action = Action.getActionByJSon(jsonObject.getJSONObject(JsonManager.JSON_EXTRA_ACTION));
    }

    public String getName() {
        return name;
    }

    public Action getAction() {
        return action;
    }
}
