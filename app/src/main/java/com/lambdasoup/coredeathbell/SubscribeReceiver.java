package com.lambdasoup.coredeathbell;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jl on 01.07.16.
 */
public class SubscribeReceiver extends WakefulBroadcastReceiver {
    private static final Set<String> ALLOWED_ACTIONS = new HashSet<String>() {
        {
            add(Intent.ACTION_BOOT_COMPLETED);
            add("android.intent.action.QUICKBOOT_POWERON");
            add("com.htc.intent.action.QUICKBOOT_POWERON"); // see http://stackoverflow.com/a/14866346/1428514
        }
    };

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (!ALLOWED_ACTIONS.contains(action)) {
            return;
        }
        startWakefulService(context, SubscribeService.getIntentOnEnsureSubscribed(context));
    }
}
