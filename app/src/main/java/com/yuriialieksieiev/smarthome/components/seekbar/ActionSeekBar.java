package com.yuriialieksieiev.smarthome.components.seekbar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.yuriialieksieiev.smarthome.R;
import com.yuriialieksieiev.smarthome.components.Action;
import com.yuriialieksieiev.smarthome.components.OnAction;
import com.yuriialieksieiev.smarthome.components.OnLongPressAction;
import com.yuriialieksieiev.smarthome.utils.SharedPreferences;

import abak.tr.com.boxedverticalseekbar.BoxedVertical;

public class ActionSeekBar implements BoxedVertical.OnValuesChangeListener, View.OnLongClickListener, Parcelable {
    private static final String TAG_SEEK_BAR = "seekBar";

    private Action action;
    private View seekBar;
    private OnAction onAction;
    private OnLongPressAction onLongPressAction;

    private String name;
    private BoxedVertical boxedVertical;

    private ActionSeekBar(Action action,
                          View seekBar,
                          OnAction onAction,
                          OnLongPressAction onLongPressAction) {
        this.action = action;
        this.seekBar = seekBar;
        this.onAction = onAction;
        this.onLongPressAction = onLongPressAction;
        boxedVertical = seekBar.findViewWithTag(TAG_SEEK_BAR);
        boxedVertical.setOnBoxedPointsChangeListener(this);
        seekBar.setOnLongClickListener(this);
    }

    private ActionSeekBar(Parcel in) {
        action = in.readParcelable(Action.class.getClassLoader());
        name = in.readString();
    }

    public static final Creator<ActionSeekBar> CREATOR = new Creator<ActionSeekBar>() {
        @Override
        public ActionSeekBar createFromParcel(Parcel in) {
            return new ActionSeekBar(in);
        }

        @Override
        public ActionSeekBar[] newArray(int size) {
            return new ActionSeekBar[size];
        }
    };

    @Override
    public void onPointsChanged(BoxedVertical boxedVertical, int i) {

    }

    public View getSeekBar() {
        return seekBar;
    }

    public String getName() {
        return name;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action)
    {
        this.action.setPinSignal(action.getPinSignal());
        boxedVertical.setValue(action.getPinSignal());
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void onStartTrackingTouch(BoxedVertical boxedVertical) {

    }

    @Override
    public void onStopTrackingTouch(BoxedVertical boxedVertical) {
        action.setPinSignal(boxedVertical.getValue());
        onAction.onAction(action);
    }

    @Override
    public boolean onLongClick(View v) {
        onLongPressAction.onLongPressSeekBarAction(this);
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(action, flags);
        dest.writeString(name);
    }

    public static class Builder {
        public static ActionSeekBar build(Context context,
                                          OnAction onAction,
                                          PatternActionSeekBar patternActionSeekBar,
                                          OnLongPressAction onLongPressAction) {
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
            int height = SharedPreferences.getHeightViews(context);
            int wight = SharedPreferences.getWidthViews(context);
            final GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(
                    new ViewGroup.LayoutParams(wight, height * 2 + 25));
            layoutParams.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 2);
            layoutParams.setMargins(10, 20, 10, 10);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(0, 0, 0, 20);
            linearLayout.setGravity(RelativeLayout.CENTER_HORIZONTAL);
            linearLayout.setBackgroundResource(R.drawable.bc_seek_bar);

            linearLayout.addView(boxedVertical);
            linearLayout.addView(textView);

            return new ActionSeekBar(patternActionSeekBar.getAction(),
                    linearLayout,
                    onAction, onLongPressAction);
        }
    }
}
