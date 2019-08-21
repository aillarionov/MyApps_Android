package ru.gastroafisha.MyApps.Utils;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import ru.gastroafisha.MyApps.MainApplication;
import ru.gastroafisha.MyApps.Model.Item.CatalogItemModel;
import ru.gastroafisha.MyApps.Model.Item.FavoriteModel;

/**
 * Created by alex on 16.01.2018.
 */

public class FavoriteUtils {

    public static Completable addToFavorites(CatalogItemModel item) {
        FavoriteModel favorite = new FavoriteModel();
        favorite.setOrgId(item.getOrgId());
        favorite.setCatalogId(item.getCatalogId());
        favorite.setId(item.getId());

        return Completable.fromAction(() ->
                MainApplication.getInstance().getLocalDataStorage().getFavorites()
                        .insert(favorite)
        ).subscribeOn(Schedulers.io());
    }

    public static Completable removeFromFavorites(CatalogItemModel item) {
        return Completable.fromAction(() ->
                MainApplication.getInstance().getLocalDataStorage().getFavorites()
                        .delete(item.getOrgId(), item.getCatalogId(), item.getId())
        ).subscribeOn(Schedulers.io());
    }

}
