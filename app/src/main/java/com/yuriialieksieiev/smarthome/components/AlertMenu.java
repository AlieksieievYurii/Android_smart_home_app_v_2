package com.yuriialieksieiev.smarthome.components;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import com.yuriialieksieiev.smarthome.Factory;
import com.yuriialieksieiev.smarthome.R;
import com.yuriialieksieiev.smarthome.components.button.Action;
import com.yuriialieksieiev.smarthome.components.button.ActionButton;
import com.yuriialieksieiev.smarthome.components.seekbar.ActionSeekBar;

public class AlertMenu {
    public interface MenuCallBack {
        void remove(Action action);

        void editActionButton(ActionButton actionButton);

        void editActionSeekBar(ActionSeekBar actionSeekBar);
    }

    private Context context;
    private Factory.TypeView typeView;
    private ActionButton actionButton;
    private ActionSeekBar actionSeekBar;
    private MenuCallBack menuCallBack;
    private String name;

    public AlertMenu(Context context, MenuCallBack menuCallBack) {
        this.context = context;
        this.menuCallBack = menuCallBack;
    }

    public void startEdition(ActionButton actionButton) {
        typeView = Factory.TypeView.BUTTON;
        this.actionButton = actionButton;
        name = actionButton.getName();
        showMenu(name);
    }

    public void startEdition(ActionSeekBar actionSeekBar) {
        typeView = Factory.TypeView.SEEK_BAR;
        this.actionSeekBar = actionSeekBar;
        name = actionSeekBar.getName();
        showMenu(name);
    }

    private void showMenu(String nameView) {
        final String[] items = {context.getString(R.string.edit),
                context.getString(R.string.remove)};

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.edit)+ " " + nameView)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 0:
                                edit();
                                break;
                            case 1:
                                alertRemove();
                                break;
                        }

                    }
                });
        builder.setCancelable(true);

        builder.create().show();
    }


    private void remove() {
        switch (typeView) {
            case BUTTON:
                menuCallBack.remove(actionButton.getAction());
                break;
            case SEEK_BAR:
                menuCallBack.remove(actionSeekBar.getAction());
                break;
        }
    }

    private void alertRemove() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.remove) + " \"" + name + "\"")
                .setMessage(R.string.do_yo_want_to_remove)
                .setCancelable(true)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        remove();
                    }})
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //nothing
                    }
                });
        builder.create().show();

    }

    private void edit() {
        switch (typeView) {
            case BUTTON:
                menuCallBack.editActionButton(actionButton);
                break;
            case SEEK_BAR:
                menuCallBack.editActionSeekBar(actionSeekBar);
                break;
        }
    }


}
