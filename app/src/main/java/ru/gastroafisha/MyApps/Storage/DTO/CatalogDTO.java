package ru.gastroafisha.MyApps.Storage.DTO;

import java.util.ArrayList;
import java.util.List;

import ru.gastroafisha.MyApps.Model.Catalog.CatalogModel;
import ru.gastroafisha.MyApps.Proxy.CatalogProxy;
import ru.gastroafisha.MyApps.Utils.Classes.GMDate;

/**
 * Created by alex on 31.12.2017.
 */

public class CatalogDTO {

    public static CatalogModel proxyToModel(Integer orgId, CatalogProxy proxy) {
        CatalogModel model = new CatalogModel();

        model.setOrgId(orgId);

        model.setId(proxy.getId());

        model.setType(proxy.getType());

        model.setLastChange(GMDate.min());

        return model;
    }

    public static List<CatalogModel> proxiesToModels(Integer orgId, List<CatalogProxy> proxies) {
        List<CatalogModel> models = new ArrayList<>();

        for (CatalogProxy proxy : proxies) {
            models.add(proxyToModel(orgId, proxy));
        }

        return models;
    }
}
