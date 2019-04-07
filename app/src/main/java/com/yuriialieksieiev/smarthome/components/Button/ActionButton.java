package com.yuriialieksieiev.smarthome.components.Button;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import com.yuriialieksieiev.smarthome.components.OnLongPressActionView;

public class ActionButton implements View.OnClickListener, View.OnLongClickListener
{
    private Button button;
    private Action action;
    private OnActionButtonClick actionButtonClick;
    private OnLongPressActionView onLongPressActionView;

    private ActionButton(Button button,
                        Action action,
                        OnActionButtonClick actionButtonClick,
                        OnLongPressActionView onLongPressActionView) {
        this.button = button;
        this.action = action;
        this.actionButtonClick = actionButtonClick;
        this.onLongPressActionView = onLongPressActionView;
        button.setOnClickListener(this);
        button.setOnLongClickListener(this);

    }

    private void changeState()
    {
        if(button.isActivated())
            button.setActivated(false);
        else
            button.setActivated(true);
    }

    public void setAction(Action action)
    {
        this.action = action;

        if(action.getPortStatus() == Action.PortStatus.HIGH)
            button.setActivated(true);
        else if(action.getPortStatus() == Action.PortStatus.LOW)
            button.setActivated(false);
    }

    @Override
    public void onClick(View v)
    {
       changeState();
       action.setPortStatus(button.isActivated()? Action.PortStatus.HIGH: Action.PortStatus.LOW);
       actionButtonClick.onClickAction(action);
    }

    public Button getButton() {
        return button;
    }

    @Override
    public boolean onLongClick(View v)
    {
        onLongPressActionView.onLongActionPress(action,v);
        return true;
    }

    public static class Builder
    {
        public static ActionButton build(Context context,
                                         OnActionButtonClick onActionButtonClick,
                                         OnLongPressActionView onLongPressActionView,
                                         PatternActionButton patternActionButton)
        {
            final Button button = new Button(context);
            final GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(
                    new ViewGroup.LayoutParams(240,240));
            layoutParams.setMargins(10,20,10,0);
            button.setLayoutParams(layoutParams);
            button.setTextColor(Color.WHITE);
            button.setTextSize(10);
            button.setPadding(5,190,5,2);

            button.setText(patternActionButton.getName());
            button.setBackgroundResource(patternActionButton.getIcon().getDrawable());

            //TODO Implement it for custom settings by user!!!

            return new ActionButton(button,
                    patternActionButton.getAction(),
                    onActionButtonClick,
                    onLongPressActionView);
        }
    }

}
