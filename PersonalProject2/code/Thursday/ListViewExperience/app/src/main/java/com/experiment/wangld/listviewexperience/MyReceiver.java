package com.experiment.wangld.listviewexperience;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        if(intent.getAction().equals("onEnterMainActivity")){
            food myfood = (food) intent.getParcelableExtra("myfood");
            int foodIndex = intent.getIntExtra("foodIndex", 0);

            Bitmap putIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.empty_star);

            Notification.Builder notify = new Notification.Builder(context).setAutoCancel(true).setContentTitle("今日推荐")
                    .setContentText(myfood.getName()).setLargeIcon(putIcon).setSmallIcon(R.drawable.empty_star);

            Intent newIntent = new Intent(context, food_detail.class);
            newIntent.putExtra("name", myfood.getName());
            newIntent.putExtra("type", myfood.getType());
            newIntent.putExtra("label", myfood.getLabel());
            newIntent.putExtra("cover", myfood.getCover());
            newIntent.putExtra("nutrition", myfood.getNutrition());
            newIntent.putExtra("color", myfood.getColor());
            newIntent.putExtra("foodIndex", foodIndex);
            newIntent.putExtra("isInFoodList", true);
            newIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            context.sendBroadcast(newIntent);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, foodIndex, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            notify.setContentIntent(pendingIntent);

            NotificationManager manager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
            manager.notify(0, notify.build());
        }
    }
}
