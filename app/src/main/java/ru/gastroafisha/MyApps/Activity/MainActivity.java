package ru.gastroafisha.MyApps.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.gastroafisha.MyApps.MainApplication;
import ru.gastroafisha.MyApps.Model.Org.OrgConfigModel;
import ru.gastroafisha.MyApps.R;
import ru.gastroafisha.MyApps.Storage.LocalDataStorage;
import ru.gastroafisha.MyApps.Utils.ActivityOpener;
import ru.gastroafisha.MyApps.Utils.Classes.DisposableHolder;
import ru.gastroafisha.MyApps.Utils.Services.NotificationOpener;
import ru.gastroafisha.MyApps.Utils.StringUtils;

public class MainActivity extends CommonActivity {

    private Timer _timer = new Timer();

    @Override
    protected Integer getLayoutId() {
        return R.layout.content_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MainApplication.getInstance().setMainActivity(this);

        setTheme(R.style.AppTheme_NoActionBar);

        _setOrgId(MainApplication.getInstance().getConfigStore().getOrgId());

        setTitle(R.string.title_empty);

        super.onCreate(savedInstanceState);

        if (getOrgId() > 0) {
            LoadOrg();
        }
        else {
            Intent orgsActivity = new Intent(this, OrgsActivity.class);
//            Intent orgsActivity = new Intent(this, SettingsListActivity.class);
            this.startActivity(orgsActivity);
            this.finish();
//            this.loadFirstApp();
        }

        _timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (getOrgId() > 0) {
                    getFormDisposable().add(MainApplication.getInstance().getLocalDataStorage().reloadCatalogItems(getOrgId())
                            .subscribe(() -> {
                            }, throwable -> {
                                Crashlytics.logException(throwable);
                            }));
                }
            }
        }, 2 * 1000, 5 * 60 * 1000);
    }


    @Override
    protected void onStart() {
        super.onStart();

        LoadOrg();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // Get notification
        if (intent != null && intent.getExtras() != null) {
            NotificationOpener.parseBundle(intent.getExtras());
        }

    }


    public void disableReload(){
        getFormDisposable().dispose();
    }

    private void LoadOrg() {
        getFormDisposable().add(MainApplication.getInstance().getLocalDataStorage().getMenuItems().listAll(getOrgId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(menuItems -> {
                    if (menuItems.size() > 0) {
                        ActivityOpener opener = new ActivityOpener(this, getOrgId(), menuItems.get(0), false);
                        opener.OpenMenu();
                    } else {
                        Intent orgsActivity = new Intent(this, SettingsListActivity.class);
                        this.startActivity(orgsActivity);
                        this.finish();
                    }
                }, throwable -> {
                    Crashlytics.logException(throwable);
                }));
    }

    protected void loadFirstApp() {

        Integer orgId = 1;

        OrgConfigModel orgConfig = new OrgConfigModel();
        orgConfig.setOrgId(orgId);
        orgConfig.setVisitor(true);
        orgConfig.setPresenter(false);
        orgConfig.setReceiveNotifications(true);

        DisposableHolder d = new DisposableHolder();

        showProgressDialog(null, this.getString(R.string.message_data_is_loading), null);

        d.setDisposable(MainApplication.getInstance().getLocalDataStorage().updateOrg(orgId)
                .andThen(Completable.fromAction(() -> {
                    MainApplication.getInstance().getLocalDataStorage().getOrgConfigs().insertOrIgnore(orgConfig);
                }))
                .andThen(Completable.fromAction(() -> {
                    MainApplication.getInstance().getConfigStore().setOrgId(orgId);
                }))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    hideProgressDialog();
                    MainApplication.getInstance().getConfigStore().setOrgId(orgId);
                    //getActivity().finish();
                    ActivityOpener activityOpener = new ActivityOpener(this, orgId, null);
                    activityOpener.ReloadMain();
                }, throwable -> {
                    hideProgressDialog();
                    Crashlytics.logException(throwable);
                    Toast.makeText(this, StringUtils.textFormat(R.string.error_cant_load_org, this.getString(R.string.app_name)), Toast.LENGTH_SHORT).show();
                }));

        getFormDisposable().add(d);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        MainApplication.getInstance().setMainActivity(null);
    }
}

