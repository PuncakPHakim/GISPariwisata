package com.example.gispariwisata;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    private final int panjang_splash = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent tampil = new Intent(SplashScreen.this, MainActivity.class);
                SplashScreen.this.startActivity(tampil);
                SplashScreen.this.finish();
            }
        }, panjang_splash);
    }
}
