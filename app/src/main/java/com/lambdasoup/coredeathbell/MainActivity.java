package com.lambdasoup.coredeathbell;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;

import static com.lambdasoup.coredeathbell.CoreDeathBell.TOPIC_NON_CORE_BLOCK_MINED;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_NON_CORE_BLOCK_MINED);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

	public void onButtonSettingsClicked(View view) {
		startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
	}

	public void onDistributionClicked(View view) {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://coin.dance/blocks#summary"));
		startActivity(intent);
	}

	public void onDonate(View view) {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("bitcoin:1ox81cadiW74fFZw3qvRXhnBYerebUGbN"));

		ResolveInfo resolveInfo = getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
		if (resolveInfo == null) {
			Toast.makeText(this, R.string.no_bitcoin_client, Toast.LENGTH_SHORT).show();
			return;
		}

		startActivity(intent);
	}
}
