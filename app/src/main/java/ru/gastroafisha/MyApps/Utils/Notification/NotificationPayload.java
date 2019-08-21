package ru.gastroafisha.MyApps.Utils.Notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alex on 16.03.2018.
 */

public class NotificationPayload {

    @SerializedName("news") @Expose // GSON
    NotificationNews news;

    public NotificationNews getNews() {
        return news;
    }

    public void setNews(NotificationNews news) {
        this.news = news;
    }
}
