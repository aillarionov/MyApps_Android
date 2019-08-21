package ru.gastroafisha.MyApps.Activity.Item.Special;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.gastroafisha.MyApps.Activity.CommonActivity;
import ru.gastroafisha.MyApps.Adapter.Item.AbstractAdapter;
import ru.gastroafisha.MyApps.MainApplication;
import ru.gastroafisha.MyApps.Model.Item.CatalogAbstractFavoriteModel;
import ru.gastroafisha.MyApps.R;
import ru.gastroafisha.MyApps.Utils.Classes.DisposableHolder;
import ru.gastroafisha.MyApps.Utils.Const;
import ru.gastroafisha.MyApps.Utils.EventUtils;

public class SearchCatalogActivityItem extends CommonActivity {

    private RecyclerView recyclerView;

    private List<CatalogAbstractFavoriteModel> items = new ArrayList<>();

    DisposableHolder disposableTask = new DisposableHolder();

    @Override
    protected Integer getLayoutId() {
        return R.layout.content_search_catalog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView = findViewById(R.id.list_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        AbstractAdapter adapter = new AbstractAdapter(this, getOrgId(), items, getFormDisposable());
        recyclerView.setAdapter(adapter);

        EditText searchTextEdit = (EditText) findViewById(R.id.search_textedit);
        searchTextEdit.addTextChangedListener(
                EventUtils.generateDelayedTextWatcher(Const.USER_INPUT_DELAY, s -> UpdateList(s))
        );
    }

    private void UpdateList(String s) {

        disposableTask.dispose();

        disposableTask.setDisposable(Flowable.combineLatest(
                ((MainApplication) getApplication()).getLocalDataStorage().getCatalogItems().search(getOrgId(), "%" + s + "%"),
                ((MainApplication) getApplication()).getLocalDataStorage().getCatalogMembers().search(getOrgId(), "%" + s + "%"),
                ((MainApplication) getApplication()).getLocalDataStorage().getCatalogNews().search(getOrgId(), "%" + s + "%"),
                ((MainApplication) getApplication()).getLocalDataStorage().getCatalogImages().search(getOrgId(), "%" + s + "%"),
                (catalogItemFavoriteModels, catalogMemberFavoriteModels, catalogNewsFavoriteModels, catalogImageFavoriteModels) -> {
                    List<CatalogAbstractFavoriteModel> list = new ArrayList<>();
                    list.addAll(catalogItemFavoriteModels);
                    list.addAll(catalogMemberFavoriteModels);
                    list.addAll(catalogNewsFavoriteModels);
                    list.addAll(catalogImageFavoriteModels);
                    return list;
                }
        )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_items -> {
                    items.clear();
                    items.addAll(_items);
                    recyclerView.getAdapter().notifyDataSetChanged();
                }));

    }

    @Override
    protected void onStop() {
        super.onStop();

        disposableTask.dispose();
    }
}
