package com.yuriialieksieiev.smarthome.components.exceptions;

public class TaskModeException extends Exception {
    public TaskModeException(String message) {
        super("Wrong taskMode" + message);
    }
}
