package com.example.filip.schoolproject.Activity;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.example.filip.schoolproject.GpsTracker;
import com.example.filip.schoolproject.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;


public class SecondActivity  extends AppCompatActivity {
    Switch sw;
    MapView map;
    private final int PERMISSION_WRITE_EXT_STORAGE_ID = 1000;
    private final int PERMISSION_FINE_LOC_ID = 1001;
    double lat,lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        sw=findViewById(R.id.switch1);
        layoutChange();

        //ziskavanie povoleni
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) !=PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_WRITE_EXT_STORAGE_ID);
        else
            //inicializacia mapy
            initialMapSetup();

        ActivityCompat.requestPermissions(SecondActivity.this, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, 123);

        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GpsTracker gt = new GpsTracker(getApplicationContext());
                Location   l = gt.getLocation();
              //      gt.onStatusChanged(turn);
                if( l == null){
                    Toast.makeText(getApplicationContext(),"GPS unable to get Value",Toast.LENGTH_SHORT).show();
                }else {
                     lat = l.getLatitude();
                     lon = l.getLongitude();
                    Toast.makeText(getApplicationContext(),"GPS Lat = "+lat+"\n lon = "+lon,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void switchBox(View v){
        if(sw.isChecked()){
            sw.setEnabled(true);
        }else
            sw.setEnabled(false);
    }

    public void layoutChange(){
        //zmena layoutov
        (findViewById(R.id.imageButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tu dam to co sa ma stat ked sa klikne na tlacidlo
                //Intent mi povie o tom co chcem urobit ... aktualne prejst z konextu aktivity MainActivity + chcem vytvorit novy kontext z aktivity FlightDetails
                Intent i = new Intent(SecondActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        (findViewById(R.id.imageButton2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tu dam to co sa ma stat ked sa klikne na tlacidlo
                //Intent mi povie o tom co chcem urobit ... aktualne prejst z konextu aktivity MainActivity + chcem vytvorit novy kontext z aktivity FlightDetails
                Intent i = new Intent(SecondActivity.this,SecondActivity.class);
                startActivity(i);
            }
        });
        (findViewById(R.id.imageButton3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tu dam to co sa ma stat ked sa klikne na tlacidlo
                //Intent mi povie o tom co chcem urobit ... aktualne prejst z konextu aktivity MainActivity + chcem vytvorit novy kontext z aktivity FlightDetails
                Intent i = new Intent(SecondActivity.this,ThirdActivity.class);
                startActivity(i);
            }
        });
    }

    private void initialMapSetup(){
        map = findViewById(R.id.map_osm);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        IMapController controller = map.getController();
        controller.setZoom(18.0d);
        GpsTracker gt = new GpsTracker(getApplicationContext());
        Location   l = gt.getLocation();
        lat = l.getLatitude();
        lon = l.getLongitude();
        controller.setCenter(new GeoPoint(lat, lon));
        Marker startMarker = new Marker(map);
        startMarker.setTitle("This is your position");
        startMarker.setPosition(new GeoPoint(lat, lon));
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(startMarker);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case PERMISSION_WRITE_EXT_STORAGE_ID:{
                for(int i=0;i<permissions.length;i++){
                    if(permissions[i] == Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        if(grantResults[i] == PackageManager.PERMISSION_GRANTED)
                            initialMapSetup();
                        else
                            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_WRITE_EXT_STORAGE_ID);
                }
                break;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(map!=null)
            map.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(map!=null)
            map.onResume();
    }
}


