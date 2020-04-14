package com.demo.app.basics;

import com.demo.app.basics.mvvm.ViewModel;

public interface ViewModelFactory {
    ViewModel createViewModel(Class className);
}
