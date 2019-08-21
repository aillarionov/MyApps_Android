package ru.gastroafisha.MyApps.Model.Item;

import android.arch.persistence.room.Embedded;

/**
 * Created by alex on 17.01.2018.
 */

public class CatalogImageFavoriteModel extends CatalogAbstractFavoriteModel<CatalogImageModel> {
    @Embedded
    private CatalogImageModel item;

    @Override
    public CatalogImageModel getItem() {
        return item;
    }

    @Override
    public void setItem(CatalogImageModel item) {
        this.item = item;
    }
}
