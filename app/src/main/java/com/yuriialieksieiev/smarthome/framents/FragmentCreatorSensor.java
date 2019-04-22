package com.yuriialieksieiev.smarthome.framents;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.yuriialieksieiev.smarthome.activity.MakerView;
import com.yuriialieksieiev.smarthome.R;
import com.yuriialieksieiev.smarthome.components.sensor.SensorVal;
import com.yuriialieksieiev.smarthome.utils.JsonManager;

import org.json.JSONException;

import java.util.Objects;

public class FragmentCreatorSensor extends Fragment
{
    private View root;
    private TextView tvExample;
    private Spinner spSensor;
    private SensorVal sensorVal;
    private SensorVal.Sensors sensors;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_creator_sensor,container,false);

        final Bundle bundle = getArguments();
        assert bundle != null;

        this.sensorVal = bundle.getParcelable(MakerView.EXTRA_SENSOR_VAL);

        init();

        if(sensorVal != null)
            setFields();

        return root;
    }

    private void setFields()
    {
        sensors = sensorVal.getSensor();
        tvExample.setBackgroundResource(sensors.getRes());
        spSensor.setSelection(sensors.ordinal());
    }

    private void init()
    {
        spSensor = root.findViewById(R.id.sp_sensor);
        tvExample = root.findViewById(R.id.tv_example);

        final ArrayAdapter<SensorVal.Sensors> adapter =
                new ArrayAdapter<>(root.getContext(), android.R.layout.simple_spinner_item, SensorVal.Sensors.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spSensor.setAdapter(adapter);
        spSensor.setSelection(0);
        spSensor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sensors = (SensorVal.Sensors) spSensor.getSelectedItem();
                tvExample.setBackgroundResource(sensors.getRes());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        root.findViewById(R.id.btn_apply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sensorVal != null)
                    try {
                        JsonManager.replaceSensor(getContext(),new SensorVal(sensors,0),sensorVal);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                else {
                    try {
                        JsonManager.addSensor(getContext(),new SensorVal(sensors,0));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Objects.requireNonNull(getActivity()).finish();
            }
        });
    }
}
