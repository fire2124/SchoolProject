package com.example.filip.schoolproject.Activity;


import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.filip.schoolproject.RecyclerView.GridViewAdapter;
import com.example.filip.schoolproject.R;
import com.example.filip.schoolproject.RoomDb.Movie;
import com.example.filip.schoolproject.WIFIdirectBroadcastReceiver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.os.Environment.getExternalStorageDirectory;


public class MainActivity extends AppCompatActivity  {


    private WifiP2pManager mManager;
    //this class provides the API for managing wifi peer to peer connetivity
    private WifiP2pManager.Channel mChannel;
    // a channel that connects the app to the wifi p2p framework
    // most p2p operations require a channel as an argument
    private BroadcastReceiver mReceiver;
    private IntentFilter mIntentFilter;

    private final String CHANNEL_ID="personal_notifications";
    private final int NOTIFICATION_ID=1;

    List<Movie> insertedMovies;
    private final int PERMISSION_ID_ACCESS_STORAGE = 1000;
    private final String fileName = "zastavky.txt";
    private final String extPath = "/dataFiles";
    private int cislo;

    String text="";

//TODO Aplikácia musí využívať tieto povinné prvky:
    //todo upravit
//Vlastnú službu (service) – niečo čo beží na pozadí //TODO stahovanie dat upravit na kazdych 15 sekund
//Notifikáciu (pozn. Toast nie je notifikácia) //TODO notifikacia na pocet stiahnuti len opravit


    //TODO:pozriet si handleri, intenty a cez nich pridavat veci
    //TODO:vsetky autobusy zobrazit do mapy-- mam jeden


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutChange();
        showNotification();
        gridView();
        wifiBroadcastReceiver();


        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            switch(requestCode){
                case PERMISSION_ID_ACCESS_STORAGE:{
                    for(int i = 0;i < permissions.length;i++){
                        if(permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                            if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                                Toast.makeText(this,"Writing to file ...",Toast.LENGTH_LONG).show();
                                writeDataToFileInternal(insertedMovies,fileName);
                                writeDataToFileExternal(insertedMovies,fileName,extPath);
                            }
                            else{
                                //In real cases, this string should not be hardcoded and would be places inside the values/strings.xml file
                                Toast.makeText(this,"Unable to get permissions, have to quit.",Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }
                    }
                }
            }
        }

        private void writeDataToFileExternal(List<Movie> data, String fileName, String relPath){
            if(checkExtStorage()[1]) {
                File storageDir = getExternalStorageDirectory();

                File dir = new File(storageDir.getAbsolutePath() + relPath);
                if (!dir.exists())
                    dir.mkdirs();

                File file = new File(dir, fileName);

                try {
                    FileOutputStream osWriter = new FileOutputStream(file);

                    osWriter.write("--- Tu sa zacina zapis ---".getBytes());
                    osWriter.write(("\n").getBytes());
                    for(Movie movie:data){
                        osWriter.write(Long.toString(movie.getId()).getBytes());
                        osWriter.write(("\t" + movie.getTitle()).getBytes());
//                    osWriter.write(("\t" + movie.getKonecnazastavka1()).getBytes());
//                    osWriter.write(("\t" + movie.getKonecnazastavka2()).getBytes());

                        osWriter.write(("\t " + movie.getRelease_year()).getBytes());
                        osWriter.write(("\t" + movie.getDirector()).getBytes());
                        osWriter.write(("\n").getBytes());
                    }
                    osWriter.write("--- Tu sa konci zapis ---".getBytes());
                    osWriter.write("\n".getBytes());
                    osWriter.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                }
            }
        }

        private boolean[] checkExtStorage(){
            boolean extStorageMounted = false;
            boolean extStorageCanWrite = false;
            String state = Environment.getExternalStorageState();

            if (Environment.MEDIA_MOUNTED.equals(state)) {
                // Read and write
                extStorageMounted = extStorageCanWrite = true;
            } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
                // Only read
                extStorageMounted = true;
                extStorageCanWrite = false;
            } else {
                // No read, no write
                extStorageMounted = extStorageCanWrite = false;
            }
            return new boolean[]{extStorageMounted,extStorageCanWrite};
        }


        private void writeDataToFileInternal(List<Movie> data, String fileName){
            FileOutputStream outputStream;
            try {
                outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                outputStream.write("--- Tu sa zacina zapis ---".getBytes());
                outputStream.write(("\n").getBytes());
                for(Movie movie:data){
                    outputStream.write(Long.toString(movie.getId()).getBytes());
                    outputStream.write(("\t" + movie.getTitle()).getBytes());
//                outputStream.write(("\t" + movie.getKonecnazastavka1()).getBytes());
//                outputStream.write(("\t" + movie.getKonecnazastavka2()).getBytes());
                    outputStream.write(("\t " + movie.getRelease_year()).getBytes());
                    outputStream.write(("\t" + movie.getDirector()).getBytes());
                    outputStream.write(("\n").getBytes());
                }
                outputStream.write("--- Tu sa konci zapis ---".getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//    private void recyclerBusses(){
//        recyclerView = findViewById(R.id.rv_movie_view);
//        layoutManager = new LinearLayoutManager(this);
//        List<MovieModel> data = Tools.getMovieData();
//        adapter = new MovieAdapter(data,new WeakReference<Context>(this));
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);
//    }


    public void broadcastIntent(View view){
        Intent intent = new Intent();
        intent.setAction("com.tutorialspoint.CUSTOM_INTENT"); sendBroadcast(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver,mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
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

    public void gridView() {
        // Get the widgets reference from XML layout
        GridView gv =  findViewById(R.id.gv);
        final TextView tv= findViewById(R.id.txtInfo);
        //  final TextView tv =  findViewById(R.id.tv);

        String[] plants = new String[]{
                "1","2","4","5","5D","7","8","10",
                "11","12","13","14","15","17","18","19",
                "20","21","22","24","27","28","29","30",
                "32","32A","33","34","35","36","37","38","39",
                "41","42","43","44","45","46",
                "N1","N2","N3"
        };

        List<String> plantsList = new ArrayList<>(Arrays.asList(plants));
        gv.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,plantsList));
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the GridView selected/clicked item text
                String selectedItem = parent.getItemAtPosition(position).toString();

                // Display the selected/clicked item text and position on TextView
                tv.setText("GridView item clicked : " +selectedItem
                        + "\nAt index position : " + position);

                Intent intent = new Intent(MainActivity.this,ActivityOne.class);
                intent.putExtra("info","This is activity from card item index  ");
                startActivity(intent);

                Intent intent1= new Intent(MainActivity.this,ActivityOne.class);

                intent1.putExtra("position",position);
                startActivity(intent1);
            }
        });

    }

    private void wifiBroadcastReceiver(){
        mManager=(WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mReceiver= new WIFIdirectBroadcastReceiver(mManager,mChannel,this);
        mIntentFilter= new IntentFilter();
        mChannel=mManager.initialize(this,getMainLooper(),null);

        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }


}



