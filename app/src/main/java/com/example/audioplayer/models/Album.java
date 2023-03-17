package com.example.audioplayer.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "albums_table")
public class Album {
    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "album_id")
    private int id;

    @ColumnInfo(name = "album_name")
    private String albumName;

    @ColumnInfo(name = "artistId")
    private int artistId;

    @Ignore
    private List<Song> songsInAlbum;

    public Album() {
        songsInAlbum = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
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

    public void addSongToAlbum(Song song) {
        songsInAlbum.add(song);
    }
}
