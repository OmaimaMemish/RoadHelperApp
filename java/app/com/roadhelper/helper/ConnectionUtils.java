package app.com.roadhelper.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;



import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by HP on 2/11/2018.
 */

public class ConnectionUtils {

    public static  boolean isNetworkAvailable(Context a) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) a.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String sendPostRequest(String requestURL, // send req to server
                                  HashMap<String, String> params) {

        String response = "";
        OkHttpClient client = new OkHttpClient();
//        client.interceptors().add(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//
//                // try the request
//                Response response = chain.proceed(request);
//
//                int tryCount = 0;
//                int maxLimit = 3; //Set your max limit here
//
//                while (!response.isSuccessful() && tryCount < maxLimit) {
//
//                    Log.d("intercept", "Request failed - " + tryCount);
//
//                    tryCount++;
//
//                    // retry the request
//                    response = chain.proceed(request);
//                }
//
//                // otherwise just pass the original response on
//                return response;
           // }//
       // });
        MultipartBody.Builder mb =  new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        for (Map.Entry<String, String> entry : params.entrySet()) {

            mb.addFormDataPart(entry.getKey(), entry.getValue());
        }
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(requestURL)
                  .post(mb.build())
                .build();
        okhttp3.Response responses = null;

        try {
            responses = client.newCall(request).execute();
        } catch (IOException e) {

          Log.d("RET ERR "  , e.getMessage());
        }
        try {
            response = responses.body().string();
            Log.d("OKHTTP3 : "  ,response);

        }catch (IOException e){    Log.d("RET2 ERR "  , e.getMessage());}
   return response;
    }

}
