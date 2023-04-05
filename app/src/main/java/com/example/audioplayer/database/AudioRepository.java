package com.example.audioplayer.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.audioplayer.models.Album;
import com.example.audioplayer.models.AlbumWithSongs;
import com.example.audioplayer.models.Artist;
import com.example.audioplayer.models.ArtistWithSongs;
import com.example.audioplayer.models.Song;

import java.util.List;

public class AudioRepository {

    private AudioDAO audioDAO;
    private LiveData<List<Song>> allSongs;
    private LiveData<List<Album>> allAlbums;
    private LiveData<List<Artist>> allArtists;
    private LiveData<List<AlbumWithSongs>> albumWithSongs;
    private LiveData<List<ArtistWithSongs>> artistWithSongs;

    public AudioRepository(Application application) {
        AudioDatabase db = AudioDatabase.getDatabase(application);
        audioDAO = db.audioDAO();
        allSongs = audioDAO.getAllSongs();
        allAlbums = audioDAO.getAllAlbums();
        allArtists = audioDAO.getAllArtists();
        albumWithSongs = audioDAO.getAlbumWithSongs();
        artistWithSongs = audioDAO.getArtistWithSongs();
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

    public void insertSong(Song song) {
        AudioDatabase.databaseWriteExecutor.execute(() -> audioDAO.insertSong(song));
    }

    public void insertAlbum(Album album) {
        AudioDatabase.databaseWriteExecutor.execute(() -> audioDAO.insertAlbum(album));
    }

    public void insertArtist(Artist artist) {
        AudioDatabase.databaseWriteExecutor.execute(() -> audioDAO.insertArtist(artist));
    }

    public LiveData<List<AlbumWithSongs>> getAlbumWithSongs() {
        return albumWithSongs;
    }

    public LiveData<List<ArtistWithSongs>> getArtistWithSongs() {
        return artistWithSongs;
    }
}
