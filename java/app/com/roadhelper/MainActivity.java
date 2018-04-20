package app.com.roadhelper;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.com.roadhelper.helper.SharedPrefManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } finally {

                    Intent s = new Intent(MainActivity.this , LoginActivity.class);
                    if(SharedPrefManager.getInstance(MainActivity.this).IsUserLoggedIn())
                        s = new Intent(MainActivity.this , MapsActivity.class);
                        startActivity(s);
                    finish();
                }
            }
        };
        timer.start();
    }
}
