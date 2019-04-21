package com.yuriialieksieiev.smarthome;

import com.yuriialieksieiev.smarthome.components.A;
import com.yuriialieksieiev.smarthome.components.button.ActionButton;
import com.yuriialieksieiev.smarthome.components.seekbar.ActionSeekBar;
import com.yuriialieksieiev.smarthome.components.sensor.SensorView;

import java.util.List;

public interface IController
{
    void onStart(A a);
    void stop();
}
