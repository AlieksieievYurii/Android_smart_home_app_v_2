package com.yuriialieksieiev.smarthome.components.Button;

import com.yuriialieksieiev.smarthome.enums.Icons;

import org.json.JSONException;
import org.json.JSONObject;

import static com.yuriialieksieiev.smarthome.components.JsonExtras.*;

public class PatternActionButton
{
    private Icons icon;
    private String name;
    private Action action;

    public PatternActionButton(JSONObject jsonObject) throws JSONException {
        init(jsonObject);
    }

    private void init(JSONObject jsonObject) throws JSONException {
        this.icon = Icons.getEnumByName(jsonObject.getString(JSON_EXTRA_ICON));
        this.name = jsonObject.getString(JSON_EXTRA_NAME);
        this.action = Action.getActionByJSon(jsonObject.getJSONObject(JSON_EXTRA_ACTION));
    }

    public PatternActionButton(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        init(jsonObject);
    }

    public PatternActionButton(Icons icon, String name, Action action) {
        this.icon = icon;
        this.name = name;
        this.action = action;
    }

    public Icons getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public Action getAction() {
        return action;
    }

    public JSONObject toJsonObject() throws JSONException {
        JSONObject pattern = new JSONObject();
        pattern.put(JSON_EXTRA_ICON,icon.getNameIcon());
        pattern.put(JSON_EXTRA_NAME,name);
        pattern.put(JSON_EXTRA_ACTION,action.toJson().toString());
        return pattern;
    }

    public boolean equals(PatternActionButton patternActionButton)
    {
        return patternActionButton.name.equals(this.name) &&
                patternActionButton.icon == this.icon &&
                patternActionButton.action.equals(this.action);
    }


    /*

    [

        {
            "type":"button"
            "icon":"lamp",
            "name":"lamp_one",
            "action": {
                        "type_port":"DIGITAL",
                        "port":23,
                        "port_status":"HIGH"
                      }
        }

    ]



     */
}
