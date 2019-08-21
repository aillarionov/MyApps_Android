package ru.gastroafisha.MyApps.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.gastroafisha.MyApps.MainApplication;
import ru.gastroafisha.MyApps.Model.Org.OrgConfigModel;
import ru.gastroafisha.MyApps.Model.Org.OrgConfigOrgModel;
import ru.gastroafisha.MyApps.Model.Org.OrgModel;
import ru.gastroafisha.MyApps.Proxy.OrgTuple;
import ru.gastroafisha.MyApps.R;
import ru.gastroafisha.MyApps.Service.RemoteService;
import ru.gastroafisha.MyApps.Utils.ActivityOpener;
import ru.gastroafisha.MyApps.Utils.Classes.DisposableHolder;
import ru.gastroafisha.MyApps.Utils.ImageUtils;
import ru.gastroafisha.MyApps.Utils.StringUtils;

public class OrgAddActivity extends CommonActivity {

    Disposable photoDisposable;
    String photoUrl;
    Button addButton;

    OrgModel org;

    @Override
    protected Integer getLayoutId() {
        return R.layout.content_org_add;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addButton = (Button) findViewById(R.id.toolbar_button);
        addButton.setText(R.string.settings_button_add);
        addButton.setVisibility(View.VISIBLE);
        addButton.setEnabled(false);
        addButton.setOnClickListener(v -> {
            this.onAddPressed();
        });

        ImageButton searchButton = (ImageButton) findViewById(R.id.button_search);
        searchButton.setOnClickListener(v -> {
            this.onSearchPressed();
        });

        Button scanButton = (Button) findViewById(R.id.button_scan);
        scanButton.setOnClickListener(v -> {
            //this.openScan();
        });

        Button chooseButton = (Button) findViewById(R.id.button_choose);
        chooseButton.setOnClickListener(v -> {
            this.openList();
        });

    }

    protected void openScan() {
        Intent intent = new Intent(OrgAddActivity.this, QRScanActivity.class);
        this.startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if(resultCode == RESULT_OK){
                String result = data.getStringExtra("result");
                EditText inputCode = (EditText) findViewById(R.id.input_code);
                inputCode.setText(result);
                this.onSearchPressed();
            }

            if (resultCode == RESULT_CANCELED) {
            }
        }
    }

    protected void openList() {
        Intent intent = new Intent(this, OrgsActivity.class);
        this.startActivity(intent);
    }

    protected void onAddPressed() {
        if (org != null) {
            OrgConfigModel orgConfig = new OrgConfigModel();
            orgConfig.setOrgId(org.getId());
            orgConfig.setVisitor(true);
            orgConfig.setPresenter(false);
            orgConfig.setReceiveNotifications(true);

            DisposableHolder d = new DisposableHolder();

            showProgressDialog(null, this.getString(R.string.message_data_is_loading), null);


            if (MainApplication.getInstance().getMainActivity() != null) {
                ((MainActivity)(MainApplication.getInstance().getMainActivity())).disableReload();
            }

            d.setDisposable(MainApplication.getInstance().getLocalDataStorage().updateOrg(org.getId())
                    .andThen(Completable.fromAction(() -> {
                        MainApplication.getInstance().getLocalDataStorage().getOrgConfigs().insertOrIgnore(orgConfig);
                    }))
                    .observeOn(AndroidSchedulers.mainThread())
                    .andThen(Completable.fromAction(() -> {
                        MainApplication.getInstance().getConfigStore().setOrgId(org.getId());
                    }))
                    .subscribe(() -> {
                        hideProgressDialog();
                        //getActivity().finish();
                        ActivityOpener activityOpener = new ActivityOpener(this, org.getId(), null);
                        activityOpener.ReloadMain();
                    }, throwable -> {
                        hideProgressDialog();
                        Crashlytics.logException(throwable);
                        Toast.makeText(this, StringUtils.textFormat(R.string.error_cant_load_org, org.getName()), Toast.LENGTH_SHORT).show();
                    }));

            getFormDisposable().add(d);
        }
    }

    protected void onSearchPressed() {
        EditText inputCode = (EditText) findViewById(R.id.input_code);

        String code = inputCode.getText().toString();

        getFormDisposable().add(RemoteService.getOrg(code)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        org -> {
                            inputCode.setError(null);
                            addButton.setEnabled(true);
                            this.setOrg(org);
                            this.org = org;
                        },
                        error -> {

                            inputCode.setError(StringUtils.textFormat(R.string.settings_add_group_not_found));
                            addButton.setEnabled(false);
                            this.clearOrg();
                            this.org = null;
                        }
                )
        );
    }

    protected void setOrg(OrgModel org) {
        ImageView photo = (ImageView) findViewById(R.id.org_logo);
        TextView name = (TextView) findViewById(R.id.org_name);

        name.setText(org.getName());

        if (photoUrl == null || !photoUrl.equals(org.getLogo().getUrl())) {
            photo.setImageDrawable(null);

            if (photoDisposable != null && !photoDisposable.isDisposed()) {
                photoDisposable.dispose();
            }

            if (org.getLogo() != null) {
                photoDisposable = ImageUtils.setTemporaryImage(getOrgId(), org.getLogo(), photo);
                photoUrl = org.getLogo().getUrl();
                getFormDisposable().add(photoDisposable);
            }
        }
    }

    protected void clearOrg() {
        ImageView photo = (ImageView) findViewById(R.id.org_logo);
        TextView name = (TextView) findViewById(R.id.org_name);

        name.setText(null);
        photo.setImageDrawable(null);
        photoUrl = null;
    }

}
