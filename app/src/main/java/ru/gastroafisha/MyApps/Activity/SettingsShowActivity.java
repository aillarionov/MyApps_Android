package ru.gastroafisha.MyApps.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.gastroafisha.MyApps.MainApplication;
import ru.gastroafisha.MyApps.Model.Org.OrgConfigModel;
import ru.gastroafisha.MyApps.Model.Org.OrgConfigOrgModel;
import ru.gastroafisha.MyApps.R;
import ru.gastroafisha.MyApps.Utils.ImageUtils;

public class SettingsShowActivity extends CommonActivity {

    private Integer itemId;

    @Override
    protected Integer getLayoutId() {
        return R.layout.content_settings_show;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        itemId = intent.getIntExtra("itemId", 0);

        getFormDisposable().add(MainApplication.getInstance().getLocalDataStorage().getOrgConfigs().getWOrgs(itemId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_item -> {
                    fillItem(_item);
                }));
    }

    private void fillItem(OrgConfigOrgModel orgConfig) {
        ImageView image = (ImageView) findViewById(R.id.item_logo);
        getFormDisposable().add(ImageUtils.setTemporaryImage(getOrgId(), orgConfig.getOrg() != null ? orgConfig.getOrg().getLogo() : null, image));

        Switch switch_visitor = (Switch) findViewById(R.id.switch_isvisitor);
        switch_visitor.setChecked(orgConfig.getOrgConfig().getVisitor());
        switch_visitor.setOnCheckedChangeListener((buttonView, isChecked) -> {
            orgConfig.getOrgConfig().setVisitor(isChecked);
            updateOrgConfig(orgConfig.getOrgConfig());
        });

        Switch switch_presenter = (Switch) findViewById(R.id.switch_ispresenter);
        switch_presenter.setChecked(orgConfig.getOrgConfig().getPresenter());
        switch_presenter.setOnCheckedChangeListener((buttonView, isChecked) -> {
            orgConfig.getOrgConfig().setPresenter(isChecked);
            updateOrgConfig(orgConfig.getOrgConfig());
        });

        ProgressBar progress_preload = (ProgressBar) findViewById(R.id.progress_preload);

        Button button_preload = (Button) findViewById(R.id.button_preload);
        button_preload.setOnClickListener(v -> {
            progress_preload.setVisibility(View.VISIBLE);
            progress_preload.setMax(0);
            progress_preload.setProgress(0);

            getFormDisposable().add(MainApplication.getInstance().getLocalDataStorage().preloadImages(orgConfig.getOrgConfig().getOrgId())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .doOnComplete(() -> {
                        progress_preload.setVisibility(View.INVISIBLE);
                        Toast.makeText(this, R.string.message_org_preload_complete, Toast.LENGTH_SHORT).show();
                    })
                    .subscribe(progress -> {
                        progress_preload.setMax(progress.getTotal());
                        progress_preload.setProgress(progress.getCurrent());
                    }));
        });

        Button button_clean = (Button) findViewById(R.id.button_clean);
        button_clean.setOnClickListener(v -> {
            getFormDisposable().add(MainApplication.getInstance().getLocalImageStore().cleanOrgTempImages(orgConfig.getOrgConfig().getOrgId())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        Toast.makeText(this, R.string.message_org_cache_clean_complete, Toast.LENGTH_SHORT).show();
                    })
            );
        });


        Button button_remove = (Button) findViewById(R.id.button_remove);
        button_remove.setOnClickListener(v -> {

            new AlertDialog.Builder(this)
                    .setMessage(getString(R.string.setting_group_delete))
                    .setCancelable(true)
                    .setPositiveButton(getString(R.string.button_yes), (dialog, id) -> {
                        deleteOrg(orgConfig.getOrgConfig());
                    })
                    .setNegativeButton(getString(R.string.button_no), null)
                    .show();
        });
    }

    private void updateOrgConfig(OrgConfigModel orgConfig) {
        getFormDisposable().add(MainApplication.getInstance().getLocalDataStorage().updateOrgConfig(orgConfig)
                .andThen(MainApplication.getInstance().getLocalDataStorage().uploadClientData())
                .subscribe(() -> {
                }, throwable -> {
                    Crashlytics.logException(throwable);
                }));
    }

    private void deleteOrg(OrgConfigModel orgConfig) {
        showProgressDialog(null, getString(R.string.message_group_deleting), null);

        getFormDisposable().add(MainApplication.getInstance().getLocalDataStorage().removeOrg(orgConfig.getOrgId())
                .subscribe(() -> {
                    hideProgressDialog();

                    if (orgConfig.getOrgId().equals(getOrgId())) {
                        MainApplication.getInstance().getConfigStore().setOrgId(0);
                        if (MainApplication.getInstance().getMainActivity() != null) {
                            MainApplication.getInstance().getMainActivity().finish();
                        }
//                        Intent orgsActivity = new Intent(this, OrgsActivity.class);
//                        this.startActivity(orgsActivity);
                        this.finish();
                    } else {
                        this.finish();
                    }
                })
        );
    }
}
