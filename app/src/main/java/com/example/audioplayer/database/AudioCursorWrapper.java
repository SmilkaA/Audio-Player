package com.example.audioplayer.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.provider.MediaStore;

import com.example.audioplayer.models.Song;

public class AudioCursorWrapper extends CursorWrapper {

    public AudioCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Song getSongData() {
        int id = getInt(getColumnIndex(MediaStore.Audio.Media._ID));
        String title = getString(getColumnIndex(MediaStore.Audio.Media.TITLE));
        String artistName = getString(getColumnIndex(MediaStore.Audio.Media.ARTIST));
        String composer = getString(getColumnIndex(MediaStore.Audio.Media.COMPOSER));
        String albumName = getString(getColumnIndex(MediaStore.Audio.Media.ALBUM));
        String data = getString(getColumnIndex(MediaStore.Audio.Media.DATA));
        int trackNumber = getInt(getColumnIndex(MediaStore.Audio.Media.TRACK));
        int year = getInt(getColumnIndex(MediaStore.Audio.Media.YEAR));
        long duration = getLong(getColumnIndex(MediaStore.Audio.Media.DURATION));
        int albumId = getInt(getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
        int artistId = getInt(getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
        long bookmark = getLong(getColumnIndex(MediaStore.Audio.Media.BOOKMARK));
        return new Song(id, title, artistName, composer, albumName, String.valueOf(albumId), data, trackNumber, year, duration, albumId, artistId, bookmark);
    }
}
