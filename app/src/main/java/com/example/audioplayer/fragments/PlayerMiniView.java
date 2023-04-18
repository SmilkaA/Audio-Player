package com.example.audioplayer.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.example.audioplayer.R;
import com.example.audioplayer.models.Song;

@RequiresApi(api = Build.VERSION_CODES.O)
public class PlayerMiniView extends LinearLayout {

    private final ImageView thumbnail;
    private final TextView musicName;
    private final TextView musicArtist;
    private final ImageView playButton;
    private final ImageView nextButton;

    public PlayerMiniView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.player_mini, this, true);

        thumbnail = findViewById(R.id.music_icon_mini);
        musicName = findViewById(R.id.music_name_mini);
        musicArtist = findViewById(R.id.music_artist_mini);
        playButton = findViewById(R.id.play_music_button_mini);
        nextButton = findViewById(R.id.next_music_button_mini);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PlayerMiniView);
        try {
            Drawable thumbnailImage = typedArray.getDrawable(R.styleable.PlayerMiniView_musicIconMini);
            String musicNameText = typedArray.getString(R.styleable.PlayerMiniView_musicNameMini);
            String musicArtistText = typedArray.getString(R.styleable.PlayerMiniView_musicArtistMini);
            Drawable playButtonImage = typedArray.getDrawable(R.styleable.PlayerMiniView_playMusicButtonMini);
            Drawable nextButtonImage = typedArray.getDrawable(R.styleable.PlayerMiniView_nextMusicButtonMini);

            thumbnail.setImageDrawable(thumbnailImage);
            musicName.setText(musicNameText);
            musicArtist.setText(musicArtistText);
            playButton.setImageDrawable(playButtonImage);
            nextButton.setImageDrawable(nextButtonImage);

        } finally {
            typedArray.recycle();
        }
    }

    public void setThumbnail(Song songToDisplay) {
        if (songToDisplay.getThumbnailUri() != null) {
            Glide.with(this)
                    .load(songToDisplay.getThumbnailUri())
                    .into(thumbnail);
        } else {
            Glide.with(this)
                    .load(R.drawable.default_album_icon)
                    .into(thumbnail);
        }
    }

    public void setSongName(String name) {
        this.musicName.setText(name);
    }

    public void setArtistName(String name) {
        this.musicArtist.setText(name);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void setPlayButton(boolean isPlaying) {
        if (isPlaying) {
            playButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_player_pause));
        } else playButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_player_play));
    }

    public ImageView getPlayButton() {
        return playButton;
    }

    public ImageView getNextButton() {
        return nextButton;
    }
}
