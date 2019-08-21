package ru.gastroafisha.MyApps.Proxy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by alex on 23.12.2017.
 */

public class ImageProxy {

    @SerializedName("width") @Expose
    public Integer width;

    @SerializedName("height") @Expose
    public Integer height;

    @SerializedName("source") @Expose
    public String source;

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
