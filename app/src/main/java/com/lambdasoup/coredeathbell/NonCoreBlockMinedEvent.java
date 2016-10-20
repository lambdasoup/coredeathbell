package com.lambdasoup.coredeathbell;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jl on 01.07.16.
 */
public class NonCoreBlockMinedEvent implements Parcelable {

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
    public final long   when;
    public final double s1d;
    public final double s7d;

    public NonCoreBlockMinedEvent(long when, double s1d, double s7d) {
        this.when = when;
        this.s1d = s1d;
        this.s7d = s7d;
    }

    protected NonCoreBlockMinedEvent(Parcel in) {
        this.when = in.readLong();
        this.s1d = in.readDouble();
        this.s7d = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.when);
        dest.writeDouble(this.s1d);
        dest.writeDouble(this.s7d);
    }
}
