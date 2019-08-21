package ru.gastroafisha.MyApps;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;

import io.fabric.sdk.android.Fabric;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.gastroafisha.MyApps.Model.Org.OrgConfigModel;
import ru.gastroafisha.MyApps.Storage.ConfigStore;
import ru.gastroafisha.MyApps.Storage.Image.LocalImageStore;
import ru.gastroafisha.MyApps.Storage.LocalDataStorage;
import ru.gastroafisha.MyApps.Utils.ActivityOpener;
import ru.gastroafisha.MyApps.Utils.Classes.DisposableHolder;
import ru.gastroafisha.MyApps.Utils.StringUtils;

/**
 * Created by alex on 24.12.2017.
 */

public class MainApplication extends Application {
    private static MainApplication instance;
    private FirebaseAnalytics mFirebaseAnalytics;

    private LocalDataStorage localDataStorage;
    private LocalImageStore localImageStore;
    private ConfigStore configStore;

    private Activity mainActivity;

    private Point screenSize = new Point(0, 0);
    private Point screenSizeDp = new Point(0, 0);

    public MainApplication() {
        instance = this;
    }

    public static MainApplication getInstance() {
        return instance;
    }

    public LocalDataStorage getLocalDataStorage() {
        return localDataStorage;
    }

    public LocalImageStore getLocalImageStore() {
        return localImageStore;
    }

    public Activity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(Activity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public ConfigStore getConfigStore() {
        return this.configStore;
    }

    public Point getScreenSize() {
        return screenSize;
    }

    public Point getScreenSizeDp() {
        return screenSizeDp;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        localDataStorage = new LocalDataStorage(this);
        localImageStore = new LocalImageStore(this);
        configStore = new ConfigStore(this);


        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getSize(screenSize);

        Configuration configuration = getResources().getConfiguration();
        screenSizeDp.x = configuration.screenWidthDp;
        screenSizeDp.y = configuration.screenHeightDp;

        // PUSH
        try {
            FirebaseApp.initializeApp(this);
        } catch (Exception e) {
            Crashlytics.logException(e);
        }

        // Analytics
        try {
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        } catch (Exception e) {
            Crashlytics.logException(e);
        }

        // Send client data
        DisposableHolder d = new DisposableHolder();
        d.setDisposable(getLocalDataStorage().uploadClientData()
                .subscribe(() -> {
                    d.dispose();
                }, throwable -> {
                    Crashlytics.logException(throwable);
                    d.dispose();
                })
        );
    }




}

