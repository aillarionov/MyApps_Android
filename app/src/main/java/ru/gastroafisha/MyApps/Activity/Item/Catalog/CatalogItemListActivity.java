package ru.gastroafisha.MyApps.Activity.Item.Catalog;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import ru.gastroafisha.MyApps.Activity.Item.CommonItemListActivity;
import ru.gastroafisha.MyApps.Adapter.Item.CatalogItemsAdapter;
import ru.gastroafisha.MyApps.Adapter.Item.CommonItemAdapter;
import ru.gastroafisha.MyApps.MainApplication;
import ru.gastroafisha.MyApps.Model.Item.CatalogItemFavoriteModel;
import ru.gastroafisha.MyApps.R;

public class CatalogItemListActivity extends CommonItemListActivity<CatalogItemFavoriteModel> {

    @Override
    protected Integer getLayoutId() {
        return R.layout.content_catalog_item_list;
    }

    @Override
    protected Flowable<List<CatalogItemFavoriteModel>> getList(Integer catalogId) {
        return ((MainApplication) getApplication()).getLocalDataStorage().getCatalogItems().list(getOrgId(),  catalogId);
    }

    @Override
    protected CommonItemAdapter getListAdapter(List<CatalogItemFavoriteModel> items) {
        return  new CatalogItemsAdapter(this, getOrgId(), items, getFormDisposable());
    }
}
