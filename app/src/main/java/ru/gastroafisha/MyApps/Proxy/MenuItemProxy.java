package ru.gastroafisha.MyApps.Proxy;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ru.gastroafisha.MyApps.Model.Menu.MenuItemCls;
import ru.gastroafisha.MyApps.Model.Menu.MenuItemType;
import ru.gastroafisha.MyApps.Model.StringMap;


/**
 * Created by alex on 22.12.2017.
 */

public class MenuItemProxy {

    @SerializedName("id") @Expose // GSON
    private Integer id;

    @SerializedName("name") @Expose // GSON
    private String name;

    @SerializedName("type") @Expose // GSON
    private MenuItemType type;

    @SerializedName("icon") @Expose // GSON
    private String icon;

    @SerializedName("params") @Expose // GSON
    private StringMap params;

    @SerializedName("order") @Expose // GSON
    private Integer order;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MenuItemType getType() {
        return type;
    }

    public void setType(MenuItemType type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public StringMap getParams() {
        return params;
    }

    public void setParams(StringMap params) {
        this.params = params;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
