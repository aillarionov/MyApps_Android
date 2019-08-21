package ru.gastroafisha.MyApps.Activity.Item.Special;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.gastroafisha.MyApps.Activity.CommonActivity;
import ru.gastroafisha.MyApps.Adapter.Item.AbstractAdapter;
import ru.gastroafisha.MyApps.MainApplication;
import ru.gastroafisha.MyApps.Model.Item.CatalogAbstractFavoriteModel;
import ru.gastroafisha.MyApps.R;

public class FavoritesCatalogActivityItem extends CommonActivity {

    private RecyclerView recyclerView;

    private List<CatalogAbstractFavoriteModel> items = new ArrayList<>();
    private Map<String, List<CatalogAbstractFavoriteModel>> itemsMap = new HashMap<>();

    @Override
    protected Integer getLayoutId() {
        return R.layout.content_favorites_catalog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView = findViewById(R.id.list_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        AbstractAdapter adapter = new AbstractAdapter(this, getOrgId(), items, getFormDisposable());
        recyclerView.setAdapter(adapter);

        String s = "";

        getFormDisposable().add(Flowable.combineLatest(
                ((MainApplication) getApplication()).getLocalDataStorage().getCatalogItems().favorites(getOrgId(), "%" + s + "%"),
                ((MainApplication) getApplication()).getLocalDataStorage().getCatalogMembers().favorites(getOrgId(), "%" + s + "%"),
                ((MainApplication) getApplication()).getLocalDataStorage().getCatalogNews().favorites(getOrgId(), "%" + s + "%"),
                ((MainApplication) getApplication()).getLocalDataStorage().getCatalogImages().favorites(getOrgId(), "%" + s + "%"),
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


}
