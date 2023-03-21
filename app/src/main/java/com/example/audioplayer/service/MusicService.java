package com.example.audioplayer.service;

import static com.example.audioplayer.service.Constants.*;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.audioplayer.R;
import com.example.audioplayer.database.DataLoader;
import com.example.audioplayer.models.Song;

public class MusicService extends Service implements
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener,
        AudioManager.OnAudioFocusChangeListener, PlayerInterface {

    private final IBinder musicBind = new MusicBinder();
    private MediaPlayer player;
    private Song currentSong;

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    protected NotificationManager mNotificationManager;
    protected NotificationCompat.Builder mNotificationBuilder;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        switch (intent.getAction()) {
            case NOTIFICATION_ACTION_PREV:
                playPrev();
                mNotificationManager.notify(NOTIFICATION_ID, NotificationHandler.createNotification(this, currentSong, true));
                break;
            case NOTIFICATION_ACTION_PAUSE:
                pause();
                mNotificationManager.notify(NOTIFICATION_ID, NotificationHandler.createNotification(this, currentSong, false));
                break;
            case NOTIFICATION_ACTION_PLAY:
                mNotificationManager.notify(NOTIFICATION_ID, NotificationHandler.createNotification(this, new DataLoader(getApplicationContext()).getAllAudioFromDevice().get(intent.getExtras().getInt(getString(R.string.song_item_id_key))), true));
                if (player != null) {
                    start();
                } else {
                    initMusicService();
                    start();
                }
                break;
            case NOTIFICATION_ACTION_NEXT:
                playNext();
                mNotificationManager.notify(NOTIFICATION_ID, NotificationHandler.createNotification(this, currentSong, true));
                break;
            case NOTIFICATION_ACTION_STOP:
                stop();
                stopForeground(true);
                stopSelf();
                break;
        }
        return START_STICKY;
    }

    @Override
    public void onAudioFocusChange(int focusChange) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    private void initMusicService() {
    }

    @Override
    public void start() {

    }

    @Override
    public void play(Song song) {
        MediaPlayer mp = new MediaPlayer();
        try {
            mp.setDataSource(song.getData());
            mp.prepare();
            mp.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playPrev() {
    }

    private void playNext() {
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void seekTo(int position) {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public long getDuration() {
        return 0;
    }

    @Override
    public int getCurrentStreamPosition() {
        return 0;
    }

    @Override
    public void setCallback(Callback callback) {

    }

    public void toBackground() {
        stopForeground(true);
    }
}
