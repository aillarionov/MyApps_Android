package ru.gastroafisha.MyApps.Utils;

import ru.gastroafisha.MyApps.Activity.Form.SimpleFormActivity;
import ru.gastroafisha.MyApps.Activity.Item.Catalog.CatalogImageItemListActivity;
import ru.gastroafisha.MyApps.Activity.Item.Catalog.CatalogImageShowActivity;
import ru.gastroafisha.MyApps.Activity.Item.Catalog.CatalogItemListActivity;
import ru.gastroafisha.MyApps.Activity.Item.Catalog.CatalogItemShowActivity;
import ru.gastroafisha.MyApps.Activity.Item.Catalog.CatalogMemberItemListActivity;
import ru.gastroafisha.MyApps.Activity.Item.Catalog.CatalogMemberShowActivity;
import ru.gastroafisha.MyApps.Activity.Item.Catalog.CatalogNewsItemListActivity;
import ru.gastroafisha.MyApps.Activity.Item.Catalog.CatalogNewsShowActivity;
import ru.gastroafisha.MyApps.Activity.Item.Special.FavoritesCatalogActivityItem;
import ru.gastroafisha.MyApps.Activity.Item.Special.SearchCatalogActivityItem;
import ru.gastroafisha.MyApps.Activity.MainActivity;
import ru.gastroafisha.MyApps.Activity.Single.AboutActivity;
import ru.gastroafisha.MyApps.Activity.Single.MapActivity;
import ru.gastroafisha.MyApps.Activity.Single.PlanActivity;
import ru.gastroafisha.MyApps.Activity.Single.TicketActivity;
import ru.gastroafisha.MyApps.Activity.Single.UrlActivity;
import ru.gastroafisha.MyApps.Model.Catalog.CatalogType;
import ru.gastroafisha.MyApps.Model.Item.CatalogImageModel;
import ru.gastroafisha.MyApps.Model.Item.CatalogItemModel;
import ru.gastroafisha.MyApps.Model.Item.CatalogMemberModel;
import ru.gastroafisha.MyApps.Model.Item.CatalogNewsModel;
import ru.gastroafisha.MyApps.Model.Menu.MenuItemCls;

/**
 * Created by alex on 15.01.2018.
 */

public class ItemClassConverter {

    public static Class catalogTypeToActivityClass(MenuItemCls menuCls) throws Exception {
        switch (menuCls) {

            case About:
                return AboutActivity.class;

            case Map:
                return MapActivity.class;

            case Plan:
                return PlanActivity.class;

            case Search:
                return SearchCatalogActivityItem.class;

            case Favorites:
                return FavoritesCatalogActivityItem.class;

            case Ticket:
                return TicketActivity.class;

            case Url:
                return UrlActivity.class;

            default:
                throw new Exception("Unknown class [" + menuCls.toString() + "]");
        }
    }

    public static Class catalogTypeToActivityClass(CatalogType catalogType) throws Exception {
        switch (catalogType) {
            case Item:
                return CatalogItemListActivity.class;

            case Member:
                return CatalogMemberItemListActivity.class;

            case Image:
                return CatalogImageItemListActivity.class;

            case News:
                return CatalogNewsItemListActivity.class;

            default:
                throw new Exception("Unknown class [" + catalogType.toString() + "]");
        }
    }


    public static Class itemToShowClass(CatalogItemModel item) {
        if (item instanceof CatalogMemberModel) {

            return CatalogMemberShowActivity.class;
        } else if (item instanceof CatalogImageModel) {

            return CatalogImageShowActivity.class;
        } else if (item instanceof CatalogNewsModel) {

            return CatalogNewsShowActivity.class;
        } else {

            return CatalogItemShowActivity.class;
        }
    }

}
