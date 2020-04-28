package com.demo.app.ncov2020.logic.MainPart;

public interface Memento<T> {
    T makeSnapshot();
    void loadSnapshot(T snapshot);
}
