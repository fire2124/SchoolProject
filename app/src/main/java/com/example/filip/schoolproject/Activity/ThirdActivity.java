package com.example.filip.schoolproject.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.filip.schoolproject.R;
import com.example.filip.schoolproject.RestApi.ApiMethods;
import com.example.filip.schoolproject.RestApi.ApiTools;
import com.example.filip.schoolproject.RestApi.Buss;
import com.google.gson.JsonObject;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.simplefastpoint.LabelledGeoPoint;
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay;
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions;
import org.osmdroid.views.overlay.simplefastpoint.SimplePointTheme;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ThirdActivity extends AppCompatActivity implements LocationListener {
//    private TextView mFirstUserTV;
//    private TextView mFirstDelay;
//    private TextView mFirstDeparture;
//    private TextView mFirstDirection;
//    private TextView mFirstLat;
//    private TextView mFirstLng;

    private List<Buss> zoznamBusov;


    public TextView locationText;
    public LocationManager locManager;
    public final int PERMISSION_LOCATION_ID = 1000;
    private final String CHANNEL_ID="personal_notifications";
    private final int NOTIFICATION_ID=1;
////   Button btnLoc;
//   public double lat,lon;
//   public Double lat[],lon[];
//   public int direction;
//   public Integer direction[];
//   public String number;
    public String[] number;

    private static int SPLASH_TIME = 15000;
    public int count;
   // Switch sw;
    Marker startMarker;
    IMapController controller;
    MapView map;
    int j;
    private final int PERMISSION_WRITE_EXT_STORAGE_ID = 1000;
    private final int PERMISSION_FINE_LOC_ID = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_activity);
//        mFirstUserTV = findViewById(R.id.tv_firstNumber);
//        mFirstDelay= findViewById(R.id.tv_firstDelay);
//        mFirstDeparture= findViewById(R.id.tv_firstDeparture);
//        mFirstDirection= findViewById(R.id.tv_firstDirection);
//        mFirstLat= findViewById(R.id.tv_firstLat);
//        mFirstLng= findViewById(R.id.tv_firstLng);

        layoutChange();
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) !=PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_WRITE_EXT_STORAGE_ID);
        else
            //inicializacia mapy
            initialMapSetup();

        lockManager();


       // btnLoc = findViewById(R.id.switch1);
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
        Thread thread = new Thread(){
            public void run(){
        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
//                Thread thread = new Thread(){
//                    public void run(){initialMapSetup(); initialMapSetup();
              //  initialMapSetup();
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).
                        baseUrl("https://mhdpresov.sk/").build();
                ApiMethods api = retrofit.create(ApiMethods.class);
                api.getGPSBusses().enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        zoznamBusov = ApiTools.getBussesFromApi(response.body(),ThirdActivity.this);

                        for(j=0;j<zoznamBusov.size();j++) {
                            if (startMarker != null) {
                                for(j=0;j<zoznamBusov.size();j++) {
                                    map.getOverlays().retainAll(Collections.singleton(startMarker));
                                }
                            }
                        }

                      //
//                        mFirstUserTV.setText(zoznamBusov.get(0).getNumber());
//                        mFirstDelay.setText(String.valueOf(zoznamBusov.get(0).getDelay()));
//                        mFirstDeparture.setText(zoznamBusov.get(0).getDeparture());
//                        mFirstDirection.setText(String.valueOf(zoznamBusov.get(0).getDirection()));
//                        mFirstLat.setText(zoznamBusov.get(0).getLat().toString());
//                        mFirstLng.setText(zoznamBusov.get(0).getLng().toString());

//                            number=zoznamBusov.get(0).getNumber();
//                            lat= zoznamBusov.get(0).getLat();
//                            lon= zoznamBusov.get(0).getLng();
//                            direction= zoznamBusov.get(0).getDirection();
                     //
                        for(j=0;j<zoznamBusov.size();j++) {
                            try{
                                controller.setCenter(new GeoPoint( zoznamBusov.get(j).getLat(), zoznamBusov.get(j).getLng()));//musi tu byt aby bolo viditelny pohyb vozidiel
                                startMarker = new Marker(map);
                                startMarker.setIcon(getResources().getDrawable(R.drawable.bus_black_24dp));
                                startMarker.setRotation(zoznamBusov.get(j).getDirection());
                                startMarker.setTitle("číslo autobusu: "+zoznamBusov.get(j).getNumber()+"\n"+
                                                        "meškanie : "+zoznamBusov.get(j).getDelay()+"\n"+
                                                        "príchod: "+zoznamBusov.get(j).getDeparture()+"\n"+
                                                        "lat: "+zoznamBusov.get(j).getLat().toString()+"\n"+
                                                        "lng: "+zoznamBusov.get(j).getLng().toString()+"\n"
                                                     );
                                startMarker.setPosition(new GeoPoint(zoznamBusov.get(j).getLat(), zoznamBusov.get(j).getLng()));
                                startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                                map.getOverlays().add(startMarker);

                           // number[j]=zoznamBusov.get(j).getNumber();
//                            lat[j]= zoznamBusov.get(j).getLat();
//                            lon[j]= zoznamBusov.get(j).getLng();
//                            direction[j]= zoznamBusov.get(j).getDirection();
                               // Toast.makeText(this,number[1].,Toast.LENGTH_LONG).show();
                             //   number[j]=zoznamBusov.get(j).getNumber();
                            }catch(NullPointerException e){System.out.println(e);}

                        }
                    //  }catch(Exception e){System.out.println(e);}
                      //  mFirstUserTV.setText(zoznamBusov.get(j).getNumber());
//                            controller.setCenter(new GeoPoint( lat[j],  lon[j]));
//                            map.getOverlays().remove(startMarker);
//                            startMarker = new Marker(map);
//                            startMarker.setIcon(getResources().getDrawable(R.drawable.bus_black_25dp));
//                            startMarker.setRotation(direction[j]);
//                            startMarker.setTitle(number[j]);
//                            startMarker.setPosition(new GeoPoint( lat[j],  lon[j]));
//                            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
//                            map.getOverlays().add(startMarker);

                                count++;
                                Intent intent = getIntent();
                                intent.putExtra("count",count);
                                Log.v("", "");
                                showNotification();

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(ThirdActivity.this, t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        },0,15000);
            }
        };

        thread.start();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                //Do any action here. Now we are moving to next page
