package com.example.audioplayer.database;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import com.example.audioplayer.models.Album;
import com.example.audioplayer.models.Artist;
import com.example.audioplayer.models.Song;

import java.util.ArrayList;
import java.util.List;

public class DataLoader {

    private final Context context;
    private Uri uri;
    private final String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
    private static final List<Song> audioList = new ArrayList<>();
    private static final List<Album> albumsList = new ArrayList<>();
    private static final List<Artist> artistsList = new ArrayList<>();

    public DataLoader(Context context) {
        this.context = context;
        getUri();
    }

    public void getUri() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.uri = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            this.uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }
    }

    public List<Song> getAllAudioFromDevice() {
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        audioList.clear();
        if (cursor != null) {
            AudioCursorWrapper audioCursorWrapper = new AudioCursorWrapper(cursor);
            while (cursor.moveToNext()) {
                audioList.add(audioCursorWrapper.getSongData());
            }
            cursor.close();
        }
        return audioList;
    }

    public List<Album> getAllAlbums() {
        for (Song audioFromDevice : audioList) {
            if (new ArrayList<>(albumsList).isEmpty()) {
                albumsList.add(createNewAlbumForSong(audioFromDevice));
            } else {
                for (Album album : new ArrayList<>(albumsList)) {
                    if (album.getAlbumName().equals(audioFromDevice.getAlbumName())) {
                        if (!album.getSongsInAlbum().contains(audioFromDevice)) {
                            album.addSongToAlbum(audioFromDevice);
                        }
                    } else {
                        if (checkAlbumBeforeAdd(audioFromDevice.getAlbumName()))
                            albumsList.add(createNewAlbumForSong(audioFromDevice));
                    }
                }
            }
        }
        Log.d("111", String.valueOf(albumsList.size()));
        return albumsList;
    }

    private Album createNewAlbumForSong(Song sourceSong) {
        Album albumToAdd = new Album();
        albumToAdd.setAlbumName(sourceSong.getAlbumName());
        albumToAdd.setId(sourceSong.getAlbumId());
        albumToAdd.setArtistId(sourceSong.getArtistId());
        albumToAdd.addSongToAlbum(sourceSong);
        return albumToAdd;
    }

    private boolean checkAlbumBeforeAdd(String albumName) {
        for (Album album : new ArrayList<>(albumsList)) {
            if (album.getAlbumName().contains(albumName)) {
                return false;
            }
        }
        return true;
    }

    public List<Artist> getAllArtists() {
        return artistsList;
    }
}
