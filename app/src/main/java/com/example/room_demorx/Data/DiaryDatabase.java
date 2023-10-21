package com.example.room_demorx.Data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.room_demorx.Model.Diary;

@Database(entities = {Diary.class}, version = 1)

public abstract class DiaryDatabase extends RoomDatabase {
    public abstract DiaryDAO getdiaryDAO() ;
}
