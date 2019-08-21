package ru.gastroafisha.MyApps.Storage.DTO;

import java.util.Map;

import ru.gastroafisha.MyApps.Model.MapPointModel;

/**
 * Created by alex on 31.12.2017.
 */

public class MapPointDTO {

    public static MapPointModel MapToModel(Map<String, String> map) {
        MapPointModel _model = new MapPointModel();

        _model.setLat(Double.parseDouble(map.get("lat")));
        _model.setLon(Double.parseDouble(map.get("lon")));
        _model.setZoom(Double.parseDouble(map.get("zoom")));
        _model.setText(map.get("text"));

        return _model;
    }
}
