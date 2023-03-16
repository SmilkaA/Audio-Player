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
import com.example.audioplayer.models.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.ViewHolder> {

    private Context context;
    private List<Album> albums;
    private OnClickListener listener;

    public AlbumsAdapter(Context context, List<Album> albums, OnClickListener listener) {
        this.context = context;
        if (albums != null) {
            this.albums = albums;
        } else {
            this.albums = new ArrayList<>();
        }
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_album, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album albumToDisplay = albums.get(position);
        holder.albumName.setText(albumToDisplay.getName());
        holder.songsAmount.setText(context.getString(albumToDisplay.getSongsInAlbum().size(),
                R.string.amount_of_songs));

        holder.itemView.setOnClickListener(v -> listener.onClick(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView albumName;
        TextView songsAmount;
        ImageView thumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            albumName = itemView.findViewById(R.id.item_album_name);
            songsAmount = itemView.findViewById(R.id.item_album_no_of_songs);
            thumbnail = itemView.findViewById(R.id.item_album_image);
        }
    }
}
