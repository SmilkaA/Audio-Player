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
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.core.app.NotificationCompat;

import com.example.audioplayer.MainActivity;
import com.example.audioplayer.R;
import com.example.audioplayer.models.Song;

public class NotificationHandler {
    public static Notification createNotification(Context context, Song currentSong, boolean playStatus) {
        NotificationManager mNotificationManager;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);
        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 125, notificationIntent, 0);


        mBuilder.setContentTitle(currentSong.getSongName())
                .setContentText(currentSong.getArtistName())
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.default_album_icon)
                .setAutoCancel(false);


        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = CHANNEL_ID;
            NotificationChannel channel = new NotificationChannel(channelId, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        return mBuilder.build();
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
