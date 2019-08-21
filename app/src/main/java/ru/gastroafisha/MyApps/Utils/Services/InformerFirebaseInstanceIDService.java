package ru.gastroafisha.MyApps.Utils.Services;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import ru.gastroafisha.MyApps.MainApplication;
import ru.gastroafisha.MyApps.Utils.Classes.DisposableHolder;

/**
 * Created by alex on 26.01.2018.
 */

public class InformerFirebaseInstanceIDService extends FirebaseInstanceIdService {

    public static String getToken() {
        return FirebaseInstanceId.getInstance().getToken();
    }

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        sendRegistrationToServer();
    }

    private void sendRegistrationToServer() {
        DisposableHolder d = new DisposableHolder();
        d.setDisposable(MainApplication.getInstance().getLocalDataStorage().uploadClientData()
                .subscribe(() -> {
                    d.dispose();
                }, throwable -> {
                    Crashlytics.logException(throwable);
                    d.dispose();
                } ));
    }
}
