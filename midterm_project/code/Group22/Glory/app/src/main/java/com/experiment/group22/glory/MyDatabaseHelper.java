package com.experiment.group22.glory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String create_Character = "create table Character ("
            +"id integer primary key autoincrement, "
            +"photo1 text,"
            +"photo text, "
            +"name text, "
            +"type text, "
            +"title text, "
            +"sex text, "
            +"survival text, "
            +"attack text, "
            +"skill text, "
            +"difficulty text,"
            +"story text)";
    public String Character[][]={
            {"ake", "ake_icon", "阿轲", "刺客", "绝对双刃", "女", "20", "70", "70", "60", "无"},
            {"anqila", "anqila_icon", "安琪拉", "法师", "魔法少女", "女", "20", "70", "70", "70", "无"}

    };

    private Context mContext;
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(create_Character);
        for(int i=0;i<Character.length;i++){
            db.execSQL("insert into Character (photo1,photo,name,type,title,sex,survival,attack,skill,difficulty,story)" +
                    " values(?,?,?,?,?,?,?,?,?,?,?)",Character[i]);
        }
        //Log.d("insert", "onClick:insert this ");
        //Toast.makeText(mContext,"create succeeded",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
