package ru.gastroafisha.MyApps.Activity;

import android.content.Intent;
import android.os.Bundle;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by alex on 15.01.2018.
 */

public abstract class CommonShowActivity<T> extends CommonActivity {

    private Integer catalogId;
    private Integer itemId;
    private T item;

    protected abstract Flowable<T> loadItem(Integer catalogId, Integer itemId);
    protected abstract void fillItem(T item);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        catalogId = intent.getIntExtra("catalogId", 0);
        itemId = intent.getIntExtra("itemId", 0);

        getFormDisposable().add(loadItem(catalogId, itemId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_item -> {
                    item = _item;
                    fillItem(item);
                }));
    }
}
