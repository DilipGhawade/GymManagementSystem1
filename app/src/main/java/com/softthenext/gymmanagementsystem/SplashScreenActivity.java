package com.softthenext.gymmanagementsystem;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.softthenext.gymmanagementsystem.Util.Utils;

public class SplashScreenActivity extends AppCompatActivity {
    private static final int Splach_TimeOut = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Utils.isConnectedToInterner(SplashScreenActivity.this)) {
                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Intent i = new Intent(SplashScreenActivity.this, ActivityConnectionFailed.class);
                    startActivity(i);
                    finish();
                }
            }
        },Splach_TimeOut);
    }
}
