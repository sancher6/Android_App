package com.e.raspberrypiclient;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

public class GlobalApplication extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();

        /* If you has other classes that need context object to initialize when application is created,
         you can use the appContext here to process. */
    }

    public static Context getAppContext() {
        return appContext;
    }

    public static void makeToast(String Butter){
        Toast.makeText(getAppContext(), Butter,
                Toast.LENGTH_SHORT).show();
    }
}
