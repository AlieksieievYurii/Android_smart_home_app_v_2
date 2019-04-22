package com.yuriialieksieiev.smarthome.components;
import com.yuriialieksieiev.smarthome.components.button.ActionButton;
import com.yuriialieksieiev.smarthome.components.seekbar.ActionSeekBar;
import com.yuriialieksieiev.smarthome.components.sensor.SensorView;
import java.util.List;

public class A
{
    private List<ActionButton> listButtons;
    private List<ActionSeekBar> listSeekBars;
    private List<SensorView> listSensorView;

    public A(List<ActionButton> listButtons, List<ActionSeekBar> listSeekBars, List<SensorView> listSensorView) {
        this.listButtons = listButtons;
        this.listSeekBars = listSeekBars;
        this.listSensorView = listSensorView;
    }

    public List<ActionButton> getListButtons() {
        return listButtons;
    }

    public List<ActionSeekBar> getListSeekBars() {
        return listSeekBars;
    }

    public List<SensorView> getListSensorView() {
        return listSensorView;
    }
}
