package ru.gastroafisha.MyApps.Storage.Image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.crashlytics.android.Crashlytics;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.gastroafisha.MyApps.Model.ImageModel;
import ru.gastroafisha.MyApps.Utils.Services.FileUtils;
import ru.gastroafisha.MyApps.Utils.StreamUtils;
import ru.gastroafisha.MyApps.Utils.StringUtils;

/**
 * Created by alex on 26.12.2017.
 */

public class LocalImageStore {

    private static String TEMPORARY_STORE = "temp_store";
    private static String PERMANENT_STORE = "perm_store";
    private static String SYSTEM_STORE = "sys_store";

    private Context context;

    public LocalImageStore(Context context) {
        this.context = context;
    }

    public void storeTemporaryImage(Integer orgId, String url) throws FileNotFoundException {
        putDataFromUrl(TEMPORARY_STORE + "/" + orgId.toString(), url);
    }

    public void storePermanentImage(Integer orgId, String url) throws FileNotFoundException {
        putDataFromUrl(PERMANENT_STORE + "/" + orgId.toString(), url);
    }

    public void storeSystemImage(String url) throws FileNotFoundException {
        putDataFromUrl(SYSTEM_STORE, url);
    }

    public void storeLocalStoredImage(Integer orgId, String name, InputStream stream) throws FileNotFoundException {
        putDataFromStream(PERMANENT_STORE + "/" + orgId.toString(), name, stream);
    }

    public Boolean isTemporaryFileExists(Integer orgId, String url) {
        String path = TEMPORARY_STORE + "/" + orgId.toString();
        if (url == null || path == null) {
            return false;
        }

        File file = null;
        try {
            file = getFile(path, url);
        } catch (FileNotFoundException e) {
            return false;
        }

        return file.exists();
    }


    public Single<String> getTemporaryImagePath(Integer orgId, ImageModel image) {
        return Single.fromCallable(() -> {
            try {
                // Попытка получить файл из хранилиша
                File file = getFile(TEMPORARY_STORE + "/" + orgId.toString(), image.getUrl());
                if (file.exists()) {
                    return file.getAbsolutePath();
                }
                // Попытка скачать файл
                storeTemporaryImage(orgId, image.getUrl());

                // Повторная попытка получить файл из хранилиша
                file = getFile(TEMPORARY_STORE + "/" + orgId.toString(), image.getUrl());

                if (file.exists()) {
                    return file.getAbsolutePath();
                }
            } catch (FileNotFoundException e1) {
                Crashlytics.logException(e1);
            }

            return "";

        }).subscribeOn(Schedulers.io());
    }

