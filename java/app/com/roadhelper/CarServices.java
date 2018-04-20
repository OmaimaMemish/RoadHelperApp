package app.com.roadhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class CarServices extends RootActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_services);
        findViewById(R.id.auto_repair_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CarServices.this ,MobileAutoRepair.class));
            }
        });
        findViewById(R.id.tow_tuck_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CarServices.this ,TrackTowService.class));
            }
        });
        findViewById(R.id.gas_delivery_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CarServices.this ,GasServices.class));


            }
        });
    }
}
