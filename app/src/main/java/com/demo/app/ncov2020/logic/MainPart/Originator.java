package com.demo.app.ncov2020.logic.MainPart;

public interface Originator<T extends Memento> {
    T makeSnapshot();
    void loadSnapshot(T snapshot);
}
