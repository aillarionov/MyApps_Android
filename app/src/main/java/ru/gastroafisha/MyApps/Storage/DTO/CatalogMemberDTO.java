package ru.gastroafisha.MyApps.Storage.DTO;

import java.util.ArrayList;
import java.util.List;

import ru.gastroafisha.MyApps.Model.Item.CatalogMemberModel;
import ru.gastroafisha.MyApps.Proxy.CatalogMemberProxy;

/**
 * Created by alex on 31.12.2017.
 */

public class CatalogMemberDTO {

    public static CatalogMemberModel proxyToModel(Integer orgId, Integer catalogId, Integer ord, CatalogMemberProxy proxy) {
        CatalogMemberModel model = new CatalogMemberModel();

        CatalogItemDTO.fillProxyToModel(orgId, catalogId, ord, proxy, model);

        fillProxyToModel(orgId, catalogId, proxy, model);

        return model;
    }

    public static List<CatalogMemberModel> proxiesToModels(Integer orgId, Integer catalogId, List<CatalogMemberProxy> proxies) {
        List<CatalogMemberModel> models = new ArrayList<>();

        Integer i = 0;
        for (CatalogMemberProxy proxy : proxies) {
            models.add(proxyToModel(orgId, catalogId, i++, proxy));
        }

        return models;
    }

    protected static void fillProxyToModel(Integer orgId, Integer catalogId, CatalogMemberProxy proxy, CatalogMemberModel model) {
        model.setName(proxy.getName());
        model.setStand(proxy.getStand());
        model.setCategories(proxy.getCategories());
        model.setEmails(proxy.getEmails());
        model.setPhones(proxy.getPhones());
        model.setSites(proxy.getSites());
        model.setVks(proxy.getVks());
        model.setFbs(proxy.getFbs());
        model.setInsts(proxy.getInsts());
    }
}
