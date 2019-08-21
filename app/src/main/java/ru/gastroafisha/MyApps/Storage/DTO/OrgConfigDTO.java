package ru.gastroafisha.MyApps.Storage.DTO;

import java.util.ArrayList;
import java.util.List;

import ru.gastroafisha.MyApps.Model.Org.OrgConfigModel;
import ru.gastroafisha.MyApps.Proxy.OrgConfigProxy;

/**
 * Created by alex on 31.12.2017.
 */

public class OrgConfigDTO {

    public static OrgConfigProxy modelToProxy(OrgConfigModel model) {
        OrgConfigProxy proxy = new OrgConfigProxy();

        proxy.setOrgId(model.getOrgId());
        proxy.setVisitor(model.getVisitor());
        proxy.setPresenter(model.getPresenter());
        proxy.setReceiveNotifications(model.getReceiveNotifications());

        return proxy;
    }

    public static List<OrgConfigProxy> modelsToProxies(List<OrgConfigModel> models) {
        List<OrgConfigProxy> proxies = new ArrayList<>();

        for (OrgConfigModel model : models) {
            proxies.add(modelToProxy(model));
        }

        return proxies;
    }
}
