package ru.gastroafisha.MyApps.Storage.DTO;

import java.util.ArrayList;
import java.util.List;

import ru.gastroafisha.MyApps.Model.Item.CatalogItemModel;
import ru.gastroafisha.MyApps.Model.ImageModel;
import ru.gastroafisha.MyApps.Proxy.CatalogItemProxy;
import ru.gastroafisha.MyApps.Proxy.ImageProxy;
import ru.gastroafisha.MyApps.Proxy.PictureProxy;

/**
 * Created by alex on 31.12.2017.
 */

public class CatalogItemDTO {

    public static CatalogItemModel proxyToModel(Integer orgId, Integer catalogId, Integer ord, CatalogItemProxy proxy) {
        CatalogItemModel model = new CatalogItemModel();

        fillProxyToModel(orgId, catalogId, ord, proxy, model);

        return model;
    }

    public static List<CatalogItemModel> proxiesToModels(Integer orgId, Integer catalogId, List<CatalogItemProxy> proxies) {
        List<CatalogItemModel> models = new ArrayList<>();

        Integer i = 0;
        for (CatalogItemProxy proxy : proxies) {
            models.add(proxyToModel(orgId, catalogId, i++, proxy));
        }

        return models;
    }

    protected static void fillProxyToModel(Integer orgId, Integer catalogId, Integer ord, CatalogItemProxy proxy, CatalogItemModel model) {

        model.setOrgId(orgId);
        model.setCatalogId(catalogId);
        model.setId(proxy.getId());

        model.setOrd(ord);

        model.setText(proxy.getText());
        model.setRaw(proxy.getRaw());


        if (proxy.getPhotos() != null) {
            List<ImageModel> imageModelList = new ArrayList<>();
            Boolean first = true;
            for (PictureProxy pictureProxy : proxy.getPhotos()) {
                if (first) {
                    model.setPhoto(PictureDTO.ProxyToModel(pictureProxy));
                    first = false;
                } else {
                    imageModelList.add(PictureDTO.ProxyToModel(pictureProxy));
                }
            }
            model.setPhotos(imageModelList);
        }
    }

}
