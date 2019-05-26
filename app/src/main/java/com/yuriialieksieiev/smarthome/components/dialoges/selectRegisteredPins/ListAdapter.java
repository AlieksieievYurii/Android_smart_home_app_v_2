package com.yuriialieksieiev.smarthome.components.dialoges.selectRegisteredPins;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuriialieksieiev.smarthome.R;
import com.yuriialieksieiev.smarthome.components.Pin;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.PinViewHolder>
{
    private List<Pin> registeredPins;
    private AlertSelectRegisteredPin.OnSelectedRegisteredPin onSelectedRegisteredPin;

    ListAdapter(List<Pin> registeredPins, AlertSelectRegisteredPin.OnSelectedRegisteredPin onSelectedRegisteredPin) {
        this.registeredPins = registeredPins;
        this.onSelectedRegisteredPin = onSelectedRegisteredPin;
    }

    @NonNull
    @Override
    public PinViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        final View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_registered_pin,viewGroup,false);
        return new PinViewHolder(v,onSelectedRegisteredPin,registeredPins);
    }

    @Override
    public void onBindViewHolder(@NonNull PinViewHolder pinViewHolder, int i) {
        final Pin pin = registeredPins.get(i);
        pinViewHolder.tvPin.setText(String.valueOf(pin.getPin()));
        pinViewHolder.tvName.setText(pin.getName());
        pinViewHolder.tvDescription.setText(pin.getDescription());
        pinViewHolder.tvTypePort.setText(pin.getTypePort().getInJson());
        pinViewHolder.tvDevice.setText(pin.getDevice().getInJson());
    }


    @Override
    public int getItemCount() {
        return registeredPins.size();
    }


    static class PinViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvPin;
        private TextView tvName;
        private TextView tvDescription;
        private TextView tvTypePort;
        private TextView tvDevice;

        PinViewHolder(@NonNull View itemView,
                      final AlertSelectRegisteredPin.OnSelectedRegisteredPin onSelectedRegisteredPin,
                      final List<Pin> pins) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelectedRegisteredPin.onSelected(pins.get(getLayoutPosition()));
                }
            });
            tvPin = itemView.findViewById(R.id.tv_pin);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvTypePort = itemView.findViewById(R.id.tv_type_port);
            tvDevice = itemView.findViewById(R.id.tv_device);
        }


    }
}
