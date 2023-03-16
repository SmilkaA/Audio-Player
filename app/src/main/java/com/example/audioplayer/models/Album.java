package com.example.audioplayer.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "albums_table")
public class Album {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "album_id")
    private int id;

    @ColumnInfo(name = "album_name")
    private String name;

    @ColumnInfo(name = "artistId")
    private int artistId;

    private List<Song> songsInAlbum;

    public Album(@NonNull int id, String name, int artistId) {
        this.id = id;
        this.name = name;
        this.artistId = artistId;
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

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public List<Song> getSongsInAlbum() {
        return songsInAlbum;
    }

    public void setSongsInAlbum(List<Song> songsInAlbum) {
        this.songsInAlbum = songsInAlbum;
    }
}
