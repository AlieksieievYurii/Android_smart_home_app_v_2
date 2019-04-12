package com.yuriialieksieiev.smarthome.framents;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.yuriialieksieiev.smarthome.MakerView;
import com.yuriialieksieiev.smarthome.R;
import com.yuriialieksieiev.smarthome.components.Button.Action;
import com.yuriialieksieiev.smarthome.components.Button.ActionButton;
import com.yuriialieksieiev.smarthome.components.Button.PatternActionButton;
import com.yuriialieksieiev.smarthome.components.Device;
import com.yuriialieksieiev.smarthome.utils.JsonManager;
import com.yuriialieksieiev.smarthome.components.enums.Icons;

import org.json.JSONException;

import java.util.Objects;

public class FragmentCreatorButton extends Fragment {

    private View root;
    private Button btnExample;
    private EditText edtName;
    private EditText edtPort;
    private Spinner spDevice;
    private Spinner spIcon;
    private Icons icons;
    private String name;
    private int port;
    private Device device;
    private ActionButton actionButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_create_button, container, false);

        Bundle bundle = getArguments();
        assert bundle != null;

        this.actionButton = bundle.getParcelable(MakerView.EXTRA_ACTION_BUTTON);
        Log.i("TAG--.",actionButton.toString());

        init();

        if (actionButton != null)
            setFields(actionButton);

        return root;
    }

    private void setFields(ActionButton actionButton)
    {
        name = actionButton.getName();
        icons = actionButton.getIcons();
        port = actionButton.getAction().getPort();
        icons = actionButton.getIcons();
        device = actionButton.getAction().getDevice();

        edtName.setText(name);
        edtPort.setText(String.valueOf(port));
        spIcon.setSelection(icons.ordinal());
        spDevice.setSelection(device.ordinal());
    }

    private void initSpinnerIcon() {
        final ArrayAdapter<Icons> adapter =
                new ArrayAdapter<>(root.getContext(), android.R.layout.simple_spinner_item, Icons.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spIcon.setAdapter(adapter);
        spIcon.setSelection(0);
        spIcon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                icons = (Icons) spIcon.getSelectedItem();
                upDateExample();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initSpinnerDevice() {
        final ArrayAdapter<Device> adapter2 =
                new ArrayAdapter<>(root.getContext(), android.R.layout.simple_spinner_item, Device.values());
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDevice.setAdapter(adapter2);
        spDevice.setSelection(0);


        spDevice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                device = (Device) spDevice.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void init() {
        btnExample = root.findViewById(R.id.btn_example);
        edtName = root.findViewById(R.id.edt_name);
        edtPort = root.findViewById(R.id.edt_pin);
        spIcon = root.findViewById(R.id.sp_icon);
        spDevice = root.findViewById(R.id.sp_device);

        initSpinnerIcon();
        initSpinnerDevice();

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
            }});

        btnExample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnExample.isActivated())
                    btnExample.setActivated(false);
                else
                    btnExample.setActivated(true);
            }
        });

        root.findViewById(R.id.btn_apply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!checkFields())
                    return;

                port = Integer.parseInt(edtPort.getText().toString());

                if (actionButton != null)
                    editAction(actionButton);
                else
                    createNewAction();

            }
        });
    }


    private void createNewAction() {

        if (JsonManager.isExist(port, device, getContext()))
            Snackbar.make(root, "Port is already exist!", Snackbar.LENGTH_LONG).show();
        else
            try {
                JsonManager.addActionButton(getContext(), name, port, icons, device);
                Objects.requireNonNull(getActivity()).finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }


    private void editAction(ActionButton actionButton) {

        if (port != actionButton.getAction().getPort())
            if (JsonManager.isExist(port, device,getContext())) {
                Snackbar.make(root, "Port is already exist!", Snackbar.LENGTH_LONG).show();
                return;
            }

        try {
            JsonManager.replaceActionButton(getContext(),
                    new PatternActionButton(
                            icons,
                            name,
                            new Action(device, port, Action.PortStatus.LOW)),
                    actionButton.getAction());

            Objects.requireNonNull(getActivity()).finish();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private boolean checkFields() {
        if (name == null || name.trim().length() == 0) {
            Snackbar.make(root, "Name can not be empty!", Snackbar.LENGTH_LONG).show();
            return false;
        }

        if (edtPort.getText() == null || edtPort.getText().toString().trim().length() == 0) {
            Snackbar.make(root, "Port can not be empty!", Snackbar.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void upDateExample() {
        btnExample.setText(name);
        btnExample.setBackgroundResource(icons.getDrawable());
    }
}
