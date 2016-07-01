package com.lambdasoup.coredeathbell;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.WorkerThread;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.NotificationCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.messaging.FirebaseMessaging;

import static com.lambdasoup.coredeathbell.CoreDeathBell.*;

/**
 * Created by jl on 01.07.16.
 */
public class SubscribeService extends IntentService {
    private static final String ACTION_ON_ENSURE_SUBSCRIBED = "com.lambdasoup.coredeathbell.action.ON_ENSURE_SUBSCRIBED";


    public SubscribeService() {
        super("SubscribeService");
    }

    /**
     * For use by the BootupReceiver. As alarms can occur during doze mode etc.,
     * this is a wakeful broadcast receiver, and this action needs to take care to release
     * the wake lock carried by the intent at the end.
     */
    public static Intent getIntentOnEnsureSubscribed(Context context) {
        Intent intent = new Intent(context, SubscribeService.class);
        intent.setAction(ACTION_ON_ENSURE_SUBSCRIBED);
        return intent;
    }

    @Override
    @WorkerThread
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (action == null) {
                throw new IllegalArgumentException("Unexpected action: " + action);
            }
            switch (action) {
                case ACTION_ON_ENSURE_SUBSCRIBED:
                    handleOnEnsureSubscribed(intent);
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected action: " + action);
            }
        }
    }

    private void handleOnEnsureSubscribed(Intent intent) {
        try {
            int checkPlayservicesAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
            if (checkPlayservicesAvailable != ConnectionResult.SUCCESS) {
                // just let the MainActivity take care of it.

                Intent activityIntent = new Intent(getApplicationContext(), MainActivity.class);

                PendingIntent contentIntent = TaskStackBuilder.create(this)
                        .addNextIntentWithParentStack(activityIntent)
                        .getPendingIntent(PENDING_INTENT_PLAY_SERVICES_NOT_AVAILABLE, PendingIntent.FLAG_UPDATE_CURRENT);

                android.support.v4.app.NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.common_ic_googleplayservices)
                        .setContentTitle(getText(R.string.notification_play_services_not_available_title))
                        .setContentText(getText(R.string.notification_play_services_not_available_text))
                        .setAutoCancel(true)
                        .setContentIntent(contentIntent)
                        .addAction(R.drawable.ic_build_black_24dp, getString(R.string.notification_play_services_not_available_action_fix_it), contentIntent);

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(NOTIFICATION_PLAY_SERVICES_NOT_AVAILABLE, notification.build());
            } else {
                FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_NON_CORE_BLOCK_MINED);
            }

        } finally {
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        }
    }
}
