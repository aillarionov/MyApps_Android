package ru.gastroafisha.MyApps.Activity.Item.Catalog;

import android.widget.ImageView;
import android.widget.TextView;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.gastroafisha.MyApps.Activity.CommonShowActivity;
import ru.gastroafisha.MyApps.MainApplication;
import ru.gastroafisha.MyApps.Model.Item.CatalogItemFavoriteModel;
import ru.gastroafisha.MyApps.R;
import ru.gastroafisha.MyApps.Utils.ImageUtils;
import ru.gastroafisha.MyApps.Utils.StringUtils;

public class CatalogItemShowActivity extends CommonShowActivity<CatalogItemFavoriteModel> {

    @Override
    protected Integer getLayoutId() {
        return R.layout.content_catalog_item_show;
    }

    @Override
    protected Flowable<CatalogItemFavoriteModel> loadItem(Integer catalogId, Integer itemId) {
        return ((MainApplication) getApplication()).getLocalDataStorage().getCatalogItems().get(getOrgId(), catalogId, itemId);
    }

    @Override
    protected void fillItem(CatalogItemFavoriteModel item) {
        // Image
        ImageView image = (ImageView) findViewById(R.id.item_photo);
        getFormDisposable().add(ImageUtils.setTemporaryImage(getOrgId(), item.getItem().getPhoto(), image));

        // Text
        TextView text = (TextView) findViewById(R.id.item_text);
        text.setText(StringUtils.textToHtml(item.getItem().getText()));
    }
}
