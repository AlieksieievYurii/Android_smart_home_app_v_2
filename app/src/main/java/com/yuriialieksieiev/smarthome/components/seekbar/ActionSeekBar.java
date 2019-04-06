package com.yuriialieksieiev.smarthome.components.seekbar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuriialieksieiev.smarthome.R;
import com.yuriialieksieiev.smarthome.components.Button.Action;

import abak.tr.com.boxedverticalseekbar.BoxedVertical;

public class ActionSeekBar implements BoxedVertical.OnValuesChangeListener
{
    private static final String TAG_SEEK_BAR = "seekBar";

    private Action action;
    private View seekBar;
    private OnActionChangeSeekBar actionChangeSeekBar;

    public ActionSeekBar(Action action, View seekBar, OnActionChangeSeekBar actionChangeSeekBar) {
        this.action = action;
        this.seekBar = seekBar;
        this.actionChangeSeekBar = actionChangeSeekBar;
        BoxedVertical boxedVertical = seekBar.findViewWithTag(TAG_SEEK_BAR);
        boxedVertical.setOnBoxedPointsChangeListener(this);
    }

    @Override
    public void onPointsChanged(BoxedVertical boxedVertical, int i) {

    }

    public View getSeekBar() {
        return seekBar;
    }

    @Override
    public void onStartTrackingTouch(BoxedVertical boxedVertical) {

    }

    @Override
    public void onStopTrackingTouch(BoxedVertical boxedVertical) {
        action.setPortSignal(boxedVertical.getValue());
        actionChangeSeekBar.onActionChangeSeekBar(action);
    }

    public static class Builder
    {
        public static ActionSeekBar build(Context context,
                                          OnActionChangeSeekBar onActionChangeSeekBar,
                                          PatternActionSeekBar patternActionSeekBar)
        {
            BoxedVertical boxedVertical = new BoxedVertical(context);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT));
            lp.weight = 1;

            boxedVertical.setLayoutParams(lp);
            boxedVertical.setCornerRadius(20);
            boxedVertical.setMax(255);
            boxedVertical.setStep(1);
            boxedVertical.setDefaultValue(0);
            boxedVertical.setTag(TAG_SEEK_BAR);

            TextView textView = new TextView(context);
            LinearLayout.LayoutParams lp_t = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp_t.weight = 0;

            textView.setLayoutParams(lp_t);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(10);
            textView.setAllCaps(true);
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            textView.setText(patternActionSeekBar.getName());

            LinearLayout linearLayout = new LinearLayout(context);
            final GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(
                    new ViewGroup.LayoutParams(250,500));
            layoutParams.rowSpec = GridLayout.spec(GridLayout.UNDEFINED,2);
            layoutParams.setMargins(10,20,10,10);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(0,0,0,20);
            linearLayout.setGravity(RelativeLayout.CENTER_HORIZONTAL);
            linearLayout.setBackgroundResource(R.drawable.bc_seek_bar);

            linearLayout.addView(boxedVertical);
            linearLayout.addView(textView);

            return new ActionSeekBar(patternActionSeekBar.getAction(),linearLayout,onActionChangeSeekBar);
        }
    }
}
