package ru.gastroafisha.MyApps.Activity.Item.Catalog;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import ru.gastroafisha.MyApps.Activity.Item.CommonItemListActivity;
import ru.gastroafisha.MyApps.Adapter.Item.CatalogNewsAdapter;
import ru.gastroafisha.MyApps.Adapter.Item.CommonItemAdapter;
import ru.gastroafisha.MyApps.MainApplication;
import ru.gastroafisha.MyApps.Model.Item.CatalogNewsFavoriteModel;
import ru.gastroafisha.MyApps.R;

public class CatalogNewsItemListActivity extends CommonItemListActivity<CatalogNewsFavoriteModel> {

    @Override
    protected Integer getLayoutId() {
        return R.layout.content_catalog_news_list;
    }

    @Override
    protected Flowable<List<CatalogNewsFavoriteModel>> getList(Integer catalogId) {
        return ((MainApplication) getApplication()).getLocalDataStorage().getCatalogNews().list(getOrgId(), catalogId);
    }

    @Override
    protected CommonItemAdapter getListAdapter(List<CatalogNewsFavoriteModel> items) {
        return  new CatalogNewsAdapter(this, getOrgId(), items, getFormDisposable());
    }
}
