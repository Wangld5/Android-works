package com.experiment.wangld.storage2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

public class nyDB extends SQLiteOpenHelper {
    public static final String DB_NAME = "database.db";
    public static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "userdata";
    public nyDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE if not exists "
                + TABLE_NAME
                + " (_id INTEGER PRIMARY KEY, name TEXT, password TEXT, head blob, comment TEXT, time TEXT, commentPeople TEXT, goodNum INTEGER)";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int ii) {

    }
    public void savePasswordAndUsername(String password, String username, byte[] img){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", username);
        cv.put("password", password);
        cv.put("head", img);
        db.insert("userdata", null, cv);
        db.close();
    }
    public void saveComment(String username, String comment, String time){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("comment", comment);
        cv.put("time", time);
        String[] args = {username};
        db.update("userdata", cv, "name=?", args);
        db.close();
    }
    public void deleteComment(String username){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("comment", "");
        String[] args = {username};
        db.update("userdata", cv, "name=?", args);
        db.close();
    }
    public boolean checkIfUserExist(String username){
        SQLiteDatabase db=getWritableDatabase();
        String Query = "Select * from userdata where name =?";
        Cursor cursor = db.rawQuery(Query,new String[] { username });
        if (cursor.getCount()>0){
            cursor.close();
            return  true;
        }
        cursor.close();
        return false;
    }
    public boolean checkIfPasswordCorrect(String password){
        SQLiteDatabase db=getWritableDatabase();
        String Query = "Select * from userdata where password =?";
        Cursor cursor = db.rawQuery(Query,new String[] { password });
        if (cursor.getCount()>0){
            cursor.close();
            return  true;
        }
        cursor.close();
        return false;
    }
    public byte[] img2byte(Drawable drawable){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
    public Bitmap getBmp(String temp_username){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        byte[] in = new byte[10000];
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            String username = cursor.getString(cursor.getColumnIndex("name"));
            if (temp_username.equals(username)) {
                in = cursor.getBlob(cursor.getColumnIndex("head"));
                break;
            }
            cursor.moveToNext();
        }
        Bitmap bmpout = BitmapFactory.decodeByteArray(in, 0, in.length);
        db.close();
        return bmpout;
    }
    public List<commentInfo> getCommentInfo() {
        List<commentInfo> data = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        int Num = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Log.d(TAG, "getCommentInfo: asd");
            String username = cursor.getString(cursor.getColumnIndex("name"));
            byte[] in = cursor.getBlob(cursor.getColumnIndex("head"));
            Bitmap bmpout = BitmapFactory.decodeByteArray(in, 0, in.length);
            Drawable head = new BitmapDrawable(bmpout);
            String content = cursor.getString(cursor.getColumnIndex("comment"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            if (content != null && !content.equals("")) {
                commentInfo save_info = new commentInfo(head, username, content, time);
                data.add(save_info);
            }
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        if(data.isEmpty())
            return new ArrayList<>();
        return data;
    }
    public int saveWhoComment(String commentPeople, String temp_username){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        String getCommentPeople = "";
        int goodNum = 0;
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            String username = cursor.getString(cursor.getColumnIndex("name"));
            if (temp_username.equals(username)) {
                getCommentPeople = cursor.getString(cursor.getColumnIndex("commentPeople"));
                goodNum = cursor.getInt(cursor.getColumnIndex("goodNum"));
                break;
            }
            cursor.moveToNext();
        }
        if(getCommentPeople != null && getCommentPeople != ""){
            getCommentPeople = getCommentPeople + commentPeople + ",";
            goodNum++;
        }else{
            getCommentPeople = commentPeople + ",";
            goodNum++;
        }
        ContentValues cv = new ContentValues();
        cv.put("commentPeople", getCommentPeople);
        cv.put("goodNum", goodNum);
        String[] args = {temp_username};
        db.update("userdata", cv, "name=?", args);
        db.close();
        Log.d(TAG, "saveWhoComment: " + getCommentPeople);
        return goodNum;
    }
    public boolean checkIfPeopleComment(String commentPeople, String temp_username){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        String getCommentPeople = "";
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            String username = cursor.getString(cursor.getColumnIndex("name"));
            if (temp_username.equals(username)) {
                getCommentPeople = cursor.getString(cursor.getColumnIndex("commentPeople"));
                break;
            }
            cursor.moveToNext();
        }
        db.close();
        if(getCommentPeople == null || getCommentPeople == ""){
            return false;
        }
        Log.d(TAG, "checkIfPeopleComment: " + getCommentPeople);
        String [] commentPeopleList = getCommentPeople.split(",");
        System.out.println(commentPeopleList);
        boolean ifExist = Arrays.asList(commentPeopleList).contains(commentPeople);
        return ifExist;
    }
    public int deleteWhoComment(String commentPeople, String temp_username) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        String getCommentPeople = "";
        int goodNum = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String username = cursor.getString(cursor.getColumnIndex("name"));
            if (temp_username.equals(username)) {
                getCommentPeople = cursor.getString(cursor.getColumnIndex("commentPeople"));
                goodNum = cursor.getInt(cursor.getColumnIndex("goodNum"));
                break;
            }
            cursor.moveToNext();
        }
        if (getCommentPeople == null) {
            return 0;
        }
        String[] commentPeopleList = getCommentPeople.split(",");
        List<String> list=Arrays.asList(commentPeopleList);
        List<String> arrayList=new ArrayList<String>(list);
        if(list.contains(commentPeople)){
            arrayList.remove(commentPeople);
        }
        String temp = "";
        for(int i=0; i<arrayList.size(); i++){
            temp = temp + arrayList.get(i) + ",";
        }
        if (goodNum > 0) goodNum --;
        System.out.println(temp);
        ContentValues cv = new ContentValues();
        cv.put("commentPeople", temp);
        cv.put("goodNum", goodNum);
        String[] args = {temp_username};
        db.update("userdata", cv, "name=?", args);
        db.close();
        Log.d(TAG, "deleteWhoComment: " + temp);
        return goodNum;
    }
    public int getGoodNum(String temp_username){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        int num = 0;
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            String username = cursor.getString(cursor.getColumnIndex("name"));
            if (temp_username.equals(username)) {
                num = cursor.getInt(cursor.getColumnIndex("goodNum"));
                break;
            }
            cursor.moveToNext();
        }
        db.close();
        return num;
    }


}
