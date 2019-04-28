package com.yuriialieksieiev.smarthome.components.exceptions;

public class TaskStatusException extends Exception {
    public TaskStatusException(String message) {
        super("Wrong task status:" + message);
    }
}
