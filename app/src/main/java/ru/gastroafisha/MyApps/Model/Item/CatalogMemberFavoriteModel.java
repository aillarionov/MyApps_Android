package ru.gastroafisha.MyApps.Model.Item;

import android.arch.persistence.room.Embedded;

/**
 * Created by alex on 17.01.2018.
 */

public class CatalogMemberFavoriteModel extends CatalogAbstractFavoriteModel<CatalogMemberModel> {
    @Embedded
    private CatalogMemberModel item;

    @Override
    public CatalogMemberModel getItem() {
        return item;
    }

    @Override
    public void setItem(CatalogMemberModel item) {
        this.item = item;
    }
}
