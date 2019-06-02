package com.yuriialieksieiev.smarthome.components;

import android.os.Parcel;
import android.os.Parcelable;

import com.yuriialieksieiev.smarthome.components.enums.Device;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class Action implements Parcelable {
    private static final String F_ACTION_EXTRA_DEVICE = "device";
    private static final String F_ACTION_EXTRA_TYPE_PIN = "type_port";
    private static final String F_ACTION_EXTRA_PIN = "port";
    private static final String F_ACTION_EXTRA_PIN_STATUS = "port_status";
    private static final String F_ACTION_EXTRA_SIGNAL_ON_PIN = "signal_on_port";

    private static final String API_EXTRA_PIN_TYPE = "pin_type";
    private static final String API_EXTRA_PIN_ID = "pin_id";
    private static final String API_EXTRA_FOR_DEVICE = "for_device";
    private static final String API_EXTRA_PIN_STATUS = "pin_status";
    private static final String API_EXTRA_PIN_VALUE = "pin_value";

    public enum TypePin {
        DIGITAL("digital"), ANALOG("analog");

        private String inJson;

        TypePin(String inJson) {
            this.inJson = inJson;
        }

        public String getInJson() {
            return inJson;
        }

        public static TypePin getTypePin(String inJson) {
            if (inJson.equals(DIGITAL.inJson))
                return DIGITAL;
            else if (inJson.equals(ANALOG.inJson))
                return ANALOG;
            else
                return null;
        }

    }

    public enum PinStatus {
        HIGH("HIGH"), LOW("LOW");

        private String inJson;

        PinStatus(String inJson) {
            this.inJson = inJson;
        }

        public String getInJson() {
            return inJson;
        }

        public static PinStatus getPinStatus(String inJson) {
            if (inJson.equals(HIGH.inJson))
                return HIGH;
            else if (inJson.equals(LOW.inJson))
                return LOW;
            else
                return null;
        }
    }

    private TypePin typePin;
    private int pin;
    private PinStatus pinStatus;
    private int pinSignal;
    private Device device;

    public Action(Device device, int pin, PinStatus pinStatus) {
        this.device = device;
        this.typePin = TypePin.DIGITAL;
        this.pin = pin;
        this.pinStatus = pinStatus;
    }

    public Action(Device device, int pin, int pinSignal) {
        this.typePin = TypePin.ANALOG;
        this.pin = pin;
        this.pinSignal = pinSignal;
        this.device = device;
    }

    public TypePin getTypePin() {
        return typePin;
    }

    public int getPin() {
        return pin;
    }

    public PinStatus getPinStatus() {
        return pinStatus;
    }

    public int getPinSignal() {
        return pinSignal;
    }

    public void setPinStatus(PinStatus pinStatus) {
        this.pinStatus = pinStatus;
    }

    public void setPinSignal(int pinSignal) {
        this.pinSignal = pinSignal;
    }

    public Device getDevice() {
        return device;
    }

    public boolean equals(Action action)
    {
            return (action.typePin == this.typePin &&
                    action.pin == this.pin &&
                    action.device == this.device);
    }

    protected Action(Parcel in) {
        pin = in.readInt();
        device = Device.getDeviceByName(Objects.requireNonNull(in.readString()));
        typePin = TypePin.getTypePin(Objects.requireNonNull(in.readString()));
        switch (Objects.requireNonNull(typePin))
        {
            case DIGITAL:
                pinStatus = PinStatus.getPinStatus(Objects.requireNonNull(in.readString()));
                break;
            case ANALOG:
                pinSignal = in.readInt();
                break;
        }
    }

    public static final Creator<Action> CREATOR = new Creator<Action>() {
        @Override
        public Action createFromParcel(Parcel in) {
            return new Action(in);
        }

        @Override
        public Action[] newArray(int size) {
            return new Action[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pin);
        dest.writeString(device.getInJson());
        dest.writeString(typePin.inJson);
        switch (typePin)
        {
            case DIGITAL:
                dest.writeString(pinStatus.getInJson());
                break;
            case ANALOG:
                dest.writeInt(pinSignal);
                break;
        }

    }

    //----------------------------------------------------------------------



    public static Action parseFactoryJson(JSONObject jsonObject) throws JSONException {
        TypePin typePin = TypePin.getTypePin(jsonObject.getString(F_ACTION_EXTRA_TYPE_PIN));
        int pin = jsonObject.getInt(F_ACTION_EXTRA_PIN);

        if (typePin == TypePin.DIGITAL) {
            PinStatus pinStatus = PinStatus.getPinStatus(jsonObject.getString(F_ACTION_EXTRA_PIN_STATUS));
            return new Action(Device.getDeviceByName(jsonObject.getString(F_ACTION_EXTRA_DEVICE)),pin, pinStatus);

        } else if (typePin == TypePin.ANALOG) {
            int pinSignal = jsonObject.getInt(F_ACTION_EXTRA_SIGNAL_ON_PIN);
            return new Action(Device.getDeviceByName(jsonObject.getString(F_ACTION_EXTRA_DEVICE)), pin,pinSignal);
        } else
            throw new JSONException("From Building:: Can not convert " + jsonObject.toString() + " to Action object!");
    }

    public static Action parseAPI(JSONObject jsonObject) throws JSONException {
        TypePin typePin = TypePin.getTypePin(jsonObject.getString(API_EXTRA_PIN_TYPE));
        int pin = jsonObject.getInt(API_EXTRA_PIN_ID);
        Device device = Device.getDeviceByName(jsonObject.getString(API_EXTRA_FOR_DEVICE));

        if (typePin == TypePin.DIGITAL) {
            PinStatus pinStatus = PinStatus.getPinStatus(jsonObject.getString(API_EXTRA_PIN_STATUS));
            return new Action(device,pin, pinStatus);

        } else if (typePin == TypePin.ANALOG) {
            int pinSignal = jsonObject.getInt(API_EXTRA_PIN_VALUE);
            return new Action(device, pin,pinSignal);
        } else
            throw new JSONException("From API:: Can not convert " + jsonObject.toString() + " to Action object!");
    }

    public static JSONObject toFactoryJson(Action action) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(F_ACTION_EXTRA_DEVICE,action.getDevice().getInJson());
        jsonObject.put(F_ACTION_EXTRA_TYPE_PIN, action.getTypePin().inJson);
        jsonObject.put(F_ACTION_EXTRA_PIN, action.getPin());

        if (action.getTypePin() == TypePin.ANALOG)
            jsonObject.put(F_ACTION_EXTRA_SIGNAL_ON_PIN, action.getPinSignal());
        else if(action.getTypePin() == TypePin.DIGITAL)
            jsonObject.put(F_ACTION_EXTRA_PIN_STATUS,action.getPinStatus().inJson);

        return jsonObject;
    }

    public static JSONObject toAPI(Action action) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(API_EXTRA_FOR_DEVICE, action.getDevice().getInJson());
        jsonObject.put(API_EXTRA_PIN_TYPE, action.getTypePin().getInJson());
        jsonObject.put(API_EXTRA_PIN_ID, action.getPin());

        if (action.getTypePin() == TypePin.DIGITAL)
            jsonObject.put(API_EXTRA_PIN_STATUS, action.getPinStatus().getInJson());
        else if (action.getTypePin() == TypePin.ANALOG)
            jsonObject.put(API_EXTRA_PIN_VALUE, action.getPinSignal());

        return jsonObject;
    }

}
