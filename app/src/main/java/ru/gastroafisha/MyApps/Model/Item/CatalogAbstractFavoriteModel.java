package ru.gastroafisha.MyApps.Model.Item;

import android.arch.persistence.room.Embedded;

/**
 * Created by alex on 17.01.2018.
 */

public abstract class CatalogAbstractFavoriteModel<T extends CatalogItemModel> {
    @Embedded
    private FavoriteModel favorite;

    abstract public T getItem();
    abstract public void setItem(T item);

    public FavoriteModel getFavorite() {
        return favorite;
    }

    public void setFavorite(FavoriteModel favorite) {
        this.favorite = favorite;
    }
}