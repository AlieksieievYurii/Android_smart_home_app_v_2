package com.yuriialieksieiev.smarthome.components;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.yuriialieksieiev.smarthome.Factory;
import com.yuriialieksieiev.smarthome.MakerView;
import com.yuriialieksieiev.smarthome.components.Button.Action;
import com.yuriialieksieiev.smarthome.components.Button.ActionButton;
import com.yuriialieksieiev.smarthome.components.seekbar.ActionSeekBar;

public class AlertMenu
{
    public interface MenuCallBack
    {
        void remove(Action action);
        void editActionButton(ActionButton actionButton);
        void editActionSeekBar(ActionSeekBar actionSeekBar);
    }

    private Context context;
    private Factory.TypeView typeView;
    private ActionButton actionButton;
    private ActionSeekBar actionSeekBar;
    private MenuCallBack menuCallBack;

    public AlertMenu(Context context,MenuCallBack menuCallBack) {
        this.context = context;
        this.menuCallBack = menuCallBack;
    }

    public void startEdition(ActionButton actionButton)
    {
        typeView = Factory.TypeView.BUTTON;
        this.actionButton = actionButton;
        showMenu(actionButton.getName());
    }

    public void startEdition(ActionSeekBar actionSeekBar)
    {
        typeView = Factory.TypeView.SEEK_BAR;
        this.actionSeekBar = actionSeekBar;
        showMenu(actionSeekBar.getName());
    }

    private void showMenu(String nameView)
    {
        final String[] items = {"Edit","Remove"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Edit " + nameView)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which)
                        {
                            case 0:
                                edit();
                                break;
                            case 1:
                                remove();
                                break;
                        }

                    }
                });
        builder.setCancelable(true);

        builder.create().show();
    }

    private void remove()
    {

    }

    private void edit()
    {
        switch (typeView)
        {
            case BUTTON:
                menuCallBack.editActionButton(actionButton);
                break;
            case SEEK_BAR:
                menuCallBack.editActionSeekBar(actionSeekBar);
                break;
        }
    }


}
