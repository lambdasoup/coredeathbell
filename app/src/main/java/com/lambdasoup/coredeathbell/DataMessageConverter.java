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

		float s1d;
		if (data.containsKey("s1d")) {
			try {
				s1d = Float.parseFloat(data.get("s1d"));
			} catch (NumberFormatException e) {
				throw new ConverterException("NonCoreBlockMinedEvent: not a float value for 's1d': " + data.get("s1d"));
			}
		} else {
			throw new ConverterException("NonCoreBlockMinedEvent: missing key 's1d'");
		}

		float s7d;
		if (data.containsKey("s1d")) {
			try {
				s7d = Float.parseFloat(data.get("s7d"));
			} catch (NumberFormatException e) {
				throw new ConverterException("NonCoreBlockMinedEvent: not a float value for 's7d': " + data.get("s7d"));
			}
		} else {
			throw new ConverterException("NonCoreBlockMinedEvent: missing key 's7d'");
		}


		return new NonCoreBlockMinedEvent(when, s1d, s7d);
	}


    public static class ConverterException extends RuntimeException {
        public ConverterException(String msg) {
            super(msg);
        }
    }
}
