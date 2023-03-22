package com.example.audioplayer.activities;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.audioplayer.R;
import com.example.audioplayer.database.DataLoader;
import com.example.audioplayer.databinding.PlayerBinding;
import com.example.audioplayer.models.Song;
import com.example.audioplayer.service.Constants;
import com.example.audioplayer.service.MusicService;

public class SongPlayerActivity extends AppCompatActivity {

    private static final int ONE_SECOND = 1000;
    private PlayerBinding binding;
    private ImageView songThumbnail;
    private TextView songName;
    private TextView songArtist;
    private SeekBar songProgress;
    private TextView songCurrentTime;
    private TextView songEndTime;
    private ImageView prevButton;
    private ImageView playButton;
    private ImageView nextButton;
    private Song songToDisplay;

    private MusicService musicService;
    boolean boundService = false;
    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            musicService = binder.getService();
            boundService = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            boundService = false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = PlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle b = getIntent().getExtras();
        int index = b.getInt(getString(R.string.song_item_id_key));


        startService(index);
        getSongToDisplay(index);
        initActivityComponents();
        setDataToComponents();
        SongSeekBarThread seekBarThread = new SongSeekBarThread();
        seekBarThread.start();
    }

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        unbindService(connection);
        boundService = false;
    }

    private void getSongToDisplay(int index) {
        songToDisplay = new DataLoader(this).getSongByIndex(index);
    }

    private void initActivityComponents() {
        songThumbnail = binding.musicIcon;
        songName = binding.musicName;
        songArtist = binding.musicArtist;
        songProgress = binding.musicSeekbar;
        songCurrentTime = binding.musicCurrentTime;
        songEndTime = binding.musicEndTime;
        prevButton = binding.prevMusicButton;
        prevButton.setOnClickListener(v -> onPreviousButtonClicked());
        playButton = binding.playMusicButton;
        playButton.setOnClickListener(v -> onPlayButtonClicked());
        nextButton = binding.nextMusicButton;
        nextButton.setOnClickListener(v -> onNextButtonClicked());
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void onPreviousButtonClicked() {
        musicService.playPrev();
        getSongToDisplay(musicService.getSongIndex());
        setDataToComponents();
        playButton.setImageDrawable(getDrawable(R.drawable.ic_player_pause));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void onPlayButtonClicked() {
        if (musicService.isPlaying()) {
            playButton.setImageDrawable(getDrawable(R.drawable.ic_player_play));
            musicService.pause();
        } else {
            playButton.setImageDrawable(getDrawable(R.drawable.ic_player_pause));
            musicService.start();
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void onNextButtonClicked() {
        musicService.playNext();
        getSongToDisplay(musicService.getSongIndex());
        setDataToComponents();
        playButton.setImageDrawable(getDrawable(R.drawable.ic_player_pause));
    }

    public void setDataToComponents() {
        if (songToDisplay.getThumbnail().equals("11")) {
            Glide.with(this)
                    .load(R.drawable.default_album_icon)
                    .into(songThumbnail);
        } else {
            Glide.with(this)
                    .load(songToDisplay.getThumbnailUri())
                    .into(songThumbnail);
        }
        songName.setText(songToDisplay.getSongName());
        songArtist.setText(songToDisplay.getArtistName());
        songCurrentTime.setText(R.string.song_start_time);
        songProgress.setProgress(0);
        songProgress.setMax((int) songToDisplay.getDuration());
        songProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                songProgress.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                musicService.seekTo(seekBar.getProgress());
                songCurrentTime.setText(DataLoader.toTimeFormat(seekBar.getProgress()));
            }
        });
        songEndTime.setText(DataLoader.toTimeFormat(songToDisplay.getDuration()));

    }

    private void startService(int index) {
        Intent playIntent = new Intent(this, MusicService.class);
        playIntent.putExtra(getString(R.string.song_item_id_key), index);
        playIntent.setAction(Constants.NOTIFICATION_ACTION_PLAY);
        startService(playIntent);
    }

    private class SongSeekBarThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(ONE_SECOND);
                    if (musicService.isPlaying()) {
                        final String time = DataLoader.toTimeFormat(musicService.getCurrentStreamPosition());
                        runOnUiThread(() -> {
                            songProgress.setProgress(musicService.getCurrentStreamPosition());
                            songCurrentTime.setText(time);
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
