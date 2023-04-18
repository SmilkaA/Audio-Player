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

import com.example.audioplayer.activities.MainActivity;
import com.example.audioplayer.R;
import com.example.audioplayer.adapters.AlbumsAdapter;
import com.example.audioplayer.adapters.OnClickListener;
import com.example.audioplayer.databinding.FragmentAlbumsBinding;
import com.example.audioplayer.models.Album;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AlbumsFragment extends Fragment implements OnClickListener {

    private FragmentAlbumsBinding binding;
    private List<Album> albums;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity mainActivity = (MainActivity) requireActivity();
        albums = mainActivity.getAlbumsData();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAlbumsBinding.inflate(inflater, container, false);

        initRecyclerView();
        addAlbumsToDB();

        return binding.getRoot();
    }

    private void initRecyclerView() {
        AlbumsAdapter adapter = new AlbumsAdapter(getContext(), albums, this);
        RecyclerView recyclerView = binding.rvAlbums;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    private void addAlbumsToDB() {
        AudioViewModel songsViewModel = new ViewModelProvider(this).get(AudioViewModel.class);
        for (Album album : albums) {
            songsViewModel.insertAlbum(album);
        }
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
        bundle.putString(getString(R.string.intent_key_album_name), albums.get(index).getAlbumName());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}