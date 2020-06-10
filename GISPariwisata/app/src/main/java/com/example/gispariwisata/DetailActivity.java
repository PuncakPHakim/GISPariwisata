package com.example.gispariwisata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    TextView dtnama, dtjam, dttiket;
    ImageView dtgambar;
    public static String id, nama, jam, tiket, gambar;
    public static Double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        dtnama = findViewById(R.id.txt_nama);
        dtjam = findViewById(R.id.txt_jam);
        dttiket = findViewById(R.id.txt_tiket);
        dtgambar = findViewById(R.id.img_gambar);

        dtnama.setText(nama);
        dtjam.setText(jam);
        dttiket.setText(tiket);
        Picasso.get().load(gambar).into(dtgambar);
    }
}
