package com.example.room_demorx.Data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.room_demorx.Model.Diary;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;

@Dao
public interface DiaryDAO {

    @Insert
    Completable insert(Diary diary);

    @Insert
    Completable insertAll(List<Diary> diaries);

    @Query("SELECT * FROM Diary")
    Single<List<Diary>> getAll();




    @Query("DELETE FROM Diary WHERE Title= :titlenum1 ")
    Completable  delete(String titlenum1);


    @Delete
    Completable  delete(Diary diary);
    @Query("DELETE FROM Diary WHERE id= :id ")
    Completable  delete(int id);

@Query("UPDATE Diary SET Body=:body WHERE id= :id")
    Completable updateDiary(Integer id,String body);
}

