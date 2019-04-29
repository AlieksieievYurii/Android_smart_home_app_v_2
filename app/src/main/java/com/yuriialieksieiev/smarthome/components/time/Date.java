package com.yuriialieksieiev.smarthome.components.time;

public class Date
{
    private int dd;
    private int mm;
    private int yy;

    public Date(int dd, int mm, int yy) {
        this.dd = dd;
        this.mm = mm;
        this.yy = yy;
    }

    public int getDd() {
        return dd;
    }

    public int getMm() {
        return mm;
    }

    public int getYy() {
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

    @Override
    public String toString() {
        return (dd<10?"0"+dd:dd)+"."+(mm<10?"0"+mm:mm)+"."+yy;
    }
}
