package com.example.filip.schoolproject.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.filip.schoolproject.R;
import com.example.filip.schoolproject.RoomDb.DbTools;
import com.example.filip.schoolproject.RoomDb.Movie;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import static android.os.Environment.getExternalStorageDirectory;

public class ActivityOne extends AppCompatActivity {

    List<Movie> insertedMovies;
    private final int PERMISSION_ID_ACCESS_STORAGE = 1000;
    private final String fileName = "zastavky.txt";
    private final String extPath = "/dataFiles";
    private int cislo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        layoutChange();
        TextView txtInfo = findViewById(R.id.txtInfo);
        if(getIntent() != null)
        {
            String info = getIntent().getStringExtra("info");
            txtInfo.setText(info);
            cislo=getIntent().getIntExtra("position",0);


        }



        DbGetData taskLoadData = new DbGetData();

        Movie newMovie = new Movie("1","Nižná Šebastová\n","Šebastová\n");
        Movie newMovie2 = new Movie("2","Bajkalská\n","Budovateľská\n");
        Movie newMovie3 = new Movie("4","Sídlisko III\n","Pod Šalgovíkom\n");
        Movie newMovie4 = new Movie("5","Budovateľská\n","Bajkalská\n");
        Movie newMovie5 = new Movie("5D","Obrancov mieru\n","Dúbrava\n");
        Movie newMovie6 = new Movie("7","Širpo\n","Budovateľská\n");
        Movie newMovie7 = new Movie("8","Sídlisko III\n","Sibírska\n");
        Movie newMovie8 = new Movie("10","Hollého\n","Sibírska\n");
        Movie newMovie9 = new Movie("11","Na Rúrkach\n","Solivar\n");
        Movie newMovie10 = new Movie("12","Šidlovec\n","Sibírska\n");
        Movie newMovie11 = new Movie("13","Veľká pošta\n","Severná\n");
        Movie newMovie12 = new Movie("14","Kanaš - Stráže\n","Záborské\n");
        Movie newMovie13 = new Movie("15","Za Kalváriou\n","Nemocnica\n");
        Movie newMovie14 = new Movie("17","Sídlisko III\n","Širpo\n");
        Movie newMovie15 = new Movie("18","Bzenov\n","Divadlo J. Záborského\n");
        Movie newMovie16 = new Movie("19","Hollého\n","Solivar\n");
        Movie newMovie17 = new Movie("20","Divadlo J. Záborského\n","Veselá\n");
        Movie newMovie18 = new Movie("21","Fintice\n","Malý Šariš\n");
        Movie newMovie19 = new Movie("22","Šidlovec\n","Teriakovce\n");
        Movie newMovie20 = new Movie("24","Obrancov mieru\n","Haniska\n");
        Movie newMovie21 = new Movie("27","Hviezdna\n","Terchovská\n");
        Movie newMovie22 = new Movie("28","Ľubotice\n","Delňa\n");
        Movie newMovie23 = new Movie("29","Sídlisko III\n","Nemocnica\n");
        Movie newMovie24 = new Movie("30","Železničná stanica\n","Budovateľská\n");
        Movie newMovie25 = new Movie("32","Sibírska\n","Trojica\n");
        Movie newMovie26 = new Movie("32A","Sibírska\n","Okružná\n");
        Movie newMovie27 = new Movie("33","Nižná Šebastová\n","Šebastová\n");
        Movie newMovie28 = new Movie("34","Širpo\n","Delňa\n");
        Movie newMovie29 = new Movie("35","Delňa\n","Širpo\n");
        Movie newMovie30 = new Movie("36","Trojica\n","Trojica\n");
        Movie newMovie31 = new Movie("37","Železničná stanica\n","Nižná Šebastová\n");
        Movie newMovie32 = new Movie("38","Nižná Šebastová\n","Šebastová\n");
        Movie newMovie33 = new Movie("39","Sídlisko III\n","Lomnická\n");
        Movie newMovie34 = new Movie("41","Surdok\n","Divadlo J. Záborského\n");
        Movie newMovie35 = new Movie("42","Borkút\n","Kpt. Nálepku\n");
        Movie newMovie36 = new Movie("43","Železničná stanica\n","Kpt. Nálepku\n");
        Movie newMovie37 = new Movie("44","Veľká pošta\n","Solivar\n");
        Movie newMovie38 = new Movie("45","Veľký Šariš\n","Delňa\n");
        Movie newMovie39 = new Movie("46","Veľká pošta\n","Ruská Nová Ves\n");
        Movie newMovie40 = new Movie("N1","Sídlisko III\n","Solivar\n");
        Movie newMovie41 = new Movie("N2","ídlisko III\n","Pod Šalgovíkom\n");
        Movie newMovie42 = new Movie("N3","Nižná Šebastová\n","Sibírska\n");





        //taskLoadData.execute(newMovie,newMovie2);

