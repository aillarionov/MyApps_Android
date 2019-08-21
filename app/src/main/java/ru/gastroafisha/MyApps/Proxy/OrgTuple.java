package ru.gastroafisha.MyApps.Proxy;

import java.util.List;

import ru.gastroafisha.MyApps.Model.Catalog.CatalogModel;
import ru.gastroafisha.MyApps.Model.Form.FormItemModel;
import ru.gastroafisha.MyApps.Model.Form.FormModel;
import ru.gastroafisha.MyApps.Model.Menu.MenuItemModel;
import ru.gastroafisha.MyApps.Model.Org.OrgModel;
import ru.gastroafisha.MyApps.Model.Org.TicketModel;

/**
 * Created by alex on 26.01.2018.
 */

public class OrgTuple {
    private OrgModel orgModel;
    private TicketModel ticketModel;
    private List<MenuItemModel> menuItems;
    private List<CatalogModel> catalogs;
    private List<FormModel> forms;
    private List<FormItemModel> formItems;

    public OrgModel getOrgModel() {
        return orgModel;
    }

    public void setOrgModel(OrgModel orgModel) {
        this.orgModel = orgModel;
    }

    public TicketModel getTicketModel() {
        return ticketModel;
    }

    public void setTicketModel(TicketModel ticketModel) {
        this.ticketModel = ticketModel;
    }

    public List<MenuItemModel> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItemModel> menuItems) {
        this.menuItems = menuItems;
    }

    public List<CatalogModel> getCatalogs() {
        return catalogs;
    }

    public void setCatalogs(List<CatalogModel> catalogs) {
        this.catalogs = catalogs;
    }

    public List<FormModel> getForms() {
        return forms;
    }

    public void setForms(List<FormModel> forms) {
        this.forms = forms;
    }

    public List<FormItemModel> getFormItems() {
        return formItems;
    }

    public void setFormItems(List<FormItemModel> formItems) {
        this.formItems = formItems;
    }
}
