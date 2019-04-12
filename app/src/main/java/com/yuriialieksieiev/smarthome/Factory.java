package com.yuriialieksieiev.smarthome;

import android.content.Context;
import com.yuriialieksieiev.smarthome.components.Button.ActionButton;
import com.yuriialieksieiev.smarthome.components.Button.PatternActionButton;
import com.yuriialieksieiev.smarthome.components.OnAction;
import com.yuriialieksieiev.smarthome.components.OnLongPressAction;
import com.yuriialieksieiev.smarthome.components.seekbar.ActionSeekBar;
import com.yuriialieksieiev.smarthome.components.seekbar.PatternActionSeekBar;
import com.yuriialieksieiev.smarthome.utils.SharedPreferences;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.yuriialieksieiev.smarthome.utils.JsonManager.JSON_EXTRA_TYPE;

public class Factory {
    public interface OnViewCreated {
        void buttonCreated(ActionButton actionButton);
        void seekBarCreated(ActionSeekBar actionSeekBar);
        void buildingFinished();
    }

    public enum TypeView {
        BUTTON("button"), SEEK_BAR("seek_bar");

        private String inJson;

        TypeView(String inJson) {
            this.inJson = inJson;
        }

        public static TypeView getTypeViewByName(String name) {
            if (name.equals(BUTTON.inJson))
                return BUTTON;
            else if (name.equals(SEEK_BAR.inJson))
                return SEEK_BAR;
            else
                return null;
        }

        public String getInJson() {
            return inJson;
        }
    }

    private OnViewCreated onViewCreated;
    private OnAction onAction;
    private Context context;
    private OnLongPressAction onLongPressAction;

    public Factory(Context context,
                   OnViewCreated onViewCreated,
                   OnAction onAction,
                   OnLongPressAction onLongPressAction) {
        this.onViewCreated = onViewCreated;
        this.onAction = onAction;
        this.context = context;
        this.onLongPressAction = onLongPressAction;
    }

    public void build() throws Exception {
        JSONArray jsonArray = new JSONArray(SharedPreferences.getActionsViewsJson(context));

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            TypeView typeView =
                    TypeView.getTypeViewByName(
                            jsonObject.getString(JSON_EXTRA_TYPE));

            if (typeView == TypeView.BUTTON)
                buildButton(jsonObject);
            else if (typeView == TypeView.SEEK_BAR)
                buildSeekBar(jsonObject);
        }
        onViewCreated.buildingFinished();
    }

    private void buildSeekBar(JSONObject jsonObject) throws JSONException {
        PatternActionSeekBar patternActionSeekBar = new PatternActionSeekBar(jsonObject);

        ActionSeekBar actionSeekBar =
                ActionSeekBar.Builder.build(context,
                        onAction,
                        patternActionSeekBar,
                        onLongPressAction);

        actionSeekBar.setName(patternActionSeekBar.getName());

        onViewCreated.seekBarCreated(actionSeekBar);
    }

    private void buildButton(JSONObject jsonObject) throws JSONException {

        PatternActionButton patternActionButton = new PatternActionButton(jsonObject);

        ActionButton actionButton =
                ActionButton.Builder.build(context,
                        onAction,
                        patternActionButton,
                        onLongPressAction);

        actionButton.setIcons(patternActionButton.getIcon());
        actionButton.setName(patternActionButton.getName());

        onViewCreated.buttonCreated(actionButton);
    }
}
