package ru.gastroafisha.MyApps.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import ru.gastroafisha.MyApps.Activity.CommonActivity;
import ru.gastroafisha.MyApps.Activity.MainActivity;
import ru.gastroafisha.MyApps.MainApplication;
import ru.gastroafisha.MyApps.Model.Org.OrgConfigModel;
import ru.gastroafisha.MyApps.Model.Org.OrgModel;
import ru.gastroafisha.MyApps.R;
import ru.gastroafisha.MyApps.Utils.ActivityOpener;
import ru.gastroafisha.MyApps.Utils.Classes.DisposableHolder;
import ru.gastroafisha.MyApps.Utils.ImageUtils;
import ru.gastroafisha.MyApps.Utils.StringUtils;

/**
 * Created by alex on 22.12.2017.
 */

public class OrgsAdapter extends CommonAdapter<OrgModel, OrgsAdapter.ViewHolder> {

    public OrgsAdapter(CommonActivity activity, List<OrgModel> items, CompositeDisposable disposable) {
        super(activity, items, disposable);
    }

    @Override
    protected ViewHolder getNewViewHolder(View v) {
        return new ViewHolder(v);
    }

    protected void openItem(OrgModel item) {

    }

    @Override
    protected Integer getLayoutId() {
        return R.layout.item_org;
    }

    @Override
    protected void fillViewHolder(ViewHolder holder, OrgModel item) {
        holder.name.setText(StringUtils.textToHtml(item.getName()));
        holder.city.setText(StringUtils.textToHtml(item.getCity() != null ? item.getCity().getName() : null));

        holder.logoDisposable.dispose();
        holder.logoDisposable.setDisposable(ImageUtils.setTemporaryImage(item.getId(), item.getLogo(), holder.logo));
        getFormDisposable().add(holder.logoDisposable);

        holder.name.setOnClickListener(v -> chooseOrg(item));
        holder.city.setOnClickListener(v -> chooseOrg(item));
        holder.logo.setOnClickListener(v -> chooseOrg(item));
    }

    private void chooseOrg(OrgModel item) {
        if (item != null) {
            OrgConfigModel orgConfig = new OrgConfigModel();
            orgConfig.setOrgId(item.getId());
            orgConfig.setVisitor(true);
            orgConfig.setPresenter(false);
            orgConfig.setReceiveNotifications(true);

            DisposableHolder d = new DisposableHolder();

            showProgressDialog(null, getActivity().getString(R.string.message_data_is_loading), null);

            if (MainApplication.getInstance().getMainActivity() != null) {
                ((MainActivity)(MainApplication.getInstance().getMainActivity())).disableReload();
            }

            d.setDisposable(MainApplication.getInstance().getLocalDataStorage().updateOrg(item.getId())
                    .andThen(Completable.fromAction(() -> {
                        MainApplication.getInstance().getLocalDataStorage().getOrgConfigs().insertOrIgnore(orgConfig);
                    }))
                    .observeOn(AndroidSchedulers.mainThread())
                    .andThen(Completable.fromAction(() -> {
                        MainApplication.getInstance().getConfigStore().setOrgId(item.getId());
                    }))
                    .subscribe(() -> {
                        hideProgressDialog();
                        //getActivity().finish();
                        ActivityOpener activityOpener = new ActivityOpener(getContext(), item.getId(), null);
                        activityOpener.ReloadMain();
                    }, throwable -> {
                        hideProgressDialog();
                        Crashlytics.logException(throwable);
                        Toast.makeText(getContext(), StringUtils.textFormat(R.string.error_cant_load_org, item.getName()), Toast.LENGTH_SHORT).show();
                    }));

            getFormDisposable().add(d);
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView city;
        ImageView logo;
        DisposableHolder logoDisposable = new DisposableHolder();

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.org_name);
            city = (TextView) itemView.findViewById(R.id.org_city);
            logo = (ImageView) itemView.findViewById(R.id.org_logo);
        }
    }

}