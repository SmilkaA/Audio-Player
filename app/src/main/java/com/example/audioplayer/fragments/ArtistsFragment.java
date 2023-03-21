package com.example.audioplayer.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.audioplayer.MainActivity;
import com.example.audioplayer.R;
import com.example.audioplayer.adapters.ArtistsAdapter;
import com.example.audioplayer.adapters.OnClickListener;
import com.example.audioplayer.database.DataLoader;
import com.example.audioplayer.databinding.FragmentArtistsBinding;
import com.example.audioplayer.models.Artist;

import java.util.List;

public class ArtistsFragment extends Fragment implements OnClickListener {

    private FragmentArtistsBinding binding;
    private List<Artist> artists;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity mainActivity = (MainActivity) requireActivity();
        DataLoader dataLoader = new DataLoader(getContext());
        artists = dataLoader.getAllArtists();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentArtistsBinding.inflate(inflater, container, false);

        initRecyclerView();

        return binding.getRoot();
    }

    private void initRecyclerView() {
        ArtistsAdapter adapter = new ArtistsAdapter(getContext(), artists, this);
        RecyclerView recyclerView = binding.rvArtists;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(int index) {
        Intent intent = new Intent(requireActivity(), MainActivity.class);
        Bundle bundle = new Bundle();
        String albumName = artists.get(index).getName();
        bundle.putString(getString(R.string.intent_key_artist_name), albumName);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}