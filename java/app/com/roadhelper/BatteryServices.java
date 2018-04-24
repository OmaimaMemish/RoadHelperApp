package app.com.roadhelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import app.com.roadhelper.helper.RequestsHandler;

public class BatteryServices extends RootActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_services);
        findViewById(R.id.battery_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestsHandler.getInstance(BatteryServices.this).sendRequest(BatteryServices.this , "Jump start battery" ,"" ,"30" ,0);
            }
        });
        findViewById(R.id.battery_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestsHandler.getInstance(BatteryServices.this).sendRequest(BatteryServices.this , "Change battery" ,"" ,"80" ,0);
            }
        });
    }
}
