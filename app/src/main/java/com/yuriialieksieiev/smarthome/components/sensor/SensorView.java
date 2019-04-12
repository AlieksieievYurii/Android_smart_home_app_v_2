package com.yuriialieksieiev.smarthome.components.sensor;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import com.yuriialieksieiev.smarthome.components.OnLongPressAction;
import com.yuriialieksieiev.smarthome.utils.SharedPreferences;

public class SensorView implements View.OnLongClickListener {

    private TextView tvValue;
    private SensorVal sensorVal;
    private OnLongPressAction onLongPressAction;

    public SensorView(TextView tvValue, SensorVal sensorVal, OnLongPressAction onLongPressAction) {
        this.tvValue = tvValue;
        this.sensorVal = sensorVal;
        this.onLongPressAction = onLongPressAction;
        tvValue.setText(String.valueOf(sensorVal.getValue()));
        tvValue.setOnLongClickListener(this);
    }

    public TextView getTvValue() {
        return tvValue;
    }

    public SensorVal getSensorVal() {
        return sensorVal;
    }

    public void setSensorVal(SensorVal sensorVal) {
        this.sensorVal = sensorVal;
        tvValue.setText(String.valueOf(sensorVal.getValue()));
    }

    @Override
    public boolean onLongClick(View v)
    {
        onLongPressAction.onLongPressSensor(sensorVal);
        return true;
    }

    public static class Builder{
        public static SensorView build(Context context,
                                      SensorVal sensorVal,
                                       OnLongPressAction onLongPressAction)
        {
            final TextView textView = new TextView(context);
            int width = SharedPreferences.getWidthViews(context);
            int height = SharedPreferences.getHeightViews(context);
            final GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(
                    new ViewGroup.LayoutParams(width, height));
            layoutParams.setMargins(10, 20, 10, 0);
            textView.setLayoutParams(layoutParams);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(12);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setPadding(5, 190, 5, 2);
            textView.setBackgroundResource(sensorVal.getSensor().getRes());
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            return new SensorView(textView,sensorVal,onLongPressAction);
        }
    }
}
