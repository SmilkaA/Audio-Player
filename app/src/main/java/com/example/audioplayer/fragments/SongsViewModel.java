package com.example.audioplayer.fragments;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.audioplayer.database.AudioRepository;
import com.example.audioplayer.models.Song;

import java.util.List;

public class SongsViewModel extends AndroidViewModel {
    private AudioRepository repository;
    private final LiveData<List<Song>> mAllWords;

    public SongsViewModel(Application application) {
        super(application);
        repository = new AudioRepository(application);
        mAllWords = repository.getAllSongs();
    }

    LiveData<List<Song>> getAllSongs() {
        return mAllWords;
    }

    void insert(Song song) {
        repository.insertSong(song);
    }
}
