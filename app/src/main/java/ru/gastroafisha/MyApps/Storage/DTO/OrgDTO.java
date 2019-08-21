package ru.gastroafisha.MyApps.Storage.DTO;

import java.util.ArrayList;
import java.util.List;

import ru.gastroafisha.MyApps.Model.ImageModel;
import ru.gastroafisha.MyApps.Model.Org.CityModel;
import ru.gastroafisha.MyApps.Model.Org.OrgModel;
import ru.gastroafisha.MyApps.Proxy.FormProxy;
import ru.gastroafisha.MyApps.Proxy.OrgProxy;
import ru.gastroafisha.MyApps.Proxy.OrgTuple;

/**
 * Created by alex on 31.12.2017.
 */

public class OrgDTO {

    public static OrgModel proxyToModel(OrgProxy proxy) {
        OrgModel model = new OrgModel();

        model.setId(proxy.getId());
        model.setName(proxy.getName());

        if (proxy.getLogo() != null) {
            ImageModel image = new ImageModel();
            image.setUrl(proxy.getLogo());
            model.setLogo(image);
        }

        model.setCity(proxy.getCity() != null ? CityDTO.proxyToModel(proxy.getCity()) : null);

        return model;
    }

    public static List<OrgModel> proxiesToModels(List<OrgProxy> proxies) {
        List<OrgModel> models = new ArrayList<>();

        for (OrgProxy proxy : proxies) {
            models.add(proxyToModel(proxy));
        }

        return models;
    }

    public static OrgTuple proxyToTuple(OrgProxy proxy){
        OrgTuple orgTuple = new OrgTuple();

        orgTuple.setOrgModel(proxyToModel(proxy));

        orgTuple.setTicketModel(proxy.getTicket() != null ? TicketDTO.proxyToModel(proxy.getId(), proxy.getTicket()) : null);

        orgTuple.setMenuItems(MenuItemDTO.proxiesToModels(proxy.getId(), proxy.getMenuItems()));

        orgTuple.setCatalogs(CatalogDTO.proxiesToModels(proxy.getId(), proxy.getCatalogs()));

        orgTuple.setForms(new ArrayList<>());
        orgTuple.setFormItems(new ArrayList<>());

        for (FormProxy formProxy: proxy.getForms()) {
            orgTuple.getForms().add(FormDTO.proxyToModel(proxy.getId(), formProxy));
            orgTuple.getFormItems().addAll(FormItemDTO.proxiesToModels(proxy.getId(), formProxy.getId(), formProxy.getFormItems()));
        }

        return orgTuple;
    }
}
