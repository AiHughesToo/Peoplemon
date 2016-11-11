package com.aihughes.peoplemon.Views;

import android.Manifest;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import com.aihughes.peoplemon.MainActivity;

import com.aihughes.peoplemon.Models.Account;
import com.aihughes.peoplemon.Models.User;
import com.aihughes.peoplemon.Network.RestClient;
import com.aihughes.peoplemon.Network.UserStore;
import com.aihughes.peoplemon.PeoplemonApplication;
import com.aihughes.peoplemon.R;
import com.aihughes.peoplemon.Stages.CaughtPeopleListStage;
import com.aihughes.peoplemon.Stages.MapsViewStage;
import com.aihughes.peoplemon.Stages.NearbyPeopleListStage;
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
import butterknife.OnClick;
import flow.Flow;
import flow.History;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.aihughes.peoplemon.PeoplemonApplication.getMainFlow;
import static com.google.android.gms.analytics.internal.zzy.t;

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
        public static Location mLocation;
        public Bitmap myIcon;




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
        RestClient restClient = new RestClient();
        restClient.getApiService().getUserInfo().enqueue(new Callback<Account>() {

            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {

                // Is the server response between 200 to 299
                if (response.isSuccessful()){

                    Account authUser = response.body();
                    mMap.setOnMyLocationChangeListener(myLocationChangeListener);
                    mLocation = new Location("");

                    mLocation.setLatitude(lat);
                    mLocation.setLongitude(lng);
                    checkin();

                    Toast.makeText(context, "Map loaded", Toast.LENGTH_SHORT).show();

                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    }
                    mMap.setMyLocationEnabled(true);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Home,17));

                    if (authUser.getAvatarBase64() == null || authUser.getAvatarBase64().length() <= 100){
                        Toast.makeText(context, "You Need To Set An Avatar", Toast.LENGTH_SHORT).show();

                    }else {

                        String encodedImage = authUser.getAvatarBase64();
                        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        decodedByte = Bitmap.createScaledBitmap(decodedByte, 160, 160, false);
                        myIcon = decodedByte;


                        mMap.addMarker(new MarkerOptions()
                                .position(Home)
                                .title("Me")
                                .icon(BitmapDescriptorFactory.fromBitmap(decodedByte)));

                    }


                    GroundOverlayOptions radar = new GroundOverlayOptions()
                            .image(BitmapDescriptorFactory.fromResource(R.mipmap.radar))
                            .position(Home, 200f, 200f);

                    GroundOverlay imageOverlay = mMap.addGroundOverlay(radar);
                    // this animates a circle for the radar.
                    final Circle circle = mMap.addCircle(new CircleOptions().center(Home)
                            .strokeColor(Color.BLUE).radius(80));

                    ValueAnimator vAnimator = new ValueAnimator();
                    vAnimator.setRepeatCount(ValueAnimator.INFINITE);
                    vAnimator.setRepeatMode(ValueAnimator.RESTART);
                    vAnimator.setIntValues(80, 0);
                    vAnimator.setDuration(2000);
                    vAnimator.setEvaluator(new IntEvaluator());
                    vAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                    vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            float animatedFraction = valueAnimator.getAnimatedFraction();
                            // Log.e("", "" + animatedFraction);
                            circle.setRadius(animatedFraction * 80);
                        }
                    });
                    vAnimator.start();





                }else{

                    Toast.makeText(context,"Get User Info Failed" + ": " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(context,"Get User Info Call Failed", Toast.LENGTH_LONG).show();
            }
        });

        checkin();
        checkNearby();



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
            mLocation = location;
            String pos = Home +"";
// move the camera to the new position
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Home,17));
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());

            if (myIcon == null){
                Toast.makeText(context, "You Need To Set An Avatar", Toast.LENGTH_SHORT).show();

            }else {
                mMap.addMarker(new MarkerOptions()
                        .position(Home)
                        .title("Me")
                        .icon(BitmapDescriptorFactory.fromBitmap(myIcon)));

            }
