package com.yuriialieksieiev.smarthome.components.time;

import android.os.Parcel;
import android.os.Parcelable;

public class Date implements Parcelable
{

    public static final String NONE = "none";
    private int dd;
    private int mm;
    private int yy;

    public Date(int dd, int mm, int yy) {
        this.dd = dd;
        this.mm = mm;
        this.yy = yy;
    }

    private Date(Parcel in) {
        dd = in.readInt();
        mm = in.readInt();
        yy = in.readInt();
    }

    public static final Creator<Date> CREATOR = new Creator<Date>() {
        @Override
        public Date createFromParcel(Parcel in) {
            return new Date(in);
        }

        @Override
        public Date[] newArray(int size) {
            return new Date[size];
        }
    };

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
        if(json.equals(NONE))
            return null;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(dd);
        dest.writeInt(mm);
        dest.writeInt(yy);
    }
}
