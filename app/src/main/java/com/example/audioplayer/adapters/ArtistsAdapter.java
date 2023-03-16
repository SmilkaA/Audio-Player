package com.example.audioplayer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.audioplayer.R;
import com.example.audioplayer.models.Artist;

import java.util.ArrayList;
import java.util.List;

public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ViewHolder> {

    private final Context context;
    private final List<Artist> artists;
    private final OnClickListener listener;

    public ArtistsAdapter(Context context, List<Artist> artists, OnClickListener onClickListener) {
        this.context = context;
        if (artists != null) {
            this.artists = artists;
        } else {
            this.artists = new ArrayList<>();
        }
        this.listener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_artist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Artist artistToDisplay = artists.get(position);
        holder.artistName.setText(artistToDisplay.getName());
        holder.albumsAmount.setText(context.getString(R.string.amount_of_albums, "1"));
        holder.songsAmount.setText(context.getString(R.string.amount_of_songs, "1"));

        holder.itemView.setOnClickListener(v -> listener.onClick(position));
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView artistName;
        TextView albumsAmount;
        TextView songsAmount;
        ImageView thumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            artistName = itemView.findViewById(R.id.item_artist_name);
            albumsAmount = itemView.findViewById(R.id.item_artist_no_of_albums);
            songsAmount = itemView.findViewById(R.id.item_artist_no_of_songs);
            thumbnail = itemView.findViewById(R.id.item_artist_image);
        }
    }
}
