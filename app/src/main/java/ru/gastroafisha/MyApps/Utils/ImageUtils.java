package ru.gastroafisha.MyApps.Utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.crashlytics.android.Crashlytics;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import ru.gastroafisha.MyApps.MainApplication;
import ru.gastroafisha.MyApps.Model.ImageModel;
import ru.gastroafisha.MyApps.Utils.Classes.DisposableHolder;

/**
 * Created by alex on 21.01.2018.
 */

public class ImageUtils {

    public static Disposable setTemporaryImage(Integer orgId, ImageModel image, SubsamplingScaleImageView view) {
        DisposableHolder d = new DisposableHolder();

        if (image == null || image.getUrl() == null || image.getUrl().isEmpty()) {
            view.setImage(null);
            return d;
        }

        d.setDisposable(MainApplication.getInstance().getLocalImageStore().getTemporaryImagePath(orgId, image)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    view.setImage(ImageSource.uri(s));
                    d.dispose();
                }, throwable -> {
                    view.setImage(null);
                    Crashlytics.logException(throwable);
                    d.dispose();
                }));

        return d;
    }



    public static Disposable setTemporaryImage(Integer orgId, ImageModel image, ImageView view) {
        DisposableHolder d = new DisposableHolder();

        if (image == null || image.getUrl() == null || image.getUrl().isEmpty()) {
            view.setImageBitmap(null);
            return d;
        }

        d.setDisposable(MainApplication.getInstance().getLocalImageStore()
                .getTemporaryImage(orgId, image)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> {
                    view.setImageBitmap(bitmap);
                    d.dispose();
                }, throwable -> {
                    Crashlytics.logException(throwable);
                    d.dispose();
                }));

        return d.getDisposable();
    }

    public static Disposable setPermanentImage(Integer orgId, ImageModel image, ImageView view) {

        DisposableHolder d = new DisposableHolder();

        d.setDisposable(MainApplication.getInstance().getLocalImageStore()
                .getPermanentImage(orgId, image)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> {
                    view.setImageBitmap(bitmap);
                   d.dispose();
                }, throwable -> {
                    Crashlytics.logException(throwable);
                    d.dispose();
                }));

        return d.getDisposable();
    }

    public static Disposable setLocatStoredImage(Integer orgId, String name, Consumer<Bitmap> consumer) {

        DisposableHolder d = new DisposableHolder();

        d.setDisposable(MainApplication.getInstance().getLocalImageStore()
                .getLocalStoredImage(orgId, name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> {
                    consumer.accept(bitmap);
                    d.dispose();
                }, throwable -> {
                    Crashlytics.logException(throwable);
                    consumer.accept(null);
                    d.dispose();
                }));

        return d.getDisposable();
    }
}
