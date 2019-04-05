package com.yuriialieksieiev.smarthome.enums;


import com.yuriialieksieiev.smarthome.R;

public enum Icons
{
    LAMP("lamp", R.drawable.lamp),
    COMPUTER("computer",R.drawable.computer),
    FAN("fan",R.drawable.fan),
    SOCKET("socket",R.drawable.socket);

    private String nameIcon;
    private int drawable;

    Icons(String nameIcon, int drawable) {
        this.nameIcon = nameIcon;
        this.drawable = drawable;
    }

    public String getNameIcon() {
        return nameIcon;
    }

    public int getDrawable() {
        return drawable;
    }

    public static Icons getEnumByName(String name)
    {
        for(Icons icon : Icons.class.getEnumConstants())
            if(icon.getNameIcon().equals(name))
                return icon;
        return null;
    }

    @Override
    public String toString() {
        return nameIcon;
    }
}
