package com.yuriialieksieiev.smarthome;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yuriialieksieiev.smarthome.components.A;
import com.yuriialieksieiev.smarthome.components.SnackBarRetry;
import com.yuriialieksieiev.smarthome.components.Action;
import com.yuriialieksieiev.smarthome.components.OnAction;
import com.yuriialieksieiev.smarthome.utils.ActionUtils;
import com.yuriialieksieiev.smarthome.utils.JsonManager;
import com.yuriialieksieiev.smarthome.utils.SharedPreferences;
import com.yuriialieksieiev.smarthome.utils.UrlUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements OnAction, IController {
    private final static int TIME_REQUEST = 3000;
    private long currentlyHashCode = 0;
    private Context context;
    private Timer timer;
    private RequestQueue requestQueue;
    private A a;
    private IView iView;

    public Controller(Context context, IView iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void onAction(Action action) {
        vibrate();
        postAction(action);
    }

    private void postAction(final Action action) {
        final PostActionListener postActionListener = new PostActionListener();
        StringRequest stringRequest =
                new StringRequest(
                        Request.Method.POST,
                        UrlUtils.getUrlForPostAction(context),
                        postActionListener,
                        postActionListener) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> param = new HashMap<>();
                        try {
                            param.put("data", JsonManager.convertToAPI(action));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        return param;
                    }
                };
        requestQueue.add(stringRequest);
    }

    private void vibrate() {
        if (SharedPreferences.isVibrated(context)) {
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
            else
                vibrator.vibrate(100);
        }
    }

    @Override
    public void onStart(A a) {
        this.a = a;
        currentlyHashCode = 0;
        this.requestQueue = Volley.newRequestQueue(context);

        startProcess();
    }

    private void startProcess() {
        final HashCodeListener hashCodeListener = new HashCodeListener();
        final SensorsListener sensorsListener = new SensorsListener();

        final StringRequest hashCodeRequest =
                new StringRequest(
                        Request.Method.GET,
                        UrlUtils.getUrlForHashCode(context),
                        hashCodeListener,
                        hashCodeListener);

        final StringRequest sensorRequest =
                new StringRequest(
                        Request.Method.GET,
                        UrlUtils.getUrlForGetSensors(context),
                        sensorsListener,
                        sensorsListener);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                requestQueue.add(hashCodeRequest);
                requestQueue.add(sensorRequest);
            }
        }, 0, TIME_REQUEST);
    }

    private void checkHashCode(long hashCode) {
        if (currentlyHashCode != hashCode)
            getActionsFromServer();
    }

    private void getActionsFromServer() {
        ActionsListener actionsListener = new ActionsListener();

        final StringRequest requestGetActions =
                new StringRequest(
                        Request.Method.GET,
                        UrlUtils.getUrlForGetActions(context),
                        actionsListener,
                        actionsListener);

        requestQueue.add(requestGetActions);
    }


    @Override
    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


    private class HashCodeListener implements Response.Listener<String>, Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError error) {
            iView.error(new SnackBarRetry.CallBack() {
                @Override
                public void onRetry() {
                    startProcess();
                }
            });
            stop();
        }

        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                checkHashCode(jsonObject.getLong("hashCode"));
            } catch (JSONException e) {
                e.printStackTrace();
                iView.error("Json Problem: " + e.getMessage());
            }
        }
    }

    private class ActionsListener implements Response.Listener<String>, Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError error) {
            iView.error("Error connection: Actions ");
        }

        @Override
        public void onResponse(String response) {
            try {
                currentlyHashCode = ActionUtils.setAllActions(new JSONObject(response), a);
            } catch (JSONException e) {
                iView.error("Json Problem: " + e.getMessage());
            }
        }
    }

    private class SensorsListener implements Response.Listener<String>, Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError error) {
        }

        @Override
        public void onResponse(String response) {
            try {
                ActionUtils.setSensors(new JSONArray(response), a.getListSensorView());
            } catch (JSONException e) {
                e.printStackTrace();
                iView.error("Json Problem: " + e.getMessage());
            }
        }
    }

    private class PostActionListener implements Response.Listener<String>, Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError error) {

        }

        @Override
        public void onResponse(String response) {
            if (response != null) {
                try {
                    JSONObject resp = new JSONObject(response);
                    if (resp.getString("Response").equals("WRONG"))
                        iView.error("Error with JSON!");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
