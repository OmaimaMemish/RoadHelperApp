package app.com.roadhelper;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

import app.com.roadhelper.helper.APIUrl;
import app.com.roadhelper.helper.ConnectionUtils;
import app.com.roadhelper.helper.SharedPrefManager;

public class UserSettings extends RootActivity {

    //2.Second
    public void Edit(final String col ,final String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT );
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              String  m_Text = input.getText().toString();
                connect(col , m_Text);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    JSONObject answer;

    public void connect(final String col , final String val) {
        class JsonOpener extends AsyncTask<String, Void, String> {
            ProgressDialog loading = new ProgressDialog(UserSettings.this);
            boolean cont =true;
            @Override
            //1.
            protected void onPreExecute() {

                loading.setTitle("Updating account..");
                loading.show();;
                super.onPreExecute();

            }

            @Override
            //3.
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    loading.dismiss();
                    String syn = answer.getString("status");
                    if(syn.equals("error")){
                        Toast.makeText(UserSettings.this, "failed to update", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(UserSettings.this,  answer.getString("body") + "\r\n Login to continue..",
                                Toast.LENGTH_SHORT).show();

                        finish();


                    }}catch(Exception e){}
            }

            @Override
            //2.
            protected String doInBackground(String... params) {
                try {
                    HashMap<String, String> pars = new HashMap<String, String>();
                    pars.put("service" , "edit");
                    pars.put("col" , col);
                    pars.put("val" , val);
                    pars.put("user_id" , SharedPrefManager.getInstance(UserSettings.this).getUser().getUser_id());
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

//------------------------------------------------------------------------------------------------
//1.first

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*If you save the state of the application in a bundle (typically non-persistent, dynamic data in onSaveInstanceState),
         it can be passed back to onCreate if the activity needs to be recreated (e.g., orientation change)
        so that you don't lose this prior information. If no data was supplied, savedInstanceState is null.
         */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
/*Android provides many ways of storing data of an application. One of this way is called Shared Preferences.
        It's allow you to save and retrieve data in the form of key,value pair.
        In order to use shared preferences, you have to call a method getSharedPreferences()
        that returns a SharedPreference instance pointing to the file that contains the values of preferences.
*/
        ((TextView)findViewById(R.id.user_name)).setText(SharedPrefManager.getInstance(this).getUser().getName());
        ((TextView)findViewById(R.id.user_email)).setText(SharedPrefManager.getInstance(this).getUser().getEmail());
        ((TextView)findViewById(R.id.user_phone)).setText(SharedPrefManager.getInstance(this).getUser().getPhone());

        ((TextView)findViewById(R.id.user_name)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit("name" , "Enter the new name here : ");
            }
        });

        ((TextView)findViewById(R.id.user_email)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit("email" , "Enter the new email here : ");
            }
        });

        ((TextView)findViewById(R.id.user_phone)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit("phone" , "Enter the new phone here : ");
            }
        });

        ((TextView)findViewById(R.id.user_password)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit("password" , "Enter the new password here : ");
            }
        });
                   findViewById(R.id.go_signout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserSettings.this , LoginActivity.class);
                /*This clears any existing task that would be associated with the Activity before the Activity is started.
                 This Activity then becomes the new root of the task and old Activities are finished. */

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

    }
}
