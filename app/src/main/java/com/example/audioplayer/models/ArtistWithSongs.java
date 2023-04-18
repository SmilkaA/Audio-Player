package com.example.audioplayer.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ArtistWithSongs {
    @Embedded
    public Artist artist;
    @Relation(
            parentColumn = "artist_id",
            entityColumn = "artistId"
    )
    public List<Song> songs;
}
