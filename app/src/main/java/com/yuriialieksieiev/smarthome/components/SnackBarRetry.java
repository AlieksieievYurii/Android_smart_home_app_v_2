package com.yuriialieksieiev.smarthome.components;
import android.support.design.widget.Snackbar;
import android.view.View;
import com.yuriialieksieiev.smarthome.R;

public class SnackBarRetry
{
    public interface CallBack{
        void onRetry();
    }

    public static void showSnackRetry(View view, final CallBack callBack)
    {
        Snackbar snackbar = Snackbar.make(view, R.string.no_connection,Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callBack.onRetry();
                    }
                });
        snackbar.show();
    }
}
