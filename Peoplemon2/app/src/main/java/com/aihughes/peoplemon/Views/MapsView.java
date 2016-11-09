package com.aihughes.peoplemon.Views;

import android.Manifest;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import com.aihughes.peoplemon.MainActivity;

import com.aihughes.peoplemon.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.Marker;

import android.widget.Toast;

//import com.google.android.gms.identity.intents.Address;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alexhughes on 11/7/16.
 */



public class MapsView extends RelativeLayout implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
    GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationButtonClickListener {

        private GoogleMap mMap;
        private Context context;
        private double lat = 37.816380;
        private double lng = -82.809195;



        @Bind(R.id.map)
        MapView mapView;


    LatLng Home = new LatLng(lat, lng);
    Place currentPlace =null;


        public MapsView(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.context = context;


        }


        @Override
    protected void onFinishInflate() {


        ButterKnife.bind(this);
        super.onFinishInflate();
        mapView.getMapAsync(this);


            mapView.onCreate(((MainActivity) getContext()).savedInstanceState);
            mapView.onResume();



    }


    private void getMapAsync(MapsView mapsView) {
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMyLocationChangeListener(myLocationChangeListener);


        Toast.makeText(context, "Map loaded", Toast.LENGTH_SHORT).show();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Home,16));

        mMap.setMyLocationEnabled(true);
        mMap.addMarker(new MarkerOptions()
                .position(Home)
                .title("Marker in DR")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
                .snippet("#EFAImpact")
                .draggable(true));


// this allows us to add and image to the map.
        GroundOverlayOptions radar = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.mipmap.radar))
                .position(Home, 500f, 500f);

        GroundOverlay imageOverlay = mMap.addGroundOverlay(radar);
        // this animates a circle for the radar.
        final Circle circle = mMap.addCircle(new CircleOptions().center(Home)
                .strokeColor(Color.BLUE).radius(150));

        ValueAnimator vAnimator = new ValueAnimator();
        vAnimator.setRepeatCount(ValueAnimator.INFINITE);
        vAnimator.setRepeatMode(ValueAnimator.RESTART);  /* PULSE */
        vAnimator.setIntValues(150, 0);
        vAnimator.setDuration(2000);
        vAnimator.setEvaluator(new IntEvaluator());
        vAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedFraction = valueAnimator.getAnimatedFraction();
                // Log.e("", "" + animatedFraction);
                circle.setRadius(animatedFraction * 150);
            }
        });
        vAnimator.start();



    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        LatLng current = new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude());
        mMap.addMarker(new MarkerOptions().position(current).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
        return true;
    }


    public void onLocationChanged(Location location) {
        lng = location.getLatitude();
        lat = location.getLongitude();


    }
    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            mMap.clear();
            lat = location.getLatitude();
            lng = location.getLongitude();
            Home = new LatLng(lat, lng);
            String pos = Home +"";
            Log.d("****", pos );
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Home,16));
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions()
                    .position(Home)
                    .title("Marker in DR")
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
                    .snippet("#EFAImpact")
                    .draggable(true));

            GroundOverlayOptions radar = new GroundOverlayOptions()
                    .image(BitmapDescriptorFactory.fromResource(R.mipmap.radar))
                    .position(Home, 500f, 500f);

            GroundOverlay imageOverlay = mMap.addGroundOverlay(radar);


        }
    };
}


