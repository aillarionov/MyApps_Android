package ru.gastroafisha.MyApps.Model.Item;

import android.arch.persistence.room.Embedded;

/**
 * Created by alex on 17.01.2018.
 */

public class CatalogNewsFavoriteModel extends CatalogAbstractFavoriteModel<CatalogNewsModel> {
    @Embedded
    private CatalogNewsModel item;

    @Override
    public CatalogNewsModel getItem() {
        return item;
    }

    @Override
    public void setItem(CatalogNewsModel item) {
        this.item = item;
    }
}
