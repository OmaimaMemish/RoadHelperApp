package app.com.roadhelper.helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

import app.com.roadhelper.MapsActivity;
import app.com.roadhelper.SignupActivity;

/**
 * Created by Luminance on 4/12/2018.
 */

public class RequestsHandler {



   static JSONObject answer;

    public static void connect(final Activity a ,final String text ,final String param) {
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
                    loading.dismiss();
                    String syn = answer.getString("status");
                    if(syn.equals("error")){
                        Toast.makeText(a, "failed to send request", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(a, answer.getString("body"), Toast.LENGTH_SHORT).show();
a.finish();
                    }}catch(Exception e){}
            }

            @Override
            protected String doInBackground(String... params) {
                //  BufferedReader bufferedReader = null;
                try {
                    HashMap<String, String> pars = new HashMap<String, String>();
                    pars.put("service" , "req");
                    pars.put("text" , text);
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




    public static void sendRequest(final Activity c , final String text , final String params){


    new AlertDialog.Builder(c)
            .setTitle("Order request confirmation")
            .setMessage("Are you sure you want to send (" + text+") request ?")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int whichButton) {
                  connect(c , text ,params);
                }})
            .setNegativeButton(android.R.string.no, null).show();




}

}
