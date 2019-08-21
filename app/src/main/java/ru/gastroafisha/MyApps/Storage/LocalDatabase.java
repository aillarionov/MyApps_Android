package ru.gastroafisha.MyApps.Storage;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import ru.gastroafisha.MyApps.Model.Item.CatalogImageModel;
import ru.gastroafisha.MyApps.Model.Item.CatalogItemModel;
import ru.gastroafisha.MyApps.Model.Item.CatalogMemberModel;
import ru.gastroafisha.MyApps.Model.Catalog.CatalogModel;
import ru.gastroafisha.MyApps.Model.Item.CatalogNewsModel;
import ru.gastroafisha.MyApps.Model.Item.FavoriteModel;
import ru.gastroafisha.MyApps.Model.Form.FormItemModel;
import ru.gastroafisha.MyApps.Model.Form.FormModel;
import ru.gastroafisha.MyApps.Model.Menu.MenuItemModel;
import ru.gastroafisha.MyApps.Model.Org.OrgConfigModel;
import ru.gastroafisha.MyApps.Model.Org.OrgModel;
import ru.gastroafisha.MyApps.Model.Org.TicketModel;
import ru.gastroafisha.MyApps.Storage.DAO.CatalogDAO;
import ru.gastroafisha.MyApps.Storage.DAO.CatalogImageDAO;
import ru.gastroafisha.MyApps.Storage.DAO.CatalogItemDAO;
import ru.gastroafisha.MyApps.Storage.DAO.CatalogMemberDAO;
import ru.gastroafisha.MyApps.Storage.DAO.CatalogNewsDAO;
import ru.gastroafisha.MyApps.Storage.DAO.FavoriteDAO;
import ru.gastroafisha.MyApps.Storage.DAO.FormDAO;
import ru.gastroafisha.MyApps.Storage.DAO.FormItemDAO;
import ru.gastroafisha.MyApps.Storage.DAO.MenuItemDAO;
import ru.gastroafisha.MyApps.Storage.DAO.OrgConfigDAO;
import ru.gastroafisha.MyApps.Storage.DAO.OrgDAO;
import ru.gastroafisha.MyApps.Storage.DAO.TicketDAO;

/**
 * Created by alex on 24.12.2017.
 */

@Database(
        version = 3,
        exportSchema = false,
        entities = {
                OrgModel.class,
                TicketModel.class,
                OrgConfigModel.class,
                MenuItemModel.class,
                FormModel.class, FormItemModel.class,
                FavoriteModel.class,
                CatalogModel.class,
                CatalogItemModel.class, CatalogMemberModel.class, CatalogImageModel.class, CatalogNewsModel.class
        }
)

@TypeConverters({Converters.class})
public abstract class LocalDatabase extends RoomDatabase {

    public abstract OrgDAO getOrgDAO();
    public abstract OrgConfigDAO getOrgConfigDAO();

    public abstract MenuItemDAO getMenuItemDAO();

    public abstract FormDAO getFormDAO();

    public abstract FormItemDAO getFormItemDAO();

    public abstract CatalogDAO getCatalogDAO();

    public abstract CatalogItemDAO getCatalogItemDAO();
    public abstract CatalogMemberDAO getCatalogMemberDAO();
    public abstract CatalogImageDAO getCatalogImageDAO();
    public abstract CatalogNewsDAO getCatalogNewsDAO();

    public abstract FavoriteDAO getFavoritesDAO();

    public abstract TicketDAO getTicketDAO();

}