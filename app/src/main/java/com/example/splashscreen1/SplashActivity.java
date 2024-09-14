package com.example.splashscreen1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.google.android.material.snackbar.Snackbar;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        checkNetworkConnection();
    }

    private void checkNetworkConnection() {
        // Check network connectivity
        if (isNetworkConnected()) {
            // Proceed to main activity after a delay
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 4000); // Delay for 4 seconds
        } else {
            // Show Snackbar message and handle retry
            Snackbar.make(findViewById(R.id.constraintLayout), "No internet connection available.", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Retry network check on button click
                            if (isNetworkConnected()) {
                                // Network is connected, proceed to main activity
                                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Network is still unavailable, retry until connected
                                Snackbar.make(findViewById(R.id.constraintLayout), "Still no internet. Trying again.", Snackbar.LENGTH_SHORT).show();
                                checkNetworkConnection(); // Recursively check network again
                            }
                        }
                    })
                    .show();
        }
    }

    private boolean isNetworkConnected() {
        // Implement network connectivity check using ConnectivityManager
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
}
}
