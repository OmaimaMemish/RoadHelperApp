package app.com.roadhelper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.com.roadhelper.helper.APIUrl;
import app.com.roadhelper.helper.ConnectionUtils;
import app.com.roadhelper.helper.SharedPrefManager;

import static android.Manifest.permission.READ_CONTACTS;


 // A login screen that offers login via email/password.

public class LoginActivity extends Activity  {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //butt Login
        ((Button)findViewById(R.id.go_login)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = ((EditText)findViewById(R.id.email)).getText().toString().trim();
                String password = ((EditText)findViewById(R.id.password)).getText().toString().trim();
                if(email.equals("")){
                    Toast.makeText(LoginActivity.this, "please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.equals("")){
                    Toast.makeText(LoginActivity.this, "please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                connect(email ,password);
             //
            }
        });


        findViewById(R.id.go_signup).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this , SignupActivity.class));

            }
        });

     //   findViewById(R.id.go_forget).setOnClickListener(new OnClickListener() {
          //  @Override
           // public void onClick(View v) {
           //     Toast.makeText(LoginActivity.this, "Sending new password to the email..", Toast.LENGTH_SHORT).show();

        /////    }
      //  });



    }


    /**
     * Callback received when a permissions request has been completed.
     */

    public void forget(final String email) {
        class JsonOpener extends AsyncTask<String, Void, String> {
            boolean cont =true;
            @Override
            protected void onPreExecute() {

                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    String syn = answer.getString("status");
                    if(syn.equals("error")){
                        Toast.makeText(LoginActivity.this, "Failed to gain new password", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(LoginActivity.this, "Password reset now !", Toast.LENGTH_SHORT).show();

                    }}catch(Exception e){}
            }

            @Override
            protected String doInBackground(String... params) {
                //  BufferedReader bufferedReader = null;
                try {
                    HashMap<String, String> pars = new HashMap<String, String>();
                    pars.put("service" , "res-password");
                    pars.put("email" , email);
                    String result =
                            ConnectionUtils.sendPostRequest(APIUrl.SERVER, pars);

                    answer = new JSONObject(result);
                } catch (Exception e) {
                    System.out.println("fetching cats error : " + e.getMessage());
                }

                return "Ready";


            }


        }
        JsonOpener ru = new JsonOpener();
        ru.execute();
    }
    JSONObject answer;

    public void connect(final String email , final String password) {
        //JsonOpener: to understand JSON by sending parameters to $service (in backend). (backend) will send response(JSON)
        //AsyncTask: operate new task in new thread with out stopping another tasks

        class JsonOpener extends AsyncTask<String, Void, String> {
            // if we extends AsyncTask, we must implement 3 methods :
            ProgressDialog loading = new ProgressDialog(LoginActivity.this);
            boolean cont =true;
            //before connecting to the server and sending the data to php Show login to inform user (waiting)
            @Override
            protected void onPreExecute() {
                loading.setTitle("signing in");
                loading.show();;
                super.onPreExecute();
            }

            //act with the response (answer)
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    loading.dismiss();//hide
                    String syn = answer.getString("status");// error or success
                    if(syn.equals("error")){
                        Toast.makeText(LoginActivity.this, "Failed to sign in", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(LoginActivity.this, "Logged in succesfully", Toast.LENGTH_SHORT).show();
                        //store signIn data in device. like cookies
                        SharedPrefManager.getInstance(LoginActivity.this).userLogin(answer.getJSONObject("body"), password);
                        // go from "LoginActivity" to "MapsActivity"
                        startActivity(new Intent(LoginActivity.this , MapsActivity.class));
                        finish();
                    }}catch(Exception e){}
            }

            //send parameters to backend (db)
            @Override
            protected String doInBackground(String... params) {
                //  BufferedReader bufferedReader = null;
                try {
                    //Prepare request
                    HashMap<String, String> pars = new HashMap<String, String>();
                    pars.put("service" , "login"); //type of service (we have 4 services in backend)
                    pars.put("email" , email);
                    pars.put("password" , password);
                    //send request
                    String result =
                            ConnectionUtils.sendPostRequest(APIUrl.SERVER, pars);

                    answer = new JSONObject(result); //response
                } catch (Exception e) {
                    System.out.println("fetching cats error : " + e.getMessage());
                }

                return "Ready";// then  :  onPostExecute


            }


        }
        JsonOpener ru = new JsonOpener();
        ru.execute();
    }


}

