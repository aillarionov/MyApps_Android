package ru.gastroafisha.MyApps.Storage.DTO;

import java.util.List;
import java.util.Map;

import ru.gastroafisha.MyApps.MainApplication;
import ru.gastroafisha.MyApps.Model.ImageModel;
import ru.gastroafisha.MyApps.Proxy.ImageProxy;
import ru.gastroafisha.MyApps.Proxy.PictureProxy;

/**
 * Created by alex on 31.12.2017.
 */

public class PictureDTO {

    public static ImageModel ProxyToModel(PictureProxy proxy) {
        ImageModel model = new ImageModel();



        model.setUrl(calculateUrl(proxy.getImages()));

        return model;
    }

    private static String calculateUrl(List<ImageProxy> images) {
        if (images.isEmpty()) {
            return null;
        }

        Integer screenWidth = MainApplication.getInstance().getScreenSize().x;
        ImageProxy maxImage = null;
        Integer maxWidth = 0;

        // Выбор самого маленького изображения из тех: что больше экрана
        for (ImageProxy image : images) {
            if (image.getWidth() >= screenWidth && (image.getWidth() < maxWidth || 0 == maxWidth)) {
                maxWidth = image.getWidth();
                maxImage = image;
            }
        }

        // Есди не найдены, то самое большое изображение
        if (null == maxImage) {
            for (ImageProxy image : images) {
                if (image.getWidth() > maxWidth) {
                    maxWidth = image.getWidth();
                    maxImage = image;
                }
            }
        }

        return maxImage != null ? maxImage.getSource() : null;
    }
}
