package com.example.gispariwisata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView ivmap = findViewById(R.id.ivmap);
        ivmap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent pindahpeta = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(pindahpeta);
    }
}
