package com.experiment.group22.glory;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import static android.app.PendingIntent.getActivity;

public class ThirdActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private String final_name ;
    private String final_type;
    private String final_title;
    private String final_sex ;
    private String final_survival;
    private String final_attack;
    private String final_skill;
    private String final_difficulty;
    private String final_story;
    private String final_allpic;
    private String final_headpic;
    private MyDatabaseHelper dbHelper;

    Button submit;
    RadioGroup sex_choose;
    RadioButton male;
    RadioButton female;
    RadioGroup type_choose1;
    RadioGroup type_choose2;
    RadioButton tank;
    RadioButton warrior;
    RadioButton assassin;
    RadioButton wizard;
    RadioButton shooter;
    RadioButton assist;
    RoundImageView head_pic;


    EditText update_name;
    EditText update_title;
    EditText update_life;
    EditText update_attack;
    EditText update_skill;
    EditText update_difficulty;
    EditText update_achievement;

    private Boolean changeGroup = false;

    private void showDialog(){
        View viewDialog;
        LayoutInflater inflater = LayoutInflater.from(this);
        viewDialog = inflater.inflate(R.layout.ratio_group, null);

        final RadioGroup mGroup = (RadioGroup)viewDialog.findViewById(R.id.the_group);
        final RadioButton m1 = (RadioButton) viewDialog.findViewById(R.id.male1);
        final RadioButton m2 = (RadioButton) viewDialog.findViewById(R.id.male2);
        final RadioButton m3 = (RadioButton) viewDialog.findViewById(R.id.male3);
        final RadioButton m4 = (RadioButton) viewDialog.findViewById(R.id.male4);
        final RadioButton m5 = (RadioButton) viewDialog.findViewById(R.id.male5);
        final RadioButton m6 = (RadioButton) viewDialog.findViewById(R.id.male6);
        final RadioButton m7 = (RadioButton) viewDialog.findViewById(R.id.male7);
        final RadioButton m8 = (RadioButton) viewDialog.findViewById(R.id.male8);
        final RadioButton m9 = (RadioButton) viewDialog.findViewById(R.id.male9);
        final RadioButton m10 = (RadioButton) viewDialog.findViewById(R.id.male10);
        final RadioButton m11 = (RadioButton) viewDialog.findViewById(R.id.male11);
        final RadioButton m12 = (RadioButton) viewDialog.findViewById(R.id.male12);
        final RadioButton m13 = (RadioButton) viewDialog.findViewById(R.id.male13);




        final RadioButton f1 = (RadioButton) viewDialog.findViewById(R.id.female1);
        final RadioButton f2 = (RadioButton) viewDialog.findViewById(R.id.female2);
        final RadioButton f3 = (RadioButton) viewDialog.findViewById(R.id.female3);
        final RadioButton f4 = (RadioButton) viewDialog.findViewById(R.id.female4);
        final RadioButton f5 = (RadioButton) viewDialog.findViewById(R.id.female5);
        final RadioButton f6 = (RadioButton) viewDialog.findViewById(R.id.female6);
        final RadioButton f7 = (RadioButton) viewDialog.findViewById(R.id.female7);

        AlertDialog.Builder builder = new AlertDialog.Builder(ThirdActivity.this);
        builder.setView(viewDialog).setTitle("选择头像").setView(viewDialog).setCancelable(true);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               if(mGroup.getCheckedRadioButtonId()==m1.getId()) {
                   final_allpic = "caocao";
                   final_headpic = "caocao_icon";
                   head_pic.setImageResource(R.mipmap.caocao_icon);
               }else if(mGroup.getCheckedRadioButtonId()==m2.getId()){
                   final_allpic = "baiqi";
                   final_headpic = "baiqi_icon";
                   head_pic.setImageResource(R.mipmap.baiqi_icon);
               }else if(mGroup.getCheckedRadioButtonId()==m3.getId()){
                   final_allpic = "bailishouyun";
                   final_headpic = "bailishouyun_icon";
                   head_pic.setImageResource(R.mipmap.bailishouyun_icon);
               }else if(mGroup.getCheckedRadioButtonId()==m4.getId()){
                   final_allpic = "bailixuance";
                   final_headpic = "bailixuance_icon";
                   head_pic.setImageResource(R.mipmap.bailixuance_icon);
               }else if(mGroup.getCheckedRadioButtonId()==m5.getId()){
                   final_allpic = "bianque";
                   final_headpic = "bianque_icon";
                   head_pic.setImageResource(R.mipmap.bianque_icon);
               }else if(mGroup.getCheckedRadioButtonId()==m6.getId()){
                   final_allpic = "chengjisihan";
                   final_headpic = "chengjisihan_icon";
                   head_pic.setImageResource(R.mipmap.chengjisihan_icon);
               }else if(mGroup.getCheckedRadioButtonId()==m7.getId()){
                   final_allpic = "chengyaojin";
                   final_headpic = "chengyaojin_icon";
                   head_pic.setImageResource(R.mipmap.chengyaojin_icon);
               }else if(mGroup.getCheckedRadioButtonId()==m8.getId()){
                   final_allpic = "shenmengxi";
                   final_headpic = "shenmengxi_icon";
                   head_pic.setImageResource(R.mipmap.shenmengxi_icon);
               }else if(mGroup.getCheckedRadioButtonId()==m9.getId()){
                   final_allpic = "damo";
                   final_headpic = "damo_icon";
                   head_pic.setImageResource(R.mipmap.damo_icon);
               }else if(mGroup.getCheckedRadioButtonId()==m10.getId()){
                   final_allpic = "dianwei";
                   final_headpic = "dianwei_icon";
                   head_pic.setImageResource(R.mipmap.baiqi_icon);
               }else if(mGroup.getCheckedRadioButtonId()==m11.getId()){
                   final_allpic = "direnjie";
                   final_headpic = "direnjie_icon";
                   head_pic.setImageResource(R.mipmap.direnjie_icon);
               }else if(mGroup.getCheckedRadioButtonId()==m12.getId()){
                   final_allpic = "donghuangtaiyi";
                   final_headpic = "donghuangtaiyi_icon";
                   head_pic.setImageResource(R.mipmap.donghuangtaiyi_icon);
               }else if(mGroup.getCheckedRadioButtonId()==m13.getId()){
                   final_allpic = "dunshan";
                   final_headpic = "dunshan_icon";
                   head_pic.setImageResource(R.mipmap.dunshan_icon);
               }else if(mGroup.getCheckedRadioButtonId()==f1.getId()){
                   final_allpic = "ake";
                   final_headpic = "ake_icon";
                   head_pic.setImageResource(R.mipmap.ake_icon);
               }else if (mGroup.getCheckedRadioButtonId()==f2.getId()) {
                   final_allpic = "anqila";
                   final_headpic = "anqila_icon";
                   head_pic.setImageResource(R.mipmap.anqila_icon);
               }else if (mGroup.getCheckedRadioButtonId()==f3.getId()){
                   final_allpic = "buzhihuowu";
                   final_headpic = "buzhihuowu_icon";
                   head_pic.setImageResource(R.mipmap.buzhihuowu_icon);
               }else if (mGroup.getCheckedRadioButtonId()==f4.getId()){
                   final_allpic = "caiwenji";
                   final_headpic = "caiwenji_icon";
                   head_pic.setImageResource(R.mipmap.caiwenji_icon);
               }else if (mGroup.getCheckedRadioButtonId()==f5.getId()){
                   final_allpic = "daji";
                   final_headpic = "daji_icon";
                   head_pic.setImageResource(R.mipmap.anqila_icon);
               }else if (mGroup.getCheckedRadioButtonId()==f6.getId()){
                   final_allpic = "daqiao";
                   final_headpic = "daqiao_icon";
                   head_pic.setImageResource(R.mipmap.daqiao_icon);
               }else if (mGroup.getCheckedRadioButtonId()==f7.getId()){
                   final_allpic = "diaochan";
                   final_headpic = "diaochan_icon";
                   head_pic.setImageResource(R.mipmap.diaochan_icon);
               }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
    public void findView(){
        submit = (Button)findViewById(R.id.submit_button);
        sex_choose = (RadioGroup)findViewById(R.id.update_sex);
        male = (RadioButton)findViewById(R.id.male);
        female = (RadioButton)findViewById(R.id.female);
        type_choose1 = (RadioGroup)findViewById(R.id.update_type1);
        type_choose2 = (RadioGroup)findViewById(R.id.update_type2);
        tank = (RadioButton)findViewById(R.id.tank);
        warrior = (RadioButton)findViewById(R.id.warrior);
        assassin = (RadioButton)findViewById(R.id.assassin);
        wizard = (RadioButton)findViewById(R.id.wizard);
        shooter = (RadioButton)findViewById(R.id.shooter);
        assist = (RadioButton)findViewById(R.id.assist);
        head_pic = (RoundImageView)findViewById(R.id.update_head);

        update_name = (EditText)findViewById(R.id.update_name);
        update_title = (EditText)findViewById(R.id.update_title);
        update_life = (EditText)findViewById(R.id.update_life);
        update_skill = (EditText) findViewById(R.id.update_skill);
        update_attack = (EditText) findViewById(R.id.update_attack);
        update_difficulty = (EditText) findViewById(R.id.update_difficulty);
        update_achievement = (EditText) findViewById(R.id.update_achievement);

        dbHelper = new MyDatabaseHelper(this,"Character.db",null,1);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_hero);
        //初始化
        findView();

        //接收intent信息
        Intent intent=this.getIntent();
        Bundle bundle = intent.getExtras();
        final int from_activity = bundle.getInt("activity",0);
        final String charac[] = bundle.getStringArray("stringArr");

        //处理界面
        if(from_activity==1) {
            final_allpic = "caocao";
            final_headpic = "caocao_icon";
            head_pic.setImageResource(getResources().getIdentifier("caocao_icon","mipmap",getPackageName()));
        }
        if(from_activity==2){
            final_allpic = charac[0];
            final_headpic = charac[1];

            submit.setText("修改");
            String name = new String(charac[2]);
            String type = new String(charac[3]);
            String title = new String(charac[4]);
            String sex = new String(charac[5]);
            String survival = new String(charac[6]);
            String attack = new String(charac[7]);
            String skill = new String(charac[8]);
            String difficulty = new String(charac[9]);
            String story = new String(charac[10]);

            update_name.setText(name);
            update_title.setText(title );
            update_life.setText(survival);
            update_attack.setText(attack);
            update_skill.setText(skill);
            update_difficulty.setText(difficulty);
            update_achievement.setText(story);
            head_pic.setImageResource(getResources().getIdentifier(final_headpic,"mipmap",getPackageName()));
        }

        //点击头像
        head_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        //点击位置
        type_choose1.setOnCheckedChangeListener(this);
        type_choose2.setOnCheckedChangeListener(this);
        //点击修改或创建按钮
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final_name = String.valueOf(update_name.getText());
                final_title = String.valueOf(update_title.getText());
                final_survival = String.valueOf(update_life.getText());
                final_attack = String.valueOf(update_attack.getText());
                final_skill = String.valueOf(update_skill.getText());
                final_difficulty = String.valueOf(update_difficulty.getText());
                final_story = String.valueOf(update_achievement.getText());

                if(sex_choose.getCheckedRadioButtonId()==male.getId()){
                    final_sex = "男";
                }else{
                    final_sex = "女";
                }

                if(type_choose1.getCheckedRadioButtonId()==tank.getId()) {
                    final_type="坦克";
                    wizard.setChecked(false);
                    shooter.setChecked(false);
                    assist.setChecked(false);
                }
                else if(type_choose1.getCheckedRadioButtonId()==warrior.getId()) {
                    final_type="战士";
                    wizard.setChecked(false);
                    shooter.setChecked(false);
                    assist.setChecked(false);
                }
                else if(type_choose1.getCheckedRadioButtonId()==assassin.getId()) {
                    final_type="刺客";
                    wizard.setChecked(false);
                    shooter.setChecked(false);
                    assist.setChecked(false);
                }
                else if(type_choose2.getCheckedRadioButtonId()==wizard.getId()) {
                    final_type="法师";
                    tank.setChecked(false);
                    warrior.setChecked(false);
                    assassin.setChecked(false);
                }
                else if(type_choose2.getCheckedRadioButtonId()==shooter.getId()) {
                    final_type="射手";
                    tank.setChecked(false);
                    warrior.setChecked(false);
                    assassin.setChecked(false);
                }
                else {
                    final_type="辅助";
                    tank.setChecked(false);
                    warrior.setChecked(false);
                    assassin.setChecked(false);
                }

                if(final_name.equals("")){
                    Toast.makeText(ThirdActivity.this,"姓名不能为空",Toast.LENGTH_SHORT).show();
                } else {
                    SQLiteDatabase db=dbHelper.getWritableDatabase();
                    if(from_activity==1){
                        db.execSQL("insert into Character (photo1,photo,name,type,title,sex,survival,attack,skill,difficulty,story) values(?,?,?,?,?,?,?,?,?,?,?)",
                        new String[]{final_allpic, final_headpic, final_name, final_type, final_title, final_sex, final_survival, final_attack, final_skill, final_difficulty, final_story});

                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("type", final_type);
                        bundle.putString("name", final_name);
                        bundle.putString("photo1", final_allpic);

                        intent.putExtras(bundle);
                        setResult(31, intent);
                        Log.d("ThirdActivity", "onClick: OKThirdtoMain");
                        finish();
                    }else if(from_activity==2){
                        db.execSQL("update Character set photo1=?,photo=?,name=?,type=?,title=?,sex=?,survival=?,attack=?,skill=?,difficulty=?,story=? where name=?",
                        new String[]{final_allpic, final_headpic, final_name, final_type, final_title, final_sex, final_survival, final_attack, final_skill, final_difficulty, final_story, charac[2]});

                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putStringArray("detail",new String[]{final_allpic, final_headpic, final_name, final_type, final_title, final_sex, final_survival, final_attack, final_skill, final_difficulty, final_story});

                        intent.putExtras(bundle);
                        setResult(32, intent);
                        Log.d("ThirdActivity", "onClick: OKSecondtoMain");
                        finish();
                    }
                }
            }
        });

        Button back = (Button)findViewById(R.id.return_button);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setResult(0, null); finish();
            }
        });
    }
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group != null && changeGroup == false) {
            if (group == type_choose1) {
                changeGroup = true;
                type_choose2.clearCheck();
                changeGroup = false;
                if(type_choose1.getCheckedRadioButtonId()==tank.getId()) {
                    final_type="坦克";
                }
                else if(type_choose1.getCheckedRadioButtonId()==warrior.getId()) {
                    final_type="战士";
                }
                else if(type_choose1.getCheckedRadioButtonId()==assassin.getId()) {
                    final_type="刺客";
                }
            } else if (group == type_choose2) {
                changeGroup = true;
                type_choose1.clearCheck();
                changeGroup = false;
                if(type_choose2.getCheckedRadioButtonId()==wizard.getId()) {
                    final_type="法师";
                }
                else if(type_choose2.getCheckedRadioButtonId()==shooter.getId()) {
                    final_type="射手";
                }
                else if(type_choose2.getCheckedRadioButtonId()==assist.getId()){
                    final_type="辅助";
                }
            }
        }
    }


}
