package com.lambdasoup.coredeathbell;

import android.app.Application;

/**
 * Created by jl on 01.07.16.
 */
public class CoreDeathBell extends Application {
    public static final String TOPIC_NON_CORE_BLOCK_MINED = "NonCoreBlockMined";
    public static final int PENDING_INTENT_PLAY_SERVICES_NOT_AVAILABLE = 1;
    public static final int NOTIFICATION_PLAY_SERVICES_NOT_AVAILABLE = 1;
    public static final int PENDING_INTENT_NON_CORE_BLOCK_MINED = 0;
    public static final int NOTIFICATION_NON_CORE_BLOCK_MINED = 0;
}