//       this sets the radar image as an overlay
            GroundOverlayOptions radar = new GroundOverlayOptions()
                    .image(BitmapDescriptorFactory.fromResource(R.mipmap.radar))
                    .position(Home, 200f, 200f);

            GroundOverlay imageOverlay = mMap.addGroundOverlay(radar);

            final Circle circle = mMap.addCircle(new CircleOptions().center(Home)
                    .strokeColor(Color.BLUE).radius(80));
            ValueAnimator vAnimator = new ValueAnimator();

            vAnimator.setRepeatCount(ValueAnimator.INFINITE);
            vAnimator.setRepeatMode(ValueAnimator.RESTART);  /* PULSE */
            vAnimator.setIntValues(80, 0);
            vAnimator.setDuration(2000);
            vAnimator.setEvaluator(new IntEvaluator());
            vAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float animatedFraction = valueAnimator.getAnimatedFraction();
                    circle.setRadius(animatedFraction * 80);
                }
            });
            vAnimator.start();



            checkin();

            // Lets Check in
            checkNearby();

        }
    };

    public void checkin(){

        Account checkin = new Account (lat, lng);
        RestClient restClient = new RestClient();
        restClient.getApiService().checkin(checkin).enqueue(new Callback<Void>() {


            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {



                if (response.isSuccessful()){

                    Toast.makeText(context,"You Have Checked In" + ": " + response.code(), Toast.LENGTH_LONG).show();


                }else{

                    Toast.makeText(context,"Check In Failed" + ": " + response.code(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                Toast.makeText(context,"Check In Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void checkNearby() {
        RestClient restClient = new RestClient();
        restClient.getApiService().findNearby(600).enqueue(new Callback<User[]>() {
            @Override
            public void onResponse(Call<User[]> call, Response<User[]> response) {
                if (response.isSuccessful()) {
                    for (User user : response.body()) {
                        lat = user.getLat();
                        lng = user.getLng();
                        String userId = user.getId();

                        if (user.getAvatarBase64() == null || user.getAvatarBase64().length() <= 100){

                            final LatLng userpos = new LatLng(lat, lng);
                            mMap.addMarker(new MarkerOptions().title(user.getUserName())
                                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.no_pic2))
                                    .snippet(user.getId())
                                    .position(userpos));
                            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){


                                @Override
                                public boolean onMarkerClick(Marker marker) {
                                    //Toast.makeText(context, "You caught " + marker.getSnippet(), Toast.LENGTH_SHORT).show();

                                    Location userLoc = new Location("");
                                    userLoc.setLatitude(marker.getPosition().latitude);
                                    userLoc.setLongitude(marker.getPosition().longitude);
                                    final String CaughtUserId = marker.getSnippet();
                                    final User user = new User(CaughtUserId, mLocation.distanceTo(userLoc));
                                    Double latC = marker.getPosition().latitude;
                                    Double lngC = marker.getPosition().longitude;
                                    LatLng markCircle = new LatLng(latC, lngC);

                                    final Circle circle = mMap.addCircle(new CircleOptions().center(markCircle)
                                            .strokeColor(Color.GREEN).radius(10));
                                    ValueAnimator vAnimator = new ValueAnimator();

                                    vAnimator.setRepeatCount(1);
                                    vAnimator.setRepeatMode(ValueAnimator.REVERSE);  /* PULSE */
                                    vAnimator.setIntValues(10, 0);
                                    vAnimator.setDuration(500);
                                    vAnimator.setEvaluator(new IntEvaluator());
                                    vAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                                    vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                        @Override
                                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                            float animatedFraction = valueAnimator.getAnimatedFraction();
                                            circle.setRadius(animatedFraction * 10);
                                        }
                                    });
                                    vAnimator.start();

                                    RestClient restClient = new RestClient();
                                    restClient.getApiService().catchUser(user).enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {

                                            if (response.isSuccessful()) {

                                                Toast.makeText(context, "Person Caught!", Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(context,"Catch Failed", Toast.LENGTH_LONG).show();

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {
                                            t.printStackTrace();

                                        }
                                    });

                                         marker.remove();
                                    return false;
                                }


                            });
                        }else{
                            try {

                                String encodedImage = user.getAvatarBase64();
                                byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                decodedByte = Bitmap.createScaledBitmap(decodedByte, 120, 120, false);



                            final LatLng userpos = new LatLng(lat, lng);
                            mMap.addMarker(new MarkerOptions().title(user.getUserName())
                                   .icon(BitmapDescriptorFactory.fromBitmap(decodedByte))
                                    .snippet(user.getId())
                                    .position(userpos));

                            }catch (Exception e){

                            }
                            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){


                                @Override
                                public boolean onMarkerClick(Marker marker) {
                                    //Toast.makeText(context, "You caught " + marker.getSnippet(), Toast.LENGTH_SHORT).show();

                                    Location userLoc = new Location("");
                                    userLoc.setLatitude(marker.getPosition().latitude);
                                    userLoc.setLongitude(marker.getPosition().longitude);
                                    final String CaughtUserId = marker.getSnippet();
                                    final User user = new User(CaughtUserId, mLocation.distanceTo(userLoc));
                                    Double latC = marker.getPosition().latitude;
                                    Double lngC = marker.getPosition().longitude;
                                    LatLng markCircle = new LatLng(latC, lngC);

                                    final Circle circle = mMap.addCircle(new CircleOptions().center(markCircle)
                                            .strokeColor(Color.RED).radius(10));
                                    ValueAnimator vAnimator = new ValueAnimator();

                                    vAnimator.setRepeatCount(1);
                                    vAnimator.setRepeatMode(ValueAnimator.REVERSE);  /* PULSE */
                                    vAnimator.setIntValues(10, 0);
                                    vAnimator.setDuration(500);
                                    vAnimator.setEvaluator(new IntEvaluator());
                                    vAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                                    vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                        @Override
                                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                            float animatedFraction = valueAnimator.getAnimatedFraction();
                                            circle.setRadius(animatedFraction * 10);
                                        }
                                    });
                                    vAnimator.start();

                                    RestClient restClient = new RestClient();
                                    restClient.getApiService().catchUser(user).enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {

                                            if (response.isSuccessful()) {

                                                Toast.makeText(context, "Person Caught!", Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(context,"That person is out side your radius", Toast.LENGTH_LONG).show();

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {
                                            t.printStackTrace();

                                        }
                                    });

                                       marker.remove();
                                    return false;
                                }


                            });

                        }



                    }
                } else {
                    Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User[]> call, Throwable t) {
            }
        });
    }


    @OnClick(R.id.view_caught_button)
    public void showAddCategoryView(){


        Flow flow = PeoplemonApplication.getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new CaughtPeopleListStage())
                .build();
        flow.setHistory(newHistory, Flow.Direction.FORWARD);

    }
    @OnClick(R.id.nearby_Button)
    public void showNearby(){


        Flow flow = PeoplemonApplication.getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new NearbyPeopleListStage())
                .build();
        flow.setHistory(newHistory, Flow.Direction.FORWARD);

    }




}



