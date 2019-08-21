package ru.gastroafisha.MyApps.Model.Form;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;


/**
 * Created by alex on 22.12.2017.
 */

@Entity(tableName = "formitems", primaryKeys = {"orgId", "formId", "name"})
public class FormItemModel {
    @NonNull @ColumnInfo(name = "orgId") // SQLite
    private Integer orgId;

    @NonNull @ColumnInfo(name = "formId") // SQLite
    private Integer formId;

    @NonNull @ColumnInfo(name = "name") // SQLite
    private String name;

    @NonNull @ColumnInfo(name = "title") // SQLite
    private String title;

    @ColumnInfo(name = "defaultValue") // SQLite
    private String defaultValue;

    @NonNull @ColumnInfo(name = "required") // SQLite
    private Boolean required;

    @ColumnInfo(name = "type") // SQLite
    private FormItemType type;

    @ColumnInfo(name = "order_") // SQLite
    private Integer order;

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(@NonNull Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getFormId() {
        return formId;
    }

    public void setFormId(Integer formId) {
        this.formId = formId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(@NonNull Boolean required) {
        this.required = required;
    }

    public FormItemType getType() {
        return type;
    }

    public void setType(FormItemType type) {
        this.type = type;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
