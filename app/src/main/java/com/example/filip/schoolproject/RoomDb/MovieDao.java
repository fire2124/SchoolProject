package com.example.filip.schoolproject.RoomDb;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM Movie")
    List<Movie> getAll();

    @Query("SELECT * FROM Movie WHERE Id LIKE :Id")
    Movie getById(int Id);

    @Insert
    void insertMovies(Movie... movies);

    @Delete
    void deleteMovie(Movie movie);
}