package com.yuriialieksieiev.smarthome.utils;
import com.yuriialieksieiev.smarthome.components.A;
import com.yuriialieksieiev.smarthome.components.Action;
import com.yuriialieksieiev.smarthome.components.button.ActionButton;
import com.yuriialieksieiev.smarthome.components.seekbar.ActionSeekBar;
import com.yuriialieksieiev.smarthome.components.sensor.SensorVal;
import com.yuriialieksieiev.smarthome.components.sensor.SensorView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

public class ActionUtils {

    private final static String API_EXTRA_ACTIONS = "actions";
    private final static String API_EXTRA_HASH_CODE = "hashCode";

    public static long setAllActions(JSONObject jsonObject, A a) throws JSONException {
        setActionButtons(jsonObject.getJSONArray(API_EXTRA_ACTIONS), a.getListButtons());
        setActionSeekBars(jsonObject.getJSONArray(API_EXTRA_ACTIONS), a.getListSeekBars());
        return jsonObject.getLong(API_EXTRA_HASH_CODE);
    }

    private static void setActionButtons(JSONArray jsonArray, List<ActionButton> list) throws JSONException {
        for(int i = 0; i < jsonArray.length(); i++)
        {
            Action action = Action.parseAPI(jsonArray.getJSONObject(i));

            for(ActionButton actionButton : list)
                if(action.equals(actionButton.getAction()))
                    actionButton.setAction(action);
        }
    }

    private static void setActionSeekBars(JSONArray jsonArray, List<ActionSeekBar> list) throws JSONException {
        for(int i = 0; i < jsonArray.length(); i++)
        {
            Action action = Action.parseAPI(jsonArray.getJSONObject(i));

            for(ActionSeekBar actionSeekBar : list)
                if(action.equals(actionSeekBar.getAction()))
                    actionSeekBar.setAction(action);
        }
    }


    public static void setSensors(JSONArray jsonArray, List<SensorView> listSensorView) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            SensorVal sensorVal = SensorVal.fromAPI(jsonArray.getJSONObject(i));

            for (SensorView sensorView : listSensorView)
                if(sensorVal.equals(sensorView.getSensorVal()))
                    sensorView.setSensorVal(sensorVal);

        }

    }
}
