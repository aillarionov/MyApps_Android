package ru.gastroafisha.MyApps.Storage.DTO;

import ru.gastroafisha.MyApps.Model.Form.FormModel;
import ru.gastroafisha.MyApps.Proxy.FormProxy;

/**
 * Created by alex on 31.12.2017.
 */

public class FormDTO {

    public static FormModel proxyToModel(Integer orgId, FormProxy proxy) {
        FormModel model = new FormModel();

        model.setOrgId(orgId);

        model.setId(proxy.getId());

        model.setName(proxy.getName());

        return model;
    }
}
