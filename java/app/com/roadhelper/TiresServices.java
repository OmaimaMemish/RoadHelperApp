package app.com.roadhelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import app.com.roadhelper.helper.RequestsHandler;

public class TiresServices extends RootActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tires_services);
        findViewById(R.id.spare_tire).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestsHandler.sendRequest(TiresServices.this , "Spare tire installation" ,"");
            }
        });
        findViewById(R.id.tire_repair).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestsHandler.sendRequest(TiresServices.this , "Tire repair" ,"");
            }
        });
    }
}
