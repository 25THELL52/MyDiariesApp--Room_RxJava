package com.example.room_demorx.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity

public class Diary {
    @PrimaryKey(autoGenerate = true)
    public Integer id;
@ColumnInfo(name="Title")
    public String title;
    @ColumnInfo(name="Date")

    public String date ;
    @ColumnInfo(name="Body")

    public String body;

    public Diary(String title, String date, String body) {
        this.title = title;
        this.date = date;
        this.body = body;
    }


}
