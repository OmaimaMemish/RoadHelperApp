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
        database.add(new StringPair("car_repair" ,"repair shop"));       database.add(new StringPair("car_repair" ,"work shop"));
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


        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

        findViewById(R.id.show_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapsActivity.this,HomeActivity.class));
            }
        });


//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
    }
    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
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
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    searchByVoice(result.get(0));
                }
                break;
            }

        }
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



    public void searchByVoice(String adderess){
        Toast.makeText(this, "Locating " + adderess, Toast.LENGTH_SHORT).show();
        searchVoice(adderess);
      //  Geocoder geoCoder = new Geocoder(this, Locale.getDefault());

//            mMap.clear();
//               if(lmr!=null) {
//                   List<Address> addresses = geoCoder.getFromLocationName(adderess, 5);
//                   if (addresses.size() > 0)
//                   {
//                       Double lat = (double) (addresses.get(0).getLatitude());
//                       Double lon = (double) (addresses.get(0).getLongitude());
//
//                       Log.d("lat-long", "" + lat + "......." + lon);
//                       final LatLng user = new LatLng(lat, lon);
//
//
//                       Location target = new Location(lmr.getProvider());
//                       target.setLongitude(lon);
//                       target.setLatitude(lat);
//
//                       if(lmr.distanceTo(target)<DISTANCE){
//
//                           mMap.addMarker(new MarkerOptions()
//                                   .position(user)
//                                   .title(adderess));
//                       }else{
//                           Toast.makeText(this, "No nearby places found !", Toast.LENGTH_SHORT).show();
//                       }
//
//                       // Move the camera instantly to hamburg with a zoom of 15.
//                      // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user, 15));
//                       // Zoom in, animating the camera.
//                   //    mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
//                   LatLng sydney = new LatLng(lmr.getLatitude(), lmr.getLongitude());
//
//                       CircleOptions circleOptions = new CircleOptions()
//                               .center(sydney)
//                               .radius(DISTANCE)
//                               .strokeWidth(2)
//                               .strokeColor(Color.BLUE)
//                               .fillColor(Color.parseColor("#500084d3"));
//                       mMap.addCircle(circleOptions);
//                   mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Saudi  Arabia"));
//                   //
//                   CameraPosition cam = new CameraPosition.Builder().target(sydney)
//                           .zoom(10)
//                           .build();
//                   mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cam));
//               }
//                   else{
//                       Toast.makeText(this, "No results found for " + adderess, Toast.LENGTH_SHORT).show();
//                   }
//
//            }else{
//                   Toast.makeText(this, "your location is unknown yet ! try again please", Toast.LENGTH_SHORT).show();
//               }

    }




//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case 1: {
//
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    init();
//
//                } else {
//
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                    Toast.makeText(MapsActivity.this, "لا يمكن المتابعة بدون هذه السماحية", Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//                return;
//            }
//
//            // other 'case' lines to check for other
//            // permissions this app might request
//        }}


//    Location lmr;
//  public void init(){
//
//      Toast.makeText(this, "Preparing map.. ", Toast.LENGTH_SHORT).show();
//findViewById(R.id.my_location).setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        mMap.clear();
//        if(lmr!=null) {
//
//// Move the camera instantly to hamburg with a zoom of 15.
//                // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user, 15));
//                // Zoom in, animating the camera.
//                //    mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
//                LatLng sydney = new LatLng(lmr.getLatitude(), lmr.getLongitude());
//
//                CircleOptions circleOptions = new CircleOptions()
//                        .center(sydney)
//                        .radius(DISTANCE)
//                        .strokeWidth(2)
//                        .strokeColor(Color.BLUE)
//                        .fillColor(Color.parseColor("#500084d3"));
//                mMap.addCircle(circleOptions);
//                mMap.addMarker(new MarkerOptions().position(sydney).title("Me"));
//                //
//                CameraPosition cam = new CameraPosition.Builder().target(sydney)
//                        .zoom(10)
//                        .build();
//                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cam));
//    }else
//            Toast.makeText(MapsActivity.this, "your location is unkown yet , try later..", Toast.LENGTH_SHORT).show();
//
//    }
//});
//
//
//
//      String permission = Manifest.permission.ACCESS_COARSE_LOCATION;
//      int res = getApplicationContext().checkCallingOrSelfPermission(permission);
//      boolean b1= (res == PackageManager.PERMISSION_GRANTED);
//
//      LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//      lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, new LocationListener() {
//          @Override
//          public void onLocationChanged(Location location) {
//              lmr = location;
//              // TODO Auto-generated method stub
//
//          }
//          @Override
//          public void onProviderDisabled(String provider) {
//              // TODO Auto-generated method stub
//          }
//          @Override
//          public void onProviderEnabled(String provider) {
//              // TODO Auto-generated method stub
//          }
//          @Override
//          public void onStatusChanged(String provider, int status,
//                                      Bundle extras) {
//              // TODO Auto-generated method stub
//          }
//      });
//
//      // Add a marker in Sydney and move the camera
//
//
//
//
//
//
//
//
//
////
////      CameraPosition cam  = new CameraPosition.Builder().target(sydney)
////              .zoom(10)
////              .bearing(90)
////              .tilt(40)
////              .build();
////      mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cam));
//
//  }
//
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
    @Override
    public void onMapReady(GoogleMap googleMap) {



        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(120000); // two minute interval
        mLocationRequest.setFastestInterval(120000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        mGoogleMap = googleMap;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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

