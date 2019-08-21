package ru.gastroafisha.MyApps.Utils.Services;

import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.gastroafisha.MyApps.MainApplication;
import ru.gastroafisha.MyApps.Utils.ActivityOpener;
import ru.gastroafisha.MyApps.Utils.Classes.DisposableHolder;
import ru.gastroafisha.MyApps.Utils.Notification.NotificationPayload;

/**
 * Created by alex on 26.01.2018.
 */

public class NotificationOpener {

    public static void parseBundle(Bundle bundle) {
        try {
            if (bundle.containsKey("payload")) {
                NotificationPayload payload = new Gson().fromJson(bundle.getString("payload"), NotificationPayload.class);

                if (payload.getNews() != null) {
                    methodOpenNews(payload.getNews().getOrgId(), payload.getNews().getCatalogId(), payload.getNews().getItemId());
                }
            }
        } catch (Exception e) {
            Crashlytics.logException(e);
            e.printStackTrace();
        }
    }

    public static void parseData(Map<String, String> data) {
        try {
            if (data.containsKey("method")) {
                NotificationPayload payload = new Gson().fromJson(data.get("payload"), NotificationPayload.class);

                if (payload.getNews() != null) {
                    methodOpenNews(payload.getNews().getOrgId(), payload.getNews().getCatalogId(), payload.getNews().getItemId());
                }
            }
        } catch (Exception e) {
            Crashlytics.logException(e);
            e.printStackTrace();
        }
    }

    private static void methodOpenNews(Integer orgId, Integer catalogId, Integer itemId) {
        DisposableHolder d = new DisposableHolder();

        d.setDisposable(MainApplication.getInstance().getLocalDataStorage().refreshCatalogItems(orgId, catalogId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(() -> {
                ActivityOpener opener = new ActivityOpener(MainApplication.getInstance().getMainActivity(), orgId, null);
                opener.OpenNews(catalogId, itemId);
                d.dispose();
            })
        );
    }
}
