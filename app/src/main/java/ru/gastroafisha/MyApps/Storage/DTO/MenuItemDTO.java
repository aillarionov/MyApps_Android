package ru.gastroafisha.MyApps.Storage.DTO;

import java.util.ArrayList;
import java.util.List;

import ru.gastroafisha.MyApps.Model.Menu.MenuItemModel;
import ru.gastroafisha.MyApps.Proxy.MenuItemProxy;

/**
 * Created by alex on 31.12.2017.
 */

public class MenuItemDTO {

    public static MenuItemModel proxyToModel(Integer orgId, MenuItemProxy proxy) {
        MenuItemModel model = new MenuItemModel();

        model.setOrgId(orgId);

        model.setId(proxy.getId());
        model.setName(proxy.getName());
        model.setIcon(proxy.getIcon());
        model.setType(proxy.getType());
        model.setParams(proxy.getParams());
        model.setOrder(proxy.getOrder());

        return model;
    }


    public static List<MenuItemModel> proxiesToModels(Integer orgId, List<MenuItemProxy> proxies) {
        List<MenuItemModel> models = new ArrayList<>();

        for (MenuItemProxy proxy : proxies) {
            models.add(proxyToModel(orgId, proxy));
        }

        return models;
    }

}
