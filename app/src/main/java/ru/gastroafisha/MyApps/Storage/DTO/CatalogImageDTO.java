package ru.gastroafisha.MyApps.Storage.DTO;

import java.util.ArrayList;
import java.util.List;

import ru.gastroafisha.MyApps.Model.Item.CatalogImageModel;
import ru.gastroafisha.MyApps.Proxy.CatalogImageProxy;

/**
 * Created by alex on 31.12.2017.
 */

public class CatalogImageDTO {

    public static CatalogImageModel proxyToModel(Integer orgId, Integer catalogId, Integer ord, CatalogImageProxy proxy) {
        CatalogImageModel model = new CatalogImageModel();

        CatalogItemDTO.fillProxyToModel(orgId, catalogId, ord, proxy, model);

        return model;
    }

    public static List<CatalogImageModel> proxiesToModels(Integer orgId, Integer catalogId, List<CatalogImageProxy> proxies) {
        List<CatalogImageModel> models = new ArrayList<>();

        Integer i = 0;
        for (CatalogImageProxy proxy : proxies) {
            models.add(proxyToModel(orgId, catalogId, i++, proxy));
        }

        return models;
    }

    protected static void fillProxyToModel(Integer orgId, Integer catalogId, CatalogImageProxy proxy, CatalogImageModel model) {

    }
}
