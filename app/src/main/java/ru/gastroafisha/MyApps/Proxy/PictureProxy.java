package ru.gastroafisha.MyApps.Proxy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alex on 23.12.2017.
 */

public class PictureProxy {

    @SerializedName("images") @Expose
    public List<ImageProxy> images;

    public List<ImageProxy> getImages() {
        return images;
    }
    public void setImages(List<ImageProxy> images) {
        this.images = images;
    }
}
