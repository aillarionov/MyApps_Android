package ru.gastroafisha.MyApps.Utils.Classes;

/**
 * Created by alex on 21.01.2018.
 */

public class Holder<T> {
    private T value;

    public Holder() {
        this.value = null;
    }

    public Holder(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
