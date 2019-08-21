package ru.gastroafisha.MyApps.Storage.DTO;

import ru.gastroafisha.MyApps.Model.ClientDataModel;
import ru.gastroafisha.MyApps.Proxy.ClientDataProxy;

/**
 * Created by alex on 31.12.2017.
 */

public class ClientDataDTO {

    public static ClientDataProxy modelToProxy(ClientDataModel model) {
        ClientDataProxy proxy = new ClientDataProxy();

        proxy.setTokenId(model.getTokenId());
        proxy.setAdId(model.getAdId());
        proxy.setOrgConfigs(OrgConfigDTO.modelsToProxies(model.getOrgConfigs()));

        return proxy;
    }
}
