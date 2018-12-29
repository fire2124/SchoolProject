package com.example.filip.schoolproject.RoomDb;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.lang.ref.WeakReference;

public class DbTools {
    private static MovieDatabase _db;
    public DbTools(WeakReference<Context> refContext){
        getDbContext(refContext);
    }
    public static MovieDatabase getDbContext(WeakReference<Context> refContext){
        if(_db!=null)
            return _db;
        return Room.databaseBuilder(refContext.get(),MovieDatabase.class,"movie-db").build();
    }
}