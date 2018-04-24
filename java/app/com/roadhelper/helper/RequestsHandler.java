package app.com.roadhelper.helper;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.HashMap;

import app.com.roadhelper.MapsActivity;
import app.com.roadhelper.SignupActivity;

/**
 * Created by HP on 4/12/2018.
 */

public class RequestsHandler {



   private  static RequestsHandler handler;
    public static RequestsHandler getInstance(Activity c){
     if(handler==null)handler = new RequestsHandler(c);
        return handler;
    }

    JSONObject answer;



    private  void connect(final Activity a ,final String text ,final String param , final String cost
            , final String lat, final String lng) {
        answer=null;
        class JsonOpener extends AsyncTask<String, Void, String> {
            ProgressDialog loading = new ProgressDialog(a);
            boolean cont =true;
            @Override
            protected void onPreExecute() {

                loading.setTitle("Sending request..");
                loading.show();;
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    if (mFusedLocationClient != null) {
                        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
                    }
                    loading.dismiss();
                    String syn = answer.getString("status");
                    if(syn.equals("error")){
                        Toast.makeText(a, "failed to send request", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(a, answer.getString("body"), Toast.LENGTH_SHORT).show();
a.finish();
                    }}catch(Exception e){
                    Log.e("FUSE 1",e.getMessage());

                }
            }

            @Override
            protected String doInBackground(String... params) {
                //  BufferedReader bufferedReader = null;
                try {
                    HashMap<String, String> pars = new HashMap<String, String>();
                    pars.put("service" , "req");
                    pars.put("text" , text);
                    pars.put("cost" , cost);
                    pars.put("lat" , lat);
                    pars.put("lng" , lng);
                    pars.put("user_id" , SharedPrefManager.getInstance(a).getUser().getUser_id());
                    pars.put("params" , param);
                    Log.d("PARAMS : "  , pars.toString());
                    String result =
                            ConnectionUtils.sendPostRequest(APIUrl.SERVER, pars);


                    answer = new JSONObject(result);
                } catch (Exception e) {
                    System.out.println("sending req error : " + e.getMessage());
                }

                return "Ready";


            }


        }
        JsonOpener ru = new JsonOpener();
        ru.execute();
    }

    String lng=null ,lat=null;

    LocationCallback mLocationCallback = new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                mLastLocation = location;
                 lat = location.getLatitude()+"";
                lng=location.getLongitude()+"";
                //Place current location marker
            }
        };

    };
    LocationRequest mLocationRequest;
    Location mLastLocation;
    FusedLocationProviderClient mFusedLocationClient;

    public RequestsHandler(Activity c) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(120000); // two minute interval
        mLocationRequest.setFastestInterval(120000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(c);

        if (ContextCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        }else Toast.makeText(c, "You dont have location permissions !", Toast.LENGTH_SHORT).show();
    }

    private void go(final Activity c , final String text , final String params  , final String cost , final int t){
        Log.d("QQD1  : " , "ok");

        if (lat == null || lng == null) {
            Log.d("QQD2  : " , "ok");
        Toast.makeText(c, "location is not ready yet , try again after a while..", Toast.LENGTH_SHORT).show();
    } else {
            Log.d("QQD3  : " , "ok");
            connect(c, text, params, cost, lat, lng);
        }

}

    public  void sendRequest(final Activity c , final String text , final String params  , final String cost ,final int t){

            new AlertDialog.Builder(c)
                    .setTitle("Order request confirmation")
                    .setMessage("Are you sure you want to send (" + text + ") request ?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            try {
                                go(c, text, params, cost, 0);
                            }catch (Exception rr){
                                Log.e("RRQ" , rr.getMessage());

                            }
     }
                    })
                    .setNegativeButton(android.R.string.no, null).show();




}

}
