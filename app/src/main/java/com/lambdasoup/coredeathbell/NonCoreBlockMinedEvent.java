package com.lambdasoup.coredeathbell;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jl on 01.07.16.
 */
public class NonCoreBlockMinedEvent implements Parcelable {
    public final long when;

    public NonCoreBlockMinedEvent(long when) {
        this.when = when;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.when);
    }

    protected NonCoreBlockMinedEvent(Parcel in) {
        this.when = in.readLong();
    }

    public static final Parcelable.Creator<NonCoreBlockMinedEvent> CREATOR = new Parcelable.Creator<NonCoreBlockMinedEvent>() {
        @Override
        public NonCoreBlockMinedEvent createFromParcel(Parcel source) {
            return new NonCoreBlockMinedEvent(source);
        }

        @Override
        public NonCoreBlockMinedEvent[] newArray(int size) {
            return new NonCoreBlockMinedEvent[size];
        }
    };
}
