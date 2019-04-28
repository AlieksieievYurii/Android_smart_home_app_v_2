package com.yuriialieksieiev.smarthome.components.exceptions;

public class TypeTaskException extends Exception
{
    public TypeTaskException(String message) {
        super("Wrong typeTask:" + message);
    }
}
