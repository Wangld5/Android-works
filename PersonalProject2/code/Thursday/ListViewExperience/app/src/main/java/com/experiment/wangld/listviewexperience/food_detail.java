package com.experiment.wangld.listviewexperience;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

public class food_detail extends AppCompatActivity {
    RelativeLayout image_panel;
    TextView name;
    TextView type;
    TextView cover;
    TextView nutrition;
    ImageView Color;
    ListView operationListView;
    ImageView back;
    ImageView star;
    ImageButton collectButton;
    int productIndex;
    boolean isInFoodList = true;
    boolean wantCollect = false;
    private static final String DYNAMICATION = "myDynamicFilter";
    private static final String DYNAMIC_RECEIVER = "MyDynamicWidgetReceiver";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        image_panel = (RelativeLayout) findViewById(R.id.image_panel);
        name = (TextView) findViewById(R.id.name);
        type = (TextView) findViewById(R.id.kind);
        cover = (TextView) findViewById(R.id.cover);
        nutrition = (TextView) findViewById(R.id.information);
        Color = (ImageView) findViewById(R.id.Color);
        operationListView = (ListView) findViewById(R.id.operationListView);
        back = (ImageView) findViewById(R.id.back);
        star = (ImageView) findViewById(R.id.star);
        collectButton = (ImageButton) findViewById(R.id.collect_button);

        int wid = getResources().getDisplayMetrics().widthPixels;
        int hei = getResources().getDisplayMetrics().heightPixels/3;
        image_panel.setLayoutParams(new ConstraintLayout.LayoutParams(wid,hei));

        Intent product = getIntent();
        name.setText(product.getStringExtra("name"));
        type.setText(product.getStringExtra("type"));
        cover.setText(product.getStringExtra("cover"));
        nutrition.setText(product.getStringExtra("nutrition"));
        Color.setImageResource(product.getIntExtra("color", 0));
        productIndex = product.getIntExtra("foodIndex", 0);
        isInFoodList = product.getBooleanExtra("isInFoodList", false);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        star.setTag(false);
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isFavor = (boolean)view.getTag();
                if (isFavor) {
                    ((ImageView)view).setImageResource(R.drawable.full_star);
                } else {
                    ((ImageView)view).setImageResource(R.drawable.empty_star);
                }
                view.setTag(!isFavor);
            }
        });

        collectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCollect();
            }
        });

        String []opArr =  {"分享信息", "不感兴趣", "查看更多信息", "出错反馈"};
        operationListView.setAdapter(new ArrayAdapter<String>(this, R.layout.food_detail_operation, R.id.operationItem, opArr));
    }
    void addToCollect() {
//        Intent intent = new Intent(food_detail.this, MainActivity.class);
//        intent.putExtra("foodIndex", productIndex);
//        intent.putExtra("collect", true);
//        Toast.makeText(food_detail.this, "已收藏", Toast.LENGTH_SHORT).show();
//        setResult(0, intent);
        if (this.wantCollect == false){
            if(isInFoodList){
                EventBus.getDefault().postSticky(new foodListEvent(productIndex));
            }else{
                EventBus.getDefault().postSticky(new collectListEvent(productIndex));
            }
            IntentFilter dynamic_filter = new IntentFilter();
            dynamic_filter.addAction(DYNAMICATION);
            registerReceiver(DataBus.dynamicReceiver, dynamic_filter);

            Intent intent = new Intent(DYNAMICATION);
            intent.putExtra("name", name.getText());
            intent.putExtra("foodIndex", productIndex);
            sendBroadcast(intent);

            IntentFilter widget_dynamic_filter = new IntentFilter();
            widget_dynamic_filter.addAction(DYNAMIC_RECEIVER);
            registerReceiver(DataBus.widgetReceiver, widget_dynamic_filter);

            Intent widgetIntent = new Intent(DYNAMIC_RECEIVER);
            widgetIntent.putExtra("name", name.getText());
            widgetIntent.putExtra("foodIndex", productIndex);
            sendBroadcast(widgetIntent);

            this.wantCollect = true;
            Toast.makeText(food_detail.this, "已收藏",Toast.LENGTH_SHORT).show();
        }
    }
}
