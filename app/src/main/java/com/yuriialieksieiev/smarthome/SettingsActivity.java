package com.yuriialieksieiev.smarthome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        findViewById(R.id.btn_add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this,MakerView.class);
                intent.putExtra(MakerView.EXTRA_WHAT_VIEW,MakerView.EXTRA_BUTTON);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_add_seek_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this,MakerView.class);
                intent.putExtra(MakerView.EXTRA_WHAT_VIEW,MakerView.EXTRA_SEEK_BAR);
                startActivity(intent);
            }
        });

    }
}
