package com.example.audioplayer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.audioplayer.database.DataLoader;
import com.example.audioplayer.databinding.ActivityMainBinding;
import com.example.audioplayer.fragments.SongsFragment;
import com.example.audioplayer.models.Song;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String filter = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initBottomNavigation();
        checkPermission();

        sendDataToSongFragment();
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
        DataLoader dataLoader = new DataLoader(getApplicationContext());
        return dataLoader.getAllAudioFromDevice();
    }

    private void sendDataToSongFragment() {
        getDataFromAlbumFragment();
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.intent_key_album_name_data), filter);
        SongsFragment songsFragment = new SongsFragment();
        songsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.songs_container, songsFragment)
                .commit();
    }

    private void getDataFromAlbumFragment() {
        try {
            Bundle b = getIntent().getExtras();
            filter = b.getString(getString(R.string.intent_key_album_name));
        } catch (Exception ignored) {
        }
    }
}
