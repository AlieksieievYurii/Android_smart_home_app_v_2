package com.yuriialieksieiev.smarthome;

import android.content.Context;

import com.yuriialieksieiev.smarthome.components.Button.ActionButton;
import com.yuriialieksieiev.smarthome.components.Button.OnActionButtonClick;
import com.yuriialieksieiev.smarthome.components.Button.PatternActionButton;
import com.yuriialieksieiev.smarthome.components.seekbar.ActionSeekBar;
import com.yuriialieksieiev.smarthome.components.seekbar.OnActionChangeSeekBar;
import com.yuriialieksieiev.smarthome.components.seekbar.PatternActionSeekBar;
import com.yuriialieksieiev.smarthome.utils.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.yuriialieksieiev.smarthome.components.JsonExtras.JSON_EXTRA_TYPE;

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
    }

    private OnViewCreated onViewCreated;
    private OnActionButtonClick onActionButtonClick;
    private OnActionChangeSeekBar onActionChangeSeekBar;
    private Context context;

    public Factory(Context context, OnViewCreated onViewCreated, OnActionButtonClick onActionButtonClick, OnActionChangeSeekBar onActionChangeSeekBar) {
        this.onViewCreated = onViewCreated;
        this.onActionButtonClick = onActionButtonClick;
        this.onActionChangeSeekBar = onActionChangeSeekBar;
        this.context = context;
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
                ActionSeekBar.Builder.build(context,onActionChangeSeekBar,patternActionSeekBar);

        onViewCreated.seekBarCreated(actionSeekBar);
    }

    private void buildButton(JSONObject jsonObject) throws JSONException {
        PatternActionButton patternActionButton = new PatternActionButton(jsonObject);
        ActionButton actionButton =
                ActionButton.Builder.build(context,onActionButtonClick,patternActionButton);

        onViewCreated.buttonCreated(actionButton);
    }
}
