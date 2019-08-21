package ru.gastroafisha.MyApps.Activity.Item.Catalog;

import android.widget.ImageView;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.gastroafisha.MyApps.Activity.CommonShowActivity;
import ru.gastroafisha.MyApps.MainApplication;
import ru.gastroafisha.MyApps.Model.Item.CatalogImageFavoriteModel;
import ru.gastroafisha.MyApps.R;
import ru.gastroafisha.MyApps.Utils.ImageUtils;

public class CatalogImageShowActivity extends CommonShowActivity<CatalogImageFavoriteModel> {

    @Override
    protected Integer getLayoutId() {
        return R.layout.content_catalog_image_show;
    }

    @Override
    protected Flowable<CatalogImageFavoriteModel> loadItem(Integer catalogId, Integer itemId) {
        return ((MainApplication) getApplication()).getLocalDataStorage().getCatalogImages().get(getOrgId(), catalogId, itemId);
    }

    @Override
    protected void fillItem(CatalogImageFavoriteModel item) {
        // Image
        SubsamplingScaleImageView image = (SubsamplingScaleImageView) findViewById(R.id.item_photo);
        getFormDisposable().add(ImageUtils.setTemporaryImage(getOrgId(), item.getItem().getPhoto(), image));
    }
}