        taskLoadData.execute(newMovie,newMovie2,newMovie3,newMovie4,newMovie5,newMovie6,newMovie7,newMovie8,newMovie9,newMovie10,
                newMovie11,newMovie12,newMovie13,newMovie14,newMovie15,newMovie16,newMovie17,
                newMovie18,newMovie19,newMovie20,newMovie21,newMovie22,newMovie23,newMovie24,newMovie25,newMovie26,
                newMovie27 ,newMovie28 ,newMovie29 ,newMovie30,newMovie31 ,newMovie32,newMovie33,newMovie34 ,
                newMovie35 ,newMovie36,newMovie37 ,newMovie38,newMovie39,newMovie40, newMovie41, newMovie42);
        //END: ROOM DB Persistence preparation
    }

    //START: ROOM DB Persistence TASKS in NEW THREAD
    class DbGetData extends AsyncTask<Movie, Integer, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(Movie... movies) {
            List<Movie> data =  DbTools.getDbContext(new WeakReference<Context>(ActivityOne.this)).movieDao().getAll();
            if(data.size()==0) {
                DbTools.getDbContext(new WeakReference<Context>(ActivityOne.this)).movieDao().insertMovies(movies);
                return DbTools.getDbContext(new WeakReference<Context>(ActivityOne.this)).movieDao().getAll();
            }
            else
                return DbTools.getDbContext(new WeakReference<Context>(ActivityOne.this)).movieDao().getAll();
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);
            insertedMovies = movies;




            Toast.makeText(ActivityOne.this,insertedMovies.get(cislo).getDirector(),Toast.LENGTH_LONG).show();

            ((TextView)findViewById(R.id.tv_movie)).setText(insertedMovies.get(cislo).getTitle());
            saveFirstMovieNameToSharedPreferences("dataKey",insertedMovies.get(cislo).getTitle());

            ((TextView)findViewById(R.id.tv_movie1)).setText(insertedMovies.get(cislo).getRelease_year());
            saveFirstMovieNameToSharedPreferences("dataKey2",insertedMovies.get(cislo).getRelease_year());

            ((TextView)findViewById(R.id.tv_movie2)).setText(insertedMovies.get(cislo).getDirector());
            saveFirstMovieNameToSharedPreferences("dataKey3",insertedMovies.get(cislo).getDirector());

//            ((TextView)findViewById(R.id.tv_movie1)).setText(insertedMovies.get(0).getKonecnazastavka1());
//            saveFirstMovieNameToSharedPreferences("dataKey",insertedMovies.get(0).getKonecnazastavka1());
//
//            ((TextView)findViewById(R.id.tv_movie2)).setText(insertedMovies.get(0).getKonecnazastavka2());
//            saveFirstMovieNameToSharedPreferences("dataKey",insertedMovies.get(0).getKonecnazastavka2());

            if(ActivityCompat.checkSelfPermission(ActivityOne.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                writeDataToFileInternal(insertedMovies,fileName);
                writeDataToFileExternal(insertedMovies,fileName, extPath);
            }
            else{
                ActivityCompat.requestPermissions(ActivityOne.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_ID_ACCESS_STORAGE);
            }

        }
    }
    //END: ROOM DB Persistence TASKS in NEW THREAD


    //START: FILE Persistence preparation and tasks - External storage
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
    //END: FILE Persistence preparation and tasks - External Storage

    //START: FILE Persistence preparation and tasks - internal storage
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

    //What happends when the user grants or denies the requested permission to write to external storage
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
                            // Toast.makeText(this,"Unable to get permissions, have to quit.",Toast.LENGTH_LONG).show();
                            // finish();
                        }
                    }
                }
            }
        }
    }
    //END: FILE Persistence preparation and tasks

    //START: SharedPreferences persistence
    private void saveFirstMovieNameToSharedPreferences(String key, String movieTitle){
        SharedPreferences sp = getSharedPreferences("sk.tuke.smartlab.lab3_datapersistence",Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString(key,movieTitle);
        ed.apply();
    }

    public void layoutChange(){
        //zmena layoutov
        (findViewById(R.id.imageButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tu dam to co sa ma stat ked sa klikne na tlacidlo
                //Intent mi povie o tom co chcem urobit ... aktualne prejst z konextu aktivity MainActivity + chcem vytvorit novy kontext z aktivity FlightDetails
                Intent i = new Intent(ActivityOne.this,MainActivity.class);
                startActivity(i);
            }
        });

        (findViewById(R.id.imageButton2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tu dam to co sa ma stat ked sa klikne na tlacidlo
                //Intent mi povie o tom co chcem urobit ... aktualne prejst z konextu aktivity MainActivity + chcem vytvorit novy kontext z aktivity FlightDetails
                Intent i = new Intent(ActivityOne.this,SecondActivity.class);
                startActivity(i);
            }
        });
        (findViewById(R.id.imageButton3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tu dam to co sa ma stat ked sa klikne na tlacidlo
                //Intent mi povie o tom co chcem urobit ... aktualne prejst z konextu aktivity MainActivity + chcem vytvorit novy kontext z aktivity FlightDetails
                Intent i = new Intent(ActivityOne.this,ThirdActivity.class);
                startActivity(i);
            }
        });
    }
}
