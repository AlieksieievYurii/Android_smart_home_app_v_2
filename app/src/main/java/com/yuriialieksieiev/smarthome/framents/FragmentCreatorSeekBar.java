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
import com.yuriialieksieiev.smarthome.MakerView;
import com.yuriialieksieiev.smarthome.R;
import com.yuriialieksieiev.smarthome.components.Button.Action;
import com.yuriialieksieiev.smarthome.components.Device;
import com.yuriialieksieiev.smarthome.components.seekbar.ActionSeekBar;
import com.yuriialieksieiev.smarthome.components.seekbar.PatternActionSeekBar;
import com.yuriialieksieiev.smarthome.utils.JsonManager;

import org.json.JSONException;

import java.util.Objects;

public class FragmentCreatorSeekBar extends Fragment {
    private View root;
    private EditText edtName;
    private EditText edtPort;
    private TextView tvName;
    private Spinner spDevice;
    private String name;
    private int port;
    private Device device;
    private ActionSeekBar actionSeekBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_creator_seek_bar, container, false);

        Bundle bundle = getArguments();
        assert bundle != null;

        this.actionSeekBar = bundle.getParcelable(MakerView.EXTRA_ACTION_SEEK_BAR);

        init();

        if (actionSeekBar != null)
            setFields();

        return root;
    }

    private void setFields() {
        name = actionSeekBar.getName();
        port = actionSeekBar.getAction().getPort();
        device = actionSeekBar.getAction().getDevice();

        edtName.setText(name);
        edtPort.setText(String.valueOf(port));
        spDevice.setSelection(device.ordinal());
    }

    private void init() {
        tvName = root.findViewById(R.id.tv_name);
        edtName = root.findViewById(R.id.edt_name);
        edtPort = root.findViewById(R.id.edt_port);

        spDevice = root.findViewById(R.id.sp_device);

        final ArrayAdapter<Device> adapter =
                new ArrayAdapter<>(root.getContext(), android.R.layout.simple_spinner_item, Device.values());
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

            }
        });

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


        root.findViewById(R.id.btn_apply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!checkFields())
                    return;

                port = Integer.parseInt(edtPort.getText().toString());


                if (actionSeekBar != null)
                    editSeekBar();
                else
                    createNewSeekBar();
            }
        });

    }

    private void createNewSeekBar() {

        if (checkPort()) {
            try {
                JsonManager.addActionSeekBar(getContext(), name, port, device);
                Objects.requireNonNull(getActivity()).finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private void editSeekBar()
    {
        if(port != actionSeekBar.getAction().getPort())
            if(!checkPort())
                return;

        try {
            JsonManager.replaceActionSeekBar(getContext(),new PatternActionSeekBar(
                    name,
                    new Action(device,port,0)),actionSeekBar.getAction());
            Objects.requireNonNull(getActivity()).finish();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private boolean checkPort() {
        if (JsonManager.isExist(port, device, getContext())) {
            Snackbar.make(root, R.string.port_existed, Snackbar.LENGTH_LONG).show();
            return false;
        } else
            return true;
    }

    private boolean checkFields() {
        if (name == null || name.trim().length() == 0) {
            Snackbar.make(root, R.string.name_can_not_be_empty, Snackbar.LENGTH_LONG).show();
            return false;
        }

        if (edtPort.getText() == null || edtPort.getText().toString().trim().length() == 0) {
            Snackbar.make(root, R.string.port_can_not_be_empty, Snackbar.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void upDateExample() {
        tvName.setText(name);
    }
}
