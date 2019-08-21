package ru.gastroafisha.MyApps.Utils.Classes;

import io.reactivex.disposables.Disposable;

/**
 * Created by alex on 28.01.2018.
 */

public class DisposableHolder implements Disposable {
    private Disposable disposable;

    public DisposableHolder() {
    }

    public DisposableHolder(Disposable disposable) {
        this.disposable = disposable;
    }

    public void setDisposable(Disposable disposable) {
        this.disposable = disposable;
    }

    public Disposable getDisposable() {
        return disposable;
    }

    @Override
    public void dispose() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public boolean isDisposed() {
        return disposable == null || disposable.isDisposed();
    }
}
