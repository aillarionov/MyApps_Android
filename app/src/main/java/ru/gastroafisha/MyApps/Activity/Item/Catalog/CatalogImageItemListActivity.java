package ru.gastroafisha.MyApps.Activity.Item.Catalog;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import ru.gastroafisha.MyApps.Activity.Item.CommonItemListActivity;
import ru.gastroafisha.MyApps.Adapter.Item.CatalogImagesAdapter;
import ru.gastroafisha.MyApps.Adapter.Item.CommonItemAdapter;
import ru.gastroafisha.MyApps.MainApplication;
import ru.gastroafisha.MyApps.Model.Item.CatalogImageFavoriteModel;
import ru.gastroafisha.MyApps.R;

public class CatalogImageItemListActivity extends CommonItemListActivity<CatalogImageFavoriteModel> {

    @Override
    protected Integer getLayoutId() {
        return R.layout.content_catalog_image_list;
    }

    @Override
    protected Flowable<List<CatalogImageFavoriteModel>> getList(Integer catalogId) {
        return ((MainApplication) getApplication()).getLocalDataStorage().getCatalogImages().list(getOrgId(), catalogId);
    }

    @Override
    protected CommonItemAdapter getListAdapter(List<CatalogImageFavoriteModel> items) {
        return  new CatalogImagesAdapter(this, getOrgId(), items, getFormDisposable());
    }
}
