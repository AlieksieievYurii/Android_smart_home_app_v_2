package com.yuriialieksieiev.smarthome.components;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.yuriialieksieiev.smarthome.R;
import java.util.List;

public class RcActionsAdapter extends RecyclerView.Adapter<RcActionsAdapter.RcActionViewHolder>
{
    public interface OnLongPressAction{
        void onLongPressAction(Action action);
    }

    private List<Action> actions;
    private final OnLongPressAction onLongPressAction;

    public RcActionsAdapter(List<Action> actions, OnLongPressAction onLongPressAction) {
        this.actions = actions;
        this.onLongPressAction = onLongPressAction;
    }

    @NonNull
    @Override
    public RcActionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cv_action,viewGroup,false);
        return new RcActionViewHolder(v,actions,onLongPressAction);
    }

    @Override
    public void onBindViewHolder(@NonNull RcActionViewHolder rcActionViewHolder, int i) {
        final Action action = actions.get(i);

        rcActionViewHolder.tvPort.setText(String.valueOf(action.getPort()));
        rcActionViewHolder.tvPortType.setText(action.getTypePort().getInJson());
        rcActionViewHolder.tvDevice.setText(action.getDevice().getInJson());

        if(action.getTypePort() == Action.TypePort.DIGITAL)
        {
            rcActionViewHolder.tvPortStatusHint.setText("Port status");
            rcActionViewHolder.tvPortStatus.setText(action.getPortStatus().getInJson());
        }else if(action.getTypePort() == Action.TypePort.ANALOG)
        {
            rcActionViewHolder.tvPortStatusHint.setText("Port signal");
            rcActionViewHolder.tvPortStatus.setText(String.valueOf(action.getPortSignal()));
        }
    }

    @Override
    public int getItemCount() {
        return actions.size();
    }

    public static class RcActionViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvPort;
        private TextView tvPortType;
        private TextView tvPortStatusHint;
        private TextView tvPortStatus;
        private TextView tvDevice;

        public RcActionViewHolder(@NonNull View itemView, final List<Action> actions, final OnLongPressAction onLongClickListener) {
            super(itemView);

            tvPort = itemView.findViewById(R.id.tv_port);
            tvPortType = itemView.findViewById(R.id.tv_port_type);
            tvPortStatusHint = itemView.findViewById(R.id.tv_hint_status_port);
            tvPortStatus = itemView.findViewById(R.id.tv_status);
            tvDevice = itemView.findViewById(R.id.tv_device);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onLongClickListener.onLongPressAction(actions.get(getAdapterPosition()));
                    return true;
                }
            });


        }
    }
}
