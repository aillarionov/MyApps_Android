package ru.gastroafisha.MyApps.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.gastroafisha.MyApps.Adapter.CommonAdapter;
import ru.gastroafisha.MyApps.R;

/**
 * Created by alex on 15.01.2018.
 */

public abstract class CommonListActivity<T> extends CommonActivity {

    private RecyclerView recyclerView;

    private List<T> items = new ArrayList<>();

    protected abstract Flowable<List<T>> getList();
    protected abstract CommonAdapter getListAdapter(List<T> items);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView = findViewById(R.id.list_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        CommonAdapter adapter = this.getListAdapter(items);
        recyclerView.setAdapter(adapter);

        getFormDisposable().add(getList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_items -> {
                    items.clear();
                    items.addAll(_items);
                    recyclerView.getAdapter().notifyDataSetChanged();
                }));
    }

}
