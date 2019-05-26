package com.yuriialieksieiev.smarthome.components.dialoges;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import com.yuriialieksieiev.smarthome.R;

public class AlertDialogEdition
{
    public interface CallBack{
        void edit();
        void remove();
    }

    private Context context;
    private CallBack callBack;
    private String name;

    public AlertDialogEdition(Context context, CallBack callBack, String name) {
        this.context = context;
        this.callBack = callBack;
        this.name = name;
    }

    public void show()
    {
        final String[] items = {context.getString(R.string.edit),
                context.getString(R.string.remove)};

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("\""+name+"\"")
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
                .setTitle(context.getString(R.string.remove) + name)
                .setMessage(R.string.do_yo_want_to_remove)
                .setCancelable(true)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callBack.remove();
                    }})
                .setNegativeButton(R.string.no, null);
        builder.create().show();
    }
}
