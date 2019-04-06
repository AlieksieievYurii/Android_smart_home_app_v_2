package com.yuriialieksieiev.smarthome;

import android.content.Context;
import android.widget.Toast;

import com.yuriialieksieiev.smarthome.components.Button.Action;
import com.yuriialieksieiev.smarthome.components.Button.ActionButton;
import com.yuriialieksieiev.smarthome.components.Button.OnActionButtonClick;
import com.yuriialieksieiev.smarthome.components.seekbar.ActionSeekBar;
import com.yuriialieksieiev.smarthome.components.seekbar.OnActionChangeSeekBar;

import java.util.List;

public class Controller implements OnActionButtonClick, OnActionChangeSeekBar {

    private List<ActionButton> listButtons;
    private List<ActionSeekBar> listSeekBars;
    private Context context;

    public Controller(Context context,List<ActionButton> listButtons, List<ActionSeekBar> listSeekBars) {
        this.listButtons = listButtons;
        this.listSeekBars = listSeekBars;
        this.context = context;
    }

    @Override
    public void onClickAction(Action action) {
        Toast.makeText(context,action.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActionChangeSeekBar(Action action) {
        Toast.makeText(context,action.toString(),Toast.LENGTH_SHORT).show();
    }
}
