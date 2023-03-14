package com.example.audioplayer.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.audioplayer.models.Album;
import com.example.audioplayer.models.Artist;
import com.example.audioplayer.models.Song;

import java.util.List;

public class AudioRepository {

    private AudioDAO audioDAO;
    private LiveData<List<Song>> allSongs;
    private LiveData<List<Album>> allAlbums;
    private LiveData<List<Artist>> allArtists;

    public AudioRepository(Application application) {
        AudioDatabase db = AudioDatabase.getDatabase(application);
        audioDAO = db.audioDAO();
        allSongs = audioDAO.getAllSongs();
        allAlbums = audioDAO.getAllAlbums();
        allArtists = audioDAO.getAllArtists();
    }

    public LiveData<List<Song>> getAllSongs() {
        return allSongs;
    }

    public LiveData<List<Album>> getAllAlbums() {
        return allAlbums;
    }

    public LiveData<List<Artist>> getAllArtists() {
        return allArtists;
    }
}
