package com.lambdasoup.coredeathbell;

import android.app.Application;

import timber.log.Timber;

public class CoreDeathBell extends Application {
    public static final String TOPIC_NON_CORE_BLOCK_MINED                 = "v2";
    public static final int    PENDING_INTENT_PLAY_SERVICES_NOT_AVAILABLE = 1;
    public static final int    NOTIFICATION_PLAY_SERVICES_NOT_AVAILABLE   = 1;
    public static final int    PENDING_INTENT_NON_CORE_BLOCK_MINED        = 0;
    public static final int    NOTIFICATION_NON_CORE_BLOCK_MINED          = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        // init logging
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
