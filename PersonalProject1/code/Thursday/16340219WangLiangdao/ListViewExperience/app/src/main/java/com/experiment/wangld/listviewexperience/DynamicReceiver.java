package com.experiment.wangld.listviewexperience;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DynamicReceiver extends BroadcastReceiver {
    private static final String DYNAMICATION = "myDynamicFilter";
    @Override
    public void onReceive(Context context, Intent intent){
        if(intent.getAction().equals(DYNAMICATION)){
            String name = intent.getStringExtra("name");
            int foodIndex = intent.getIntExtra("foodIndex", 0);
            Bitmap collectIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.empty_star);

            Notification.Builder notify = new Notification.Builder(context).setAutoCancel(true).setContentTitle("已收藏").setContentText(name)
                    .setLargeIcon(collectIcon).setSmallIcon(R.drawable.empty_star);

            Intent notifyIntent = new Intent(context, MainActivity.class);
            notifyIntent.putExtra("isCollectList", true);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, foodIndex, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            notify.setContentIntent(pendingIntent);

            NotificationManager manager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
            manager.notify(0, notify.build());

            context.unregisterReceiver(DataBus.dynamicReceiver);
        }
    }

}
