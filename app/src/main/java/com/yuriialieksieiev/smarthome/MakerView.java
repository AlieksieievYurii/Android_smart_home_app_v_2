package com.yuriialieksieiev.smarthome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.yuriialieksieiev.smarthome.components.button.ActionButton;
import com.yuriialieksieiev.smarthome.components.seekbar.ActionSeekBar;
import com.yuriialieksieiev.smarthome.framents.FragmentCreatorButton;
import com.yuriialieksieiev.smarthome.framents.FragmentCreatorSeekBar;

public class MakerView extends AppCompatActivity {
    public static final String EXTRA_WHAT_VIEW = "what_view";
    public static final String EXTRA_BUTTON = "button";
    public static final String EXTRA_SEEK_BAR = "seek_bar";
    public static final String EXTRA_SENSOR = "sensor";

    public static final String EXTRA_ACTION_BUTTON = "action_button";
    public static final String EXTRA_ACTION_SEEK_BAR = "action_seek_bar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maker_view);
        switch (getIntent().getStringExtra(EXTRA_WHAT_VIEW)) {
            case EXTRA_BUTTON:
                creatorButton((ActionButton) getIntent().getParcelableExtra(EXTRA_ACTION_BUTTON));
                break;
            case EXTRA_SEEK_BAR:
                creatorSeekBar((ActionSeekBar) getIntent().getParcelableExtra(EXTRA_ACTION_SEEK_BAR));
                break;
            case EXTRA_SENSOR:
                //TODO
                break;
        }

    }

    private void creatorButton(ActionButton actionButton) {
        FragmentCreatorButton fragmentCreatorButton =
                new FragmentCreatorButton();

        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_ACTION_BUTTON, actionButton);

        fragmentCreatorButton.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root,
                        fragmentCreatorButton)
                .commit();
    }

    private void creatorSeekBar(ActionSeekBar actionSeekBar) {
        FragmentCreatorSeekBar fragmentCreatorSeekBar =
                new FragmentCreatorSeekBar();

        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_ACTION_SEEK_BAR, actionSeekBar);

        fragmentCreatorSeekBar.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root,
                        fragmentCreatorSeekBar)
                .commit();
    }
}
