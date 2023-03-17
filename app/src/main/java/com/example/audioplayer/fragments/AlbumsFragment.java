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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.audioplayer.MainActivity;
import com.example.audioplayer.adapters.AlbumsAdapter;
import com.example.audioplayer.adapters.OnClickListener;
import com.example.audioplayer.database.DataLoader;
import com.example.audioplayer.databinding.FragmentAlbumsBinding;
import com.example.audioplayer.models.Album;

import java.util.List;

public class AlbumsFragment extends Fragment {

    private FragmentAlbumsBinding binding;
    private MainActivity mainActivity;
    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private OnClickListener listener;
    private List<Album> albums;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) requireActivity();
        DataLoader dataLoader = new DataLoader(getContext());
        albums = dataLoader.getAllAlbums();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAlbumsBinding.inflate(inflater, container, false);


        listener = new OnClickListener() {
            @Override
            public void onClick(int index) {
                Toast.makeText(getContext(), "clicked item index is " + index, Toast.LENGTH_LONG).show();
            }
        };
        adapter = new AlbumsAdapter(getContext(), albums, listener);

        recyclerView = binding.rvAlbums;
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