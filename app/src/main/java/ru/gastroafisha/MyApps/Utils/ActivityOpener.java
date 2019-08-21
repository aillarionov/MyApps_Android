package ru.gastroafisha.MyApps.Utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.gastroafisha.MyApps.Activity.Form.SimpleFormActivity;
import ru.gastroafisha.MyApps.Activity.Item.Catalog.CatalogNewsShowActivity;
import ru.gastroafisha.MyApps.Activity.MainActivity;
//import ru.gastroafisha.Informer.Activity.OrgsActivity;
import ru.gastroafisha.MyApps.Activity.SettingsListActivity;
import ru.gastroafisha.MyApps.MainApplication;
import ru.gastroafisha.MyApps.Model.Menu.MenuItemCls;
import ru.gastroafisha.MyApps.Model.Menu.MenuItemModel;
import ru.gastroafisha.MyApps.R;
import ru.gastroafisha.MyApps.Utils.Classes.Holder;

/**
 * Created by alex on 11.01.2018.
 */

public class ActivityOpener {
    private Integer orgId;
    private Context context;
    private MenuItemModel menu;
    private Boolean gotoMain;

    public ActivityOpener(Context context, Integer orgId, MenuItemModel menu, Boolean gotoMain) {
        this.orgId = orgId;
        this.context = context;
        this.menu = menu;
        this.gotoMain = gotoMain;
    }

    public ActivityOpener(Context context, Integer orgId, MenuItemModel menu) {
        this.orgId = orgId;
        this.context = context;
        this.menu = menu;
        this.gotoMain = true;
    }

    public void OpenMenu() {
        if (menu.getType() == null) {
            Toast.makeText(context, StringUtils.textFormat(R.string.error_cant_open_activity, menu.getName()), Toast.LENGTH_SHORT).show();
            return;
        }

        switch (menu.getType()) {

            case Standart:
                OpenStandardMenu();
                break;

            case Catalog:
                OpenItemMenu();
                break;

            case Form:
                OpenFormMenu();
                break;

        }
    }


    private void OpenStandardMenu() {
        try {
            String cls = menu.getParams().get("cls");
            Gson gson = new Gson();
            MenuItemCls menuCls = gson.fromJson(cls, MenuItemCls.class);

            Intent intent = new Intent(context, ItemClassConverter.catalogTypeToActivityClass(menuCls));

            intent.putExtra("orgId", orgId);
            intent.putExtra("title", menu.getName());
            intent.putExtra("params", menu.getParams());

            if (this.gotoMain) {
                goToMain();
            }

            context.startActivity(intent);

        } catch (Exception e) {
            Crashlytics.logException(e);
            Toast.makeText(context, StringUtils.textFormat(R.string.error_cant_open_activity, menu.getName()), Toast.LENGTH_SHORT).show();
        }
    }

    private void OpenItemMenu() {
        try {
            Integer catalogId = Integer.parseInt(menu.getParams().get("catalog"));
            Holder<Disposable> d = new Holder<>();

            d.setValue(MainApplication.getInstance().getLocalDataStorage().getCatalogs().get(orgId, catalogId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(catalogModel -> {
                                try {
                                    Intent intent = new Intent(context, ItemClassConverter.catalogTypeToActivityClass(catalogModel.getType()));

                                    intent.putExtra("orgId", orgId);
                                    intent.putExtra("catalogId", catalogId);
                                    intent.putExtra("title", menu.getName());

                                    if (this.gotoMain) {
                                        goToMain();
                                    }

                                    context.startActivity(intent);

                                    d.getValue().dispose();
                                    
                                } catch (Exception e) {
                                    Crashlytics.logException(e);
                                    Toast.makeText(context, StringUtils.textFormat(R.string.error_cant_open_activity, menu.getName()), Toast.LENGTH_SHORT).show();
                                }
                            },
                            throwable -> {
                                Crashlytics.logException(throwable);
                                Toast.makeText(context, StringUtils.textFormat(R.string.error_cant_open_activity, menu.getName()), Toast.LENGTH_SHORT).show();
                            }));

        } catch (Exception e) {
            Crashlytics.logException(e);
            Toast.makeText(context, StringUtils.textFormat(R.string.error_cant_open_activity, menu.getName()), Toast.LENGTH_SHORT).show();
        }
    }

    private void OpenFormMenu() {
        try {
            Intent intent = new Intent(context, SimpleFormActivity.class);

            intent.putExtra("orgId", orgId);
            intent.putExtra("params", menu.getParams());
            intent.putExtra("title", menu.getName());

            if (this.gotoMain) {
                goToMain();
            }

            context.startActivity(intent);

        } catch (Exception e) {
            Crashlytics.logException(e);
            Toast.makeText(context, StringUtils.textFormat(R.string.error_cant_open_activity, menu.getName()), Toast.LENGTH_SHORT).show();
        }
    }

    private void goToMain() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

//    public void OpenChangeOrg() {
//        Intent intent = new Intent(context, OrgsActivity.class);
//        intent.putExtra("orgId", orgId);
//        if (this.gotoMain) {
//            goToMain();
//        }
//        context.startActivity(intent);
//    }

    public void OpenSettingsList() {
        Intent intent = new Intent(context, SettingsListActivity.class);
        intent.putExtra("orgId", orgId);
        if (this.gotoMain) {
            goToMain();
        }
        context.startActivity(intent);
    }


    public void OpenMain() {
        goToMain();
    }

    public void ReloadMain() {
        if (MainApplication.getInstance().getMainActivity() != null) {
            MainApplication.getInstance().getMainActivity().finish();
        }

        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
    }

    public void OpenNews(Integer catalogId, Integer itemId) {
        try {
            Intent intent = new Intent(context, CatalogNewsShowActivity.class);

            intent.putExtra("orgId", this.orgId);
            intent.putExtra("catalogId", catalogId);
            intent.putExtra("itemId", itemId);

            if (this.gotoMain) {
                goToMain();
            }

            context.startActivity(intent);

        } catch (Exception e) {
            Crashlytics.logException(e);
            Toast.makeText(context, StringUtils.textFormat(R.string.error_cant_open_activity, menu.getName()), Toast.LENGTH_SHORT).show();
        }
    }
}
