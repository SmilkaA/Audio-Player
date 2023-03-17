package com.example.audioplayer.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

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
    private List<Album> albumsPerArtist;

    public Artist() {
    }

    public Artist(@NotNull List<Album> albumsPerArtist) {
        this.albumsPerArtist = albumsPerArtist;
    }

    public Artist(@NonNull int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Album> getAlbumsPerArtist() {
        return albumsPerArtist;
    }

    public void setAlbumsPerArtist(List<Album> albumsPerArtist) {
        this.albumsPerArtist = albumsPerArtist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
