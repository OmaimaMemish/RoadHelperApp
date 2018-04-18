package app.com.roadhelper.helper;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import app.com.roadhelper.HomeActivity;
import app.com.roadhelper.R;

/**
 * Created by Luminance on 4/11/2018.
 */
public class MyReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        //you might want to check what's inside the Intent
        if(intent.getStringExtra("myAction") != null &&
                intent.getStringExtra("myAction").equals("mDoNotify")){

            String id=intent.getStringExtra("id");
            String title=intent.getStringExtra("title");
            String message=intent.getStringExtra("message");
            String type=intent.getStringExtra("type");
             DatabaseHelper db = new DatabaseHelper(context);
            if(db.searchData(id)){
            NotificationManager mNotifyMgr =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                if(alarmSound == null){
                    alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                    if(alarmSound == null){
                        alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    }
                }
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_notifications_black_24dp)

                    //example for large icon
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSound(alarmSound)
                    .setOngoing(false)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);
            Intent i = new Intent(context, HomeActivity.class);
            PendingIntent pendingIntent =
                    PendingIntent.getActivity(
                            context,
                           Integer.parseInt(id),
                            i,
                            PendingIntent.FLAG_ONE_SHOT
                    );
            // example for blinking LED
            mBuilder.setLights(0xFFb71c1c, 1000, 2000);
            mBuilder.setContentIntent(pendingIntent);
            mNotifyMgr.notify(12345, mBuilder.build());
        }}

    }
}