package com.demo.app.basics.mvvm;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.hg.basics.koin.JavaKoin;
import org.koin.core.scope.Scope;
import org.koin.java.KoinJavaComponent;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    private Scope scope;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    protected void onStop() {
        //disposeScope();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        disposeScope();
        super.onDestroy();
    }

    protected void createScope() {
        if (scope == null)
            scope = JavaKoin.createScopeWithContext(this);
    }

    protected void disposeScope() {
        if (scope != null) {
            scope.close();
            scope = null;
        }
    }

    protected <T> T instanceOf(Class<T> componentClass) {
        T result;
        if (scope != null)
            result = scope.get(componentClass);
        else
            result = KoinJavaComponent.get(componentClass);
        return result;
    }


}
