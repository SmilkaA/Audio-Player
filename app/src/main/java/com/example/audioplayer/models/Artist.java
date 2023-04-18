package com.example.audioplayer.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "artists_table")
public class Artist {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "artist_id")
    private int id;

    @ColumnInfo(name = "album_name")
    private String name;

    @Ignore
    private List<Song> songsPerArtist;

    public Artist() {
        this.songsPerArtist = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Song> getSongsPerArtist() {
        return songsPerArtist;
    }

    public void setSongsPerArtist(List<Song> songsPerArtist) {
        this.songsPerArtist = songsPerArtist;
    }

    public void addSongsPerArtist(Song song) {
        this.songsPerArtist.add(song);
    }
}
