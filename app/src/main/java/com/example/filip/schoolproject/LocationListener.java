package com.example.filip.schoolproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class LocationListener extends AppCompatActivity implements android.location.LocationListener {
    public TextView locationText;
    LocationManager locManager;
    public final int PERMISSION_LOCATION_ID = 1000;



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
