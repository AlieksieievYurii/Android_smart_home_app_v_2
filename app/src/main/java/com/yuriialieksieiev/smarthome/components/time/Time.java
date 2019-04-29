package com.yuriialieksieiev.smarthome.components.time;

public class Time {
    private int hh;
    private int mm;

    public Time(int hh, int mm) {
        this.hh = hh;
        this.mm = mm;
    }

    public int getHh() {
        return hh;
    }

    public int getMm() {
        return mm;
    }

    @Override
    public String toString() {
        return (hh<10?"0"+hh:hh) + ":" + (mm<10?"0"+mm:mm);
    }

    public static Time parse(String s) {
        final String[] p = s.split(":");

        final byte hh = Byte.parseByte(p[0]);
        final byte mm = Byte.parseByte(p[1]);

        return new Time(hh, mm);
    }
}
