package com.example.audioplayer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.audioplayer.MainActivity;
import com.example.audioplayer.adapters.ArtistsAdapter;
import com.example.audioplayer.adapters.OnClickListener;
import com.example.audioplayer.databinding.FragmentArtistsBinding;
import com.example.audioplayer.models.Artist;

import java.util.List;

public class ArtistsFragment extends Fragment {

    private FragmentArtistsBinding binding;
    private MainActivity mainActivity;
    private RecyclerView recyclerView;
    private ArtistsAdapter adapter;
    private OnClickListener listener;
    private List<Artist> artists;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) requireActivity();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentArtistsBinding.inflate(inflater, container, false);
        listener = new OnClickListener() {
            @Override
            public void onClick(int index) {
                Toast.makeText(getContext(), "clicked item index is " + index, Toast.LENGTH_LONG).show();
            }
        };
        adapter = new ArtistsAdapter(getContext(), artists, listener);

        recyclerView = binding.rvArtists;
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