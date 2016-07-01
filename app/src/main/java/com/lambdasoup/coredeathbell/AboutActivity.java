package com.lambdasoup.coredeathbell;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.common.GoogleApiAvailability;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //noinspection ConstantConditions
        ((TextView) findViewById(R.id.text_play_services_license_text)).setText(GoogleApiAvailability.getInstance().getOpenSourceSoftwareLicenseInfo(this));
    }
}
