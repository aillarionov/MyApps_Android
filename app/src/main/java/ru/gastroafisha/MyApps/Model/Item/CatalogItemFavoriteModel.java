package ru.gastroafisha.MyApps.Model.Item;

import android.arch.persistence.room.Embedded;

/**
 * Created by alex on 17.01.2018.
 */

public class CatalogItemFavoriteModel extends CatalogAbstractFavoriteModel<CatalogItemModel> {
    @Embedded
    private CatalogItemModel item;

    @Override
    public CatalogItemModel getItem() {
        return item;
    }

    @Override
    public void setItem(CatalogItemModel item) {
        this.item = item;
    }
}
