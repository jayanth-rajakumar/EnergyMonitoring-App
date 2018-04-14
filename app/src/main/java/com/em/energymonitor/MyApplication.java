package com.em.energymonitor;

import android.app.Application;

/**
 * Created by Jayanth Rajakumar on 24-Feb-18.
 */

public class MyApplication extends Application {

    public float[] arr=new float[100];
    public float[] arr_vrms=new float[100];
    public float[] arr_irms=new float[100];
    public float[] arr_freq=new float[100];
    char c='E';

    public int arrkey=0,arrkey_irms=0,arrkey_vrms=0,arrkey_freq=0;
    public String username="User_01";
    public int getSomeVariable() {
        return arrkey;
    }


}