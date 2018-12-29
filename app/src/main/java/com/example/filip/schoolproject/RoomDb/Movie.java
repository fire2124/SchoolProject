package com.example.filip.schoolproject.RoomDb;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Movie {

    public Movie(){

    }
    public Movie(String title, String released, String director){
        setTitle(title);
        setRelease_year(released);
        setDirector(director);
    }

    @PrimaryKey(autoGenerate = true)

    private long Id;
    @ColumnInfo(name="title")
    private String Title;
    @ColumnInfo(name="release_year")
    private String Release_year;
    @ColumnInfo(name="director")
    private String Director;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getRelease_year() {
        return Release_year;
    }

    public void setRelease_year(String release_year) {
        Release_year = release_year;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }
    //Getters
//
//    public Movie(){
//
//    }
//
//    public Movie(String title,String konecnazastavka1,String konecnazastavka2){
//        setTitle(title);
//        setKonecnazastavka1(konecnazastavka1);
//        setKonecnazastavka2(konecnazastavka2);
//    }
//
//
//    @PrimaryKey(autoGenerate = true)
//
//    private long Id;
//    @ColumnInfo(name="title")
//    private String Title;
//    @ColumnInfo(name="konecna_zastavka_1")
//    private String Konecnazastavka1;
//    @ColumnInfo(name="konecna_zastavka_2")
//    private String Konecnazastavka2;
//
//
//
//
//
//    public long getId() {
//        return Id;
//    }
//    public void setId(long id) {
//        Id = id;
//    }
//
//    public String getTitle() {
//        return Title;
//    }
//    public void setTitle(String title) {
//        Title = title;
//    }
//
//
//    public String getKonecnazastavka1() {
//        return Konecnazastavka1;
//    }
//    public void setKonecnazastavka1(String konecnazastavka1) {
//        Konecnazastavka1 = konecnazastavka1;
//    }
//
//    public String getKonecnazastavka2() {
//        return Konecnazastavka2;
//    }
//    public void setKonecnazastavka2(String konecnazastavka2) {
//        Konecnazastavka2 = konecnazastavka2;
//    }
    //Getters
}
