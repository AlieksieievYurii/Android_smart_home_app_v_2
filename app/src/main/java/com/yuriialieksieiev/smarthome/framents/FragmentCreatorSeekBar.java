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
import com.yuriialieksieiev.smarthome.components.dialoges.selectRegisteredPins.AlertSelectRegisteredPin;
import com.yuriialieksieiev.smarthome.components.enums.Device;
import com.yuriialieksieiev.smarthome.components.seekbar.ActionSeekBar;
import com.yuriialieksieiev.smarthome.components.seekbar.PatternActionSeekBar;
import com.yuriialieksieiev.smarthome.utils.JsonManager;
import com.yuriialieksieiev.smarthome.utils.UrlUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;
import java.util.Objects;

public class FragmentCreatorSeekBar extends Fragment {
    private View root;
    private EditText edtName;
    private EditText edtPort;
    private TextView tvName;
    private Spinner spDevice;
    private ActionSeekBar actionSeekBar;
    private Button btnSelectRegisteredPin;
    private List<Pin> registeredPins;
    private boolean isDigital;
    private boolean mEnableTextWatcher = true;

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

    private void setFields() {
        edtName.setText(actionSeekBar.getName());
        edtPort.setText(String.valueOf(actionSeekBar.getAction().getPort()));
        spDevice.setSelection(actionSeekBar.getAction().getDevice().ordinal());
    }

    private void init() {
        tvName = root.findViewById(R.id.tv_name);
        edtName = root.findViewById(R.id.edt_name);
        edtPort = root.findViewById(R.id.edt_port);
        btnSelectRegisteredPin = root.findViewById(R.id.btn_select_from_registered);

        spDevice = root.findViewById(R.id.sp_device);

        final ArrayAdapter<Device> adapter =
                new ArrayAdapter<>(root.getContext(), android.R.layout.simple_spinner_item, Device.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spDevice.setAdapter(adapter);
        spDevice.setSelection(0);

        spDevice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if( !mEnableTextWatcher || registeredPins == null)
                    return;

                tryFindRegisteredPin();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edtPort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(!mEnableTextWatcher || registeredPins == null)
                    return;

                root.findViewById(R.id.tv_error_port).setVisibility(View.GONE);

                tryFindRegisteredPin();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvName.setText(edtName.getText().toString());
                root.findViewById(R.id.tv_error_name).setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        root.findViewById(R.id.btn_apply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!checkFields())
                    return;


                if (actionSeekBar != null)
                    editSeekBar();
                else
                    createNewSeekBar();
            }
        });
        
        btnSelectRegisteredPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    private void insertRegisteredPin(Pin pin)
    {
        mEnableTextWatcher = false;
        isDigital = false;
        root.findViewById(R.id.tv_error_port).setVisibility(View.GONE);
        final EditText edtNamePin = root.findViewById(R.id.edt_name_pin);
        root.findViewById(R.id.til_edt_name_pin).setVisibility(View.VISIBLE);
        edtNamePin.setText(pin.getName());
        edtPort.setText(String.valueOf(pin.getPin()));
        spDevice.setSelection(pin.getDevice().ordinal());
        if(pin.getTypePort() != Action.TypePort.ANALOG) {
            errorPort(R.string.error_its_digital);
            isDigital = true;
        }
        mEnableTextWatcher = true;
    }

    private void createNewSeekBar() {

        final String name = edtName.getText().toString();
        final int port = Integer.parseInt(edtPort.getText().toString());
        final Device device = (Device) spDevice.getSelectedItem();

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
        final String name = edtName.getText().toString();
        final int port = Integer.parseInt(edtPort.getText().toString());
        final Device device = (Device) spDevice.getSelectedItem();

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
        final int port = Integer.parseInt(edtPort.getText().toString());
        final Device device = (Device) spDevice.getSelectedItem();

        if (JsonManager.isExist(port, device, getContext())) {
            errorPort(R.string.port_existed);
            Snackbar.make(root, R.string.port_existed, Snackbar.LENGTH_LONG).show();
            return false;
        } else
            return true;
    }

    private boolean checkFields()
    {
        final String name = edtName.getText().toString();
        if (name.trim().length() == 0) {
            root.findViewById(R.id.tv_error_name).setVisibility(View.VISIBLE);
            Snackbar.make(root, R.string.name_can_not_be_empty, Snackbar.LENGTH_LONG).show();
            return false;
        }

        if (edtPort.getText() == null || edtPort.getText().toString().trim().length() == 0) {
            errorPort(R.string.port_can_not_be_empty);
            Snackbar.make(root, R.string.port_can_not_be_empty, Snackbar.LENGTH_LONG).show();
            return false;
        }

        if(isDigital)
        {
            errorPort(R.string.error_its_digital);
            Snackbar.make(root, R.string.error_its_digital, Snackbar.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
    private void errorPort(int idTextRes)
    {
        final TextView tvPortError = root.findViewById(R.id.tv_error_port);
        tvPortError.setVisibility(View.VISIBLE);
        tvPortError.setText(idTextRes);
    }

    private void tryFindRegisteredPin()
    {
        if(edtPort.getText().toString().length() == 0)
        {
            root.findViewById(R.id.til_edt_name_pin).setVisibility(View.GONE);
            return;
        }

        final int _port = Integer.parseInt(edtPort.getText().toString());
        final Device _device = (Device) spDevice.getSelectedItem();
        for(Pin pin : registeredPins) {
            if (pin.getPin() == _port && pin.getDevice() == _device) {
                insertRegisteredPin(pin);
                break;
            }
            root.findViewById(R.id.til_edt_name_pin).setVisibility(View.GONE);
        }

    }

}
