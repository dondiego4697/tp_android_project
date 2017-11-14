package com.koala.infinitum.android_project.mapSearch;


import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.koala.infinitum.android_project.R;
import com.koala.infinitum.android_project.Singletons.CategorySingleton;
import com.koala.infinitum.android_project.httpApi.interfaces.ClientCallback;
import com.koala.infinitum.android_project.httpApi.models.Place;
import com.koala.infinitum.android_project.httpApi.models.Responses;
import com.koala.infinitum.android_project.httpApi.services.PlaceService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Response;

public class MapSearchActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, GoogleMap.OnCameraIdleListener,
        GoogleMap.OnMapClickListener, View.OnClickListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMarkerDragListener{

    private GoogleMap map;
    private GoogleApiClient googleApiClient;
    private CameraPosition cameraPosition;

    HashMap<String, Marker> markersHashMap;
    HashMap<String, String> categories;

    Boolean locationPermissionGranted = false;
    final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    Location lastKnownLocation;

    Integer DEFAULT_ZOOM = 8;
    String NEW_MARKER = "marker";
    String currentCategory = null;

    FloatingActionButton fabAddPlace;
    Marker newPlaceMarker = null;
    double[] newPlaceMarkerPoint = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_search);
        getCategories();
        markersHashMap = new HashMap<>();
        clearMap();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fabAddPlace = (FloatingActionButton) findViewById(R.id.fabAddPlace);
        fabAddPlace.setOnClickListener(this);

        if (googleApiClient == null) {
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
    }

    private void setVisibilityFabAddPlace(Boolean state) {
        fabAddPlace.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    private void setMapInContainer() {
        MapFragment mapFragment = MapFragment.newInstance();
        android.app.FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.container, mapFragment);
        transaction.commit();
        mapFragment.getMapAsync(this);
    }

    //TODO АНДРЕЙ ВОТ В ТАКИХ ФУНКЦИЯ МЕНЯЙ
    private void setSMTHINCONTAINER() {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        setMapInContainer();
    }

    @Override
    public void onConnectionSuspended(int i) {
        setMapInContainer();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}

    @Override
    public void onCameraIdle() {
        updateCameraPosition();
        getPlacesInArea(getStep(), cameraPosition.target);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (newPlaceMarker != null) {
            newPlaceMarker.remove();
        }
        newPlaceMarker = map.addMarker(new MarkerOptions().position(latLng).draggable(true).title(getString(R.string.dot_new_place)));
        newPlaceMarkerPoint = new double[]{latLng.latitude, latLng.longitude};
        setVisibilityFabAddPlace(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        getDeviceLocation();
        setMapUI();

        map.setOnCameraIdleListener(this);
        map.setOnMapClickListener(this);

        updateCameraPosition();
        map.setOnMarkerClickListener(this);
        getPlacesInArea(getStep(), cameraPosition.target);

        setVisibilityFabAddPlace(false);
        if (newPlaceMarkerPoint != null) {
            onMapClick(new LatLng(newPlaceMarkerPoint[0], newPlaceMarkerPoint[1]));
        }
    }

    private double getStep() {
        double step = calculationByDistance(map.getProjection().getVisibleRegion().latLngBounds.northeast, cameraPosition.target);
        step = step + step / 4;
        return step;
    }

    public void updateCameraPosition() {
        cameraPosition = map.getCameraPosition();
    }

    public double calculationByDistance(LatLng startP, LatLng endP) {
        float[] results = new float[1];
        Location.distanceBetween(startP.latitude, startP.longitude, endP.latitude, endP.longitude, results);
        return results[0];
    }

    private void clearMap() {
        for(Marker marker : markersHashMap.values()) {
            marker.remove();
        }
        markersHashMap.clear();
        categories.put(getResources().getString(R.string.all), null);
    }

    private void getPlacesInArea(Double step, LatLng center) {
        ClientCallback<Responses<Place>> callback = new PlaceService().getAroundPlace(center.latitude, center.longitude, 100, 0, step, currentCategory,
                new ClientCallback<Responses<Place>>() {
                    @Override
                    public void onSuccess(Response<Responses<Place>> response) {
                        updatePlacesOnMap(response.body().getData());
                    }

                    @Override
                    public void onError(String err) {
                        Log.d("myLogs", err);
                    }
                });
    }

    private void updatePlacesOnMap(List<Place> list) {
        HashMap<String, Marker> newMarkers = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            Place place = list.get(i);
            List<Double> point = place.getPoint();
            String mKey = place.getId() + "" + point.get(0) + "" + point.get(1);

            Marker marker;
            Marker currMarker = markersHashMap.get(mKey);
            if (currMarker == null) {
                marker = map.addMarker(new MarkerOptions()
                        .position(new LatLng(point.get(0), point.get(1)))
                        .title(place.getTitle())
                        .draggable(false)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                marker.setTag(place);
            } else {
                marker = currMarker;
            }
            newMarkers.put(mKey, marker);
        }

        for (String key : markersHashMap.keySet()) {
            if (newMarkers.get(key) == null) {
                markersHashMap.get(key).remove();
            }
        }

        markersHashMap.clear();
        markersHashMap.putAll(newMarkers);
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
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }

        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(true);
    }

    private void getDeviceLocation() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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

        if (cameraPosition != null) {
            map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        } else if (lastKnownLocation != null) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(lastKnownLocation.getLatitude(),
                            lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
        }
    }

    private void getCategories() {
        categories = CategorySingleton.getInstance().getCategories();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_filter: {
                if (categories.size() < 0) break;

                final ArrayList<String> categoryNames = new ArrayList<>();
                categoryNames.addAll(categories.keySet());
                CharSequence[] cs = categoryNames.toArray(new CharSequence[categories.size()]);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getResources().getString(R.string.title_category_list))
                        .setItems(cs, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String newCategory = categories.get(categoryNames.get(which));
                                if (Objects.equals(currentCategory, newCategory)) return;
                                currentCategory = newCategory;
                                updateCameraPosition();
                                clearMap();
                                getPlacesInArea(getStep(), cameraPosition.target);
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            }
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fabAddPlace: {
                //TODO Андрей, здесь можно вызвать функцию для вставки фрагмента
                break;
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Place place = (Place) marker.getTag();
        if (place != null) {
            //Toast.makeText(this, "open Place", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}
