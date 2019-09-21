package com.example.dms.ui.splash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dms.R;
import com.example.dms.SessionManager;
import com.example.dms.ui.MainActivity;
import com.example.dms.ui.login.LoginActivity;

public class SplashScreenActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        final SessionManager session = new SessionManager(SplashScreenActivity.this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (session.isLoggedIn()) {
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
