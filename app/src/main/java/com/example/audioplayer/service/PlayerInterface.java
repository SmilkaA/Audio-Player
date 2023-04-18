package com.example.audioplayer.service;

import com.example.audioplayer.models.Song;

public interface PlayerInterface {

    void start();
    void play(Song song);
    void pause();
    void stop();
    void seekTo(int position);
    boolean isPlaying();
    int getCurrentStreamPosition();
}
