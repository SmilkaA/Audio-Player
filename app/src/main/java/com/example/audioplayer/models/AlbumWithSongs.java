package com.example.audioplayer.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class AlbumWithSongs {
    @Embedded
    public Album album;
    @Relation(
            parentColumn = "album_id",
            entityColumn = "albumId"
    )
    public List<Song> songs;
}
