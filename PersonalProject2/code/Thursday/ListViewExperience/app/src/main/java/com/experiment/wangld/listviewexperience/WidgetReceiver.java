package com.experiment.wangld.listviewexperience;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class WidgetReceiver extends BroadcastReceiver {
    private static final String DYNAMIC_RECEIVER = "MyDynamicWidgetReceiver";

    @Override
    public void onReceive(Context context, Intent intent){
        if (intent.getAction().equals(DYNAMIC_RECEIVER)){
            String name = intent.getStringExtra("name");
            int foodIndex = intent.getIntExtra("foodIndex", 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
            views.setTextViewText(R.id.appwidget_text, "已收藏"+name);

            Intent toCollect = new Intent(context, MainActivity.class);
            toCollect.putExtra("isCollectList", true);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, foodIndex, toCollect, PendingIntent.FLAG_UPDATE_CURRENT);

            views.setOnClickPendingIntent(R.id.widget, pendingIntent);

            AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context.getApplicationContext(), NewAppWidget.class), views);

            context.unregisterReceiver(DataBus.widgetReceiver);
        }
    }
}
