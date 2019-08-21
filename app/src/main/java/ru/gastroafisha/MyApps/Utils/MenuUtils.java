package ru.gastroafisha.MyApps.Utils;

import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;

import java.lang.reflect.Field;
import java.util.List;

import ru.gastroafisha.MyApps.Model.Menu.MenuItemModel;
import ru.gastroafisha.MyApps.Model.Org.OrgModel;
import ru.gastroafisha.MyApps.R;

/**
 * Created by alex on 29.12.2017.
 */

public class MenuUtils {

    public static void FillMenu(DrawerLayout drawer, Context context, Integer orgId, Menu menu, List<MenuItemModel> menuItemModels) {
        menu.clear();

        Integer itemId = 0;

        // Common
//        itemId++;
//        menu.add(2, itemId, itemId, R.string.title_activity_choose_group)
//                .setIcon(R.drawable.ic_menu_exhibition)
//                .setOnMenuItemClickListener(item -> {
//                    ActivityOpener opener = new ActivityOpener(context, orgId, null);
//                    opener.OpenChangeOrg();
//                    drawer.closeDrawer(GravityCompat.START);
//                    return true;
//                });

//        if (orgId > 0) {
//            itemId++;
//            menu.add(2, itemId, itemId, R.string.title_activity_about)
//                    .setIcon(R.drawable.ic_menu_exhibitions)
//                    .setOnMenuItemClickListener(item -> {
//                        ActivityOpener opener = new ActivityOpener(context, orgId, null);
//                        opener.OpenMain();
//                        drawer.closeDrawer(GravityCompat.START);
//                        return true;
//                    });
//        }

        //  My Apps
        itemId++;
        menu.add(4, itemId, itemId, R.string.title_activity_settings)
                .setIcon(R.drawable.ic_menu_setting)
                .setOnMenuItemClickListener(item -> {
                    ActivityOpener opener = new ActivityOpener(context, orgId, null);
                    opener.OpenSettingsList();
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                });

        // Items
        Integer maxId = 0;
        for (MenuItemModel menuItem : menuItemModels) {
            menu.add(3, menuItem.getOrder() + itemId, menuItem.getOrder() + itemId, menuItem.getName())
                    .setIcon(getResId("ic_menu_" + menuItem.getIcon(), R.drawable.class))
                    .setOnMenuItemClickListener(item -> {
                        ActivityOpener opener = new ActivityOpener(context, orgId, menuItem);
                        opener.OpenMenu();
                        drawer.closeDrawer(GravityCompat.START);
                        return true;
                    });
            if (menuItem.getOrder() > maxId) {
                maxId = menuItem.getOrder();
            }
        }
        itemId += maxId;


    }

    private static int getResId(String variableName, Class<?> с) {

        Field field = null;
        int resId = 0;
        try {
            field = с.getField(variableName);
            try {
                resId = field.getInt(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resId;

    }
}