    public Single<Bitmap> getTemporaryImage(Integer orgId, ImageModel image) {
        return Single.fromCallable(() -> {
            Bitmap bitmap = null;

            try {
                // Попытка получить файл из хранилиша
                bitmap = getData(TEMPORARY_STORE + "/" + orgId.toString(), image.getUrl());
            } catch (FileNotFoundException e) {
                try {
                    // Попытка скачать файл
                    storeTemporaryImage(orgId, image.getUrl());

                    // Повторная попытка получить файл из хранилиша
                    bitmap = getData(TEMPORARY_STORE + "/" + orgId.toString(), image.getUrl());
                    updateSize(image, bitmap);
                } catch (FileNotFoundException e1) {
                    Crashlytics.logException(e1);
                }
            }

            return bitmap != null ? bitmap : Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565);
        }).subscribeOn(Schedulers.io());
    }

    public Single<Bitmap> getPermanentImage(Integer orgId, ImageModel image) {
        return Single.fromCallable(() -> {
            Bitmap bitmap = null;

            try {
                // Попытка получить файл из хранилиша
                bitmap = getData(PERMANENT_STORE + "/" + orgId.toString(), image.getUrl());
            } catch (FileNotFoundException e) {
                try {
                    // Попытка скачать файл
                    storePermanentImage(orgId, image.getUrl());

                    // Повторная попытка получить файл из хранилиша
                    bitmap = getData(PERMANENT_STORE + "/" + orgId.toString(), image.getUrl());
                    updateSize(image, bitmap);
                } catch (FileNotFoundException e1) {
                    Crashlytics.logException(e1);
                }

            }

            return bitmap != null ? bitmap : Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565);
        }).subscribeOn(Schedulers.io());
    }

    public Single<Bitmap> getSystemImage(ImageModel image) {
        return Single.fromCallable(() -> {
            Bitmap bitmap = null;

            try {
                // Попытка получить файл из хранилиша
                bitmap = getData(SYSTEM_STORE, image.getUrl());
            } catch (FileNotFoundException e) {
                try {
                    // Попытка скачать файл
                    storeSystemImage(image.getUrl());

                    // Повторная попытка получить файл из хранилиша
                    bitmap = getData(SYSTEM_STORE, image.getUrl());
                    updateSize(image, bitmap);
                } catch (FileNotFoundException e1) {
                    Crashlytics.logException(e1);
                }
            }

            return bitmap != null ? bitmap : Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565);
        }).subscribeOn(Schedulers.io());
    }

    public Single<Bitmap> getLocalStoredImage(Integer orgId, String name) {
        return Single.fromCallable(() -> {
            Bitmap bitmap = null;

            try {
                // Попытка получить файл из хранилиша
                bitmap = getData(PERMANENT_STORE + "/" + orgId.toString(), name);
            } catch (FileNotFoundException e) {
                //Crashlytics.logException(e);
            }

            return bitmap;
        }).subscribeOn(Schedulers.io());
    }


    private void updateSize(ImageModel image, Bitmap bitmap) {
        image.setWidth(bitmap.getWidth());
        image.setHeight(bitmap.getHeight());
    }

    private File getFile(String path, String url) throws FileNotFoundException {
        if (url == null || path == null) {
            throw new FileNotFoundException();
        }

        File directory = new File(context.getFilesDir(), path);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(directory, StringUtils.md5(url));
        return file;
    }

    private Bitmap getData(String path, String url) throws FileNotFoundException {
        if (url == null || path == null) {
            throw new FileNotFoundException();
        }

        File file = getFile(path, url);

        if (!file.exists()) {
            throw new FileNotFoundException();
        } else {

            BufferedInputStream fis = null;
            Bitmap bitmap = null;

            try {
                fis = new BufferedInputStream(new FileInputStream(file));
                bitmap = BitmapFactory.decodeStream(fis);
            } catch (Exception e) {
                Crashlytics.logException(e);
                e.printStackTrace();
            }

            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    Crashlytics.logException(e);
                    e.printStackTrace();
                }
            }

            return bitmap != null ? bitmap : Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565);
        }
    }

    private void putDataFromUrl(String path, String url) throws FileNotFoundException {
        if (url == null || path == null) {
            throw new FileNotFoundException();
        }

        InputStream is = null;

        try {
            is = new URL(url).openConnection().getInputStream();
            FileOutputStream fos = null;

            File rf = getFile(path, url);

            try {
                fos = new FileOutputStream(rf);
                StreamUtils.copy(is, fos);
            } catch (Exception e) {

                try {
                    rf.delete();
                } catch (Exception ef) {}

                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void putDataFromStream(String path, String name, InputStream stream) throws FileNotFoundException {
        if (stream == null || path == null || name == null) {
            throw new FileNotFoundException();
        }

        FileOutputStream fos = null;

        File rf = getFile(path, name);

        try {
            fos = new FileOutputStream(rf);
            StreamUtils.copy(stream, fos);
        } catch (Exception e) {

            try {
                rf.delete();
            } catch (Exception ef) {}

            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public Completable cleanOrgTempImages(Integer orgId) {
        return Completable.fromAction(() -> {
            // Clean temp
            FileUtils.deleteDirectoryRecursive(new File(context.getFilesDir(), TEMPORARY_STORE + "/" + orgId.toString()));
        }).subscribeOn(Schedulers.io());
    }

    public Completable cleanOrgImages(Integer orgId) {
        return Completable.fromAction(() -> {
            // Clean temp
            FileUtils.deleteDirectoryRecursive(new File(context.getFilesDir(), TEMPORARY_STORE + "/" + orgId.toString()));
            // Clean permanent (Ticket, etc...)
            FileUtils.deleteDirectoryRecursive(new File(context.getFilesDir(), PERMANENT_STORE + "/" + orgId.toString()));
        }).subscribeOn(Schedulers.io());
    }

}