////                Intent mySuperIntent = new Intent(ActivitySplash.this, MainActivity.class);
////                startActivity(mySuperIntent);
////                /* This 'finish()' is for exiting the app when back button pressed
////                 *  from Home page which is ActivityHome
////                 */
////                finish();
//                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).
//                        baseUrl("https://mhdpresov.sk/").build();
//                ApiMethods api = retrofit.create(ApiMethods.class);
//                api.getGPSBusses().enqueue(new Callback<JsonObject>() {
//                    @Override
//                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                        zoznamBusov = ApiTools.getBussesFromApi(response.body(),ThirdActivity.this);
//
//                        mFirstUserTV.setText(zoznamBusov.get(0).getNumber());
//                        mFirstDelay.setText(String.valueOf(zoznamBusov.get(0).getDelay()));
//                        mFirstDeparture.setText(zoznamBusov.get(0).getDeparture());
//                        mFirstDirection.setText(String.valueOf(zoznamBusov.get(0).getDirection()));
//                        mFirstLat.setText(zoznamBusov.get(0).getLat().toString());
//                        mFirstLng.setText(zoznamBusov.get(0).getLng().toString());
//
//                        lat= zoznamBusov.get(0).getLat();
//                        lon= zoznamBusov.get(0).getLng();
//                        direction=zoznamBusov.get(0).getDirection();
//                        number= zoznamBusov.get(0).getNumber();
//
//                        if(ActivityCompat.checkSelfPermission(ThirdActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) !=PackageManager.PERMISSION_GRANTED)
//                            ActivityCompat.requestPermissions(ThirdActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_WRITE_EXT_STORAGE_ID);
//                        else
//                            initialMapSetup();
//                        Log.v("","");
//                    }
//
//                    @Override
//                    public void onFailure(Call<JsonObject> call, Throwable t) {
//                        Toast.makeText(ThirdActivity.this, t.getMessage(),Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//        }, 15000);

