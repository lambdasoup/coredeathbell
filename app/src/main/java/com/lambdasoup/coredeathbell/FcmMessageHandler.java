package com.lambdasoup.coredeathbell;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static com.lambdasoup.coredeathbell.CoreDeathBell.*;

/**
 * Created by jl on 01.07.16.
 */
public class FcmMessageHandler extends FirebaseMessagingService {
    private static final String TAG = FcmMessageHandler.class.getSimpleName();

    private static final String TOPICS = "/topics/";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived: data=" + remoteMessage.getData() + " from=" + remoteMessage.getFrom());
        if (remoteMessage.getFrom().equals(TOPICS + TOPIC_NON_CORE_BLOCK_MINED)) {
            handleNonCoreBlockMinedMessage(remoteMessage);
        }
    }

    private void handleNonCoreBlockMinedMessage(RemoteMessage remoteMessage) {
        NonCoreBlockMinedEvent event;
        try {
            event = DataMessageConverter.convert(remoteMessage, NonCoreBlockMinedEvent.class);
        } catch (DataMessageConverter.ConverterException e) {
            Log.e(TAG, "could not convert event with data " + remoteMessage.getData());
            return;
        }

        Intent activityIntent = new Intent(getApplicationContext(), MainActivity.class);

        PendingIntent contentIntent = TaskStackBuilder.create(this)
                .addNextIntentWithParentStack(activityIntent)
                .getPendingIntent(PENDING_INTENT_NON_CORE_BLOCK_MINED, PendingIntent.FLAG_UPDATE_CURRENT);
        android.support.v4.app.NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                .setWhen(event.when)
                .setShowWhen(true)
                .setContentTitle(getText(R.string.notification_non_core_block_title))
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_stat_notification)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setCategory(Notification.CATEGORY_EVENT);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String notificationSoundPref = preferences.getString(getString(R.string.pref_key_notification_sound), getString(R.string.setting_notification_sound_bell));
        if (notificationSoundPref.equals(getString(R.string.setting_notification_sound_bell))) {
            notification.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bell_sound));
        } else if (notificationSoundPref.equals(getString(R.string.setting_notification_sound_default))) {
            notification.setSound(RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION));
        } else if (notificationSoundPref.equals(getString(R.string.setting_notification_sound_silent))) {
            // silence requested, nothing to do
        }


        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_NON_CORE_BLOCK_MINED, notification.build());


    }
}
