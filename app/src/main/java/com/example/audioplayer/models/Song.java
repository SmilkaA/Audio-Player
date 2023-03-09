package com.example.audioplayer.models;

import androidx.room.*;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "songs_table", foreignKeys = {
        @ForeignKey(
                entity = Album.class,
                parentColumns = {"album_id"},
                childColumns = {"album_id"},
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = Artist.class,
                parentColumns = {"artist_id"},
                childColumns = {"artist_id"},
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )})
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

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "albumId")
    private int albumId;

    @ColumnInfo(name = "artistId")
    private int artistId;

    public Song(@NonNull int id, int trackNumber, int year, int albumId,
                int artistId, long duration, long bookmark,
                String title, String date) {
        this.id = id;
        this.trackNumber = trackNumber;
        this.year = year;
        this.albumId = albumId;
        this.artistId = artistId;
        this.duration = duration;
        this.bookmark = bookmark;
        this.title = title;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public int getYear() {
        return year;
    }

    public int getAlbumId() {
        return albumId;
    }

    public int getArtistId() {
        return artistId;
    }

    public long getDuration() {
        return duration;
    }

    public long getBookmark() {
        return bookmark;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setBookmark(long bookmark) {
        this.bookmark = bookmark;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }
}
