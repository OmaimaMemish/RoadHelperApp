package app.com.roadhelper.helper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

/**
 * Created by HP on 4/11/2018.
 */

public class AlarmUtils {

    public static int saveAlarm(Context c , String title , String message , String type ,long when){
// storing notification as alarm servicewith its info  and pass it to reciever with waken up screen type of notification
        //preparing notification as event and pass it to reciver
         long now  = System.currentTimeMillis();
        //will fire in 60 seconds

        if(now>when)return -1;//repeating validation
        int id = new DatabaseHelper(c).insertData(title  ,message ,when+"" ,type);//inserting data into DB
        AlarmManager am = (AlarmManager)c.getSystemService(Context.ALARM_SERVICE);//saved as alarm
        Intent intent = new Intent(c, MyReceiver.class);//at the end passing this intent to reciever
        // notification info
        intent.putExtra("myAction", "mDoNotify");
        intent.putExtra("title", title);
        intent.putExtra("message", message);
        intent.putExtra("type", type);
        intent.putExtra("id", String.valueOf(id));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(c, id, intent, 0);
        am.set(AlarmManager.RTC_WAKEUP, when, pendingIntent);//waking up screen whe notification trigged
        return id;
    }

}
