package com.yuriialieksieiev.smarthome.components.time;

import android.os.Parcel;
import android.os.Parcelable;

public class Time implements Parcelable {
    private int hh;
    private int mm;

    public Time(int hh, int mm) {
        this.hh = hh;
        this.mm = mm;
    }

    private Time(Parcel in) {
        hh = in.readInt();
        mm = in.readInt();
    }

    public static final Creator<Time> CREATOR = new Creator<Time>() {
        @Override
        public Time createFromParcel(Parcel in) {
            return new Time(in);
        }

        @Override
        public Time[] newArray(int size) {
            return new Time[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(hh);
        dest.writeInt(mm);
    }
}
