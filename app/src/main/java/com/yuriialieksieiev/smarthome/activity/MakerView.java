package com.yuriialieksieiev.smarthome.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.yuriialieksieiev.smarthome.R;
import com.yuriialieksieiev.smarthome.components.Task;
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
    public static final String EXTRA_OBJECT_TASK = "obj_task";

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
                creatorTask((Task) getIntent().getParcelableExtra(EXTRA_OBJECT_TASK));
        }
    }

    private void creatorTask(Task taskForEdit) {
        if(taskForEdit != null)
            setTitle("Edit Task");
        else
            setTitle("Create Task");

        final FragmentCreatorTask fragmentCreatorTask = new FragmentCreatorTask();
        final Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_OBJECT_TASK,taskForEdit);

        fragmentCreatorTask.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root, fragmentCreatorTask)
                .commit();
    }

    private void creatorSensor(SensorVal sensorValForEdit)
    {
        if(sensorValForEdit != null)
            setTitle("Edit Sensor");
        else
            setTitle("Create Sensor");

        final FragmentCreatorSensor fragmentCreatorSensor =
                new FragmentCreatorSensor();

        final Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_SENSOR_VAL, sensorValForEdit);

        fragmentCreatorSensor.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root, fragmentCreatorSensor)
                .commit();
    }

    private void creatorButton(ActionButton actionButtonForEdit) {
        if(actionButtonForEdit != null)
            setTitle("Edit Button");
        else
            setTitle("Create Button");

        final FragmentCreatorButton fragmentCreatorButton =
                new FragmentCreatorButton();

        final Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_ACTION_BUTTON, actionButtonForEdit);

        fragmentCreatorButton.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root,
                        fragmentCreatorButton)
                .commit();
    }

    private void creatorSeekBar(ActionSeekBar actionSeekBarForEdit)
    {
        if(actionSeekBarForEdit != null)
            setTitle("Edit SeekBar");
        else
            setTitle("Create SeekBar");

        final FragmentCreatorSeekBar fragmentCreatorSeekBar =
                new FragmentCreatorSeekBar();

        final Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_ACTION_SEEK_BAR, actionSeekBarForEdit);

        fragmentCreatorSeekBar.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root,
                        fragmentCreatorSeekBar)
                .commit();
    }
}
