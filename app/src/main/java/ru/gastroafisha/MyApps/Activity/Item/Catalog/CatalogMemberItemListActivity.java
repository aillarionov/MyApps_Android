package ru.gastroafisha.MyApps.Activity.Item.Catalog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.gastroafisha.MyApps.Activity.CommonActivity;
import ru.gastroafisha.MyApps.Activity.Item.CommonItemListActivity;
import ru.gastroafisha.MyApps.Adapter.Item.AbstractAdapter;
import ru.gastroafisha.MyApps.Adapter.Item.CatalogMembersAdapter;
import ru.gastroafisha.MyApps.Adapter.Item.CommonItemAdapter;
import ru.gastroafisha.MyApps.MainApplication;
import ru.gastroafisha.MyApps.Model.Item.CatalogAbstractFavoriteModel;
import ru.gastroafisha.MyApps.Model.Item.CatalogMemberFavoriteModel;
import ru.gastroafisha.MyApps.Model.Item.CatalogMemberModel;
import ru.gastroafisha.MyApps.R;
import ru.gastroafisha.MyApps.Utils.Classes.DisposableHolder;
import ru.gastroafisha.MyApps.Utils.Const;
import ru.gastroafisha.MyApps.Utils.EventUtils;
import ru.gastroafisha.MyApps.Utils.StringUtils;

public class CatalogMemberItemListActivity extends CommonActivity {

    private RecyclerView recyclerView;

    private List<CatalogMemberFavoriteModel> items = new ArrayList<>();
    private List<String> categories = new ArrayList<>();
    private Integer catalogId;

    DisposableHolder disposableTask = new DisposableHolder();

    private static String EMPTY = StringUtils.textFormat(R.string.member_list_all);

    private String searchString = "";
    private String categoryString = EMPTY;

    @Override
    protected Integer getLayoutId() {
        return R.layout.content_catalog_member_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        catalogId = intent.getIntExtra("catalogId", 0);

        // List
        recyclerView = findViewById(R.id.list_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Search
        CatalogMembersAdapter searchAdapter = new CatalogMembersAdapter(this, getOrgId(), items, getFormDisposable());
        recyclerView.setAdapter(searchAdapter);

        EditText searchTextEdit = (EditText) findViewById(R.id.search_textedit);
        searchTextEdit.addTextChangedListener(
                EventUtils.generateDelayedTextWatcher(Const.USER_INPUT_DELAY, s -> {
                    searchString = s;
                    UpdateList();
                })
        );

        // Categories
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, this.categories);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner categories_spinner = findViewById(R.id.category_spinner);
        categories_spinner.setAdapter(categoriesAdapter);

        categories_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryString = categories.get(position);
                UpdateList();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                categoryString = "";
                UpdateList();
            }
        });

        getFormDisposable().add(((MainApplication) getApplication()).getLocalDataStorage().getCatalogMembers().listForCategories(getOrgId(), catalogId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(catalogMemberModels -> {
                    this.categories.clear();
                    this.categories.add(EMPTY);

                    for (CatalogMemberModel member : catalogMemberModels) {
                        for (String category : member.getCategories()){
                            if (!this.categories.contains(category)) {
                                this.categories.add(category);
                            }
                        }
                    }

                    categoriesAdapter.notifyDataSetChanged();

                    categories_spinner.setSelection(0);
                }));

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(() -> {

                DisposableHolder d = new DisposableHolder();

                d.setDisposable(updateList(catalogId)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {

                            swipeRefreshLayout.setRefreshing(false);
                            d.dispose();

                        }, throwable -> {

                            Crashlytics.logException(throwable);
                            swipeRefreshLayout.setRefreshing(false);
                            d.dispose();

                        }));

                getFormDisposable().add(d);
            });
        }
    }

    protected Completable updateList(Integer catalogId) {
        return MainApplication.getInstance().getLocalDataStorage().refreshCatalogItems(getOrgId(), catalogId);
    }

    private void UpdateList() {
        disposableTask.dispose();

        disposableTask.setDisposable(((MainApplication) getApplication()).getLocalDataStorage().getCatalogMembers().search(getOrgId(), catalogId, "%" + this.searchString + "%")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_items -> {
                    items.clear();

                    for (CatalogMemberFavoriteModel item : _items) {
                        if (categoryString == EMPTY || item.getItem().getCategories().contains(categoryString)) {
                            items.add(item);
                        }
                    }

                    recyclerView.getAdapter().notifyDataSetChanged();
                }));
    }

    @Override
    protected void onStop() {
        super.onStop();

        disposableTask.dispose();
    }
}
