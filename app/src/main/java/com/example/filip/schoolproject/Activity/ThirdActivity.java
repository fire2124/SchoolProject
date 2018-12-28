package com.example.filip.schoolproject.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.filip.schoolproject.GpsTracker;
import com.example.filip.schoolproject.R;
import com.example.filip.schoolproject.RestApi.ApiMethods;
import com.example.filip.schoolproject.RestApi.ApiTools;
import com.example.filip.schoolproject.RestApi.Buss;
import com.google.gson.JsonObject;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ThirdActivity extends AppCompatActivity implements LocationListener {
    private TextView mFirstUserTV;
    private TextView mFirstDelay;
    private TextView mFirstDeparture;
    private TextView mFirstDirection;
    private TextView mFirstLat;
    private TextView mFirstLng;
    private List<Buss> zoznamBusov;
 //   Button btnLoc;
    public TextView locationText;
    public LocationManager locManager;
    public final int PERMISSION_LOCATION_ID = 1000;

    public double lat,lon;
    //public List<Double> lat,lon;
    public int direction;
    //public List<Integer> direction;
    public String number;
    //public List<String> number;

   // Switch sw;
    MapView map;
    private final int PERMISSION_WRITE_EXT_STORAGE_ID = 1000;
    private final int PERMISSION_FINE_LOC_ID = 1001;
    //double lat,lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_activity);
        mFirstUserTV = findViewById(R.id.tv_firstNumber);
        mFirstDelay= findViewById(R.id.tv_firstDelay);
        mFirstDeparture= findViewById(R.id.tv_firstDeparture);
        mFirstDirection= findViewById(R.id.tv_firstDirection);
        mFirstLat= findViewById(R.id.tv_firstLat);
        mFirstLng= findViewById(R.id.tv_firstLng);

        //locationText = findViewById(R.id.tv_location);
        layoutChange();
//        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) !=PackageManager.PERMISSION_GRANTED)
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_WRITE_EXT_STORAGE_ID);
//        else
//            //inicializacia mapy
//            initialMapSetup();

//        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_LOCATION_ID);
//            return;
//        }
//        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 5, this);
//
//       // btnLoc = findViewById(R.id.switch1);
//        ActivityCompat.requestPermissions(ThirdActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);
//        btnLoc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                GpsTracker gt = new GpsTracker(getApplicationContext());
//                Location l = gt.getLocation();
//                if( l == null){
//                    Toast.makeText(getApplicationContext(),"GPS unable to get Value",Toast.LENGTH_SHORT).show();
//                }else {
//                    double lat = l.getLatitude();
//                    double lon = l.getLongitude();
//                    Toast.makeText(getApplicationContext(),"GPS Lat = "+lat+"\n lon = "+lon,Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).
                baseUrl("https://mhdpresov.sk/").build();
        ApiMethods api = retrofit.create(ApiMethods.class);
        api.getGPSBusses().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                zoznamBusov = ApiTools.getBussesFromApi(response.body(),ThirdActivity.this);

                mFirstUserTV.setText(zoznamBusov.get(0).getNumber());
                mFirstDelay.setText(String.valueOf(zoznamBusov.get(0).getDelay()));
                mFirstDeparture.setText(zoznamBusov.get(0).getDeparture());
                mFirstDirection.setText(String.valueOf(zoznamBusov.get(0).getDirection()));
                mFirstLat.setText(zoznamBusov.get(0).getLat().toString());
                mFirstLng.setText(zoznamBusov.get(0).getLng().toString());

                lat= zoznamBusov.get(0).getLat();
                lon= zoznamBusov.get(0).getLng();
                direction=zoznamBusov.get(0).getDirection();
                number= zoznamBusov.get(0).getNumber();

                if(ActivityCompat.checkSelfPermission(ThirdActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) !=PackageManager.PERMISSION_GRANTED)
                    ActivityCompat.requestPermissions(ThirdActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_WRITE_EXT_STORAGE_ID);
                else
                    initialMapSetup();
                Log.v("","");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ThirdActivity.this, t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }


    public void layoutChange(){

        //zmena layoutov
        (findViewById(R.id.imageButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tu dam to co sa ma stat ked sa klikne na tlacidlo
                //Intent mi povie o tom co chcem urobit ... aktualne prejst z konextu aktivity MainActivity + chcem vytvorit novy kontext z aktivity FlightDetails
                Intent i = new Intent(ThirdActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        (findViewById(R.id.imageButton2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tu dam to co sa ma stat ked sa klikne na tlacidlo
                //Intent mi povie o tom co chcem urobit ... aktualne prejst z konextu aktivity MainActivity + chcem vytvorit novy kontext z aktivity FlightDetails
                Intent i = new Intent(ThirdActivity.this,SecondActivity.class);
                startActivity(i);
            }
        });
        (findViewById(R.id.imageButton3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tu dam to co sa ma stat ked sa klikne na tlacidlo
                //Intent mi povie o tom co chcem urobit ... aktualne prejst z konextu aktivity MainActivity + chcem vytvorit novy kontext z aktivity FlightDetails
                Intent i = new Intent(ThirdActivity.this,ThirdActivity.class);
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
        controller.setCenter(new GeoPoint(lat, lon));

       // List<Marker> startMarker= new ArrayList<Marker>((Collection<? extends Marker>) map);
        //Creating a list of markers
      //  for(int i = 0 ; i<zoznamBusov.size();i++) {
            Marker startMarker = new Marker(map);

            startMarker.setIcon(getResources().getDrawable(R.drawable.bus_black_25dp));
            startMarker.setRotation(direction);
            startMarker.setTitle(number);
            startMarker.setPosition(new GeoPoint(lat, lon));
            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            map.getOverlays().add(startMarker);

       // }

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
    @Override
    public void onLocationChanged(Location location) {
        locationText.setText("Lat:" + Double.toString(location.getLatitude()) + " Lon:" +  Double.toString(location.getLongitude()));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText(this,"GPS bolo zapnute",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(this,"GPS bolo vypnute",Toast.LENGTH_LONG).show();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_LOCATION_ID:{
                for(int i=0;i<permissions.length;i++){
                    if(permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)){
                        if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5,5,this);
                        }
                        else{
                            Toast.makeText(this,"Unable to get proper permission. You sure?",Toast.LENGTH_LONG).show();
                            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_LOCATION_ID);
                        }
                    }
                }
                break;
            }
        }
   }

}


