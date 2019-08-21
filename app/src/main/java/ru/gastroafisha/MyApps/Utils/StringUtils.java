package ru.gastroafisha.MyApps.Utils;

import android.os.Build;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ru.gastroafisha.MyApps.MainApplication;

/**
 * Created by alex on 25.12.2017.
 */

public class StringUtils {

    public static String textFormat(Integer resource, Object... args) {
        String s = MainApplication.getInstance().getResources().getString(resource);
        return String.format(s, args);
    }

    public static Spanned textToHtml(String html){
        if (html == null) {
            return null;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(html);
        }
    }

    @Nullable
    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            StringBuilder hexString = new StringBuilder();
            for (byte digestByte : md.digest(input.getBytes()))
                hexString.append(String.format("%02X", digestByte));

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
