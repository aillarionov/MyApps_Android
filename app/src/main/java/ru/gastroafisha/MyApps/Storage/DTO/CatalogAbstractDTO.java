package ru.gastroafisha.MyApps.Storage.DTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 20.01.2018.
 */

public abstract class CatalogAbstractDTO<MT, PT> {

    protected abstract void fillProxyToModel(MT model, PT proxy);
    protected abstract MT newModel(Integer orgId, Integer catalogId, Integer ord);

    public MT proxyToModel(Integer orgId, Integer catalogId, Integer ord, PT proxy) {
        MT model = newModel(orgId, catalogId, ord);

        fillProxyToModel(model, proxy);

        return model;
    }


    public List<MT> proxiesToModels(Integer orgId, Integer catalogId, List<PT> proxies) {
        List<MT> items = new ArrayList<>();

        Integer i = 0;
        for (PT proxy : proxies) {
            items.add(proxyToModel(orgId, catalogId, i++, proxy));
        }

        return items;
    }
}
