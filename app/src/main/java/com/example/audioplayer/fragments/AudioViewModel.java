package com.example.audioplayer.fragments;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.audioplayer.database.AudioRepository;
import com.example.audioplayer.models.Album;
import com.example.audioplayer.models.Artist;
import com.example.audioplayer.models.Song;

public class AudioViewModel extends AndroidViewModel {
    private final AudioRepository repository;

    public AudioViewModel(Application application) {
        super(application);
        repository = new AudioRepository(application);
    }

    void insertSong(Song song) {
        repository.insertSong(song);
    }

    void insertAlbum(Album album) {
        repository.insertAlbum(album);
    }

    void insertArtist(Artist artist) {
        repository.insertArtist(artist);
    }
}
