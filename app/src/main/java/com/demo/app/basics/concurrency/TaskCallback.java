package com.demo.app.basics.concurrency;

@FunctionalInterface
public interface TaskCallback<T> {
    void onCompleted(T result, Fault fault);
}