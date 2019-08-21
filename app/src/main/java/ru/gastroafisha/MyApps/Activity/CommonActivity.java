package ru.gastroafisha.MyApps.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import ru.gastroafisha.MyApps.MainApplication;
import ru.gastroafisha.MyApps.Model.Menu.MenuItemModel;
import ru.gastroafisha.MyApps.Model.Org.OrgModel;
import ru.gastroafisha.MyApps.R;
import ru.gastroafisha.MyApps.Utils.Classes.Tuple;
import ru.gastroafisha.MyApps.Utils.MenuUtils;

public abstract class CommonActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final CompositeDisposable mDisposable = new CompositeDisposable();
    private Integer orgId = 0;

    protected abstract Integer getLayoutId();

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        _setOrgId(intent.getIntExtra("orgId", orgId));

        setContentView(R.layout.activity_common);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (title != null) {
            setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset)
            {
                super.onDrawerSlide(drawerView, slideOffset);
                drawer.bringChildToFront(drawerView);
                drawer.requestLayout();
                drawer.setScrimColor(Color.TRANSPARENT);

            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Add activity content
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View childLayout = inflater.inflate(getLayoutId(), (ViewGroup) findViewById(R.id.content_view));
        //parentLayout.addView(childLayout);

        prepareMenu();
    }

    private void prepareMenu() {
        if (orgId > 0) {
            getFormDisposable().add(
                    Flowable.combineLatest(MainApplication.getInstance().getLocalDataStorage().getOrgs().get(orgId),
                            MainApplication.getInstance().getLocalDataStorage().getMenuItems().listAll(orgId),
                            Tuple::new)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(orgModelListTuple -> {
                        updateMenu(orgModelListTuple.getValue1(), orgModelListTuple.getValue2());
                    }, throwable -> {
                        Crashlytics.logException(throwable);
                    }));
        } else {
            updateMenu(null, new ArrayList<>());
        }
    }

    private void updateMenu(OrgModel org, List<MenuItemModel> menuItemModels) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        TextView org_name = (TextView) findViewById(R.id.org_name);
        if (org_name != null) {
            org_name.setText(org != null ? org.getName() : "");
        }

        if (drawer != null && navigationView != null) {
            MenuUtils.FillMenu(drawer, this, orgId, navigationView.getMenu(), menuItemModels);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // clear all the subscriptions
        mDisposable.clear();
    }

    public void showProgressDialog(String title, String message, Action onCancel) {
        if (progress != null) {
            Crashlytics.log("showProgressDialog while another one active");
            return;
        }

        progress = new ProgressDialog(this);
        progress.setTitle(title);
        progress.setMessage(message);
        if (onCancel != null) {
            progress.setCancelable(true);
            progress.setOnCancelListener(dialog -> {
                try {
                    onCancel.run();
                } catch (Exception e) {
                    Crashlytics.logException(e);
                    e.printStackTrace();
                }
            });
        } else {
            progress.setCancelable(false);
        }

        progress.show();
    }

    public void hideProgressDialog() {
        if (progress != null) {
            progress.dismiss();
        }

        progress = null;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return true;
    }

    public CompositeDisposable getFormDisposable() {
        return mDisposable;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void _setOrgId(Integer orgId){
        this.orgId = orgId;
    }

}
