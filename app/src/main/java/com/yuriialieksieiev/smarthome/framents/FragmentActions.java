package com.yuriialieksieiev.smarthome.framents;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import com.yuriialieksieiev.smarthome.Controller;
import com.yuriialieksieiev.smarthome.Factory;
import com.yuriialieksieiev.smarthome.IController;
import com.yuriialieksieiev.smarthome.MakerView;
import com.yuriialieksieiev.smarthome.components.A;
import com.yuriialieksieiev.smarthome.components.AlertMenu;
import com.yuriialieksieiev.smarthome.components.button.Action;
import com.yuriialieksieiev.smarthome.components.OnLongPressAction;
import com.yuriialieksieiev.smarthome.R;
import com.yuriialieksieiev.smarthome.components.button.ActionButton;
import com.yuriialieksieiev.smarthome.components.seekbar.ActionSeekBar;
import com.yuriialieksieiev.smarthome.components.sensor.SensorVal;
import com.yuriialieksieiev.smarthome.components.sensor.SensorView;
import com.yuriialieksieiev.smarthome.utils.JsonManager;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;

public class FragmentActions extends Fragment implements
        Factory.OnViewCreated,
        OnLongPressAction,
        AlertMenu.MenuCallBack {

    private View root;
    private Factory factoryViews;
    private IController controller;
    private GridLayout gl_root;

    private List<ActionButton> listButtons;
    private List<ActionSeekBar> listSeekBars;
    private List<SensorView> listSensors;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_actions, container, false);
        this.gl_root = root.findViewById(R.id.gl_actions);

        Controller controller = new Controller(getContext());
        this.controller = controller;

        factoryViews = new Factory(getContext(),
                this,
                controller,
                this);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        upDate();
    }

    private void upDate() {
        gl_root.removeAllViews();

        listButtons = new ArrayList<>();
        listSeekBars = new ArrayList<>();
        listSensors = new ArrayList<>();

        try {
            factoryViews.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void buttonCreated(ActionButton actionButton) {
        listButtons.add(actionButton);
        gl_root.addView(actionButton.getButton());
    }

    @Override
    public void seekBarCreated(ActionSeekBar actionSeekBar) {
        listSeekBars.add(actionSeekBar);
        gl_root.addView(actionSeekBar.getSeekBar());
    }

    @Override
    public void sensorCreated(SensorView sensorView) {
        listSensors.add(sensorView);
        gl_root.addView(sensorView.getTvValue());
    }

    @Override
    public void buildingFinished() {
        //TODO DO request to server for set all actions
        A a = new A(listButtons,listSeekBars,listSensors);
        controller.onStart(a);
    }

    @Override
    public void onLongPressButtonAction(ActionButton actionButton) {
        new AlertMenu(getContext(),this).startEdition(actionButton);
    }

    @Override
    public void onLongPressSeekBarAction(ActionSeekBar actionSeekBar) {
        new AlertMenu(getContext(),this).startEdition(actionSeekBar);    }

    @Override
    public void onLongPressSensor(SensorVal sensorVal) {
        new AlertMenu(getContext(),this).startEdition(sensorVal);
    }

    @Override
    public void removeAction(Action action)
    {
        try {
            JsonManager.remove(getContext(),action);
            upDate();
            Snackbar.make(root, R.string.action_removed,Snackbar.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeSensor(SensorVal sensorVal) {
        try {
            JsonManager.remove(getContext(),sensorVal);
            upDate();
            Snackbar.make(root, R.string.sensor_removed,Snackbar.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editActionButton(ActionButton actionButton) {
        Intent intent = new Intent(getContext(),MakerView.class);
        intent.putExtra(MakerView.EXTRA_WHAT_VIEW,MakerView.EXTRA_BUTTON);
        intent.putExtra(MakerView.EXTRA_ACTION_BUTTON,actionButton);

        startActivity(intent);
    }

    @Override
    public void editActionSeekBar(ActionSeekBar actionSeekBar) {
        Intent intent = new Intent(getContext(),MakerView.class);
        intent.putExtra(MakerView.EXTRA_WHAT_VIEW,MakerView.EXTRA_SEEK_BAR);
        intent.putExtra(MakerView.EXTRA_ACTION_SEEK_BAR,actionSeekBar);

        startActivity(intent);
    }

    @Override
    public void editSensor(SensorVal sensorVal) {
        Intent intent = new Intent(getContext(),MakerView.class);
        intent.putExtra(MakerView.EXTRA_WHAT_VIEW,MakerView.EXTRA_SENSOR);
        intent.putExtra(MakerView.EXTRA_SENSOR_VAL,sensorVal);

        startActivity(intent);
    }

    @Override
    public void onStop() {
        super.onStop();
        controller.stop();
    }
}
