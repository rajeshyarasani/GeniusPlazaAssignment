package com.test.genplaza.users;

import android.app.Application;

import com.test.genplaza.users.ui.database.Preference;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        ImageTableManager.initRoom(this);
        Preference.init(this);
    }
}
