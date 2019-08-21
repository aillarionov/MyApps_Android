package ru.gastroafisha.MyApps.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.gastroafisha.MyApps.Adapter.CommonAdapter;
import ru.gastroafisha.MyApps.Adapter.Item.CatalogMembersAdapter;
import ru.gastroafisha.MyApps.Adapter.OrgsAdapter;
import ru.gastroafisha.MyApps.MainApplication;
import ru.gastroafisha.MyApps.Model.Item.CatalogMemberFavoriteModel;
import ru.gastroafisha.MyApps.Model.Item.CatalogMemberModel;
import ru.gastroafisha.MyApps.Model.Org.OrgModel;
import ru.gastroafisha.MyApps.Proxy.CityProxy;
import ru.gastroafisha.MyApps.R;
import ru.gastroafisha.MyApps.Service.RemoteService;
import ru.gastroafisha.MyApps.Storage.ConfigStore;
import ru.gastroafisha.MyApps.Utils.Classes.DisposableHolder;
import ru.gastroafisha.MyApps.Utils.Const;
import ru.gastroafisha.MyApps.Utils.EventUtils;

public class OrgsActivity extends CommonActivity {

    RecyclerView recyclerView;

    List<OrgModel> orgs = new ArrayList<>();
    List<OrgModel> filteredOrgs = new ArrayList<>();

    List<CityProxy> cities = new ArrayList<>();

    DisposableHolder disposableTask = new DisposableHolder();
    DisposableHolder disposableTask2 = new DisposableHolder();

    private String searchString = "";
    private Integer cityId;

    @Override
    protected Integer getLayoutId() {
        return R.layout.content_orgs;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        // List
        recyclerView = findViewById(R.id.list_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Search
        OrgsAdapter searchAdapter = new OrgsAdapter(this, this.filteredOrgs, getFormDisposable());
        recyclerView.setAdapter(searchAdapter);

        EditText searchTextEdit = (EditText) findViewById(R.id.search_textedit);
        searchTextEdit.addTextChangedListener(
                EventUtils.generateDelayedTextWatcher(Const.USER_INPUT_DELAY, s -> {
                    searchString = s;
                    UpdateList();
                })
        );

        // City choose
        ImageButton btn = findViewById(R.id.button_city);
        btn.setOnClickListener(v -> {
            this.ShowChooseList();
        });

        cityId = MainApplication.getInstance().getConfigStore().getCityId();
        if (cityId != null) {
            ReloadList();
        } else {
            ShowChooseList();
        }
    }

    private void ShowChooseList() {
        disposableTask2.dispose();

        disposableTask2.setDisposable(RemoteService.listCities()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(items -> {
                    this.cities = items;
                    this.DisplayChooseDialog();
                }));
    }

    private void DisplayChooseDialog() {
        List<String> items = new ArrayList<>();

        for (CityProxy city : this.cities) {
            items.add(city.getName());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_choose_city);
        builder.setItems(items.toArray(new String[this.cities.size()]), (dialog, which) -> {
            try {
                MainApplication.getInstance().getConfigStore().setCityId(cities.get(which).getId());
                this.cityId = cities.get(which).getId();
                ReloadList();
            } catch (Exception e) {
                Crashlytics.logException(e);
            }
        });
        builder.show();
    }

    private void ReloadList() {
        disposableTask.dispose();

        if (cityId != null) {
            disposableTask.setDisposable(RemoteService.listOrgs(this.cityId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(items -> {
                        this.orgs = items;
                        this.UpdateList();
                    }));
        }
    }

    private void UpdateList() {
        filteredOrgs.clear();

        for (OrgModel org : orgs) {
            if (org.getName().toLowerCase().contains(searchString.toLowerCase())) {
                filteredOrgs.add(org);
            }
        }

        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();

        disposableTask.dispose();
    }
}
