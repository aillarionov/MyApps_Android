package ru.gastroafisha.MyApps.Proxy;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * Created by alex on 22.12.2017.
 */

public class CatalogItemProxy {

    @SerializedName("id") @Expose // GSON
    private Integer id;

    @SerializedName("text") @Expose // GSON
    private String text;

    @SerializedName("raw") @Expose // GSON
    private String raw;

    @SerializedName("pictures") @Expose // GSON
    private List<PictureProxy> photos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public List<PictureProxy> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PictureProxy> photos) {
        this.photos = photos;
    }
}
