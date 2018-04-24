package app.com.roadhelper;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;

import android.location.Location;
import android.os.Build;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.CircleOptions;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import app.com.roadhelper.helper.StringPair;

public class MapsActivity extends RootActivity implements OnMapReadyCallback {
public int DISTANCE=20000;
    private ImageButton btnSpeak;

    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    FusedLocationProviderClient mFusedLocationClient;
ArrayList<StringPair> database;


    // "Go to NearbyStuffMap ...
    public void searchVoice(String word){
       word =  word.toLowerCase().trim();
        for(StringPair p : database){
         if(p.word.toLowerCase().trim().equals(word)){
             String key = p.type;
             Intent ii = new Intent(MapsActivity.this ,NearbyStuffMap.class);
             ii.putExtra("type" ,key);
             startActivity(ii);
             break;
         }
        }




        ;
    }


    private final int REQ_CODE_SPEECH_INPUT = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        database = new ArrayList<StringPair>();
        database.add(new StringPair("car_repair" ,"repair shop"));
        database.add(new StringPair("car_repair" ,"work shop"));
        database.add(new StringPair("car_repair" ,"car shop"));
        database.add(new StringPair("car_repair" ,"auto shop"));
        database.add(new StringPair("car_repair" ,"auto repair"));
        database.add(new StringPair("car_repair" ,"car repair"));
        database.add(new StringPair("car_repair" ,"garage"));
        database.add(new StringPair("car_repair" ,"barn"));
        database.add(new StringPair("car_repair" ,"car barn"));
        database.add(new StringPair("car_repair" ,"parking"));
        database.add(new StringPair("car_repair" ,"parking lot"));
        database.add(new StringPair("car_repair" ,"car stall"));
        database.add(new StringPair("car_repair" ,"fix"));
        database.add(new StringPair("car_repair" ,"car fix"));
        database.add(new StringPair("car_repair" ,"car recover"));
        database.add(new StringPair("car_repair" ,"car store"));
        database.add(new StringPair("car_repair" ,"service"));
        database.add(new StringPair("car_repair" ,"service station"));
        //gas sattion words
        database.add(new StringPair("gas_station" ,"station"));
        database.add(new StringPair("gas_station" ,"gas"));
        database.add(new StringPair("gas_station" ,"fuel"));
        database.add(new StringPair("gas_station" ,"gasoline"));
        database.add(new StringPair("gas_station" ,"petrol"));
        database.add(new StringPair("gas_station" ,"oil"));
        database.add(new StringPair("gas_station" ,"diesel"));
        database.add(new StringPair("gas_station" ,"filling"));
        database.add(new StringPair("gas_station" ,"filling station"));
        database.add(new StringPair("gas_station" ,"oil station"));
        database.add(new StringPair("gas_station" ,"petrol station"));
        database.add(new StringPair("gas_station" ,"gasoline station"));
        database.add(new StringPair("gas_station" ,"fuel station"));
        database.add(new StringPair("gas_station" ,"gas station"));
        database.add(new StringPair("gas_station" ,"diesel station"));

        // I want to link this object to the button of voice by it's ID..
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        // When user click the Image button of voice search..
        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);// get fragment by "map" as ID of frag...

        mapFrag.getMapAsync(this);// prepare map into the fragment...

        findViewById(R.id.show_hints).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapsActivity.this,HinstActivity.class));
            }
        });
        findViewById(R.id.show_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapsActivity.this,HomeActivity.class));
            }
        });



    }
    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        // we want the application to record the sound in the language according to the language we specify...
        //1.Starts an activity that will prompt the user for speech and send it through a speech recognizer.
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        //2.It's Inform the recognizer which speech model to prefer when performing
        // and we Considers input in free form English...
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //3.Set the Default language of android in speech recognition...
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        //4. Text prompt to show to the user when asking them to speak...
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
        try {
            //Getting a Result from an Activity
            //"request code" that identifies your request. When you receive the result Intent, the callback
            // provides the same request code so that your app can properly identify the result and determine how to handle it.

            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }




    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            // Check which request it is that we're responding to
            case REQ_CODE_SPEECH_INPUT: {
                // Make sure the request was successful
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    searchByVoice(result.get(0));
                }
                break;
            }

        }
    }

    public void searchByVoice(String adderess){
        Toast.makeText(this, "Locating " + adderess, Toast.LENGTH_SHORT).show();
        searchVoice(adderess);


    }


@Override
public void onPause() {
    super.onPause();

    //stop location updates when Activity is no longer active
    if (mFusedLocationClient != null) {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }
}

    LocationCallback mLocationCallback = new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                mLastLocation = location;
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }

                //Place current location marker
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Current Position");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
                CircleOptions circleOptions = new CircleOptions()
                        .center(latLng)
                        .radius(DISTANCE)
                        .strokeWidth(2)
                        .strokeColor(Color.BLUE)
                        .fillColor(Color.parseColor("#500084d3"));
                mGoogleMap.addCircle(circleOptions);
                //move map camera
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
            }
        };

    };
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



        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(120000); // two minute interval in millisecond
        mLocationRequest.setFastestInterval(120000); // in millisecond
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);// block level accuracy

        mGoogleMap = googleMap;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {// if remove it will give an error
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
        else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());

            mGoogleMap.setMyLocationEnabled(true);
        }
    }


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }

    }

}

