package com.koala.infinitum.android_project.mapSearch;


import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.BoringLayout;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.koala.infinitum.android_project.R;
import com.koala.infinitum.android_project.httpApi.interfaces.ClientCallback;
import com.koala.infinitum.android_project.httpApi.models.Place;
import com.koala.infinitum.android_project.httpApi.models.Responses;
import com.koala.infinitum.android_project.httpApi.services.PlaceService;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import retrofit2.Response;

public class MapSearchActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, GoogleMap.OnCameraIdleListener{

    GoogleMap map;
    Boolean locationPermissionGranted = false;
    final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    Location lastKnownLocation;
    private GoogleApiClient googleApiClient;
    CameraPosition cameraPosition;
    Integer DEFAULT_ZOOM = 8;
    HashMap<String, Marker> markerHashMap;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_search);
        markerHashMap = new HashMap<>();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,
                        this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        googleApiClient.connect();
    }

    private void createMapView() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        Log.d("myLogs", "onMapReady");
        getDeviceLocation();
        setMapUI();

        map.setOnCameraIdleListener(this);
    }

    private void setMapUI() {
        try {
            if (locationPermissionGranted) {
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }

        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        locationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true;
                }
            }
        }
        setMapUI();
    }

    private void getDeviceLocation() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        if (locationPermissionGranted) {
            lastKnownLocation = LocationServices.FusedLocationApi
                    .getLastLocation(googleApiClient);
        }

        // Set the map's camera position to the current location of the device.
        if (cameraPosition != null) {
            map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        } else if (lastKnownLocation != null) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(lastKnownLocation.getLatitude(),
                            lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("myLogs", "onConnectionFailed");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("myLogs", "onConnectionSuspended");
        createMapView();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("myLogs", "onConnectionSuspended");
        createMapView();
    }

    @Override
    public void onCameraIdle() {
        cameraPosition = map.getCameraPosition();
        Double step = calculationByDistance(map.getProjection().getVisibleRegion().latLngBounds.northeast, cameraPosition.target);
        createPlaces(step, cameraPosition.target);
    }

    public double calculationByDistance(LatLng startP, LatLng endP) {
        float[] results = new float[1];
        Location.distanceBetween(startP.latitude, startP.longitude, endP.latitude, endP.longitude, results);
        return results[0];
    }

    private void createPlaces(Double step, LatLng center) {
        new PlaceService().getAroundPlace(center.latitude, center.longitude, 100, 0, step, "", new ClientCallback<Responses<Place>>() {
            @Override
            public void onSuccess(Response<Responses<Place>> response) {
                addPlaces(response.body().getData());
            }

            @Override
            public void onError(String err) {
                Log.d("myLogs", err);
            }
        });
    }

    private void addPlaces(List<Place> list) {
        HashMap<String, Marker> newMarkers = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            Place place = list.get(i);
            List<Double> point = place.getPoint();
            String mKey = place.getId() + "" + point.get(0) + "" + point.get(1);

            Marker marker;
            Marker currMarker = markerHashMap.get(mKey);
            if (currMarker == null) {
                marker = map.addMarker(new MarkerOptions()
                        .position(new LatLng(point.get(0), point.get(1)))
                        .title(place.getTitle()));

            } else {
                marker = currMarker;
            }
            newMarkers.put(mKey, marker);
        }

        for (String key : markerHashMap.keySet()) {
            if (newMarkers.get(key) == null) {
                markerHashMap.get(key).remove();
            }
        }

        markerHashMap.clear();
        markerHashMap.putAll(newMarkers);
    }
}
