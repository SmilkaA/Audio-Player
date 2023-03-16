package com.example.audioplayer.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.audioplayer.models.Album;
import com.example.audioplayer.models.Artist;
import com.example.audioplayer.models.RecentHistory;
import com.example.audioplayer.models.Song;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Album.class, Artist.class, Song.class, RecentHistory.class}, version = 1)
public abstract class AudioDatabase extends RoomDatabase {

    public abstract AudioDAO audioDAO();

    private static volatile AudioDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static AudioDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AudioDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AudioDatabase.class, "audio_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                AudioDAO dao = INSTANCE.audioDAO();
                Song song = new Song(1,"","","","","","",1,1,1,1,1,1);
                dao.insertSong(song);
            });
        }
    };
}
