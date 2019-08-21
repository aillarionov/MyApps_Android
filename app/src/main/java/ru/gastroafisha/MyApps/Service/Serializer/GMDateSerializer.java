package ru.gastroafisha.MyApps.Service.Serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ru.gastroafisha.MyApps.Utils.Classes.GMDate;

public class GMDateSerializer implements JsonDeserializer {

    public static final SimpleDateFormat sServerDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    @Override
    public GMDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json != null) {
            final String jsonString = json.getAsString();
            try {
                return new GMDate(sServerDateFormat.parse(jsonString));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}