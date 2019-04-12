package com.yuriialieksieiev.smarthome.framents;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import com.yuriialieksieiev.smarthome.Controller;
import com.yuriialieksieiev.smarthome.Factory;
import com.yuriialieksieiev.smarthome.MakerView;
import com.yuriialieksieiev.smarthome.components.AlertMenu;
import com.yuriialieksieiev.smarthome.components.Button.Action;
import com.yuriialieksieiev.smarthome.components.OnLongPressAction;
import com.yuriialieksieiev.smarthome.R;
import com.yuriialieksieiev.smarthome.components.Button.ActionButton;
import com.yuriialieksieiev.smarthome.components.seekbar.ActionSeekBar;
import java.util.ArrayList;
import java.util.List;

public class FragmentActions extends Fragment implements
        Factory.OnViewCreated,
        OnLongPressAction,
        AlertMenu.MenuCallBack {

    private View root;
    private Factory factoryViews;
    private Controller controller;
    private GridLayout gl_root;

    private List<ActionButton> listButtons;
    private List<ActionSeekBar> listSeekBars;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_actions, container, false);
        this.gl_root = root.findViewById(R.id.gl_actions);

        controller = new Controller(getContext(), listButtons, listSeekBars);

        factoryViews = new Factory(getContext(),
                this,
                controller,
                this);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        upDate();
    }

    private void upDate() {
        gl_root.removeAllViews();

        listButtons = new ArrayList<>();
        listSeekBars = new ArrayList<>();

        try {
            factoryViews.build();
        } catch (Exception e) {
            e.printStackTrace();
            //TODO Show SnackBar error(Nothing)
        }
    }

    @Override
    public void buttonCreated(ActionButton actionButton) {
        listButtons.add(actionButton);
        gl_root.addView(actionButton.getButton());
    }

    @Override
    public void seekBarCreated(ActionSeekBar actionSeekBar) {
        listSeekBars.add(actionSeekBar);
        gl_root.addView(actionSeekBar.getSeekBar());
    }

    @Override
    public void buildingFinished() {
        //TODO DO request to server for set all actions
    }

    @Override
    public void onLongPressButtonAction(ActionButton actionButton) {
        new AlertMenu(getContext(),this).startEdition(actionButton);
    }

    @Override
    public void onLongPressSeekBarAction(ActionSeekBar actionSeekBar) {
        new AlertMenu(getContext(),this).startEdition(actionSeekBar);    }

    @Override
    public void remove(Action action) {

    }

    @Override
    public void editActionButton(ActionButton actionButton) {
        Intent intent = new Intent(getContext(),MakerView.class);
        intent.putExtra(MakerView.EXTRA_WHAT_VIEW,MakerView.EXTRA_BUTTON);
        intent.putExtra(MakerView.EXTRA_ACTION_BUTTON,actionButton);

        startActivity(intent);
    }

    @Override
    public void editActionSeekBar(ActionSeekBar actionSeekBar) {
        Intent intent = new Intent(getContext(),MakerView.class);
        intent.putExtra(MakerView.EXTRA_WHAT_VIEW,MakerView.EXTRA_SEEK_BAR);
        intent.putExtra(MakerView.EXTRA_ACTION_BUTTON,actionSeekBar);

        startActivity(intent);
    }
}
