package com.yuriialieksieiev.smarthome.components;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import com.yuriialieksieiev.smarthome.R;
import com.yuriialieksieiev.smarthome.components.enums.Device;
import java.util.List;
import java.util.Objects;

public class AlertCreateAction extends AlertDialog
{
    public interface CallBack{
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

    public AlertCreateAction(@NonNull Context context, CallBack callBack, List<Action> actions) {
        super(context);
        this.callBack = callBack;
        this.actions = actions;
    }

    public AlertCreateAction(@NonNull Context context, CallBack callBack,List<Action> actions, Action actionForEdit) {
        super(context);
        this.actions = actions;
        this.actionForEdit = actionForEdit;
        this.callBack = callBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_create_action);

        Objects.requireNonNull(getWindow()).clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        Objects.requireNonNull(findViewById(R.id.btn_apply)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check())
                    apply();
            }
        });

        spPortStatus = findViewById(R.id.sp_port_status);
        spPortStatus.setAdapter(new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item, Action.PortStatus.values()));
        if(actionForEdit != null && actionForEdit.getTypePort() == Action.TypePort.DIGITAL)
            spPortStatus.setSelection(actionForEdit.getPortStatus().ordinal());

        edtSignalPort = findViewById(R.id.edt_port_signal);
        if(actionForEdit != null && actionForEdit.getTypePort() == Action.TypePort.ANALOG)
            edtSignalPort.setText(String.valueOf(actionForEdit.getPortSignal()));

        lnDigitalType = findViewById(R.id.ln_tool_digital);
        analogTYpe = findViewById(R.id.tool_analog);

        edtPort = findViewById(R.id.edt_port);
        if(actionForEdit != null)
            edtPort.setText(String.valueOf(actionForEdit.getPort()));

        spTypePort = findViewById(R.id.sp_type_port);
        spTypePort.setAdapter(new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item, Action.TypePort.values()));
        if(actionForEdit != null)
            spTypePort.setSelection(actionForEdit.getTypePort().ordinal());

        spTypePort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spTypePort.getSelectedItem() == Action.TypePort.DIGITAL)
                {
                    analogTYpe.setVisibility(View.GONE);
                    lnDigitalType.setVisibility(View.VISIBLE);
                }else if(spTypePort.getSelectedItem() == Action.TypePort.ANALOG)
                {
                    analogTYpe.setVisibility(View.VISIBLE);
                    lnDigitalType.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spDevice = findViewById(R.id.sp_device);
        spDevice.setAdapter(new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item, Device.values()));
        if(actionForEdit != null)
            spDevice.setSelection(actionForEdit.getDevice().ordinal());


    }

    private void apply()
    {
        if(spTypePort.getSelectedItem() == Action.TypePort.ANALOG)
            callBack.applyAction(new Action((Device) spDevice.getSelectedItem(),
                    Integer.parseInt(edtPort.getText().toString()),
                    Integer.parseInt(edtSignalPort.getText().toString())));
        else if(spTypePort.getSelectedItem() == Action.TypePort.DIGITAL)
            callBack.applyAction(new Action((Device) spDevice.getSelectedItem(),
                    Integer.parseInt(edtPort.getText().toString()),
                    (Action.PortStatus) spPortStatus.getSelectedItem()));

        dismiss();
        hide();

    }

    private boolean check() {
        if(edtPort.getText().toString().trim().length() == 0) {
            Toast.makeText(getContext(),"Port: Can not be empty",Toast.LENGTH_LONG).show();
            return false;
        }

        if(isExistedPort(Integer.parseInt(edtPort.getText().toString()), (Device) spDevice.getSelectedItem()))
        {
            Toast.makeText(getContext(),"Port is exist here!",Toast.LENGTH_LONG).show();
            return false;
        }

        if(spTypePort.getSelectedItem() == Action.TypePort.ANALOG)
        {
            final String sig = edtSignalPort.getText().toString();

            if(sig.trim().length() == 0)
            {
                Toast.makeText(getContext(),"Signal can not be empty!",Toast.LENGTH_LONG).show();
                return false;
            }

            int signal = Integer.parseInt(sig);
            if (signal < 0 || signal > 255 )
            {
                Toast.makeText(getContext(),"Signal: only 0..255",Toast.LENGTH_LONG).show();
                return false;
            }
        }

        return true;

    }

    private boolean isExistedPort(int port, Device device) {

        if(actionForEdit != null && device == actionForEdit.getDevice() && port == actionForEdit.getPort())
            return false;

        for(Action action : actions)
            if(action.getPort() == port && action.getDevice() == device)
                return true;

        return false;
    }
}
