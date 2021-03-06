package com.yuriialieksieiev.smarthome.components.dialoges;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yuriialieksieiev.smarthome.R;
import com.yuriialieksieiev.smarthome.components.Action;
import com.yuriialieksieiev.smarthome.components.Pin;
import com.yuriialieksieiev.smarthome.components.dialoges.selectRegisteredPins.AlertSelectRegisteredPin;
import com.yuriialieksieiev.smarthome.components.enums.Device;
import com.yuriialieksieiev.smarthome.utils.JsonManager;
import com.yuriialieksieiev.smarthome.utils.UrlUtils;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.List;
import java.util.Objects;

public class AlertCreateAction extends AlertDialog {
    public interface CallBack {
        void applyAction(Action action);
    }

    private CallBack callBack;
    private EditText edtPort;
    private Spinner spTypePort;
    private Spinner spDevice;
    private Spinner spPortStatus;
    private EditText edtSignalPort;
    private LinearLayout lnDigitalType;
    private TextInputLayout analogTYpe;
    private List<Action> actions;
    private Action actionForEdit;
    private TextView tvErrorPort;
    private TextView tvErrorSignal;
    private Button btnSelectRegisteredPin;
    private List<Pin> registeredPins;
    private View root;
    private boolean mEnableTextWatcher = true;

    public AlertCreateAction(@NonNull Context context, CallBack callBack, List<Action> actions) {
        super(context);
        this.callBack = callBack;
        this.actions = actions;
    }

    public AlertCreateAction(@NonNull Context context, CallBack callBack, List<Action> actions, Action actionForEdit) {
        super(context);
        this.actions = actions;
        this.actionForEdit = actionForEdit;
        this.callBack = callBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_create_action);

