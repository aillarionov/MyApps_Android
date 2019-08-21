package ru.gastroafisha.MyApps.Storage;

import android.arch.persistence.room.TypeConverter;
import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import ru.gastroafisha.MyApps.Model.Catalog.CatalogType;
import ru.gastroafisha.MyApps.Model.Form.FormItemType;
import ru.gastroafisha.MyApps.Model.ImageModel;
import ru.gastroafisha.MyApps.Model.Menu.MenuItemCls;
import ru.gastroafisha.MyApps.Model.Menu.MenuItemType;
import ru.gastroafisha.MyApps.Model.Org.CityModel;
import ru.gastroafisha.MyApps.Model.Org.TicketType;
import ru.gastroafisha.MyApps.Model.StringMap;

/**
 * Created by alex on 24.12.2017.
 */

public class Converters {

    private static String firstDelimiter = String.valueOf((char)7); // beep char
    private static String secondDelimiter = "\n";

    @TypeConverter
    public static String fromImageModel(ImageModel model) {

        if (model == null)
            return null;

        return model.getUrl() + firstDelimiter + model.getWidth() + firstDelimiter + model.getHeight();
    }

    @TypeConverter
    public static ImageModel toImageModel(String data) {

        if (data == null)
            return null;

        String[] d = data.split(firstDelimiter);

        ImageModel im = new ImageModel();

        im.setUrl(d[0]);

        if (d.length >= 3) {
            im.setWidth(d[1].equals("null") ? null : Integer.parseInt(d[1]));
            im.setHeight(d[2].equals("null") ? null : Integer.parseInt(d[2]));
        }

        return im;
    }

    @TypeConverter
    public static String fromImageModelList(List<ImageModel> data) {

        if (data == null)
            return null;

        String result = "";

        for (ImageModel imageModel : data) {
            result += fromImageModel(imageModel)+ secondDelimiter;
        }

        return !result.equals("") ? result : null;
    }

    @TypeConverter
    public static List<ImageModel> toImageModelList(String data) {
        List<ImageModel> listImageModels = new ArrayList<>();

        if (data == null)
            return listImageModels;

        if (!data.equals("")) {
            for (String d : data.split(secondDelimiter)) {
                listImageModels.add(toImageModel(d));
            }
        }

        return listImageModels;
    }

    @TypeConverter
    public static Integer fromTicketType(TicketType data) {

        if (data == null)
            return null;

        return data.getCode();
    }

    @TypeConverter
    public static TicketType toTicketType(Integer data) {

        if (data == null)
            return null;

        return TicketType.getByCode(data);
    }


    @TypeConverter
    public static Integer fromMenuItemType(MenuItemType data) {

        if (data == null)
            return null;

        return data.getCode();
    }

    @TypeConverter
    public static MenuItemType toMenuItemType(Integer data) {

        if (data == null)
            return null;

        return MenuItemType.getByCode(data);
    }

    @TypeConverter
    public static Integer fromMenuItemCls(MenuItemCls data) {

        if (data == null)
            return null;

        return data.getCode();
    }

    @TypeConverter
    public static MenuItemCls toMenuItemCls(Integer data) {

        if (data == null)
            return null;

        return MenuItemCls.getByCode(data);
    }

    @TypeConverter
    public static Integer fromFormItemType(FormItemType data) {

        if (data == null)
            return null;

        return data.getCode();
    }

    @TypeConverter
    public static FormItemType toFormItemType(Integer data) {

        if (data == null)
            return null;

        return FormItemType.getByCode(data);
    }

    @TypeConverter
    public static Integer fromCatalogType(CatalogType data) {

        if (data == null)
            return null;

        return data.getCode();
    }

    @TypeConverter
    public static CatalogType toCatalogType(Integer data) {

        if (data == null)
            return null;

        return CatalogType.getByCode(data);
    }

    @TypeConverter
    public static byte[] fromStringMap(StringMap data) {

        if (data == null)
            return null;

        try {
            byte[] result;

            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(data);
            out.flush();

            result = byteOut.toByteArray();

            out.close();
            byteOut.close();


            return result;

        } catch (IOException e) {
            return null;
        }
    }

    @TypeConverter
    public static StringMap toStringMap(byte[] data) {

        if (data == null)
            return null;

        try {
            ByteArrayInputStream byteIn = new ByteArrayInputStream(data);
            ObjectInputStream in = new ObjectInputStream(byteIn);
            StringMap result = (StringMap) in.readObject();
            in.close();
            byteIn.close();

            return result;

        } catch (IOException e) {
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    @TypeConverter
    public static String fromStringList(List<String> list) {

        if (list == null)
            return null;

        return TextUtils.join(firstDelimiter, list);
    }

    @TypeConverter
    public static List<String> toStringList(String data) {

        if (data == null)
            return null;

        return Arrays.asList(TextUtils.split(data, firstDelimiter));
    }

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static byte[] fromCityModel(CityModel data) {

        if (data == null)
            return null;

        try {
            byte[] result;

            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(data);
            out.flush();

            result = byteOut.toByteArray();

            out.close();
            byteOut.close();


            return result;

        } catch (IOException e) {
            return null;
        }
    }

    @TypeConverter
    public static CityModel toCityModel(byte[] data) {

        if (data == null)
            return null;

        try {
            ByteArrayInputStream byteIn = new ByteArrayInputStream(data);
            ObjectInputStream in = new ObjectInputStream(byteIn);
            CityModel result = (CityModel) in.readObject();
            in.close();
            byteIn.close();

            return result;

        } catch (IOException e) {
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}