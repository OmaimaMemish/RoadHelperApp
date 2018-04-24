package app.com.roadhelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

import app.com.roadhelper.helper.APIUrl;
import app.com.roadhelper.helper.ConnectionUtils;
import app.com.roadhelper.helper.SharedPrefManager;

public class SignupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Button mEmailSignInButton = (Button) findViewById(R.id.go_signup);
//        findViewById(R.id.go_login).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SignupActivity.this , LoginActivity.class));
//                finish();
//            }
//        });
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  startActivity(new Intent(LoginActivity.this , MainActivity.class));
                //  attemptLogin();

                String email = ((EditText)findViewById(R.id.email)).getText().toString().trim();
                String password = ((EditText)findViewById(R.id.password)).getText().toString().trim();
                String name = ((EditText)findViewById(R.id.name)).getText().toString().trim();
                String phone = ((EditText)findViewById(R.id.phone)).getText().toString().trim();

                if(email.equals("")){
                    Toast.makeText(SignupActivity.this, "please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.equals("")){
                    Toast.makeText(SignupActivity.this, "please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(name.equals("")){
                    Toast.makeText(SignupActivity.this, "please enter name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(phone.equals("")){
                    Toast.makeText(SignupActivity.this, "please enter phone", Toast.LENGTH_SHORT).show();
                    return;
                }
                connect(email ,password , name , phone);

            }
        });

    }




    JSONObject answer;

    public void connect(final String email , final String password ,final String name , final String phone
           ) {
        class JsonOpener extends AsyncTask<String, Void, String> {
            ProgressDialog loading = new ProgressDialog(SignupActivity.this);
            boolean cont =true;
            @Override
            protected void onPreExecute() {

                loading.setTitle("Creating account..");
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
                        Toast.makeText(SignupActivity.this, "Email already exists !", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(SignupActivity.this, "logged in succesfully", Toast.LENGTH_SHORT).show();
                        SharedPrefManager.getInstance(SignupActivity.this).userLogin(answer.getJSONObject("body"), password);
                        Intent intent = new Intent(SignupActivity.this, MapsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


                        startActivity(intent);
                        finish();


                    }}catch(Exception e){}
            }

            @Override
            protected String doInBackground(String... params) {
                //  BufferedReader bufferedReader = null;
                try {
                    HashMap<String, String> pars = new HashMap<String, String>();
                    pars.put("service" , "signup");
                    pars.put("email" , params[0]);
                    pars.put("password" , params[1]);
                    pars.put("name" , params[2]);
                    pars.put("phone" , params[3]);
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
        ru.execute(email , password ,name , phone);
    }



}
