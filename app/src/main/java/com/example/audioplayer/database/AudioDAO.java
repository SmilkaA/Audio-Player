package com.example.audioplayer.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.audioplayer.models.Album;
import com.example.audioplayer.models.AlbumWithSongs;
import com.example.audioplayer.models.Artist;
import com.example.audioplayer.models.ArtistWithSongs;
import com.example.audioplayer.models.Song;

import java.util.List;

@Dao
public interface AudioDAO {
    @Query("SELECT * FROM songs_table ORDER BY song_id ASC")
    LiveData<List<Song>> getAllSongs();

    @Query("SELECT * FROM albums_table ORDER BY album_id ASC")
    LiveData<List<Album>> getAllAlbums();

    @Query("SELECT * FROM artists_table ORDER BY artist_id ASC")
    LiveData<List<Artist>> getAllArtists();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertSong(Song song);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAlbum(Album album);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertArtist(Artist artist);

    @Transaction
    @Query("SELECT * FROM albums_table ORDER BY album_id ASC")
    LiveData<List<AlbumWithSongs>> getAlbumWithSongs();

    @Transaction
    @Query("SELECT * FROM artists_table ORDER BY artist_id ASC")
    LiveData<List<ArtistWithSongs>> getArtistWithSongs();
}
