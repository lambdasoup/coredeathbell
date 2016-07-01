package com.lambdasoup.coredeathbell;

import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by jl on 01.07.16.
 */
public class DataMessageConverter {

    public static NonCoreBlockMinedEvent convert(RemoteMessage remoteMessage, Class<NonCoreBlockMinedEvent> type) {
        Map<String, String> data = remoteMessage.getData();
        long when;
        if (data.containsKey("when")) {
            try {
                when = Long.decode(data.get("when"));
            } catch (NumberFormatException e) {
                throw new ConverterException("NonCoreBlockMinedEvent: not a long value for 'when': " + data.get("when"));
            }
        } else {
            throw new ConverterException("NonCoreBlockMinedEvent: missing key 'when'");
        }

        return new NonCoreBlockMinedEvent(when);
    }


    public static class ConverterException extends RuntimeException {
        public ConverterException(String msg) {
            super(msg);
        }
    }
}
