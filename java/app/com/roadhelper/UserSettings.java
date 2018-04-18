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
            protected void onPreExecute() {

                loading.setTitle("Updating account..");
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
                        Toast.makeText(UserSettings.this, "failed to update", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(UserSettings.this,  answer.getString("body") + "\r\n Login to continue..",
                                Toast.LENGTH_SHORT).show();

                        finish();


                    }}catch(Exception e){}
            }

            @Override
            protected String doInBackground(String... params) {
                //  BufferedReader bufferedReader = null;
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









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

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
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

    }
}
