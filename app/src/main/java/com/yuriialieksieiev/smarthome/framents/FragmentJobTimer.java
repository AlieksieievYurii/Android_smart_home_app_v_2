package com.yuriialieksieiev.smarthome.framents;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yuriialieksieiev.smarthome.R;
import com.yuriialieksieiev.smarthome.components.Action;
import com.yuriialieksieiev.smarthome.components.AlertCreateAction;
import com.yuriialieksieiev.smarthome.components.RcActionsAdapter;
import java.util.ArrayList;
import java.util.List;

public class FragmentJobTimer extends Fragment implements FragmentCreatorTask.TakerActions
{

    private View root;
    private RecyclerView rcActions;
    private List<Action> actions;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_job_timer,container,false);
        init();
        return root;
    }

    private void init() {
        actions = getTestActions();

        rcActions = root.findViewById(R.id.rc_actions);
        rcActions.setAdapter(new RcActionsAdapter(actions));
        rcActions.setLayoutManager(new LinearLayoutManager(getContext()));
        rcActions.setHasFixedSize(true);

        root.findViewById(R.id.btn_add_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAction();
            }
        });
    }

    private void addAction()
    {
        new AlertCreateAction(getContext(), new AlertCreateAction.CallBack() {
            @Override
            public void applyAction(Action action) {
                actions.add(action);
            }
        },actions).show();
    }

    public List<Action> getTestActions()
    {
        List<Action> actions = new ArrayList<>();

        /*actions.add(new Action(Device.TCOD,12,255));
        actions.add(new Action(Device.TCOD,12, Action.PortStatus.HIGH));
        actions.add(new Action(Device.TCOD,1432, Action.PortStatus.HIGH));
        actions.add(new Action(Device.TCOD,12, Action.PortStatus.HIGH));
        actions.add(new Action(Device.TCOD,12, Action.PortStatus.LOW));
        actions.add(new Action(Device.TCOD,54,255));
        actions.add(new Action(Device.TCOD,122,0));
        actions.add(new Action(Device.TCOD,12,255));*/
        return actions;
    }

    @Override
    public List<Action> getActions() {
        return actions;
    }


}
