package com.example.audioplayer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.audioplayer.R;
import com.example.audioplayer.models.Song;

import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

    private Context context;
    private List<Song> songs;
    private OnClickListener listener;

    public SongAdapter(Context context, List<Song> songs, OnClickListener onClickListener) {
        this.context = context;
        if (songs != null) {
            this.songs = songs;
        } else {
            this.songs = new ArrayList<>();
        }
        this.listener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_song, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song songToDisplay = songs.get(position);
        holder.songName.setText(songToDisplay.getSongName());
        holder.artistName.setText(songToDisplay.getArtistName());

        loadThumbnail(holder, songToDisplay);

        holder.itemView.setOnClickListener(v -> listener.onClick(position));
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView songName;
        TextView artistName;
        ImageView thumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.item_song_name);
            artistName = itemView.findViewById(R.id.item_song_artist_name);
            thumbnail = itemView.findViewById(R.id.icon_item_song);
        }
    }

    private void loadThumbnail(@NonNull ViewHolder holder, Song songToDisplay) {
        if (songToDisplay.getThumbnail().equals("11")) {
            Glide.with(context)
                    .load(R.drawable.default_album_icon)
                    .into(holder.thumbnail);
        } else {
            Glide.with(context)
                    .load(songToDisplay.getThumbnailUri())
                    .into(holder.thumbnail);
        }
    }
}