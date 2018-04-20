package app.com.roadhelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NearestPlaces extends RootActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_places);
        findViewById(R.id.gas_stations).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(NearestPlaces.this ,NearbyStuffMap.class);
                ii.putExtra("type" ,"gas_station");
                startActivity(ii);
            }
        });
        findViewById(R.id.repair_shops).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(NearestPlaces.this ,NearbyStuffMap.class);
                ii.putExtra("type" ,"car_repair");
                startActivity(ii);

            }
        });
    }
}
