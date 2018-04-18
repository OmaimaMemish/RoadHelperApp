package app.com.roadhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MobileAutoRepair extends RootActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_auto_repair);
        findViewById(R.id.battery_auto_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s = new Intent(MobileAutoRepair.this , BatteryServices.class);
                startActivity(s);

            }
        });
        findViewById(R.id.tires_auto_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s = new Intent(MobileAutoRepair.this , TiresServices.class);
                startActivity(s);

            }
        });
    }
}
