package com.example.audioplayer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.audioplayer.MainActivity;
import com.example.audioplayer.adapters.OnClickListener;
import com.example.audioplayer.adapters.SongAdapter;
import com.example.audioplayer.databinding.FragmentSongsBinding;
import com.example.audioplayer.models.Song;

import java.util.List;

public class SongsFragment extends Fragment {

    private FragmentSongsBinding binding;
    private MainActivity mainActivity;
    private RecyclerView recyclerView;
    private SongAdapter adapter;
    private OnClickListener listener;
    private List<Song> songs;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) requireActivity();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSongsBinding.inflate(inflater, container, false);
        SongsViewModel songsViewModel = new ViewModelProvider(this).get(SongsViewModel.class);
        songs = mainActivity.getAudioData();
        for (Song song : songs) {
            songsViewModel.insert(song);
        }

        listener = new OnClickListener() {
            @Override
            public void onClick(int index) {
                Toast.makeText(getContext(), "clicked item index is " + index, Toast.LENGTH_LONG).show();
            }
        };
        adapter = new SongAdapter(getContext(), songs, listener);

        recyclerView = binding.rvSongs;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}