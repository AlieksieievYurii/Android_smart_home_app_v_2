package com.yuriialieksieiev.smarthome.components;

import com.yuriialieksieiev.smarthome.components.button.ActionButton;
import com.yuriialieksieiev.smarthome.components.seekbar.ActionSeekBar;
import com.yuriialieksieiev.smarthome.components.sensor.SensorVal;

public interface OnLongPressAction
{
    void onLongPressButtonAction(ActionButton actionButton);
    void onLongPressSeekBarAction(ActionSeekBar actionSeekBar);
    void onLongPressSensor(SensorVal sensorVal);
}
