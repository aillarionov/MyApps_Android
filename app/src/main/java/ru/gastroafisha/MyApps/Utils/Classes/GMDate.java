package ru.gastroafisha.MyApps.Utils.Classes;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;

public class GMDate {

    private Date date;

    public static final SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);
    public static final SimpleDateFormat lastModifiedFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);

    public GMDate() {
        this.date = null;
    }

    public GMDate(Date date) {
        this.date = date;
    }

    public static Date min() {
        return new Date(Long.MIN_VALUE);
    }

    public static GMDate fromISO8601(String string) throws ParseException {
        if (string == null || string.isEmpty()) {
            return new GMDate();
        } else {
            return new GMDate(iso8601Format.parse(string));
        }
    }

    public static GMDate fromISO8601(ResponseBody body) throws ParseException, IOException {
        return fromISO8601(body.string());
    }

    public static GMDate fromLastModified(String string) throws ParseException {
        if (string == null || string.isEmpty()) {
            return new GMDate();
        } else {
            return new GMDate(lastModifiedFormat.parse(string));
        }
    }

    public static GMDate fromLastModified(ResponseBody body) throws ParseException, IOException {
        return fromISO8601(body.string());
    }


    public Date getDate() {
        return date;
    }

    public Boolean isEmpty() {
        return date == null;
    }

    public Boolean differ(Date value) {
        if (date == null || value == null) {
            return true;
        }

        return date.compareTo(value) != 0;
    }
}
