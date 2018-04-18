package app.com.roadhelper.helper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

/**
 * Created by Luminance on 4/11/2018.
 */

public class AlarmUtils {

    public static int saveAlarm(Context c , String title , String message , String type ,long when){

         long now  = System.currentTimeMillis();
        //will fire in 60 seconds

        if(now>when)return -1;
        int id = new DatabaseHelper(c).insertData(title  ,message ,when+"" ,type);
        AlarmManager am = (AlarmManager)c.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(c, MyReceiver.class);
        intent.putExtra("myAction", "mDoNotify");
        intent.putExtra("title", title);
        intent.putExtra("message", message);
        intent.putExtra("type", type);
        intent.putExtra("id", String.valueOf(id));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(c, id, intent, 0);
        am.set(AlarmManager.RTC_WAKEUP, when, pendingIntent);
        return id;
    }

}
