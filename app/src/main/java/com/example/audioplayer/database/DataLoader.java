package com.example.audioplayer.database;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import com.example.audioplayer.models.Album;
import com.example.audioplayer.models.Artist;
import com.example.audioplayer.models.Song;

import java.util.ArrayList;
import java.util.List;

public class DataLoader {

    public static final Uri THUMBNAIL_URI = Uri.parse("content://media/external/audio/albumart");
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
        albumsList.clear();
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
                        if (checkAlbumBeforeAdd(audioFromDevice.getAlbumName())) {
                            albumsList.add(createNewAlbumForSong(audioFromDevice));
                        }
                    }
                }
            }
        }
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
        artistsList.clear();
        for (Song song : audioList) {
            if (new ArrayList<>(artistsList).isEmpty()) {
                artistsList.add(createNewArtistBySong(song));
            } else {
                for (Artist artist : new ArrayList<>(artistsList)) {
                    String artistName = song.getArtistName();
                    if (artist.getName().equals(artistName)) {
                        if (!artist.getSongsPerArtist().contains(song)) {
                            artist.addSongsPerArtist(song);
                        }
                    } else {
                        if (checkArtistBeforeAdd(artistName)) {
                            artistsList.add(createNewArtistBySong(song));
                        }
                    }
                }
            }
        }
        return artistsList;
    }

    private Artist createNewArtistBySong(Song song) {
        Artist artistToAdd = new Artist();
        artistToAdd.setName(song.getArtistName());
        artistToAdd.addSongsPerArtist(song);
        return artistToAdd;
    }

    private boolean checkArtistBeforeAdd(String artistName) {
        for (Artist artist : new ArrayList<>(artistsList)) {
            if (artist.getName().contains(artistName)) {
                return false;
            }
        }
        return true;
    }
}
