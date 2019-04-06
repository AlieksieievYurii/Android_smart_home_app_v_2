package com.yuriialieksieiev.smarthome.components.Button;

import org.json.JSONException;
import org.json.JSONObject;

public class Action {
    static final String ACTION_EXTRA_TYPE_PORT = "type_port";
    static final String ACTION_EXTRA_PORT = "port";
    static final String ACTION_EXTRA_PORT_STATUS = "port_status";
    static final String ACTION_EXTRA_SIGNAL_ON_PORT = "signal_on_port";

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

    public Action() {
    }

    public Action(int port, PortStatus portStatus) {
        this.typePort = TypePort.DIGITAL;
        this.port = port;
        this.portStatus = portStatus;
    }

    public Action(int port, int portSignal) {
        this.typePort = TypePort.ANALOG;
        this.port = port;
        this.portSignal = portSignal;
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


    public boolean equals(Action action)
    {
        if(action.typePort == this.typePort &&
            action.port == this.port)
            if(action.typePort == TypePort.DIGITAL)
                return action.portStatus == this.portStatus;
            else if(action.typePort == TypePort.ANALOG)
                return action.portSignal == this.portSignal;
            else
                return false;
        else
            return false;
    }

    @Override
    public String toString() {
        return "Action{" +
                "typePort=" + typePort +
                ", port=" + port +
                ", portStatus=" + portStatus +
                ", portSignal=" + portSignal +
                '}';
    }

    //----------------------------------------------------------------------



    public static Action getActionByJSon(JSONObject jsonObject) throws JSONException {
        Action.TypePort typePort = Action.TypePort.getTypePort(jsonObject.getString(ACTION_EXTRA_TYPE_PORT));
        int port = jsonObject.getInt(ACTION_EXTRA_PORT);

        if (typePort == TypePort.DIGITAL) {
            PortStatus portStatus = PortStatus.getPortStatus(jsonObject.getString(ACTION_EXTRA_PORT_STATUS));
            return new Action(port, portStatus);
        } else if (typePort == TypePort.ANALOG) {
            int signalOnPort = jsonObject.getInt(ACTION_EXTRA_SIGNAL_ON_PORT);
            return new Action(port, signalOnPort);
        } else
            throw new JSONException("Can not convert " + jsonObject.toString() + " to Action object!");
    }

    JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ACTION_EXTRA_TYPE_PORT, typePort.inJson);
        jsonObject.put(ACTION_EXTRA_PORT, port);

        if (typePort == TypePort.ANALOG)
            jsonObject.put(ACTION_EXTRA_SIGNAL_ON_PORT, portSignal);
        else if(typePort == TypePort.DIGITAL)
            jsonObject.put(ACTION_EXTRA_PORT_STATUS,portStatus.inJson);

        return jsonObject;
    }

}