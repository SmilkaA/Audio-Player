package com.example.audioplayer.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "artists_table")
public class Artist {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "artist_id")
    private int id;

    @ColumnInfo(name = "album_name")
    private String name;

    public Artist(@NonNull int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
