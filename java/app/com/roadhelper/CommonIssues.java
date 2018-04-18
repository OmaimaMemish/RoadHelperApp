package app.com.roadhelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CommonIssues extends RootActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_issues);

findViewById(R.id.tires_btn).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent ii= new Intent(CommonIssues.this , StepsDetails.class);
        ii.putExtra("type" ,1);
        startActivity(ii);
    }
});

        findViewById(R.id.oil_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii= new Intent(CommonIssues.this , StepsDetails.class);
                ii.putExtra("type" ,2);
                startActivity(ii);
            }
        });

        findViewById(R.id.battery_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii= new Intent(CommonIssues.this , StepsDetails.class);
                ii.putExtra("type" ,3);
                startActivity(ii);
            }
        });

    }
}
