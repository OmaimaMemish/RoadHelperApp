package app.com.roadhelper;


import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import app.com.roadhelper.adapters.AlarmItem;
import app.com.roadhelper.adapters.CustomersAdapter;
import app.com.roadhelper.helper.AlarmUtils;
import app.com.roadhelper.helper.DatabaseHelper;

public class NotificationsActivity extends RootActivity {


    public void init(){
        List<AlarmItem> cats = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        CustomersAdapter mAdapter = new CustomersAdapter(cats ,NotificationsActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        //  recyclerView.addItemDecoration(new DividerItemDecoration(CategoriesActivity.this, LinearLayoutManager.VERTICAL));

        Cursor cursor = new DatabaseHelper(this).getAllData("0");


        try {
            while (cursor.moveToNext()) {
                cats.add(new AlarmItem(cursor.getString(0) ,cursor.getString(1) ,cursor.getString(2) ,cursor.getString(3)));
            }
        } finally {
            cursor.close();
        }


        mAdapter.notifyDataSetChanged();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);



init();

findViewById(R.id.delete_all).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
     new DatabaseHelper(NotificationsActivity.this).resetData();
        init();
    }
});


        findViewById(R.id.add_new_alarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final View dialogView = View.inflate(NotificationsActivity.this, R.layout.date_time_picker, null);
                final AlertDialog alertDialog = new AlertDialog.Builder(NotificationsActivity.this).create();

                dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                        TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);

                        Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                                datePicker.getMonth(),
                                datePicker.getDayOfMonth(),
                                timePicker.getCurrentHour(),
                                timePicker.getCurrentMinute());

                      long  time = calendar.getTimeInMillis();
                        String title =((EditText)dialogView.findViewById(R.id.title)).getText().toString();
                        String details =((EditText)dialogView.findViewById(R.id.details)).getText().toString();

                           boolean weekBefore = ((CheckBox)dialogView.findViewById(R.id.notify_week)).isChecked();
                        if(weekBefore)time-=(7*24*60*60*1000);



                        if(title.trim().equals("")){
                            Toast.makeText(NotificationsActivity.this, "Please enter title", Toast.LENGTH_SHORT).show();
                        return;
                        }
                        if(details.trim().equals("")){
                            Toast.makeText(NotificationsActivity.this, "Please enter details", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(System.currentTimeMillis()>time){
                            Toast.makeText(NotificationsActivity.this, "time can not be in the past !", Toast.LENGTH_SHORT).show();
                            return;
                        }


                      int y=  AlarmUtils.saveAlarm(NotificationsActivity.this  ,title ,details ,"1" ,time );
                        Toast.makeText(NotificationsActivity.this, "Reminder saved succesfully "+y, Toast.LENGTH_SHORT).show();
                        init();
                        alertDialog.dismiss();
                    }});
                alertDialog.setView(dialogView);
                alertDialog.show();

            }
        });
    }
}
