package com.yuriialieksieiev.smarthome.components;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.yuriialieksieiev.smarthome.R;

public class AlertDialogAction
{
    public interface CallBack{
        void edit();
        void remove();
    }

    private Context context;
    private CallBack callBack;
    private int port;

    public AlertDialogAction(Context context, CallBack callBack, int port) {
        this.context = context;
        this.callBack = callBack;
        this.port = port;
    }

    public void show()
    {
        final String[] items = {context.getString(R.string.edit),
                context.getString(R.string.remove)};

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Action-> port:"+port)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                callBack.edit();
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

    private void alertRemove() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.remove) + " Action with port:" + port)
                .setMessage(R.string.do_yo_want_to_remove)
                .setCancelable(true)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callBack.remove();
                    }})
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //nothing
                    }
                });
        builder.create().show();
    }
}
