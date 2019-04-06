package com.yuriialieksieiev.smarthome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.yuriialieksieiev.smarthome.framents.FragmentCreatorButton;
import com.yuriialieksieiev.smarthome.framents.FragmentCreatorSeekBar;

public class MakerView extends AppCompatActivity {
    public static final String EXTRA_WHAT_VIEW = "what_view";
    public static final String EXTRA_BUTTON = "button";
    public static final String EXTRA_SEEK_BAR = "seek_bar";
    public static final String EXTRA_SENSOR = "sensor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maker_view);
        switch (getIntent().getStringExtra(EXTRA_WHAT_VIEW)) {
            case EXTRA_BUTTON:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.root,
                                new FragmentCreatorButton())
                        .commit();
                break;
            case EXTRA_SEEK_BAR:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.root,
                                new FragmentCreatorSeekBar())
                        .commit();
                break;
            case EXTRA_SENSOR:break;
        }

    }
}
