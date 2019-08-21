package ru.gastroafisha.MyApps.Model;

/**
 * Created by alex on 23.12.2017.
 */

public class MapPointModel {

    private Double lat;
    private Double lon;
    private Double zoom;
    private String text;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getZoom() {
        return zoom;
    }

    public void setZoom(Double zoom) {
        this.zoom = zoom;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
