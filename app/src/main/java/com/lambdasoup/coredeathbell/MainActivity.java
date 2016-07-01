package com.lambdasoup.coredeathbell;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ResolvingResultCallbacks;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import static com.lambdasoup.coredeathbell.CoreDeathBell.TOPIC_NON_CORE_BLOCK_MINED;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = MainActivity.class.getSimpleName();

	// TODO: About Activity

	// TODO: main activity layout, donate button, current status button
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
