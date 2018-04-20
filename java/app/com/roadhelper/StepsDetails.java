package app.com.roadhelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StepsDetails extends RootActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       final int x = getIntent().getIntExtra("type" ,1);
        if(x==1)
        setContentView(R.layout.activity_steps_details_tires);
        if(x==2)
            setContentView(R.layout.activity_steps_details_oil);
        if(x==3)
            setContentView(R.layout.activity_steps_details_battery);
        if(x==4)
            setContentView(R.layout.activity_steps_details_tires_two);
        if(x==5)
            setContentView(R.layout.activity_steps_details_tires_three);


        if(x==1 || x==4)
            findViewById(R.id.done_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ii = new Intent(StepsDetails.this,StepsDetails.class);
                    if(x==1){ii.putExtra("type" ,4);}
                    if(x==4){ii.putExtra("type" ,5);}
                    startActivity(ii);
                }
            });
        else
        findViewById(R.id.done_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
