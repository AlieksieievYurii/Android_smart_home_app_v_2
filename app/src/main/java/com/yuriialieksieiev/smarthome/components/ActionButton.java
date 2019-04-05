package com.yuriialieksieiev.smarthome.components;


import android.view.View;
import android.widget.Button;

public class ActionButton implements View.OnClickListener
{
    private Button button;
    private Action action;
    private OnActionButtonClick actionButtonClick;

    public ActionButton(Button button, Action action, OnActionButtonClick actionButtonClick) {
        this.button = button;
        this.action = action;
        this.actionButtonClick = actionButtonClick;

    }

    private void changeState()
    {
        if(button.isActivated())
            button.setActivated(false);
        else
            button.setActivated(true);
    }

    @Override
    public void onClick(View v)
    {
       changeState();
       action.setPortStatus(button.isActivated()? Action.PortStatus.HIGH: Action.PortStatus.LOW);
       actionButtonClick.onClickAction(action);
    }

}
