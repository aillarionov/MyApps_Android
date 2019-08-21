package ru.gastroafisha.MyApps.Adapter.Item;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import ru.gastroafisha.MyApps.Activity.CommonActivity;
import ru.gastroafisha.MyApps.Adapter.CommonAdapter;
import ru.gastroafisha.MyApps.Model.Item.CatalogAbstractFavoriteModel;
import ru.gastroafisha.MyApps.Utils.FavoriteUtils;
import ru.gastroafisha.MyApps.Utils.ItemClassConverter;

/**
 * Created by alex on 22.12.2017.
 */

public abstract class CommonItemAdapter<T extends CatalogAbstractFavoriteModel, VH extends RecyclerView.ViewHolder> extends CommonAdapter<T, VH> {

    protected Integer orgId;

    public Integer getOrgId() {
        return orgId;
    }

    public CommonItemAdapter(CommonActivity activity, Integer orgId, List<T> items, CompositeDisposable disposable) {
        super(activity, items, disposable);
        this.orgId = orgId;
    }

    protected void openItem(T item) {
        Intent intent = new Intent(getContext(), ItemClassConverter.itemToShowClass(item.getItem()));

        intent.putExtra("orgId", item.getItem().getOrgId());
        intent.putExtra("catalogId", item.getItem().getCatalogId());
        intent.putExtra("itemId", item.getItem().getId());
        intent.putExtra("title", "");

        getContext().startActivity(intent);
    }



    protected void changeFavorite(T item) {
        if (item.getFavorite() == null) {
            FavoriteUtils.addToFavorites(item.getItem()).subscribe();
        } else {
            FavoriteUtils.removeFromFavorites(item.getItem()).subscribe();
        }
    }


}