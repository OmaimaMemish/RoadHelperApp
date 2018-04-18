package app.com.roadhelper;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends RootActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        (findViewById(R.id.common_issues_layout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             startActivity(new Intent(HomeActivity.this,CommonIssues.class));
            }
        });
        (findViewById(R.id.car_services_layout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,CarServices.class));
            }
        });  (findViewById(R.id.notificatons_layout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,NotificationsActivity.class));
            }
        });
        (findViewById(R.id.nearest_places)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,NearestPlaces.class));
            }
        });
    }
}
