package ru.gastroafisha.MyApps.Proxy;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ru.gastroafisha.MyApps.Model.Form.FormItemType;


/**
 * Created by alex on 22.12.2017.
 */

public class FormItemProxy {

    @SerializedName("name") @Expose // GSON
    private String name;

    @SerializedName("title") @Expose // GSON
    private String title;

    @SerializedName("defaultValue") @Expose // GSON
    private String defaultValue;

    @SerializedName("required") @Expose // GSON
    private Boolean required;

    @SerializedName("type") @Expose // GSON
    private FormItemType type;

    @SerializedName("order") @Expose // GSON
    private Integer order;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
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

    public void setRequired(Boolean required) {
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
