package com.example.lostandfoundapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lostandfoundapp.db.DatabaseHelper;
import com.example.lostandfoundapp.model.Item;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        dbHelper = new DatabaseHelper(this);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        ArrayList<Item> items = dbHelper.getAllItems();

        for (Item item : items) {
            double lat = item.getLatitude();
            double lng = item.getLongitude();

            if (lat != 0.0 && lng != 0.0) {
                LatLng location = new LatLng(lat, lng);
                mMap.addMarker(new MarkerOptions()
                        .position(location)
                        .title(item.getName())
                        .snippet(item.getType() + ": " + item.getDescription()));
            }
        }

        if (!items.isEmpty()) {
            // Move camera to the first item
            Item first = items.get(0);
            LatLng firstLocation = new LatLng(first.getLatitude(), first.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLocation, 12f));
        }
    }
}