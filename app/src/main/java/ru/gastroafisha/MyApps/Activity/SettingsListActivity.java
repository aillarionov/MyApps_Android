package ru.gastroafisha.MyApps.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import ru.gastroafisha.MyApps.Activity.Item.CommonItemListActivity;
import ru.gastroafisha.MyApps.Adapter.CommonAdapter;
import ru.gastroafisha.MyApps.Adapter.SettingsListAdapter;
import ru.gastroafisha.MyApps.MainApplication;
import ru.gastroafisha.MyApps.Model.Org.OrgConfigOrgModel;
import ru.gastroafisha.MyApps.R;
import ru.gastroafisha.MyApps.Utils.StringUtils;

public class SettingsListActivity extends CommonItemListActivity<OrgConfigOrgModel> {


    @Override
    protected CommonAdapter getListAdapter(List<OrgConfigOrgModel> items) {
        return new SettingsListAdapter(this, getOrgId(), items, getFormDisposable());
    }

    @Override
    protected Flowable<List<OrgConfigOrgModel>> getList(Integer catalogId) {
        return ((MainApplication) getApplication()).getLocalDataStorage().getOrgConfigs().listWOrgs();
    }

    @Override
    protected Completable updateList(Integer catalogId) {
        return null;
    }

    @Override
    protected Integer getLayoutId() {
        return R.layout.content_settings_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button button = (Button) findViewById(R.id.toolbar_button);
        button.setText(R.string.settings_button_add);
        button.setVisibility(View.VISIBLE);
        button.setOnClickListener(v -> {
            this.onAddPressed();
        });
    }

    protected void onAddPressed() {
        //Intent intent = new Intent(this, OrgAddActivity.class);
        Intent intent = new Intent(this,  OrgsActivity.class);
        intent.putExtra("title", StringUtils.textFormat(R.string.title_activity_org_add));
        this.startActivity(intent);
    }
}
