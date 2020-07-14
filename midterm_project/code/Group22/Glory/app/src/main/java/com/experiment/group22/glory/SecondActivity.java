package com.experiment.group22.glory;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ProgressBar;
//coded by wangld
public class SecondActivity extends AppCompatActivity {
    TextView name_view;
    TextView type_view;
    TextView title_view;
    TextView sex_view;
    ProgressBar survival_view;
    ProgressBar attack_view;
    ProgressBar skill_view;
    ProgressBar difficulty_view;
    ImageView all_pic;
    RoundImageView head_pic;
    TextView story_view;
    MyDatabaseHelper dbHelper;

    int id_ = -1;

    public void findView(){
        name_view = (TextView) findViewById(R.id.detail_name);
        type_view = (TextView) findViewById(R.id.detail_type);
        title_view = (TextView) findViewById(R.id.detail_title);
        sex_view = (TextView) findViewById(R.id.detail_sex);
        survival_view = (ProgressBar) findViewById(R.id.detail_life);
        attack_view = (ProgressBar) findViewById(R.id.detail_attack);
        skill_view = (ProgressBar) findViewById(R.id.detail_skill);
        difficulty_view = (ProgressBar) findViewById(R.id.detail_difficulty);
        all_pic = (ImageView) findViewById(R.id.poster);
        head_pic = (RoundImageView) findViewById(R.id.head_photo);
        story_view = (TextView) findViewById(R.id.detail_achievement);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        //init
        findView();

        //receive from main
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        final String charac[] = bundle.getStringArray("detail");
        final String photo1 = new String(charac[0]) ;
        final String photo = new String(charac[1]) ;
        final String name = new String(charac[2]) ;
        final String type = new String(charac[3]) ;
        final String title = new String(charac[4]) ;
        final String sex = new String(charac[5]) ;
        final String survival = new String(charac[6]) ;
        final String attack = new String(charac[7]) ;
        final String skill = new String(charac[8]) ;
        final String difficulty = new String(charac[9]);
        final String story = new String(charac[10]);


        dbHelper = new MyDatabaseHelper(this,"Character.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Character where name=?",new String[]{name});
        if(cursor.moveToFirst()) {
            id_ = cursor.getInt(cursor.getColumnIndex("id"));
        }

        //set text for detail
        name_view.setText(name);
        type_view.setText(type);
        title_view.setText(title);
        sex_view.setText(sex);
        story_view.setText(story);
        survival_view.setProgress(Integer.parseInt(survival));
        attack_view.setProgress(Integer.parseInt(attack));
        skill_view.setProgress(Integer.parseInt(skill));
        difficulty_view.setProgress(Integer.parseInt(difficulty));
        all_pic.setBackgroundResource(getResources().getIdentifier(photo1,"mipmap",getPackageName()));
        head_pic.setImageResource(getResources().getIdentifier(photo,"mipmap",getPackageName()));

        //返回按钮
        Button back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("id", id_);

                intent.putExtra("pre_name", name_view.getText().toString());
                setResult(21, intent);
                finish();
            }
        });

        //修改按钮，进入修改界面
        Button modify = (Button)findViewById(R.id.write);
        modify.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent2To3 = new Intent(SecondActivity.this,ThirdActivity.class);

                Bundle bundle2To3 = new Bundle();
                bundle2To3.putInt("activity",2);
                bundle2To3.putStringArray("stringArr",new String[]{photo1, photo, name, type, title, sex, survival, attack, skill, difficulty, story});

                intent2To3.putExtras(bundle2To3);
                startActivityForResult(intent2To3, 0);
            }
        });
    }

    /*------------------------*/
    /*  修改人物时的回调处理  */
    /*------------------------*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(resultCode == 32) {
            Bundle bundle = intent.getExtras();

            name_view.setText(bundle.getStringArray("detail")[2]);
            type_view.setText(bundle.getStringArray("detail")[3]);
            title_view.setText(bundle.getStringArray("detail")[4]);
            sex_view.setText(bundle.getStringArray("detail")[5]);
            survival_view.setProgress(Integer.parseInt(bundle.getStringArray("detail")[6]));
            attack_view.setProgress(Integer.parseInt(bundle.getStringArray("detail")[7]));
            skill_view.setProgress(Integer.parseInt(bundle.getStringArray("detail")[8]));
            difficulty_view.setProgress(Integer.parseInt(bundle.getStringArray("detail")[9]));
            story_view.setText(bundle.getStringArray("detail")[10]);
            all_pic.setBackgroundResource(getResources().getIdentifier(bundle.getStringArray("detail")[0],"mipmap",getPackageName()));
            head_pic.setImageResource(getResources().getIdentifier(bundle.getStringArray("detail")[1],"mipmap",getPackageName()));
        }
    }

}
