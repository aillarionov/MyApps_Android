package ru.gastroafisha.MyApps.Utils;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by alex on 16.01.2018.
 */

public class EventUtils {

    public interface Callback {
        public void call(String s);
    }


    public static TextWatcher generateDelayedTextWatcher(Integer delay, Callback callback) {
        return new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            Handler handler = new Handler(Looper.getMainLooper());
            Runnable workRunnable;

            @Override
            public void afterTextChanged(final Editable s) {
                handler.removeCallbacks(workRunnable);
                workRunnable = () -> callback.call(s.toString());
                handler.postDelayed(workRunnable, delay);
            }
        };
    }

}
