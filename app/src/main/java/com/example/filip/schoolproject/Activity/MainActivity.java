package com.example.filip.schoolproject.Activity;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.example.filip.schoolproject.RecyclerView.MovieAdapter;
import com.example.filip.schoolproject.RecyclerView.MovieHolder;
import com.example.filip.schoolproject.RecyclerView.MovieModel;
import com.example.filip.schoolproject.R;
import com.example.filip.schoolproject.RecyclerView.Tools;
import java.lang.ref.WeakReference;
import java.util.List;


public class MainActivity extends AppCompatActivity  {


    private final String CHANNEL_ID="personal_notifications";
    private final int NOTIFICATION_ID=1;
    private BroadcastReceiver broadcastReceiver;
    private final int PERMISSION_REQUEST_SMS=1000;
    private IntentFilter intentFilter;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter<MovieHolder> adapter;
    private RecyclerView.LayoutManager layoutManager;

//TODO Aplikácia musí využívať tieto povinné prvky:
//Broadcast receiver – niečo čo pošle echo v aplikácií v prípade, že nastane udalosť na kt. neexistuje listener skontrolovat ci mam pripojenie na wifi alebo k datam
//Vlastnú službu (service) – niečo čo beží na pozadí // stahovanie dat upravit na kazdych 15 sekund
//Aspoň 1 senzor (GPS sa neráta) // LIGHT nech pomaly blika vtedy ked (zapne gps) stahuju data
//Notifikáciu (pozn. Toast nie je notifikácia) // notifikacia na pocet stiahnuti
//Internú databázu (SQLite) //dpmp cestovny poriadok
//osetrit vynimku na gps v pripade ze nejde gps

    //TODO:pozriet si handleri, intenty a cez nich pridavat veci
    //TODO:vsetky autobusy zobrazit do mapy-- mam jeden


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerBusses();
        layoutChange();
        showNotification();

    }

    private void recyclerBusses(){
        recyclerView = findViewById(R.id.rv_movie_view);
        layoutManager = new LinearLayoutManager(this);
        List<MovieModel> data = Tools.getMovieData();
        adapter = new MovieAdapter(data,new WeakReference<Context>(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void showNotification(){
            String id= "main_channel";

            NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            CharSequence name="Channel Name";
            String description= "Channel Description";

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

    public void layoutChange(){
        //zmena layoutov
        (findViewById(R.id.imageButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tu dam to co sa ma stat ked sa klikne na tlacidlo
                //Intent mi povie o tom co chcem urobit ... aktualne prejst z konextu aktivity MainActivity + chcem vytvorit novy kontext z aktivity FlightDetails
                Intent i = new Intent(MainActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        (findViewById(R.id.imageButton2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tu dam to co sa ma stat ked sa klikne na tlacidlo
                //Intent mi povie o tom co chcem urobit ... aktualne prejst z konextu aktivity MainActivity + chcem vytvorit novy kontext z aktivity FlightDetails
                Intent i = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(i);
            }
        });
        (findViewById(R.id.imageButton3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tu dam to co sa ma stat ked sa klikne na tlacidlo
                //Intent mi povie o tom co chcem urobit ... aktualne prejst z konextu aktivity MainActivity + chcem vytvorit novy kontext z aktivity FlightDetails
                Intent i = new Intent(MainActivity.this,ThirdActivity.class);
                startActivity(i);
            }
        });
    }
}



