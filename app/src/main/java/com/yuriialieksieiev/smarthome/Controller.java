package com.yuriialieksieiev.smarthome;

import android.content.Context;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yuriialieksieiev.smarthome.components.A;
import com.yuriialieksieiev.smarthome.components.button.Action;
import com.yuriialieksieiev.smarthome.components.OnAction;
import com.yuriialieksieiev.smarthome.utils.ActionUtils;
import com.yuriialieksieiev.smarthome.utils.UrlUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements OnAction, IController {
    //TODO IMPLEMENT CONNECTION TPO SERVER AND POST GET REQUEST

    private final static int TIME_REQUEST = 1000;

    private long currentlyHashCode = 0;

    private Context context;
    private Timer timer;
    private RequestQueue requestQueue;
    private A a;

    public Controller(Context context) {
        this.context = context;
    }

    @Override
    public void onAction(Action action) {
        Toast.makeText(context, action.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart(A a) {
        this.a = a;
        this.requestQueue = Volley.newRequestQueue(context);

        startThreadHashCode();
    }

    private void startThreadHashCode() {
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

        final StringRequest requestGETactions =
                new StringRequest(
                        Request.Method.GET,
                        UrlUtils.getUrlForGetActions(context),
                        actionsListener,
                        actionsListener);

        requestQueue.add(requestGETactions);
    }


    @Override
    public void stop() {
        timer.cancel();
        timer = null;
        currentlyHashCode = 0;
    }


    private class HashCodeListener implements Response.Listener<String>, Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError error) {
            //TODO Implement error
        }

        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                checkHashCode(jsonObject.getLong("hashCode"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class ActionsListener implements Response.Listener<String>, Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError error) {
            //TODO Implement error
        }

        @Override
        public void onResponse(String response) {
            try {
                currentlyHashCode = ActionUtils.setAllActions(new JSONObject(response), a);
            } catch (JSONException e) {
                e.printStackTrace();
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
            }
        }
    }
}
