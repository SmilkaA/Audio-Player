package com.example.audioplayer.service;

import com.example.audioplayer.models.Song;

public interface PlayerInterface {

    void play(Song song);
    void pause();
    void stop();
    void seekTo(int position);
    boolean isPlaying();
    long getDuration();
    int getCurrentStreamPosition();
    void setCallback(Callback callback);

    interface Callback {
        void onCompletion(Song song);
        void onTrackChange(Song song);
        void onPause();
    }
}
