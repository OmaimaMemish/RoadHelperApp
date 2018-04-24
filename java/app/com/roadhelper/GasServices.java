package app.com.roadhelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import app.com.roadhelper.helper.RequestsHandler;

public class GasServices extends RootActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_services);
        findViewById(R.id.send_red).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestsHandler.getInstance(GasServices.this).sendRequest(GasServices.this , "Gas 95 delivery" ,"" ,"30",0);
            }
        });

        findViewById(R.id.send_green).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestsHandler.getInstance(GasServices.this).sendRequest(GasServices.this , "Gas 91 delivery" ,"" ,"30" ,0);
            }
        });
    }
}