        Objects.requireNonNull(getWindow()).clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        Objects.requireNonNull(findViewById(R.id.btn_apply)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check())
                    apply();
            }
        });

        root = findViewById(R.id.root);
        tvErrorPort = findViewById(R.id.tv_error_pin);
        tvErrorSignal = findViewById(R.id.tv_error_signal);
        spPortStatus = findViewById(R.id.sp_port_status);
        btnSelectRegisteredPin = findViewById(R.id.btn_select_from_registered);
        edtSignalPort = findViewById(R.id.edt_port_signal);
        lnDigitalType = findViewById(R.id.ln_tool_digital);
        analogTYpe = findViewById(R.id.tool_analog);
        edtPort = findViewById(R.id.edt_port);
        spTypePort = findViewById(R.id.sp_type_port);
        spDevice = findViewById(R.id.sp_device);

        mEnableTextWatcher = false;
        spPortStatus.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, Action.PinStatus.values()));
        if (actionForEdit != null && actionForEdit.getTypePin() == Action.TypePin.DIGITAL)
            spPortStatus.setSelection(actionForEdit.getPinStatus().ordinal());


        edtSignalPort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (tvErrorSignal.getVisibility() == View.VISIBLE)
                    tvErrorSignal.setVisibility(View.GONE);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        if (actionForEdit != null && actionForEdit.getTypePin() == Action.TypePin.ANALOG)
            edtSignalPort.setText(String.valueOf(actionForEdit.getPinSignal()));


        edtPort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (tvErrorPort.getVisibility() == View.VISIBLE)
                    tvErrorPort.setVisibility(View.GONE);
                spTypePort.setEnabled(true);

                if(!mEnableTextWatcher || registeredPins == null)
                    return;

                tryFindRegisteredPin();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        if (actionForEdit != null)
            edtPort.setText(String.valueOf(actionForEdit.getPin()));


        spTypePort.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, Action.TypePin.values()));
        if (actionForEdit != null)
            spTypePort.setSelection(actionForEdit.getTypePin().ordinal());

        spTypePort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spTypePort.getSelectedItem() == Action.TypePin.DIGITAL) {
                    analogTYpe.setVisibility(View.GONE);
                    lnDigitalType.setVisibility(View.VISIBLE);
                } else if (spTypePort.getSelectedItem() == Action.TypePin.ANALOG) {
                    analogTYpe.setVisibility(View.VISIBLE);
                    lnDigitalType.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spDevice.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, Device.values()));
        if (actionForEdit != null)
            spDevice.setSelection(actionForEdit.getDevice().ordinal());
        spDevice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!mEnableTextWatcher || registeredPins == null)
                    return;

                spTypePort.setEnabled(true);
                tryFindRegisteredPin();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSelectRegisteredPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertSelectRegisteredPin selectRegisteredPin = new AlertSelectRegisteredPin(Objects.requireNonNull(getContext()),
                        registeredPins, new AlertSelectRegisteredPin.OnSelectedRegisteredPin() {
                    @Override
                    public void onSelected(Pin pin) {
                        insertRegisteredPin(pin);
                    }
                });

                selectRegisteredPin.show();
            }
        });
        mEnableTextWatcher = true;
        takeListOfRegisteredPins();
    }

    private void insertRegisteredPin(Pin pin) {
        mEnableTextWatcher = false;
        Objects.requireNonNull(findViewById(R.id.til_edt_name_pin)).setVisibility(View.VISIBLE);
        ((EditText) Objects.requireNonNull(findViewById(R.id.edt_pin_name))).setText(pin.getName());
        edtPort.setText(String.valueOf(pin.getPin()));
        spDevice.setSelection(pin.getDevice().ordinal());
        spTypePort.setSelection(pin.getTypePin().ordinal());
        spTypePort.setEnabled(false);
        mEnableTextWatcher = true;
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
                                    btnSelectRegisteredPin.setAlpha(1);
                                    if(actionForEdit != null)
                                        tryFindRegisteredPin();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Snackbar.make(root, "Can not get registered pins!", Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Snackbar.make(root, "Can not get registered pins!", Snackbar.LENGTH_SHORT).show();
                            }
                        });
        Volley.newRequestQueue(Objects.requireNonNull(getContext())).add(requestGetRegisteredPins);
    }

    private void apply() {
        if (spTypePort.getSelectedItem() == Action.TypePin.ANALOG)
            callBack.applyAction(new Action((Device) spDevice.getSelectedItem(),
                    Integer.parseInt(edtPort.getText().toString()),
                    Integer.parseInt(edtSignalPort.getText().toString())));
        else if (spTypePort.getSelectedItem() == Action.TypePin.DIGITAL)
            callBack.applyAction(new Action((Device) spDevice.getSelectedItem(),
                    Integer.parseInt(edtPort.getText().toString()),
                    (Action.PinStatus) spPortStatus.getSelectedItem()));

        dismiss();
        hide();

    }

    private boolean check() {
        if (edtPort.getText().toString().trim().length() == 0) {
            tvErrorPort.setVisibility(View.VISIBLE);
            tvErrorPort.setText(getContext().getString(R.string.pin_can_not_be_empty));
            return false;
        }

        if (isExistedPort(Integer.parseInt(edtPort.getText().toString()), (Device) spDevice.getSelectedItem())) {
            tvErrorPort.setVisibility(View.VISIBLE);
            tvErrorPort.setText(getContext().getString(R.string.pin_existed));
            return false;
        }

        if (spTypePort.getSelectedItem() == Action.TypePin.ANALOG) {
            final String sig = edtSignalPort.getText().toString();

            if (sig.trim().length() == 0) {
                tvErrorSignal.setVisibility(View.VISIBLE);
                tvErrorSignal.setText(getContext().getString(R.string.signal_empty));
                return false;
            }

            int signal = Integer.parseInt(sig);
            if (signal < 0 || signal > 255) {
                tvErrorSignal.setVisibility(View.VISIBLE);
                tvErrorSignal.setText(getContext().getString(R.string.signal_hint));
                return false;
            }
        }

        return true;

    }

    private boolean isExistedPort(int port, Device device) {
        if (actionForEdit != null && device == actionForEdit.getDevice() && port == actionForEdit.getPin())
            return false;

        for (Action action : actions)
            if (action.getPin() == port && action.getDevice() == device)
                return true;

        return false;
    }

    private void tryFindRegisteredPin() {
        if (edtPort.getText().toString().length() == 0) {
            root.findViewById(R.id.til_edt_name_pin).setVisibility(View.GONE);
            return;
        }

        final int _port = Integer.parseInt(edtPort.getText().toString());
        final Device _device = (Device) spDevice.getSelectedItem();
        for (Pin pin : registeredPins) {
            if (pin.getPin() == _port && pin.getDevice() == _device) {
                insertRegisteredPin(pin);
                break;
            }
            root.findViewById(R.id.til_edt_name_pin).setVisibility(View.GONE);
        }
    }
}
