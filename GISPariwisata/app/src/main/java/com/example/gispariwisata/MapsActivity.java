package com.example.gispariwisata;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String[] nama, jam_buka, tiket_masuk, gambar, id;
    int jum_data;
    private Double[] latitude, longitude;
    Boolean MarkerD[];
    LatLng latLng[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void getlokasi(){
        String url = "https://gispariwisata.000webhostapp.com/gispariwisata.php";
        JsonArrayRequest request = new JsonArrayRequest
                (Request.Method.GET, url, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        jum_data = response.length();
                        Log.d("DEBUG", "PARSE JSON");
                        latLng = new LatLng[jum_data];
                        MarkerD = new Boolean[jum_data];
                        nama = new String[jum_data];
                        jam_buka = new String[jum_data];
                        tiket_masuk = new String[jum_data];
                        gambar = new String[jum_data];
                        id = new String[jum_data];
                        latitude = new Double[jum_data];
                        longitude = new Double[jum_data];

                        for (int i = 0; i < jum_data; i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                id[i] = data.getString("id");
                                latLng[i] = new LatLng(data.getDouble("latitude"), data.getDouble("longitude"));
                                nama[i] = data.getString("nama");
                                jam_buka[i] = data.getString("jam buka");
                                tiket_masuk[i] = data.getString("tiket masuk");
                                gambar[i] = data.getString("gambar");
                                id[i] = data.getString("id");
                                latitude[i] = data.getDouble("latitude");
                                longitude[i] = data.getDouble("longitude");
                                MarkerD[i] = false;
                                mMap.addMarker(new MarkerOptions()
                                        .position(latLng[i])
                                        .snippet(jam_buka[i])
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.maps))
                                        .title(nama[i]));
                            } catch (JSONException je) {

                            }
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng[i], 15.5f));
                        }

                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                for (int i=0;i<jum_data;i++){
                                    if (MarkerD[i]){
                                        DetailActivity.id = id[i];
                                        DetailActivity.nama = nama;
                                        DetailActivity.jam = jam_buka;
                                        DetailActivity.tiket = tiket_masuk;
                                        DetailActivity.gambar = gambar;
                                        DetailActivity.latitude = latitude;
                                        DetailActivity.longitude = longitude;
                                        Intent pindahdetail = new Intent(MapsActivity.this,DetailActivity.class);
                                        startActivity(pindahdetail);
                                        MarkerD[i]=false;
                                    }else {
                                        MarkerD[i] = true;
                                        marker.showInfoWindow();
                                        Toast pesan = Toast.makeText(MapsActivity.this, "Silahkan klik sekali lagi untuk detail", Toast.LENGTH_LONG);
                                        TextView txt = pesan.getView().findViewById(R.id.message);
                                        if (txt = null)
                                            txt.setGravity(Gravity.CENTER);
                                        pesan.show();
                                    }else {
                                        MarkerD[i]=false;
                                    }
                                }
                            return false;
                            }
                        });

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                        builder.setTitle("error");
                        builder.setMessage("connection failed");
                        builder.setIcon(android.R.drawable.ic_dialog_alert);
                        builder.setPositiveButton("Reload", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getlokasi();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
        Volley.newRequestQueue(this).add(request);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getlokasi();
        mMap.getUiSettings().setZoomControlsEnabled(true);
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
