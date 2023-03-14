package com.example.audioplayer.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "recent_history_table")
public class RecentHistory {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "history_id")
    private int id;

    @ColumnInfo(name = "time_played_id")
    private long timePlayed;

    public RecentHistory(long timePlayed) {
        this.timePlayed = timePlayed;
    }

    public int getId() {
        return id;
    }

    public long getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(long timePlayed) {
        this.timePlayed = timePlayed;
    }
}
