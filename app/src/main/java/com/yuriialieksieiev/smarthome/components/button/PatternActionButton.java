package com.yuriialieksieiev.smarthome.components.Button;

import android.os.Parcel;
import android.os.Parcelable;
import com.yuriialieksieiev.smarthome.Factory;
import com.yuriialieksieiev.smarthome.components.enums.Icons;
import org.json.JSONException;
import org.json.JSONObject;

import static com.yuriialieksieiev.smarthome.utils.JsonManager.*;

public class PatternActionButton implements Parcelable
{
    private Icons icon;
    private String name;
    private Action action;

    public PatternActionButton(JSONObject jsonObject) throws JSONException {
        init(jsonObject);
    }

    private PatternActionButton(Parcel in) {
        name = in.readString();
        action = in.readParcelable(Action.class.getClassLoader());
    }

    public static final Creator<PatternActionButton> CREATOR = new Creator<PatternActionButton>() {
        @Override
        public PatternActionButton createFromParcel(Parcel in) {
            return new PatternActionButton(in);
        }

        @Override
        public PatternActionButton[] newArray(int size) {
            return new PatternActionButton[size];
        }
    };

    private void init(JSONObject jsonObject) throws JSONException {
        this.icon = Icons.getEnumByName(jsonObject.getString(JSON_EXTRA_ICON));
        this.name = jsonObject.getString(JSON_EXTRA_NAME);
        this.action = Action.getActionByJSon(jsonObject.getJSONObject(JSON_EXTRA_ACTION));
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
        pattern.put(JSON_EXTRA_TYPE, Factory.TypeView.BUTTON.getInJson());
        pattern.put(JSON_EXTRA_ICON,icon.getNameIcon());
        pattern.put(JSON_EXTRA_NAME,name);
        pattern.put(JSON_EXTRA_ACTION,action.toJson());
        return pattern;
    }

    public boolean equals(PatternActionButton patternActionButton)
    {
        return patternActionButton.name.equals(this.name) &&
                patternActionButton.icon == this.icon &&
                patternActionButton.action.equals(this.action);
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
