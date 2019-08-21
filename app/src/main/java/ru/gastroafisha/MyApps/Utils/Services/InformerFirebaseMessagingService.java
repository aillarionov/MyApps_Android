package ru.gastroafisha.MyApps.Utils.Services;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


/**
 * Created by alex on 26.01.2018.
 */

public class InformerFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0) {
            NotificationOpener.parseData(remoteMessage.getData());

        }

        if (remoteMessage.getNotification() != null) {
        }
    }
}
