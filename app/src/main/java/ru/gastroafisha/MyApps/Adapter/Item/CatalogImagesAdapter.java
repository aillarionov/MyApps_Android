package ru.gastroafisha.MyApps.Adapter.Item;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.gastroafisha.MyApps.Activity.CommonActivity;
import ru.gastroafisha.MyApps.Model.Item.CatalogImageFavoriteModel;
import ru.gastroafisha.MyApps.R;
import ru.gastroafisha.MyApps.Utils.ImageUtils;

/**
 * Created by alex on 22.12.2017.
 */

public class CatalogImagesAdapter extends CommonItemAdapter<CatalogImageFavoriteModel, CatalogImagesAdapter.ViewHolder> {

    public CatalogImagesAdapter(CommonActivity activity, Integer orgId, List<CatalogImageFavoriteModel> items, CompositeDisposable disposable) {
        super(activity, orgId, items, disposable);
    }

    @Override
    protected ViewHolder getNewViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    protected Integer getLayoutId() {
        return R.layout.item_catalog_image;
    }

    @Override
    protected void fillViewHolder(ViewHolder holder, CatalogImageFavoriteModel item) {

        if (holder.photoUrl == null || !holder.photoUrl.equals(item.getItem().getPhoto() != null ? item.getItem().getPhoto().getUrl() : null)) {
            holder.photo.setImageDrawable(null);

            if (holder.photoDisposable != null && !holder.photoDisposable.isDisposed()) {
                holder.photoDisposable.dispose();
            }


            if (item.getItem().getPhoto() != null) {
                holder.photoDisposable = ImageUtils.setTemporaryImage(getOrgId(), item.getItem().getPhoto(), holder.photo);
                holder.photoUrl = item.getItem().getPhoto().getUrl();
                getFormDisposable().add(holder.photoDisposable);
            }
        }

        holder.photo.setOnClickListener(v -> openItem(item));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        Disposable photoDisposable;
        String photoUrl;

        public ViewHolder(View itemView) {
            super(itemView);
            photo = (ImageView) itemView.findViewById(R.id.item_photo);
        }
    }
}