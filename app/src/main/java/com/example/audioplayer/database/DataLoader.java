package com.example.audioplayer.database;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import com.example.audioplayer.models.Song;

import java.util.ArrayList;
import java.util.List;

public class DataLoader {

    private final Context context;
    private Uri uri;
    private final String selection = MediaStore.Audio.Media.DATA + " like ? ";
    private final String[] selectionArgs = new String[]{"%/storage/emulated/%"};
    private static final List<Song> audioList = new ArrayList<>();

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
        Cursor cursor = context.getContentResolver().query(uri, null, selection, selectionArgs, null);
        if (cursor != null) {
            AudioCursorWrapper audioCursorWrapper = new AudioCursorWrapper(cursor);
            while (cursor.moveToNext()) {
                audioList.add(audioCursorWrapper.getSongData());
            }
            cursor.close();
        }
        return audioList;
    }
}
