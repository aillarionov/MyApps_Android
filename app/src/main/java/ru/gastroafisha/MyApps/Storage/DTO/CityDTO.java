package ru.gastroafisha.MyApps.Storage.DTO;

import ru.gastroafisha.MyApps.Model.Org.CityModel;
import ru.gastroafisha.MyApps.Proxy.CityProxy;


/**
 * Created by alex on 12.05.2018.
 */

public class CityDTO {

    public static CityModel proxyToModel(CityProxy proxy) {
        CityModel model = new CityModel();

        model.setId(proxy.getId());

        model.setName(proxy.getName());

        return model;
    }

}
