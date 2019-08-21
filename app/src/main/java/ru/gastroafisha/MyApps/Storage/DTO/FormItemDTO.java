package ru.gastroafisha.MyApps.Storage.DTO;

import java.util.ArrayList;
import java.util.List;

import ru.gastroafisha.MyApps.Model.Form.FormItemModel;
import ru.gastroafisha.MyApps.Proxy.FormItemProxy;

/**
 * Created by alex on 31.12.2017.
 */

public class FormItemDTO {

    public static FormItemModel proxyToModel(Integer orgId, Integer formId, FormItemProxy proxy) {
        FormItemModel model = new FormItemModel();

        model.setOrgId(orgId);

        model.setFormId(formId);

        model.setName(proxy.getName());
        model.setTitle(proxy.getTitle());
        model.setType(proxy.getType());
        model.setRequired(proxy.getRequired());
        model.setDefaultValue(proxy.getDefaultValue());
        model.setOrder(proxy.getOrder());

        return model;
    }

    public static List<FormItemModel> proxiesToModels(Integer orgId, Integer formId, List<FormItemProxy> proxies) {
        List<FormItemModel> models = new ArrayList<>();

        for (FormItemProxy proxy : proxies) {
            models.add(proxyToModel(orgId, formId, proxy));
        }

        return models;
    }
}
