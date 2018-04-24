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
// this class is responsiple for handling notification - DB SQL Live

    public void init(){
        List<AlarmItem> cats = new ArrayList<>();//for the elements to be showed in the adapter which the adapter in his turn will linked it with recyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);//used to show the item0,1,2 as a linear or grid


        CustomersAdapter mAdapter = new CustomersAdapter(cats ,NotificationsActivity.this);//to fill recyclerView with data
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());//design the layout
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());//controliing layout
        recyclerView.setAdapter(mAdapter);//linking recyclerView with adapter
        //  recyclerView.addItemDecoration(new DividerItemDecoration(CategoriesActivity.this, LinearLayoutManager.VERTICAL));

        Cursor cursor = new DatabaseHelper(this).getAllData("0");//a way to move between recods and elements in the DB


        try {
            while (cursor.moveToNext()) {//moving cursor to fill out the alarms
                cats.add(new AlarmItem(cursor.getString(0) ,cursor.getString(1) ,cursor.getString(2) ,cursor.getString(3)));
            }
        } finally {
            cursor.close();//closing it
        }

/*
array list is filled with cursor then passing arrayList to adapter then to recyclerView layout them showed in the alarm list
 */
        mAdapter.notifyDataSetChanged();//udpating the list and changes



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {//
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);



init();//helping to fill recyclerView with the adapter - extracting alarm from DB

findViewById(R.id.delete_all).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {//
     new DatabaseHelper(NotificationsActivity.this).resetData();// deleting all alarms through SQL Command in DatabaseHelper
        init();
    }
});

//starting to add new alarms
        findViewById(R.id.add_new_alarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final View dialogView = View.inflate(NotificationsActivity.this, R.layout.date_time_picker, null);
                final AlertDialog alertDialog = new AlertDialog.Builder(NotificationsActivity.this).create();

                dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // 2- showing calneder to the user as calender formatfor the date  and sppiner for the time
                        DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                        TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);

                        // 1- creating calender object to get the day,month,hour and minute
                        Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                                datePicker.getMonth(),
                                datePicker.getDayOfMonth(),
                                timePicker.getCurrentHour(),
                                timePicker.getCurrentMinute());

                        //3- converting date and time to milliseconds because of android iternal watch
                      long  time = calendar.getTimeInMillis();
                      //taking title and details as text
                        String title =((EditText)dialogView.findViewById(R.id.title)).getText().toString();
                        String details =((EditText)dialogView.findViewById(R.id.details)).getText().toString();

                        // 4- checking if user clicked notificating befor 1 week
                           boolean weekBefore = ((CheckBox)dialogView.findViewById(R.id.notify_week)).isChecked();
                        if(weekBefore)time-=(7*24*60*60*1000);//calculating before one week notification 7=week , 60= hours and seconds , 1000=millisecond in a second



                        // 5- validation for title and details
                        if(title.trim().equals("")){
                            Toast.makeText(NotificationsActivity.this, "Please enter title", Toast.LENGTH_SHORT).show();
                        return;
                        }
                        if(details.trim().equals("")){
                            Toast.makeText(NotificationsActivity.this, "Please enter details", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // 6- validating if the current time bigger than the chosen time if true error msg will apear
                        if(System.currentTimeMillis()>time){
                            Toast.makeText(NotificationsActivity.this, "time can not be in the past !", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        // 7- saving alarm and toast msg appear for saving confirmation
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
