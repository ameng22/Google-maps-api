package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MapsActivity extends AppCompatActivity {

    private static final String TAG = "MapsActivity";
    private static final String FINE_LOCATION = "Manifest.permission.ACCESS_FINE_LOCATION";
    private static final String COARSE_LOCATION = "Manifest.permission.ACCESS_COARSE_LOCATION";
    private boolean mLocationPermisssionGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        getLocationpermissions();
    }

    private void initMap(){
        Log.d(TAG,"Initializing Maps");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                Toast.makeText(MapsActivity.this, "Map is Ready", Toast.LENGTH_SHORT).show();
                Log.d(TAG,"onMapReady: Map is Ready");
                mMap = googleMap;
            }
        });
    }

    private void getLocationpermissions(){

        Log.d(TAG,"getLocationPermission: Getting Permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                mLocationPermisssionGranted = true;
            }
            else{
                ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG,"onRequestPermissionsResult: Called.");
        mLocationPermisssionGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.GET_PERMISSIONS) {
                            mLocationPermisssionGranted = false;
                            Log.d(TAG,"onRequestPermissionsResult: Permissions Denied");
                            return;
                        }
                    }
                    mLocationPermisssionGranted = true;
                    Log.d(TAG,"onRequestPermissionsResult: Permissions Granted");

                    initMap();
                }
            }
        }
    }
}