//        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).
//                baseUrl("https://mhdpresov.sk/").build();
//        ApiMethods api = retrofit.create(ApiMethods.class);
//        api.getGPSBusses().enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                zoznamBusov = ApiTools.getBussesFromApi(response.body(),ThirdActivity.this);
//
//                mFirstUserTV.setText(zoznamBusov.get(0).getNumber());
//                mFirstDelay.setText(String.valueOf(zoznamBusov.get(0).getDelay()));
//                mFirstDeparture.setText(zoznamBusov.get(0).getDeparture());
//                mFirstDirection.setText(String.valueOf(zoznamBusov.get(0).getDirection()));
//                mFirstLat.setText(zoznamBusov.get(0).getLat().toString());
//                mFirstLng.setText(zoznamBusov.get(0).getLng().toString());
//
//                lat= zoznamBusov.get(0).getLat();
//                lon= zoznamBusov.get(0).getLng();
//                direction=zoznamBusov.get(0).getDirection();
//                number= zoznamBusov.get(0).getNumber();
//
//                if(ActivityCompat.checkSelfPermission(ThirdActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) !=PackageManager.PERMISSION_GRANTED)
//                    ActivityCompat.requestPermissions(ThirdActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_WRITE_EXT_STORAGE_ID);
//                else
//                    initialMapSetup();
//                Log.v("","");
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Toast.makeText(ThirdActivity.this, t.getMessage(),Toast.LENGTH_LONG).show();
//            }
//        });
    }

    private void lockManager() {
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_LOCATION_ID);
            return;
        }
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 5, this);
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
        controller = map.getController();
        controller.setZoom(14.0d);
        controller.setCenter(new GeoPoint(48.9970342,21.2404656));
        //49°00′00″S 21°14′00″V


       // List<Marker> startMarker= new ArrayList<Marker>((Collection<? extends Marker>) map);
        //Creating a list of markers
      //  for(int i = 0 ; i<zoznamBusov.size();i++) {
//            Marker startMarker = new Marker(map);
//            startMarker.setIcon(getResources().getDrawable(R.drawable.bus_black_25dp));
//            startMarker.setRotation(direction);
//            startMarker.setTitle(number);
//            startMarker.setPosition(new GeoPoint(lat, lon));
//            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
//            map.getOverlays().add(startMarker);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                Marker startMarker = new Marker(map);
//                startMarker.setIcon(getResources().getDrawable(R.drawable.bus_black_25dp));
//                startMarker.setRotation(direction);
//                startMarker.setTitle(number);
//                startMarker.setPosition(new GeoPoint(lat, lon));
//                startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
//                map.getOverlays().add(startMarker);
//
//
//            }
//        }, SPLASH_TIME);

//        new Timer().scheduleAtFixedRate(new TimerTask(){
//            @Override
//            public void run(){
//                Marker startMarker = new Marker(map);
//                startMarker.setIcon(getResources().getDrawable(R.drawable.bus_black_25dp));
//                startMarker.setRotation(direction);
//                startMarker.setTitle(number);
//                startMarker.setPosition(new GeoPoint(lat, lon));
//                startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
//                map.getOverlays().add(startMarker);
//            }
//        },0,15000);

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

    private void showNotification(){
        String id= "main_channel";

        Intent intent = getIntent();
        Integer count = intent.getIntExtra("count",0);

        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        CharSequence name="Pocet stiahnuti "+count;
        String description= "Channel Description " +count;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance= NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel= new NotificationChannel(id,name,importance);
            notificationChannel.setDescription(description);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.WHITE);
            notificationChannel.enableVibration(false);

            if(notificationManager != null){
                notificationManager.createNotificationChannel(notificationChannel);
            }
            NotificationCompat.Builder notificationBuilder= new NotificationCompat.Builder(this,id);
            notificationBuilder.setSmallIcon(R.drawable.ic_directions_bus_black_24dp);
            notificationBuilder.setContentTitle(name);
            notificationBuilder.setContentText(description);
            notificationBuilder.setLights(Color.WHITE,500,5000);
            notificationBuilder.setColor(Color.RED);
            notificationBuilder.setDefaults(Notification.DEFAULT_SOUND);
            NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(1000,notificationBuilder.build());

        }else{
            NotificationCompat.Builder builder=new NotificationCompat.Builder(this,CHANNEL_ID);
            builder.setSmallIcon(R.drawable.ic_directions_bus_black_24dp);
            builder.setStyle(new NotificationCompat.BigTextStyle().bigText("much longer text that cannot fin one line"))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentTitle(name)
                    .setContentText(description);
            NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
        }
    }
}


