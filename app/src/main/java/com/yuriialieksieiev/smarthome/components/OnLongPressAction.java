package com.yuriialieksieiev.smarthome.components;

import com.yuriialieksieiev.smarthome.components.Button.ActionButton;
import com.yuriialieksieiev.smarthome.components.seekbar.ActionSeekBar;

public interface OnLongPressAction
{
    void onLongPressButtonAction(ActionButton actionButton);
    void onLongPressSeekBarAction(ActionSeekBar actionSeekBar);
}
