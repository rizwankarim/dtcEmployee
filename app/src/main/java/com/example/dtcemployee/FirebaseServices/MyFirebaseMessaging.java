package com.example.dtcemployee.FirebaseServices;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.dtcemployee.Activities.HomeActivity;
import com.example.dtcemployee.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import io.paperdb.Paper;

public class MyFirebaseMessaging extends FirebaseMessagingService {

    public static String device_token;
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        device_token= FirebaseInstanceId.getInstance().getToken();
        SharedPreferences deviceshare= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor= deviceshare.edit();
        editor.putString("device_token",device_token);
        editor.apply();
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        showNotification(remoteMessage.getData());

    }

    private void showNotification(Map<String, String> data) {
        Paper.init(this);
        String title = data.get("title");
        String body = data.get("body");
        String click_Action= data.get("click_action");

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "com.example.dtcemployee";

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID, "Notification", NotificationManager.IMPORTANCE_HIGH
            );

            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();

            notificationChannel.setDescription("DTC Employee");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setSound(uri,att);
            notificationChannel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationManager.createNotificationChannel(notificationChannel);

        }

        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent openIntent = new Intent(getApplicationContext(), HomeActivity.class);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_CHANNEL_ID);
        PendingIntent myIntent= PendingIntent.getActivity(this,0,openIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setAutoCancel(true)
                .setSound(uri)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setVibrate(new long[]{0, 1000, 500, 1000})
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.logo_high)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(myIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(body));

        notificationManager.notify(0, notificationBuilder.build());

    }

}
