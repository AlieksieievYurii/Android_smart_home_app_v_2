package com.yuriialieksieiev.smarthome.framents;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.yuriialieksieiev.smarthome.R;
import com.yuriialieksieiev.smarthome.components.Device;
import com.yuriialieksieiev.smarthome.utils.JsonManager;

import org.json.JSONException;

import java.util.Objects;

public class FragmentCreatorSeekBar extends Fragment
{
    private View root;
    private EditText edtName;
    private EditText edtPort;
    private TextView tvName;
    private String name;
    private String port;
    private Device device;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_creator_seek_bar,container,false);
        init();
        return root;
    }

    private void init() {
        tvName = root.findViewById(R.id.tv_name);
        edtName = root.findViewById(R.id.edt_name);
        edtPort = root.findViewById(R.id.edt_port);

        final Spinner spDevice = root.findViewById(R.id.sp_device);

        final ArrayAdapter<Device> adapter =
                new ArrayAdapter<>(root.getContext(),android.R.layout.simple_spinner_item, Device.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spDevice.setAdapter(adapter);
        spDevice.setSelection(0);
        device = Device.TCOD;

        spDevice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                device = (Device) spDevice.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }});

        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                name = edtName.getText().toString();
                upDateExample();
            }
        });

        edtPort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                port = edtPort.getText().toString();
            }
        });

        root.findViewById(R.id.btn_apply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAll()) {
                    try {
                        JsonManager.addActionSeekBar(getContext(),name, Integer.parseInt(port),device);
                        Objects.requireNonNull(getActivity()).finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private boolean checkAll() {

        if(name == null || name.trim().length() == 0)
        {
            Snackbar.make(root,"Name can not be empty!",Snackbar.LENGTH_LONG).show();
            return false;
        }

        if(port == null || port.trim().length() == 0)
        {
            Snackbar.make(root,"Port can not be empty!",Snackbar.LENGTH_LONG).show();
            return false;
        }

        if(JsonManager.isExist(Integer.parseInt(port),getContext()))
        {
            Snackbar.make(root,"Port is already exist!",Snackbar.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void upDateExample() {
        tvName.setText(name);
    }
}
