package com.yuriialieksieiev.smarthome;

import com.yuriialieksieiev.smarthome.components.button.ActionButton;
import com.yuriialieksieiev.smarthome.components.seekbar.ActionSeekBar;
import com.yuriialieksieiev.smarthome.components.sensor.SensorView;

import java.util.List;

public interface IController
{
    void start(List<ActionButton> listButtons,
               List<ActionSeekBar> listSeekBars,
               List<SensorView> listSensorView);
    void stop();
}
