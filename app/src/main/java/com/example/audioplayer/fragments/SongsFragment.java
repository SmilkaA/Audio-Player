package com.example.audioplayer.fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.audioplayer.MainActivity;
import com.example.audioplayer.R;
import com.example.audioplayer.adapters.OnClickListener;
import com.example.audioplayer.adapters.SongAdapter;
import com.example.audioplayer.databinding.FragmentSongsBinding;
import com.example.audioplayer.models.Song;
import com.example.audioplayer.service.Constants;
import com.example.audioplayer.service.MusicService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class SongsFragment extends Fragment implements OnClickListener {

    private FragmentSongsBinding binding;
    private MainActivity mainActivity;
    private BottomNavigationView bottomNavigationView;
    private List<Song> songs;
    private MusicService musicService;
    boolean boundService = false;
    private String filterByAlbum = "";
    private String filterByArtist = "";
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) requireActivity();
        songs = mainActivity.getAudioData();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSongsBinding.inflate(inflater, container, false);
        bottomNavigationView = mainActivity.findViewById(R.id.nav_view);
        SongsViewModel songsViewModel = new ViewModelProvider(this).get(SongsViewModel.class);

        initRecyclerView();

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if ((!filterByArtist.equals("")) || (!filterByAlbum.equals(""))) {
                bottomNavigationView.setVisibility(View.GONE);
            }
        } catch (Exception exception) {
        }
    }

    private void initRecyclerView() {
        filterSongsList();
        SongAdapter adapter = new SongAdapter(getContext(), songs, this);
        RecyclerView recyclerView = binding.rvSongs;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent(requireActivity(), MusicService.class);
        requireActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        requireActivity().unbindService(connection);
        boundService = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        songs = mainActivity.getAudioData();
    }

    @Override
    public void onClick(int index) {
        Intent playIntent = new Intent(getContext(), MusicService.class);
        playIntent.putExtra(getString(R.string.song_item_id_key), index);
        playIntent.setAction(Constants.NOTIFICATION_ACTION_PLAY);
        requireActivity().startService(playIntent);
    }

    private void filterSongsList() {
        try {
            Bundle bundle = getArguments();
            filterByAlbum = bundle.getString(getString(R.string.intent_key_album_name_data));
            filterByArtist = bundle.getString(getString(R.string.intent_key_artist_name_data));
            filterByAlbum(filterByAlbum);
            filterByArtist(filterByArtist);
            bundle.clear();
        } catch (Exception ignored) {
        }
    }

    private void filterByAlbum(String filterByAlbum) {
        if (filterByAlbum != null) {
            if (!filterByAlbum.equals("")) {
                for (Song song : new ArrayList<>(songs)) {
                    if (!song.getAlbumName().equals(filterByAlbum)) {
                        songs.remove(song);
                    }
                }
            }
        }
    }

    private void filterByArtist(String filterByArtist) {
        if (filterByArtist != null) {
            if (!filterByArtist.equals("")) {
                for (Song song : new ArrayList<>(songs)) {
                    if (!song.getArtistName().equals(filterByArtist)) {
                        songs.remove(song);
                    }
                }
            }
        }
    }

}