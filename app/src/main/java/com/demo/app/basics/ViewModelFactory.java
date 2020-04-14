package com.demo.app.basics;

import androidx.lifecycle.ViewModel;

public interface ViewModelFactory {
    ViewModel createViewModel(Class className);
}
