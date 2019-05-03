package com.example.registrationapp.util;

import android.app.Application;
import android.widget.Toast;

public class App extends Application {

    CheckInternet checkInternet;
    @Override
    public void onCreate() {
        super.onCreate();

        //check internet connection
        checkInternet = new CheckInternet(this);
        if (checkInternet.isConnected()) {
        } else {

            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
        }
    }
}
