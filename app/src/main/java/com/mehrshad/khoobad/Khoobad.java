package com.mehrshad.khoobad;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class Khoobad extends Application {

    @SuppressLint("StaticFieldLeak")
    public static Context context;

    public Khoobad() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
    }

    public static Context getContext()
    {
        return context;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
