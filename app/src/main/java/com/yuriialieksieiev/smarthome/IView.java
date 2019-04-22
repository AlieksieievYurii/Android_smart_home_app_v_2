package com.yuriialieksieiev.smarthome;

import com.yuriialieksieiev.smarthome.components.SnackBarRetry;

public interface IView
{
    void error(String mes);
    void error(SnackBarRetry.CallBack callBack);
}

