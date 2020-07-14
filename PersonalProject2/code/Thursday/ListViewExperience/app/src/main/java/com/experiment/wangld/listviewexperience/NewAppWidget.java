package com.experiment.wangld.listviewexperience;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    private static final String STATIC_RECEIVER = "MyWidgetReceiver";
    private static final String DYNAMIC_RECEIVER = "MyDynamicWidgetReceiver";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_defaultText);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        //views.setTextViewText(R.id.appwidget_text, widgetText);
        views.setTextViewText(R.id.appwidget_text, widgetText);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent){
        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        if(intent.getAction().equals(STATIC_RECEIVER)){
            food myfood = (food) intent.getParcelableExtra("myfood");
            int foodIndex = intent.getIntExtra("foodIndex", 0);

            String name = myfood.getName();
            //设置widget内容
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
            views.setTextViewText(R.id.appwidget_text, "今日推荐 "+name);
            //设置intent
            Intent newIntent = new Intent(context, food_detail.class);
            newIntent.putExtra("name", myfood.getName());
            newIntent.putExtra("type", myfood.getType());
            newIntent.putExtra("label", myfood.getLabel());
            newIntent.putExtra("cover", myfood.getCover());
            newIntent.putExtra("nutrition", myfood.getNutrition());
            newIntent.putExtra("color", myfood.getColor());
            newIntent.putExtra("foodIndex", foodIndex);
            newIntent.putExtra("isInFoodList", true);
            //设置pendingIntent
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);
            //更新widget
            ComponentName thisWidget = new ComponentName(context.getApplicationContext(), NewAppWidget.class);
            appWidgetManager.updateAppWidget(thisWidget, views);
        }
    }


}

