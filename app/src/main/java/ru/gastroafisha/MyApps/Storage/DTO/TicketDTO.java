package ru.gastroafisha.MyApps.Storage.DTO;

import java.util.ArrayList;

import ru.gastroafisha.MyApps.Model.Org.TicketModel;
import ru.gastroafisha.MyApps.Proxy.TicketProxy;


/**
 * Created by alex on 31.12.2017.
 */

public class TicketDTO {

    public static TicketModel proxyToModel(Integer orgId, TicketProxy proxy) {
        TicketModel model = new TicketModel();

        model.setOrgId(orgId);

        model.setType(proxy.getType());
        model.setText(proxy.getText());
        model.setSource(proxy.getSource());
        model.setButton(proxy.getButton());

        return model;
    }

}
