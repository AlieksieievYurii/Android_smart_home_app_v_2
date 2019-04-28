package com.yuriialieksieiev.smarthome;


import com.yuriialieksieiev.smarthome.framents.FragmentActions;

public interface IView
{
    void error(String mes);
    void error(FragmentActions.CallBack callBack);
}

