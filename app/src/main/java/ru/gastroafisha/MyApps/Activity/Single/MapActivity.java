package ru.gastroafisha.MyApps.Activity.Single;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

import ru.gastroafisha.MyApps.Activity.CommonActivity;
import ru.gastroafisha.MyApps.MainApplication;
import ru.gastroafisha.MyApps.Model.MapPointModel;
import ru.gastroafisha.MyApps.R;
import ru.gastroafisha.MyApps.Storage.DTO.MapPointDTO;
import ru.gastroafisha.MyApps.Utils.MapUtils;

public class MapActivity extends CommonActivity {

    private GoogleMap googleMap;
    private MapPointModel mapPoint;

    @Override
    protected Integer getLayoutId() {
        return R.layout.content_map;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mapPoint = MapPointDTO.MapToModel((Map)getIntent().getSerializableExtra("params"));


        MapView mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        Context context = this;

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap _googleMap) {
                googleMap = _googleMap;

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    googleMap.setMyLocationEnabled(true);
                }

                UpdateMap();
                mapView.onResume();
            }
        });

    }

    protected void UpdateMap() {
        if (mapPoint != null && googleMap != null) {
            LatLng coordinates = new LatLng(mapPoint.getLat(), mapPoint.getLon());
            googleMap.addMarker(new MarkerOptions().position(coordinates).title(mapPoint.getText()));

            DisplayMetrics metrics = getResources().getDisplayMetrics();
            MapView mapView = (MapView) findViewById(R.id.map);
            float mapWidth = MainApplication.getInstance().getScreenSize().x / metrics.density;

            double zoom = MapUtils.getZoomForMetersWide(mapPoint.getZoom().floatValue(), mapWidth, mapPoint.getLat());

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, (float)zoom));
        }
    }
}
