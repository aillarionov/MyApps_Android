package ru.gastroafisha.MyApps.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import ru.gastroafisha.MyApps.Activity.CommonActivity;
import ru.gastroafisha.MyApps.Activity.SettingsShowActivity;
import ru.gastroafisha.MyApps.MainApplication;
import ru.gastroafisha.MyApps.Model.Org.OrgConfigOrgModel;
import ru.gastroafisha.MyApps.R;
import ru.gastroafisha.MyApps.Utils.ActivityOpener;
import ru.gastroafisha.MyApps.Utils.Classes.DisposableHolder;
import ru.gastroafisha.MyApps.Utils.ImageUtils;

/**
 * Created by alex on 22.12.2017.
 */

public class SettingsListAdapter extends CommonAdapter<OrgConfigOrgModel, SettingsListAdapter.ViewHolder> {

    private Integer orgId;

    public SettingsListAdapter(CommonActivity activity, Integer orgId, List<OrgConfigOrgModel> items, CompositeDisposable disposable) {
        super(activity, items, disposable);

        this.orgId = orgId;
    }

    @Override
    protected ViewHolder getNewViewHolder(View v) {
        return new ViewHolder(v);
    }

    protected void openItem(OrgConfigOrgModel item) {

    }

    @Override
    protected Integer getLayoutId() {
        return R.layout.item_settings;
    }

    @Override
    protected void fillViewHolder(ViewHolder holder, OrgConfigOrgModel item) {

        holder.logoDisposable.dispose();
        holder.logoDisposable.setDisposable(ImageUtils.setTemporaryImage(item.getOrgConfig().getOrgId(), item.getOrg() != null ? item.getOrg().getLogo() : null, holder.logo));
        getFormDisposable().add(holder.logoDisposable);

        holder.button_switch.setOnClickListener(v -> switchOrg(item));
        holder.button_settings.setOnClickListener(v -> settingsOrg(item));
    }


    private void switchOrg(OrgConfigOrgModel item) {
        MainApplication.getInstance().getConfigStore().setOrgId(item.getOrgConfig().getOrgId());

        getActivity().finish();

        ActivityOpener activityOpener = new ActivityOpener(getContext(), item.getOrgConfig().getOrgId(), null);
        activityOpener.ReloadMain();
    }

    private void settingsOrg(OrgConfigOrgModel item) {
        Intent intent = new Intent(getContext(), SettingsShowActivity.class);

        intent.putExtra("orgId", this.orgId);
        intent.putExtra("itemId", item.getOrgConfig().getOrgId());
        intent.putExtra("title", item.getOrg().getName());

        getContext().startActivity(intent);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView logo;
        DisposableHolder logoDisposable = new DisposableHolder();
        Button button_switch;
        Button button_settings;


        public ViewHolder(View itemView) {
            super(itemView);
            logo = (ImageView) itemView.findViewById(R.id.org_logo);
            button_switch = (Button) itemView.findViewById(R.id.button_switch);
            button_settings = (Button) itemView.findViewById(R.id.button_settings);
        }
    }

}