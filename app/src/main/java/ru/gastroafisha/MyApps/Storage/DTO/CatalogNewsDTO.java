package ru.gastroafisha.MyApps.Storage.DTO;

import java.util.ArrayList;
import java.util.List;

import ru.gastroafisha.MyApps.Model.Item.CatalogNewsModel;
import ru.gastroafisha.MyApps.Proxy.CatalogNewsProxy;

/**
 * Created by alex on 31.12.2017.
 */

public class CatalogNewsDTO {

    public static CatalogNewsModel proxyToModel(Integer orgId, Integer catalogId, Integer ord, CatalogNewsProxy proxy) {
        CatalogNewsModel model = new CatalogNewsModel();

        CatalogItemDTO.fillProxyToModel(orgId, catalogId, ord, proxy, model);

        return model;
    }

    public static List<CatalogNewsModel> proxiesToModels(Integer orgId, Integer catalogId, List<CatalogNewsProxy> proxies) {
        List<CatalogNewsModel> models = new ArrayList<>();

        Integer i = 0;
        for (CatalogNewsProxy proxy : proxies) {
            models.add(proxyToModel(orgId, catalogId, i++, proxy));
        }

        return models;
    }

    protected static void fillProxyToModel(Integer orgId, Integer catalogId, CatalogNewsProxy proxy, CatalogNewsModel model) {

    }
}
