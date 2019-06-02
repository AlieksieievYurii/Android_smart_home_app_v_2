package com.yuriialieksieiev.smarthome.components.dialoges.selectRegisteredPins;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yuriialieksieiev.smarthome.R;
import com.yuriialieksieiev.smarthome.components.Pin;
import java.util.List;

public class AlertSelectRegisteredPin extends AlertDialog {
    public interface OnSelectedRegisteredPin {
        void onSelected(Pin pin);
    }

    private OnSelectedRegisteredPin onSelectedRegisteredPin;
    private List<Pin> pins;

    public AlertSelectRegisteredPin(@NonNull Context context,
                                    List<Pin> pins,
                                    OnSelectedRegisteredPin onSelectedRegisteredPin) {
        super(context);
        this.pins = pins;
        this.onSelectedRegisteredPin = onSelectedRegisteredPin;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_select_registered_pins);

        RecyclerView recyclerView = findViewById(R.id.rc_registered_pins);
        assert recyclerView != null;
        if(pins.isEmpty())
        {
            final TextView tvEmpty = findViewById(R.id.registered_pins_empty);
            assert tvEmpty != null;
            tvEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            return;
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ListAdapter(pins, new OnSelectedRegisteredPin() {
            @Override
            public void onSelected(Pin pin) {
                onSelectedRegisteredPin.onSelected(pin);
                hide();
                dismiss();

            }
        }));
    }
}
