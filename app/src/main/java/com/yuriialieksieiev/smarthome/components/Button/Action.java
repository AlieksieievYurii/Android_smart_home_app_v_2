package com.yuriialieksieiev.smarthome.components.Button;

import android.os.Parcel;
import android.os.Parcelable;

import com.yuriialieksieiev.smarthome.components.Device;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class Action implements Parcelable {
    private static final String ACTION_EXTRA_DEVICE = "device";
    private static final String ACTION_EXTRA_TYPE_PORT = "type_port";
    private static final String ACTION_EXTRA_PORT = "port";
    private static final String ACTION_EXTRA_PORT_STATUS = "port_status";
    private static final String ACTION_EXTRA_SIGNAL_ON_PORT = "signal_on_port";

    public enum TypePort {
        DIGITAL("digital"), ANALOG("analog");

        private String inJson;

        TypePort(String inJson) {
            this.inJson = inJson;
        }

        public static TypePort getTypePort(String inJson) {
            if (inJson.equals(DIGITAL.inJson))
                return DIGITAL;
            else if (inJson.equals(ANALOG.inJson))
                return ANALOG;
            else
                return null;
        }
    }

    public enum PortStatus {
        HIGH("high"), LOW("low");

        private String inJson;

        PortStatus(String inJson) {
            this.inJson = inJson;
        }

        public static PortStatus getPortStatus(String inJson) {
            if (inJson.equals(HIGH.inJson))
                return HIGH;
            else if (inJson.equals(LOW.inJson))
                return LOW;
            else
                return null;
        }
    }

    private TypePort typePort;
    private int port;
    private PortStatus portStatus;
    private int portSignal;
    private Device device;

    public Action(Device device, int port, PortStatus portStatus) {
        this.device = device;
        this.typePort = TypePort.DIGITAL;
        this.port = port;
        this.portStatus = portStatus;
    }

    public Action(Device device,int port, int portSignal) {
        this.typePort = TypePort.ANALOG;
        this.port = port;
        this.portSignal = portSignal;
        this.device = device;
    }

    public TypePort getTypePort() {
        return typePort;
    }

    public int getPort() {
        return port;
    }

    public PortStatus getPortStatus() {
        return portStatus;
    }

    public int getPortSignal() {
        return portSignal;
    }

    void setPortStatus(PortStatus portStatus) {
        this.portStatus = portStatus;
    }

    public void setPortSignal(int portSignal) {
        this.portSignal = portSignal;
    }

    public Device getDevice() {
        return device;
    }

    public boolean equals(Action action)
    {
            return (action.typePort == this.typePort &&
                    action.port == this.port &&
                    action.device == this.device);
    }

    @Override
    public String toString() {
        return "Action{" +
                "typePort=" + typePort +
                ", port=" + port +
                ", portStatus=" + portStatus +
                ", portSignal=" + portSignal +
                ", device=" + device +
                '}';
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ACTION_EXTRA_DEVICE,device.getInJson());
        jsonObject.put(ACTION_EXTRA_TYPE_PORT, typePort.inJson);
        jsonObject.put(ACTION_EXTRA_PORT, port);

        if (typePort == TypePort.ANALOG)
            jsonObject.put(ACTION_EXTRA_SIGNAL_ON_PORT, portSignal);
        else if(typePort == TypePort.DIGITAL)
            jsonObject.put(ACTION_EXTRA_PORT_STATUS,portStatus.inJson);

        return jsonObject;
    }

    protected Action(Parcel in) {
        port = in.readInt();
        device = Device.getDeviceByName(Objects.requireNonNull(in.readString()));
        typePort = TypePort.getTypePort(Objects.requireNonNull(in.readString()));
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
        dest.writeInt(port);
        dest.writeString(device.getInJson());
        dest.writeString(typePort.inJson);
    }



    //----------------------------------------------------------------------



    public static Action getActionByJSon(JSONObject jsonObject) throws JSONException {
        Action.TypePort typePort = Action.TypePort.getTypePort(jsonObject.getString(ACTION_EXTRA_TYPE_PORT));
        int port = jsonObject.getInt(ACTION_EXTRA_PORT);

        if (typePort == TypePort.DIGITAL) {
            PortStatus portStatus = PortStatus.getPortStatus(jsonObject.getString(ACTION_EXTRA_PORT_STATUS));
            return new Action(Device.getDeviceByName(jsonObject.getString(ACTION_EXTRA_DEVICE)),port, portStatus);

        } else if (typePort == TypePort.ANALOG) {
            int signalOnPort = jsonObject.getInt(ACTION_EXTRA_SIGNAL_ON_PORT);
            return new Action(Device.getDeviceByName(jsonObject.getString(ACTION_EXTRA_DEVICE)), port,signalOnPort);
        } else
            throw new JSONException("Can not convert " + jsonObject.toString() + " to Action object!");
    }

}
