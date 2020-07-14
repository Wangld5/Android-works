package com.experiment.group22.glory;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;

public class MainActivity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;

    private FrameLayout mFrameLayout;
    private EditText mSearch;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mAdapter;
    private FloatingActionButton pause_music_Fab;
    private FloatingActionButton add_person_Fab;
    int mp_flag;    //主线程与子线程通过该标志通信来控制MediaPlayer

    ArrayList<MyRecyclerViewData> datas = new ArrayList<>();   //列表数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** 创建数据库 */
        dbHelper = new MyDatabaseHelper(this,"Character.db",null,1);
        dbHelper.getWritableDatabase();

        /** 初始化 */
        mFrameLayout = (FrameLayout) findViewById(R.id.main_layout);
        mSearch = (EditText) findViewById(R.id.search_text);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        pause_music_Fab = (FloatingActionButton) findViewById(R.id.music);
        add_person_Fab = (FloatingActionButton) findViewById(R.id.add);

        /** 播放背景音乐 */
        mp_flag = 1;
        Thread mThread = new Thread() {
            MediaPlayer mp;
            @Override
            public void run() {
                while(true) {
                    if(mp_flag == 1) {      //等于0时表示创建MediaPlayer并启动
                        mp = new MediaPlayer();
                        try {
                            mp = MediaPlayer.create(MainActivity.this, R.raw.b);
                            mp.setLooping(true);
                            mp.start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        mp_flag = 0;        //等于0时表示不做什么
                    }
                    else if(mp_flag == 2) { //等于2时表示停止播放
                        mp.pause(); mp_flag = 0;
                    }
                    else if(mp_flag == 3) { //等于3时表示恢复播放
                        mp.start(); mp_flag = 0;
                    }
                    else if(mp_flag == 4) { //等于4时表示结束播放
                        mp.release();
                        break;
                    }
                }
            }
        };
        mThread.start();

        /** 把主界面frameLayout的前景图片设置为较透明 */
        mFrameLayout.getForeground().setAlpha(50);

        /** 监听搜索栏 */
        mSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String search_content = String.valueOf(mSearch.getText());

                SQLiteDatabase db=dbHelper.getWritableDatabase();
                Cursor cursor=db.rawQuery("select * from Character where name=?",new String[]{search_content});
                if(cursor.moveToFirst()) {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String photo1 = cursor.getString(cursor.getColumnIndex("photo1"));
                    String photo = cursor.getString(cursor.getColumnIndex("photo"));
                    String sex = cursor.getString(cursor.getColumnIndex("sex"));
                    String type = cursor.getString(cursor.getColumnIndex("type"));
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String survival = cursor.getString(cursor.getColumnIndex("survival"));
                    String attack = cursor.getString(cursor.getColumnIndex("attack"));
                    String skill = cursor.getString(cursor.getColumnIndex("skill"));
                    String difficulty = cursor.getString(cursor.getColumnIndex("difficulty"));
                    String story = cursor.getString(cursor.getColumnIndex("story"));
                    //Log.d("query",name+"|"+photo+"|"+sex+"|"+country+"|"+bornplace+"|"
                    //        +bd_t+"|"+introd+"|"+story+"|"+e_d);

                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putStringArray("detail", new String[]{photo1, photo, name, type, title, sex, survival, attack, skill, difficulty, story});
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 0);
                } else {
                    Toast.makeText(MainActivity.this, "查无此人，可自行创建", Toast.LENGTH_SHORT).show();
                }

                cursor.close();
            }
            return false;
            }
        });

        /** 创建RecyclerView */
        initData();   //初始化数据

        mAdapter = new MyRecyclerViewAdapter(this, datas);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager
                (2, StaggeredGridLayoutManager.VERTICAL));  //声名为瀑布流的布局方式: 2列,垂直方向

        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(mAdapter);
        animationAdapter.setDuration(1000);                 //添加适配器并设置动画
        mRecyclerView.setAdapter(animationAdapter);
        mRecyclerView.setItemAnimator(new OvershootInLeftAnimator());

        //添加点击事件
        mAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String click_content = datas.get(position).name;

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.rawQuery("select * from Character where name=?", new String[]{click_content});
                if (cursor.moveToFirst()) {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String photo1 = cursor.getString(cursor.getColumnIndex("photo1"));
                    String photo = cursor.getString(cursor.getColumnIndex("photo"));
                    String sex = cursor.getString(cursor.getColumnIndex("sex"));
                    String type = cursor.getString(cursor.getColumnIndex("type"));
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String survival = cursor.getString(cursor.getColumnIndex("survival"));
                    String attack = cursor.getString(cursor.getColumnIndex("attack"));
                    String skill = cursor.getString(cursor.getColumnIndex("skill"));
                    String difficulty = cursor.getString(cursor.getColumnIndex("difficulty"));
                    String story = cursor.getString(cursor.getColumnIndex("story"));


                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putStringArray("detail", new String[]{photo1, photo, name, type, title, sex, survival, attack, skill, difficulty, story});
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 0);
                }
            }
        });
        //设置长按事件
        mAdapter.setOnItemLongClickListener(new MyRecyclerViewAdapter.onRecyclerItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("移除人物").setMessage("确定删除" + datas.get(position).name + "及其相关信息吗？");
                builder.setCancelable(true);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MyRecyclerViewData data = mAdapter.removeItem(position);
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.execSQL("delete from Character where name=?",new String[]{data.name});

                        Toast.makeText(MainActivity.this, "已删除:" + data.name, Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        /** 点击浮动按钮暂停与恢复音乐 */
        pause_music_Fab.setTag(0);
        pause_music_Fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(pause_music_Fab.getTag().equals(0)) {
                    Snackbar.make(pause_music_Fab, "已暂停音乐", Snackbar.LENGTH_SHORT)
                            .setAction("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setActionTextColor(0xff0000ff)
                            .show();

                    pause_music_Fab.setImageResource(R.mipmap.close);
                    pause_music_Fab.setTag(1);
                    mp_flag = 2;
                } else {
                    Snackbar.make(pause_music_Fab, "已恢复音乐", Snackbar.LENGTH_SHORT)
                            .setAction("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setActionTextColor(0xff0000ff)
                            .show();

                    pause_music_Fab.setImageResource(R.mipmap.vol);
                    pause_music_Fab.setTag(0);
                    mp_flag = 3;
                }
            }
        });

        /** 点击浮动按钮添加人物及信息 */
        add_person_Fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Bundle bundle = new Bundle();
                bundle.putInt("activity",1);

                Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });

    }

    /*------------------------*/
    /*      初始化数据        */
    /*------------------------*/
    public void initData() {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from Character",null);
        while (cursor.moveToNext()){

            String name = cursor.getString(cursor.getColumnIndex("name"));
            String photo1 = cursor.getString(cursor.getColumnIndex("photo1"));
            String type = cursor.getString(cursor.getColumnIndex("type"));

            datas.add(new MyRecyclerViewData(type, name, photo1));
        }
        cursor.close();
    }

    /*------------------------*/
    /*         回调处理       */
    /*------------------------*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //添加人物
        if(resultCode == 31) {
            Bundle bundle = intent.getExtras();

            MyRecyclerViewData data = new MyRecyclerViewData(bundle.getString("type"),
                    bundle.getString("name"), bundle.getString("photo1"));
            mAdapter.addItem(-1, data);
        }
        //只是修改信息
        else if(resultCode == 21) {
            String pre_name = intent.getStringExtra("pre_name");
            int index = 0;

            for(int i=0; i<datas.size(); i++) {
                if(pre_name.equals(datas.get(i).name)) {
                    index = i;
                    break;
                }
            }

            Integer id = intent.getIntExtra("id", 0);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from Character where name=?",new String[]{pre_name});
            if(cursor.moveToFirst()) {
                MyRecyclerViewData data = new MyRecyclerViewData(cursor.getString(cursor.getColumnIndex("type")),
                        cursor.getString(cursor.getColumnIndex("name")), cursor.getString(cursor.getColumnIndex("photo1")));
                mAdapter.removeItem(index);
                mAdapter.addItem(index, data);
            }
        }
    }

    @Override
    protected void onDestroy() {
        mp_flag = 4;    //释放MediaPlayer
        super.onDestroy();
    }
}
