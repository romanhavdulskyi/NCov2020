package com.demo.app.basics.mvvm;

import android.app.Application;


public class BaseAndroidViewModel extends ViewModel {
    private final Application application;

    public BaseAndroidViewModel(Application application)
    {
        this.application = application;
    }

    public Application getApplication() {
        return application;
    }
}
