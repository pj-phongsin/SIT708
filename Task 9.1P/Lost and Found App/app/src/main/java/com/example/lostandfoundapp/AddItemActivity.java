package com.example.lostandfoundapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.lostandfoundapp.db.DatabaseHelper;
import com.example.lostandfoundapp.model.Item;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.gms.common.api.Status;

import java.util.Arrays;
import java.util.List;

public class AddItemActivity extends AppCompatActivity {

    private RadioGroup radioGroupType;
    private RadioButton radioLost, radioFound;
    private EditText inputName, inputPhone, inputDescription, inputDate, inputLocation;
    private Button saveButton, btnGetCurrentLocation;
    private DatabaseHelper dbHelper;

    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    private FusedLocationProviderClient fusedLocationClient;
    private double selectedLatitude = 0.0;
    private double selectedLongitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Initialize Places API
        if (!Places.isInitialized()) {
            //Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
            Places.initialize(getApplicationContext(), "put_api_key_here");
        }

        radioGroupType = findViewById(R.id.radioGroupType);
        radioLost = findViewById(R.id.radioLost);
        radioFound = findViewById(R.id.radioFound);

        inputName = findViewById(R.id.inputName);
        inputPhone = findViewById(R.id.inputPhone);
        inputDescription = findViewById(R.id.inputDescription);
        inputDate = findViewById(R.id.inputDate);
        inputLocation = findViewById(R.id.inputLocation);
        btnGetCurrentLocation = findViewById(R.id.btnGetCurrentLocation);
        saveButton = findViewById(R.id.saveButton);

        dbHelper = new DatabaseHelper(this);

        inputLocation.setOnClickListener(v -> {
            try {
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(this);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
                Toast.makeText(this, "Launching Autocomplete...", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Error launching autocomplete: " + e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });

        btnGetCurrentLocation.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
                return;
            }

            fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    selectedLatitude = location.getLatitude();
                    selectedLongitude = location.getLongitude();
                    inputLocation.setText("Current Location");
                } else {
                    Toast.makeText(this, "Unable to get location", Toast.LENGTH_SHORT).show();
                }
            });
        });

        saveButton.setOnClickListener(v -> {
            int selectedId = radioGroupType.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(AddItemActivity.this, "Please select post type", Toast.LENGTH_SHORT).show();
                return;
            }

            String type = (selectedId == R.id.radioLost) ? "Lost" : "Found";
            String name = inputName.getText().toString();
            String phone = inputPhone.getText().toString();
            String description = inputDescription.getText().toString();
            String date = inputDate.getText().toString();
            String location = inputLocation.getText().toString();

            if (name.isEmpty() || phone.isEmpty() || description.isEmpty()
                    || date.isEmpty() || location.isEmpty()) {
                Toast.makeText(AddItemActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Item item = new Item(type, name, phone, description, date, location);
            item.setLatitude(selectedLatitude);
            item.setLongitude(selectedLongitude);

            dbHelper.insertItem(item);
            Toast.makeText(AddItemActivity.this, "Advert saved!", Toast.LENGTH_SHORT).show();
            finish(); // go back to main
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                if (place.getLatLng() != null) {
                    selectedLatitude = place.getLatLng().latitude;
                    selectedLongitude = place.getLatLng().longitude;
                    inputLocation.setText(place.getName());
                }
            } else if (resultCode == RESULT_CANCELED && data != null) {
                Status status = Autocomplete.getStatusFromIntent(data);
                if (status != null && status.getStatusMessage() != null) {
                    Toast.makeText(this, "Error: " + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Autocomplete cancelled or error occurred.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}