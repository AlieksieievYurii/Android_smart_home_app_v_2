package com.yuriialieksieiev.smarthome.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.yuriialieksieiev.smarthome.R;
import com.yuriialieksieiev.smarthome.utils.SharedPreferences;

public class SettingsActivity extends AppCompatActivity
{
    private EditText edtUrlServer;
    private EditText edtModuleName;
    private EditText edtPassword;
    private EditText edtWidthViews;
    private EditText edtHeightViews;
    private Switch vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings");

        edtUrlServer = findViewById(R.id.edt_url_server);
        edtModuleName = findViewById(R.id.edt_name_servlet);
        edtPassword = findViewById(R.id.edt_password);
        edtWidthViews = findViewById(R.id.edt_width_buttons);
        edtHeightViews = findViewById(R.id.edt_height_buttons);
        vibrator = findViewById(R.id.sw_vibrate);
        vibrator.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.saveModeVibration(SettingsActivity.this,isChecked);
            }
        });

        findViewById(R.id.btn_add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MakerView.class);
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

        findViewById(R.id.btn_add_sensor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this,MakerView.class);
                intent.putExtra(MakerView.EXTRA_WHAT_VIEW,MakerView.EXTRA_SENSOR);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.saveUrlToServer(SettingsActivity.this,edtUrlServer.getText().toString());
                SharedPreferences.saveModuleName(SettingsActivity.this,edtModuleName.getText().toString());
                SharedPreferences.savePasswordToServer(SettingsActivity.this,edtPassword.getText().toString());
                try {
                    SharedPreferences.saveWidthViews(SettingsActivity.this,Integer.parseInt(edtWidthViews.getText().toString()));
                    SharedPreferences.saveHeightViews(SettingsActivity.this,Integer.parseInt(edtHeightViews.getText().toString()));
                }catch (Exception ignored){}

                finish();
            }
        });

        init();
    }

    private void init()
    {
        String urlServer = SharedPreferences.getUrlToServer(this);

        if(urlServer != null)
            edtUrlServer.setText(urlServer);

        String moduleName = SharedPreferences.getServerName(this);

        if(moduleName != null)
            edtModuleName.setText(moduleName);

        String password = SharedPreferences.getPasswordServer(this);

        if(password != null)
            edtPassword.setText(password);

        edtWidthViews.setText(String.valueOf(SharedPreferences.getWidthViews(this)));
        edtHeightViews.setText(String.valueOf(SharedPreferences.getHeightViews(this)));

        vibrator.setChecked(SharedPreferences.isVibrated(this));
    }
}
