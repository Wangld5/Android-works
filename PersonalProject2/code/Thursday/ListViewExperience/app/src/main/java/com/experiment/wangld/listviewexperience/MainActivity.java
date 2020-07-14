package com.experiment.wangld.listviewexperience;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;

public class MainActivity extends AppCompatActivity {

    LinearLayout collectListLayout;
    ListView foodListView;
    SimpleAdapter foodListAdapter;
    RecyclerView rv;
    ArrayList<Map<String, Object>> foodList = new ArrayList<>();
    FloatingActionButton fab;
    boolean isCollectList = false;
    MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = (RecyclerView)findViewById(R.id.recyclerView);
        collectListLayout = (LinearLayout) findViewById(R.id.foodListLayout);
        foodListView = (ListView) findViewById(R.id.foodListView);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        EventBus.getDefault().register(this);

        Intent intent = new Intent("onEnterMainActivity");
        Intent widgetIntent = new Intent("MyWidgetReceiver");


        setLayoutVisibility();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCollectList = !isCollectList;
                setLayoutVisibility();
            }
        });

        foodListAdapter = new SimpleAdapter(this,
                foodList,
                R.layout.collect_list,
                new String[]{"label", "name"},
                new int[] { R.id.collectListlabel, R.id.collectListname});
        foodListView.setAdapter(foodListAdapter);

        foodListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int foodIndex, long l) {
                final int index =  foodIndex;
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("移除食品")
                        .setMessage("从收藏夹移除" + foodList.get(foodIndex).get("name") + "?")
                        .setCancelable(true)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                foodList.remove(index);
                                foodListAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // do nothing
                            }
                        })
                        .show();
                return true;
            }
        });
        foodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, food_detail.class);
                Bundle data = new Bundle();
                HashMap<String, Object> m =  (HashMap<String, Object>) foodList.get(i);
                data.putString("name", (String)m.get("name"));
                data.putString("type", (String)m.get("type"));
                data.putString("cover", (String)m.get("cover"));
                data.putString("nutrition", (String)m.get("nutrition"));
                data.putInt("color", (int)m.get("color"));
                data.putInt("foodIndex", i);
                data.putBoolean("isInFoodList", false);

                intent.putExtras(data);
                startActivityForResult(intent, 1);
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapter(getData());
        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(myAdapter);
        scaleInAnimationAdapter.setDuration(1000);
        rv.setAdapter((scaleInAnimationAdapter));
        rv.setItemAnimator(new OvershootInLeftAnimator());

        int randomIndex = new Random().nextInt(myAdapter.getItemCount());
        food myfood = myAdapter.getFood(randomIndex);

        intent.putExtra("foodIndex", randomIndex);
        intent.putExtra("myfood", (Parcelable) myfood);
        sendBroadcast(intent);

        widgetIntent.putExtra("foodIndex", randomIndex);
        widgetIntent.putExtra("myfood", (Parcelable)myfood);
        sendBroadcast(widgetIntent);

    }
    @Override
    protected void onNewIntent(Intent intent){
        this.isCollectList = intent.getBooleanExtra("isCollectList", false);
        setLayoutVisibility();
        super.onNewIntent(intent);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text, "没有任何信息");
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget, pendingIntent);
        AppWidgetManager.getInstance(this).updateAppWidget(new ComponentName(this.getApplicationContext(), NewAppWidget.class), views);
        EventBus.getDefault().unregister(this);
    }
    private ArrayList<food> getData() {
        ArrayList<food> list = new ArrayList<>();
        list.add(new food("大豆", "粮食", "粮", "蛋白质",   "富含", R.color.deepRed));
        list.add(new food("十字花科蔬菜", "蔬菜", "蔬",  "维生素C",  "富含", R.color.brown));
        list.add(new food("牛奶", "饮品" , "饮",  "钙",   "富含", R.color.blue));
        list.add(new food("海鱼", "肉食", "肉", "蛋白质", "富含", R.color.green));
        list.add(new food("菌菇类", "蔬菜", "蔬","微量元素", "富含", R.color.deepRed));
        list.add(new food("番茄", "蔬菜", "蔬","番茄红素",   "富含", R.color.blue));
        list.add(new food("胡萝卜", "蔬菜", "蔬","胡萝卜素",  "富含", R.color.green));
        list.add(new food("荞麦", "粮食", "粮","膳食纤维",  "富含", R.color.deepRed));
        list.add(new food("鸡蛋", "杂", "杂","几乎所有营养物质",   "富含", R.color.brown));
        return list;
    }


    @Subscribe
    public void onfoodListEvent(foodListEvent event){
        int foodIndex = event.getFoodIndex();
        Map<String, Object> p1 = new HashMap<>();
        food myfood = (food) myAdapter.getFood(foodIndex);
        p1.put("label", myfood.getLabel());
        p1.put("name", myfood.getName());
        p1.put("type", myfood.getType());
        p1.put("cover", myfood.getCover());
        p1.put("nutrition", myfood.getNutrition());
        p1.put("color", myfood.getColor());
        p1.put("foodIndex", foodIndex);
        foodList.add(p1);
        foodListAdapter.notifyDataSetChanged();
    }
    @Subscribe
    public void oncollectListEvent(collectListEvent event){
        int foodIndex = event.getFoodIndex();
        Map<String, Object> p1 = new HashMap<>();
        Map<String, Object> myfood = foodList.get(foodIndex);

        p1.put("label", myfood.get("label"));
        p1.put("name", myfood.get("name"));
        p1.put("type", myfood.get("type"));
        p1.put("cover", myfood.get("cover"));
        p1.put("nutrition", myfood.get("nutrition"));
        p1.put("color", myfood.get("color"));
        p1.put("foodIndex", foodIndex);
        foodList.add(p1);
        foodListAdapter.notifyDataSetChanged();
    }

    public void setLayoutVisibility(){
        if (isCollectList) {
            rv.setVisibility(View.INVISIBLE);
            collectListLayout.setVisibility(View.VISIBLE);
            fab.setImageResource(R.drawable.mainpage);
        } else {
            rv.setVisibility(View.VISIBLE);
            collectListLayout.setVisibility(View.INVISIBLE);
            fab.setImageResource(R.drawable.collect);
        }
    }
}
