package com.example.audioplayer.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.audioplayer.R;
import com.example.audioplayer.activities.MainActivity;
import com.example.audioplayer.activities.SongPlayerActivity;
import com.example.audioplayer.adapters.OnClickListener;
import com.example.audioplayer.adapters.SongAdapter;
import com.example.audioplayer.databinding.FragmentSongsBinding;
import com.example.audioplayer.models.Song;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class SongsFragment extends Fragment implements OnClickListener {

    private FragmentSongsBinding binding;
    private MainActivity mainActivity;
    private BottomNavigationView bottomNavigationView;
    private List<Song> songs;
    private String filterByAlbum = "";
    private String filterByArtist = "";

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
        } catch (Exception ignored) {
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        songs = mainActivity.getAudioData();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(int index) {
        Intent playIntent = new Intent(getContext(), SongPlayerActivity.class);
        playIntent.putExtra(getString(R.string.song_item_id_key), index);
        requireActivity().startActivity(playIntent);
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