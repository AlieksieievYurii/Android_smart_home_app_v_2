package com.yuriialieksieiev.smarthome.components;

import com.yuriialieksieiev.smarthome.enums.Icons;

import org.json.JSONException;
import org.json.JSONObject;

public class PatternActionButton
{
    private static final String JSON_EXTRA_ICON = "icon";
    private static final String JSON_EXTRA_NAME = "name";
    private static final String JSON_EXTRA_ACTION = "action";

    private Icons icon;
    private String name;
    private Action action;

    public PatternActionButton(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);

        this.icon = Icons.getEnumByName(jsonObject.getString(JSON_EXTRA_ICON));
        this.name = jsonObject.getString(JSON_EXTRA_NAME);
        this.action = Action.getActionByJSon(jsonObject.getJSONObject(JSON_EXTRA_ACTION));
    }

    public PatternActionButton(Icons icon, String name, Action action) {
        this.icon = icon;
        this.name = name;
        this.action = action;
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
