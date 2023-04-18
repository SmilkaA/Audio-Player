package com.example.audioplayer.models;

import androidx.room.*;

import com.example.audioplayer.database.DataLoader;

import java.io.File;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "songs_table")
public class Song {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "song_id")
    private int id;

    @ColumnInfo(name = "trackNumber")
    private int trackNumber;

    @ColumnInfo(name = "year")
    private int year;

    @ColumnInfo(name = "duration")
    private long duration;

    @ColumnInfo(name = "bookmark")
    private long bookmark;

    @ColumnInfo(name = "song_name")
    private String songName;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "genre")
    private String genre;

    @ColumnInfo(name = "albumId")
    private int albumId;

    @ColumnInfo(name = "song_album_name")
    private String albumName;

    @ColumnInfo(name = "song_thumbnail")
    private String thumbnail;

    @ColumnInfo(name = "artistId")
    private int artistId;

    @ColumnInfo(name = "artist_name")
    private String artistName;

    @ColumnInfo(name = "composer")
    private String composer;

    @ColumnInfo(name = "data")
    private String data;

    public Song() {
    }

    public Song(int id, String title, String artistName,
                String composer, String albumName, String albumArt,
                String data, int trackNumber, int year, long duration,
                int albumId, int artistId, long bookmark) {
        this.id = id;
        this.songName = title;
        this.artistName = artistName;
        this.composer = composer;
        this.albumName = albumName;
        this.thumbnail = albumArt;
        this.data = data;
        this.trackNumber = trackNumber;
        this.year = year;
        this.duration = duration;
        this.albumId = albumId;
        this.artistId = artistId;
        this.bookmark = bookmark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getBookmark() {
        return bookmark;
    }

    public void setBookmark(long bookmark) {
        this.bookmark = bookmark;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getThumbnailUri() {
        String uri = DataLoader.THUMBNAIL_URI + getId() + DataLoader.THUMBNAIL_TYPE;
        File thumbnail = new File(uri);
        if (thumbnail.exists()) {
            return uri;
        } else return null;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
