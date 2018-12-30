package com.example.filip.schoolproject.Activity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.filip.schoolproject.R;

import static android.hardware.Sensor.TYPE_PROXIMITY;

public class ActivitySplash extends AppCompatActivity implements SensorEventListener{
    private SensorManager sensorManager;
    private Sensor sensor;
    private View root;
    private float maxValue;
    private static int SPLASH_TIME = 3000; //This is 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Code to start timer and take action after the timer ends
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do any action here. Now we are moving to next page
                Intent mySuperIntent = new Intent(ActivitySplash.this, MainActivity.class);
                startActivity(mySuperIntent);
                /* This 'finish()' is for exiting the app when back button pressed
                 *  from Home page which is ActivityHome
                 */
                finish();
            }
        }, SPLASH_TIME);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(TYPE_PROXIMITY);

        if(sensor==null){
            Toast.makeText(this,"The device has not light sensor",Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {

        float value = event.values[0];
        maxValue=sensor.getMaximumRange();
        //getSupportActionBar().setTitle("Proximity: "+ value + " lx");
        int newValue=(int) (255 * value / maxValue);

        //   root.setBackgroundColor(Color.rgb(newValue,newValue,newValue));

        if(newValue==0){
            finish();
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
