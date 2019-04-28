package com.yuriialieksieiev.smarthome.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yuriialieksieiev.smarthome.R;
import com.yuriialieksieiev.smarthome.components.button.ActionButton;
import com.yuriialieksieiev.smarthome.components.seekbar.ActionSeekBar;
import com.yuriialieksieiev.smarthome.components.sensor.SensorVal;
import com.yuriialieksieiev.smarthome.framents.FragmentCreatorButton;
import com.yuriialieksieiev.smarthome.framents.FragmentCreatorSeekBar;
import com.yuriialieksieiev.smarthome.framents.FragmentCreatorSensor;
import com.yuriialieksieiev.smarthome.framents.FragmentCreatorTask;

public class MakerView extends AppCompatActivity {
    public static final String EXTRA_WHAT_VIEW = "what_view";
    public static final String EXTRA_BUTTON = "button";
    public static final String EXTRA_SEEK_BAR = "seek_bar";
    public static final String EXTRA_SENSOR = "sensor";
    public static final String EXTRA_TASK = "task";

    public static final String EXTRA_ACTION_BUTTON = "action_button";
    public static final String EXTRA_ACTION_SEEK_BAR = "action_seek_bar";
    public static final String EXTRA_SENSOR_VAL = "sensor_val";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maker_view);
        switch (getIntent().getStringExtra(EXTRA_WHAT_VIEW)) {
            case EXTRA_BUTTON:
                creatorButton((ActionButton) getIntent().getParcelableExtra(EXTRA_ACTION_BUTTON));
                break;
            case EXTRA_SEEK_BAR:
                creatorSeekBar((ActionSeekBar) getIntent().getParcelableExtra(EXTRA_ACTION_SEEK_BAR));
                break;
            case EXTRA_SENSOR:
                creatorSensor((SensorVal) getIntent().getParcelableExtra(EXTRA_SENSOR_VAL));
                break;
            case EXTRA_TASK:
                creatorTask();
        }

    }

    private void creatorTask() {

        setTitle("Create Task");

        FragmentCreatorTask fragmentCreatorTask = new FragmentCreatorTask();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root, fragmentCreatorTask)
                .commit();
    }

    private void creatorSensor(SensorVal sensorVal)
    {
        setTitle("Create Sensor");

        FragmentCreatorSensor fragmentCreatorSensor =
                new FragmentCreatorSensor();

        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_SENSOR_VAL, sensorVal);

        fragmentCreatorSensor.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root, fragmentCreatorSensor)
                .commit();
    }

    private void creatorButton(ActionButton actionButton) {
        setTitle("Create Button");

        FragmentCreatorButton fragmentCreatorButton =
                new FragmentCreatorButton();

        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_ACTION_BUTTON, actionButton);

        fragmentCreatorButton.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root,
                        fragmentCreatorButton)
                .commit();
    }

    private void creatorSeekBar(ActionSeekBar actionSeekBar) {
        setTitle("Create SeekBar");
        FragmentCreatorSeekBar fragmentCreatorSeekBar =
                new FragmentCreatorSeekBar();

        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_ACTION_SEEK_BAR, actionSeekBar);

        fragmentCreatorSeekBar.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root,
                        fragmentCreatorSeekBar)
                .commit();
    }
}
