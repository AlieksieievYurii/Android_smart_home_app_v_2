package com.yuriialieksieiev.smarthome.components.Button;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import com.yuriialieksieiev.smarthome.components.OnAction;
import com.yuriialieksieiev.smarthome.components.OnLongPressAction;
import com.yuriialieksieiev.smarthome.components.enums.Icons;
import com.yuriialieksieiev.smarthome.utils.SharedPreferences;

public class ActionButton implements View.OnClickListener, View.OnLongClickListener, Parcelable {
    private Button button;
    private Action action;
    private OnAction onAction;
    private OnLongPressAction onLongPressAction;

    private String name;
    private Icons icons;

    private ActionButton(Button button,
                         Action action,
                         OnAction onAction,
                         OnLongPressAction onLongPressAction) {
        this.button = button;
        this.action = action;
        this.onAction = onAction;
        this.onLongPressAction = onLongPressAction;
        button.setOnClickListener(this);
        button.setOnLongClickListener(this);

    }

    private ActionButton(Parcel in) {
        action = in.readParcelable(Action.class.getClassLoader());
        name = in.readString();
        icons = Icons.getEnumByName(in.readString());
    }

    public static final Creator<ActionButton> CREATOR = new Creator<ActionButton>() {
        @Override
        public ActionButton createFromParcel(Parcel in) {
            return new ActionButton(in);
        }

        @Override
        public ActionButton[] newArray(int size) {
            return new ActionButton[size];
        }
    };

    private void changeState() {
        if (button.isActivated())
            button.setActivated(false);
        else
            button.setActivated(true);
    }

    public void setAction(Action action) {
        this.action = action;

        if (action.getPortStatus() == Action.PortStatus.HIGH)
            button.setActivated(true);
        else if (action.getPortStatus() == Action.PortStatus.LOW)
            button.setActivated(false);
    }

    @Override
    public void onClick(View v) {
        changeState();
        action.setPortStatus(button.isActivated() ? Action.PortStatus.HIGH : Action.PortStatus.LOW);
        onAction.onAction(action);
    }

    public String getName() {
        return name;
    }

    public Icons getIcons() {
        return icons;
    }

    public Action getAction() {
        return action;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcons(Icons icons) {
        this.icons = icons;
    }

    public Button getButton() {
        return button;
    }

    @Override
    public boolean onLongClick(View v) {
        onLongPressAction.onLongPressButtonAction(this);
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
        dest.writeString(icons.getNameIcon());
    }

    @Override
    public String toString() {
        return "ActionButton{" +
                "button=" + button +
                ", action=" + action +
                ", onAction=" + onAction +
                ", onLongPressAction=" + onLongPressAction +
                ", name='" + name + '\'' +
                ", icons=" + icons +
                '}';
    }

    public static class Builder {
        public static ActionButton build(Context context,
                                         OnAction onAction,
                                         PatternActionButton patternActionButton,
                                         OnLongPressAction onLongPressAction) {
            final Button button = new Button(context);
            int width = SharedPreferences.getWidthViews(context);
            int height = SharedPreferences.getHeightViews(context);
            final GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(
                    new ViewGroup.LayoutParams(width, height));
            layoutParams.setMargins(10, 20, 10, 0);
            button.setLayoutParams(layoutParams);
            button.setTextColor(Color.WHITE);
            button.setTextSize(10);
            button.setPadding(5, 190, 5, 2);

            button.setText(patternActionButton.getName());
            button.setBackgroundResource(patternActionButton.getIcon().getDrawable());

            //TODO Implement it for custom settings by user!!!

            return new ActionButton(button,
                    patternActionButton.getAction(),
                    onAction,
                    onLongPressAction);
        }
    }

}
