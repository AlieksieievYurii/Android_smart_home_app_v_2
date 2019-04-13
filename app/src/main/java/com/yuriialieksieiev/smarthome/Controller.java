package com.yuriialieksieiev.smarthome;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.yuriialieksieiev.smarthome.components.button.Action;
import com.yuriialieksieiev.smarthome.components.button.ActionButton;
import com.yuriialieksieiev.smarthome.components.OnAction;
import com.yuriialieksieiev.smarthome.components.enums.Device;
import com.yuriialieksieiev.smarthome.components.seekbar.ActionSeekBar;
import com.yuriialieksieiev.smarthome.components.sensor.SensorVal;
import com.yuriialieksieiev.smarthome.components.sensor.SensorView;

import java.util.List;

public class Controller implements OnAction,IController {
    //TODO IMPLEMENT CONNECTION TPO SERVER AND POST GET REQUEST
    private List<ActionButton> listButtons;
    private List<ActionSeekBar> listSeekBars;
    private List<SensorView> listSensorView;
    private Context context;

    public Controller(Context context) {
        this.context = context;
    }

    @Override
    public void onAction(Action action) {
        Toast.makeText(context,action.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void start(List<ActionButton> listButtons,
                      List<ActionSeekBar> listSeekBars,
                      List<SensorView> listSensorView)
    {
        this.listButtons = listButtons;
        this.listSeekBars = listSeekBars;
        this.listSensorView = listSensorView;

    }

    @Override
    public void stop() {

    }
}
