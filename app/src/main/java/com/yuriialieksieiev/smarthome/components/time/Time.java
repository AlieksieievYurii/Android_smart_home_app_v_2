package com.yuriialieksieiev.smarthome.components.time;

public class Time
{
    private byte hh;
    private byte mm;

    public Time(byte hh, byte mm) {
        this.hh = hh;
        this.mm = mm;
    }

    public byte getHh() {
        return hh;
    }

    public byte getMm() {
        return mm;
    }

    public static Time parse(String s)
    {
        final String[] p = s.split(":");

        final byte hh = Byte.parseByte(p[0]);
        final byte mm = Byte.parseByte(p[1]);

        return new Time(hh,mm);
    }
}
