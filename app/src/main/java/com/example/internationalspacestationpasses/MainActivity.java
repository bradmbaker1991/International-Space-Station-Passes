package com.example.internationalspacestationpasses;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_LOCATION = 1;

    private Button btnGetPasses;
    private LocationManager locationManager;
    private ListView listPasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listPasses = (ListView) findViewById(R.id.listView);
        btnGetPasses = (Button) findViewById(R.id.btnGetPasses);

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        // set click function here instead of xml.
        btnGetPasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });
    }

    /**
     * getLocation
     * First check for permission(s) required.
     * If permission granted, create a locationManager and get Latitude and Longitude.
     */
    private void getLocation() {
        // Check if we have permissions at runtime
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); // NETWORK_PROVIDER could also be used

            if (location != null){
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                Toast.makeText(this, "Latitude: " + latitude + "\nLongitude: " + longitude, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Unable to find correct location", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * onRequestPermissionsResult
     * If permission(s) needed to be requested and is given, run getLocation again.
     * @param requestCode int
     * @param permissions String[]
     * @param grantResults int[]
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION:
                getLocation();
                break;
        }
    }
}
