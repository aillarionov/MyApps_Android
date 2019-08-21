package ru.gastroafisha.MyApps.Utils;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.crashlytics.android.Crashlytics;

import java.io.IOException;

import ru.gastroafisha.MyApps.MainApplication;

/**
 * Created by alex on 28.01.2018.
 */

public class ADUtils {
    public static String getADId() {

        AdvertisingIdClient.Info idInfo = null;
        try {
            idInfo = AdvertisingIdClient.getAdvertisingIdInfo(MainApplication.getInstance().getApplicationContext());
        } catch (IOException e) {
            Crashlytics.logException(e);
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            Crashlytics.logException(e);
            e.printStackTrace();
        } catch (GooglePlayServicesRepairableException e) {
            Crashlytics.logException(e);
            e.printStackTrace();
        }

        String advertId = null;
        try {
            advertId = idInfo.getId();
        } catch (NullPointerException e) {
            Crashlytics.logException(e);
            e.printStackTrace();
        }

        return advertId;
    }
}
