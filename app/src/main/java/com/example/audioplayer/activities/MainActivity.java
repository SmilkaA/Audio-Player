package com.example.audioplayer.activities;

import static com.example.audioplayer.service.Constants.NOTIFICATION_ID;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.audioplayer.R;
import com.example.audioplayer.database.DataLoader;
import com.example.audioplayer.databinding.ActivityMainBinding;
import com.example.audioplayer.fragments.PlayerMiniView;
import com.example.audioplayer.fragments.SongsFragment;
import com.example.audioplayer.models.Album;
import com.example.audioplayer.models.Artist;
import com.example.audioplayer.models.Song;
import com.example.audioplayer.service.Constants;
import com.example.audioplayer.service.MusicService;
import com.example.audioplayer.service.NotificationHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {

    private DataLoader dataLoader;
    private String filterByAlbum = "";
    private String filterByArtist = "";
    private PlayerMiniView playerMiniView;
    private NotificationManager notificationManager;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataLoader = new DataLoader(getApplicationContext());
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        playerMiniView = binding.miniPlayer;
        setContentView(binding.getRoot());

        initBottomNavigation();
        checkPermission();

        sendDataToSongFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            setDataToComponents(musicService.getCurrentSong());
        } catch (Exception ignored) {
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_bluetooth) {
            Intent playIntent = new Intent(this, BluetoothActivity.class);
            startActivity(playIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_songs, R.id.navigation_albums, R.id.navigation_artists)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        }
    }

    public List<Song> getAudioData() {
        return dataLoader.getAllAudioFromDevice();
    }

    public List<Album> getAlbumsData() {
        return dataLoader.getAllAlbums();
    }

    public List<Artist> getArtistsData() {
        return dataLoader.getAllArtists();
    }

    private void sendDataToSongFragment() {
        getDataFromFragments();
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.intent_key_album_name_data), filterByAlbum);
        bundle.putString(getString(R.string.intent_key_artist_name_data), filterByArtist);
        SongsFragment songsFragment = new SongsFragment();
        songsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.songs_container, songsFragment)
                .commit();
    }

    private void getDataFromFragments() {
        try {
            Bundle b = getIntent().getExtras();
            filterByAlbum = b.getString(getString(R.string.intent_key_album_name));
            filterByArtist = b.getString(getString(R.string.intent_key_artist_name));
        } catch (Exception ignored) {
        }
    }

    private void startService(int index) {
        Intent playIntent = new Intent(this, MusicService.class);
        playIntent.putExtra(getString(R.string.song_item_id_key), index);
        playIntent.setAction(Constants.NOTIFICATION_ACTION_PLAY);
        startService(playIntent);
    }

    public void initMiniPlayer(int index) {
        Song songInMini = DataLoader.getSongByIndex(index);
        setDataToComponents(songInMini);
        playerMiniView.getPlayButton().setOnClickListener(v -> onPlayButtonClicked(songInMini));
        playerMiniView.getNextButton().setOnClickListener(v -> onNextButtonClicked());
        startService(index);
    }

    private void setDataToComponents(Song songToDisplay) {
        playerMiniView.setVisibility(View.VISIBLE);
        playerMiniView.setThumbnail(songToDisplay);
        playerMiniView.setSongName(songToDisplay.getSongName());
        playerMiniView.setArtistName(songToDisplay.getArtistName());
        playerMiniView.setPlayButton(musicService.isPlaying());
        playerMiniView.setOnClickListener(v -> onMiniPlayerClicked(DataLoader.findIndex(songToDisplay)));
    }

    private void onMiniPlayerClicked(int index) {
        Intent playIntent = new Intent(getApplicationContext(), SongPlayerActivity.class);
        playIntent.putExtra(getString(R.string.song_item_id_key), index);
        startActivity(playIntent);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void onPlayButtonClicked(Song songInMini) {
        if (musicService.isPlaying()) {
            playerMiniView.getPlayButton().setImageDrawable(getDrawable(R.drawable.ic_player_play));
            musicService.pause();
            notificationManager.notify(NOTIFICATION_ID, NotificationHandler.createNotification(getApplicationContext(), songInMini, false));
        } else {
            playerMiniView.getPlayButton().setImageDrawable(getDrawable(R.drawable.ic_player_pause));
            musicService.start();
            notificationManager.notify(NOTIFICATION_ID, NotificationHandler.createNotification(getApplicationContext(), songInMini, true));
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void onNextButtonClicked() {
        musicService.playNext();
        Song songToDisplay = DataLoader.getSongByIndex(musicService.getSongIndex());
        setDataToComponents(songToDisplay);
        playerMiniView.getPlayButton().setImageDrawable(getDrawable(R.drawable.ic_player_pause));
        notificationManager.notify(NOTIFICATION_ID, NotificationHandler.createNotification(getApplicationContext(), songToDisplay, true));
    }
}
