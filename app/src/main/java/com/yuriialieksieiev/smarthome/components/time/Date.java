package com.yuriialieksieiev.smarthome.components.time;

public class Date
{
    private byte dd;
    private byte mm;
    private short yy;

    public Date(byte dd, byte mm, short yy) {
        this.dd = dd;
        this.mm = mm;
        this.yy = yy;
    }

    public byte getDd() {
        return dd;
    }

    public byte getMm() {
        return mm;
    }

    public short getYy() {
        return yy;
    }

    public static Date parse(String json)
    {
        final String[] s = json.split("\\.");

        final byte dd = Byte.parseByte(s[0]);
        final byte mm = Byte.parseByte(s[1]);
        final short yy = Short.parseShort(s[2]);

        return new Date(dd,mm,yy);
    }
}
