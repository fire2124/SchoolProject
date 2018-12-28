package com.sk.tuke.smartlab.lab5_maps_osmdroid;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MainActivity extends AppCompatActivity {

    MapView map;
    private final int PERMISSION_WRITE_EXT_STORAGE_ID = 1000;
    private final int PERMISSION_FINE_LOC_ID = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) !=PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_WRITE_EXT_STORAGE_ID);
        else
            initialMapSetup();
    }

    private void initialMapSetup(){
        map = findViewById(R.id.map_osm);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        IMapController controller = map.getController();
        controller.setZoom(18.0d);
        controller.setCenter(new GeoPoint(48.730515, 21.245275));
        Marker startMarker = new Marker(map);
        startMarker.setTitle("This is the main building of TU Košice, Slovakia");
        startMarker.setPosition(new GeoPoint(48.730515, 21.245275));
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
