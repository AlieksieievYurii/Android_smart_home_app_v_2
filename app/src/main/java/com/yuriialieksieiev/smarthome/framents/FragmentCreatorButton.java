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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yuriialieksieiev.smarthome.activity.MakerView;
import com.yuriialieksieiev.smarthome.R;
import com.yuriialieksieiev.smarthome.components.Action;
import com.yuriialieksieiev.smarthome.components.Pin;
import com.yuriialieksieiev.smarthome.components.button.ActionButton;
import com.yuriialieksieiev.smarthome.components.button.PatternActionButton;
import com.yuriialieksieiev.smarthome.components.dialoges.selectRegisteredPins.AlertSelectRegisteredPin;
import com.yuriialieksieiev.smarthome.components.enums.Device;
import com.yuriialieksieiev.smarthome.utils.JsonManager;
import com.yuriialieksieiev.smarthome.components.enums.Icons;
import com.yuriialieksieiev.smarthome.utils.UrlUtils;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.List;
import java.util.Objects;

public class FragmentCreatorButton extends Fragment {

    private View root;
    private Button btnExample;
    private EditText edtName;
    private EditText edtPort;
    private Spinner spDevice;
    private Spinner spIcon;
    private ActionButton actionButton;
    private Button btnSelectRegisteredPin;
    private List<Pin> registeredPins;
    private boolean mEnableTextWatcher = true;
    private boolean isAnalog = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_create_button, container, false);

        Bundle bundle = getArguments();
        assert bundle != null;
        this.actionButton = bundle.getParcelable(MakerView.EXTRA_ACTION_BUTTON);

        init();
        if (actionButton != null)
            setFields();

        takeListOfRegisteredPins();
        return root;
    }

    private void takeListOfRegisteredPins() {
        final StringRequest requestGetRegisteredPins =
                new StringRequest(
                        Request.Method.GET,
                        UrlUtils.getUrlForGetRegisteredPins(getContext()),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    registeredPins = JsonManager.parsePins(new JSONArray(response));
                                    btnSelectRegisteredPin.setEnabled(true);
                                    if(actionButton != null)
                                        tryFindRegisteredPin();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Snackbar.make(root,"Can not get registered pins!",Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Snackbar.make(root,"Can not get registered pins!",Snackbar.LENGTH_SHORT).show();
                            }
                        });
        Volley.newRequestQueue(Objects.requireNonNull(getContext())).add(requestGetRegisteredPins);
    }

    private void setFields()
    {
        edtName.setText(actionButton.getName());
        edtPort.setText(String.valueOf(actionButton.getAction().getPort()));
        spIcon.setSelection(actionButton.getIcons().ordinal());
        spDevice.setSelection(actionButton.getAction().getDevice().ordinal());
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
                btnExample.setBackgroundResource(((Icons) spIcon.getSelectedItem()).getDrawable());
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
    }

    private void init() {
        btnExample = root.findViewById(R.id.btn_example);
        edtName = root.findViewById(R.id.edt_name);
        edtPort = root.findViewById(R.id.edt_pin);
        spIcon = root.findViewById(R.id.sp_icon);
        spDevice = root.findViewById(R.id.sp_device);
        btnSelectRegisteredPin = root.findViewById(R.id.btn_select_from_registered);

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
                root.findViewById(R.id.tv_error_name).setVisibility(View.GONE);
               btnExample.setText(edtName.getText().toString());
            }});

        edtPort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isAnalog = false;
                if(!mEnableTextWatcher || registeredPins == null)
                    return;

                root.findViewById(R.id.tv_error_signal).setVisibility(View.GONE);

                if(edtPort.getText().toString().length() == 0)
                {
                    root.findViewById(R.id.til_edt_name_pin).setVisibility(View.GONE);
                    return;
                }
                tryFindRegisteredPin();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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

                if (actionButton != null)
                    editAction(actionButton);
                else
                    createNewAction();
            }
        });


        btnSelectRegisteredPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                selectRegisteredPin();
            }
        });
    }

    private void selectRegisteredPin() {
        final AlertSelectRegisteredPin selectRegisteredPin = new AlertSelectRegisteredPin(Objects.requireNonNull(getContext()),
                registeredPins, new AlertSelectRegisteredPin.OnSelectedRegisteredPin() {
            @Override
            public void onSelected(Pin pin) {
                insertRegisteredPin(pin);
            }
        });
        selectRegisteredPin.show();
    }

    private void insertRegisteredPin(final Pin pin) {
        isAnalog = false;
        final EditText edtNamePin = root.findViewById(R.id.edt_name_pin);
        root.findViewById(R.id.til_edt_name_pin).setVisibility(View.VISIBLE);
        edtNamePin.setText(pin.getName());
        edtPort.setText(String.valueOf(pin.getPin()));
        if(pin.getTypePort() != Action.TypePort.DIGITAL) {
            errorPort(R.string.error_its_analog);
            isAnalog = true;
        }
        mEnableTextWatcher = true;
    }

    private void createNewAction()
    {
        final String name = edtName.getText().toString();
        final int port = Integer.parseInt(edtPort.getText().toString());
        final Icons icon = (Icons) spIcon.getSelectedItem();
        final Device device = (Device) spDevice.getSelectedItem();

        if (JsonManager.isExist(port, device, getContext())) {
            Snackbar.make(root, R.string.port_existed, Snackbar.LENGTH_LONG).show();
            errorPort(R.string.port_exist);
        }else if(isAnalog)
            Snackbar.make(root, R.string.port_is_analog, Snackbar.LENGTH_LONG).show();
        else
            try {
                JsonManager.addActionButton(getContext(), name, port, icon, device);
                Objects.requireNonNull(getActivity()).finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }


    private void editAction(ActionButton actionButton) {

        final String name = edtName.getText().toString();
        final int port = Integer.parseInt(edtPort.getText().toString());
        final Icons icon = (Icons) spIcon.getSelectedItem();
        final Device device = (Device) spDevice.getSelectedItem();

        if(isAnalog)
        {
            errorPort(R.string.port_is_analog);
            Snackbar.make(root, R.string.port_is_analog, Snackbar.LENGTH_LONG).show();
            return;
        }

        if (port != actionButton.getAction().getPort())
            if (JsonManager.isExist(port, device,getContext())) {
                Snackbar.make(root, R.string.port_existed, Snackbar.LENGTH_LONG).show();
                errorPort(R.string.port_exist);
                return;
            }

        try {
            JsonManager.replaceActionButton(getContext(),
                    new PatternActionButton(
                            icon,
                            name,
                            new Action(device, port, Action.PortStatus.LOW)),
                    actionButton.getAction());

            Objects.requireNonNull(getActivity()).finish();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean checkFields()
    {
        final String name = edtName.getText().toString();

        if (name.trim().length() == 0) {
            Snackbar.make(root, R.string.name_can_not_be_empty, Snackbar.LENGTH_LONG).show();
            root.findViewById(R.id.tv_error_name).setVisibility(View.VISIBLE);
            return false;
        }

        if (edtPort.getText() == null || edtPort.getText().toString().trim().length() == 0) {
            errorPort(R.string.port_can_not_be_empty);
            Snackbar.make(root, R.string.port_can_not_be_empty, Snackbar.LENGTH_LONG).show();
            return false;
        }

        return true;
    }


    private void errorPort(int idTextRes)
    {
        final TextView tvPortError = root.findViewById(R.id.tv_error_signal);
        tvPortError.setVisibility(View.VISIBLE);
        tvPortError.setText(idTextRes);

    }

    private void tryFindRegisteredPin()
    {
        final int _port = Integer.parseInt(edtPort.getText().toString());

        for(Pin pin : registeredPins) {
            if (pin.getPin() == _port) {
                mEnableTextWatcher = false;
                insertRegisteredPin(pin);
                break;
            }
            root.findViewById(R.id.til_edt_name_pin).setVisibility(View.GONE);
        }
    }
}
