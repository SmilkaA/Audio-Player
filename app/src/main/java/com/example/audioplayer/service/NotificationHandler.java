package com.example.audioplayer.service;

import static com.example.audioplayer.service.Constants.CHANNEL_ID;
import static com.example.audioplayer.service.Constants.CHANNEL_NAME;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;

import com.example.audioplayer.MainActivity;
import com.example.audioplayer.R;
import com.example.audioplayer.models.Song;

public class NotificationHandler {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Notification createNotification(Context context, Song currentSong, boolean playStatus) {
        MediaSession mediaSession = new MediaSession(context, "session");
        Notification.Builder builder = new Notification.Builder(context, CHANNEL_ID);
        NotificationManager mNotificationManager;

        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Intent previousIntent = new Intent(context, MusicService.class);
        previousIntent.setAction("action.prev");
        PendingIntent pendingPreviousIntent = PendingIntent.getService(context, 0, previousIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Intent playIntent = new Intent(context, MusicService.class);
        playIntent.setAction("action.play");
        PendingIntent pendingPlayIntent = PendingIntent.getService(context, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Intent pauseIntent = new Intent(context, MusicService.class);
        pauseIntent.setAction("action.pause");
        PendingIntent pendingPauseIntent = PendingIntent.getService(context, 0, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Intent nextIntent = new Intent(context, MusicService.class);
        nextIntent.setAction("action.next");
        PendingIntent pendingNextIntent = PendingIntent.getService(context, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        builder.setContentTitle(currentSong.getSongName())
                .setContentText(currentSong.getArtistName())
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.default_album_icon)
                .setLargeIcon(getBitmap(context, currentSong.getThumbnailUri()))
                .addAction(R.drawable.ic_action_prev, context.getResources().getString(R.string.action_previous), pendingPreviousIntent);

        if (playStatus) {
            builder.addAction(android.R.drawable.ic_media_pause,  context.getResources().getString(R.string.action_play), pendingPauseIntent);
        } else {
            builder.addAction(android.R.drawable.ic_media_play,  context.getResources().getString(R.string.action_pause), pendingPlayIntent);
        }

        builder.addAction(R.drawable.ic_action_next, context.getResources().getString(R.string.action_next), pendingNextIntent)
                .setStyle(new Notification.MediaStyle()
                        .setMediaSession(mediaSession.getSessionToken())
                        .setShowActionsInCompactView(1))
                .setAutoCancel(false);

        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = CHANNEL_ID;
            NotificationChannel channel = new NotificationChannel(channelId, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }

        return builder.build();
    }

    public static Bitmap getBitmap(Context context, String uri) {
        Bitmap bitmap;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(uri));
        } catch (Exception e) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_album_icon);
        }
        return bitmap;
    }

}
