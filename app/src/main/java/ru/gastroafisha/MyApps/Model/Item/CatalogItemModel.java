package ru.gastroafisha.MyApps.Model.Item;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import java.util.List;

import ru.gastroafisha.MyApps.Model.ImageModel;


/**
 * Created by alex on 22.12.2017.
 */

@Entity(tableName = "catalogitems", primaryKeys = {"orgId", "catalogId", "id"})
public class CatalogItemModel {

    @NonNull @ColumnInfo(name = "orgId") // SQLite
    private Integer orgId;

    @NonNull @ColumnInfo(name = "catalogId") // SQLite
    private Integer catalogId;

    @NonNull @ColumnInfo(name = "id") // SQLite
    private Integer id;

    @ColumnInfo(name = "ord") // SQLite
    private Integer ord;

    @ColumnInfo(name = "text") // SQLite
    private String text;

    @ColumnInfo(name = "raw") // SQLite
    private String raw;

    @ColumnInfo(name = "photo") // SQLite
    private ImageModel photo;

    @ColumnInfo(name = "photos") // SQLite
    private List<ImageModel> photos;

    @NonNull
    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(@NonNull Integer orgId) {
        this.orgId = orgId;
    }

    @NonNull
    public Integer getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(@NonNull Integer catalogId) {
        this.catalogId = catalogId;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public Integer getOrd() {
        return ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
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

    public ImageModel getPhoto() {
        return photo;
    }

    public void setPhoto(ImageModel photo) {
        this.photo = photo;
    }

    public List<ImageModel> getPhotos() {
        return photos;
    }

    public void setPhotos(List<ImageModel> photos) {
        this.photos = photos;
    }

    public String getTitle() {
        return this.text;
    }
}
