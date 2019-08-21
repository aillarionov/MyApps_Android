package ru.gastroafisha.MyApps.Adapter.Item;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.gastroafisha.MyApps.Activity.CommonActivity;
import ru.gastroafisha.MyApps.Model.Item.CatalogMemberFavoriteModel;
import ru.gastroafisha.MyApps.R;
import ru.gastroafisha.MyApps.Utils.ImageUtils;
import ru.gastroafisha.MyApps.Utils.StringUtils;

/**
 * Created by alex on 22.12.2017.
 */

public class CatalogMembersAdapter extends CommonItemAdapter<CatalogMemberFavoriteModel, CatalogMembersAdapter.ViewHolder> {

    public CatalogMembersAdapter(CommonActivity activity, Integer orgId, List<CatalogMemberFavoriteModel> items, CompositeDisposable disposable) {
        super(activity, orgId, items, disposable);
    }

    @Override
    protected ViewHolder getNewViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    protected Integer getLayoutId() {
        return R.layout.item_catalog_member;
    }

    @Override
    protected void fillViewHolder(ViewHolder holder, CatalogMemberFavoriteModel item) {
        holder.name.setText(StringUtils.textToHtml(item.getItem().getTitle()));

        holder.like.setImageResource(item.getFavorite() != null ? R.drawable.ic_button_liked : R.drawable.ic_button_like);

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

        holder.name.setOnClickListener(v -> openItem(item));
        holder.photo.setOnClickListener(v -> openItem(item));
        holder.like.setOnClickListener(v -> changeFavorite(item));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView photo;
        ImageButton like;
        Disposable photoDisposable;
        String photoUrl;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_name);
            photo = (ImageView) itemView.findViewById(R.id.item_photo);
            like = (ImageButton) itemView.findViewById(R.id.item_like);
        }
    }
}