package ru.gastroafisha.MyApps.Activity.Item;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.crashlytics.android.Crashlytics;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.gastroafisha.MyApps.Activity.CommonListActivity;
import ru.gastroafisha.MyApps.MainApplication;
import ru.gastroafisha.MyApps.R;
import ru.gastroafisha.MyApps.Utils.Classes.DisposableHolder;

/**
 * Created by alex on 15.01.2018.
 */

public abstract class CommonItemListActivity<T> extends CommonListActivity<T> {

    private Integer catalogId;

    protected abstract Flowable<List<T>> getList(Integer catalogId);

    //protected abstract Completable updateList(Integer catalogId);
    protected Completable updateList(Integer catalogId) {
        return MainApplication.getInstance().getLocalDataStorage().refreshCatalogItems(getOrgId(), catalogId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        catalogId = intent.getIntExtra("catalogId", 0);

        super.onCreate(savedInstanceState);

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(() -> {

                DisposableHolder d = new DisposableHolder();

                d.setDisposable(updateList(catalogId)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {

                            swipeRefreshLayout.setRefreshing(false);
                            d.dispose();

                        }, throwable -> {

                            Crashlytics.logException(throwable);
                            swipeRefreshLayout.setRefreshing(false);
                            d.dispose();

                        }));

                getFormDisposable().add(d);
            });
        }

    }

    @Override
    protected final Flowable<List<T>> getList() {
        return getList(catalogId);
    }
}
