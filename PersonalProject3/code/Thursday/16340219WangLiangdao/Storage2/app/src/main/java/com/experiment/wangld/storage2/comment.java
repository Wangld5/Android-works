package com.experiment.wangld.storage2;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class comment extends AppCompatActivity {
    private ImageView head, good;
    private TextView commentUser, commentTime, commentContent;
    private TextView goodNum;
    private EditText editComment;
    private Button send;
    private ListView commentListView;
    private commentAdapter myAdapter;
    private List<commentInfo> data;
    private nyDB myDB;
    private boolean isChange = false;
    private void findView(){
        head = (ImageView) findViewById(R.id.head);
        good = (ImageView) findViewById(R.id.good);
        commentUser = (TextView) findViewById(R.id.commentUser);
        commentTime = (TextView) findViewById(R.id.commentTime);
        commentContent = (TextView) findViewById(R.id.commentContent);
        goodNum = (TextView) findViewById(R.id.goodNum);
        editComment = (EditText) findViewById(R.id.editComment);
        send = (Button) findViewById(R.id.send);
        commentListView = (ListView) findViewById(R.id.commentList);
    }
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editfile);
        myDB = new nyDB(this);
        findView();
        initView();
    }
    private void initView(){
        final Bundle User = getIntent().getExtras();
        data = new ArrayList<>();
        data = myDB.getCommentInfo();
        myAdapter = new commentAdapter(comment.this, data, User.getString("name"), myDB);
        commentListView.setAdapter(myAdapter);
        setListener();
    }

    public void setListener(){
        final Bundle User = getIntent().getExtras();
        final String temp_username = User.getString("name");
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editComment.getText().toString().equals("")){
                    Toast.makeText(comment.this, "Comment cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    Bitmap temp_img = myDB.getBmp(temp_username);
                    commentInfo comInfo = new commentInfo();
                    comInfo.setHead(new BitmapDrawable(temp_img));
                    comInfo.setUsername(temp_username);
                    comInfo.setContent(editComment.getText().toString());

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
                    //获取当前时间
                    Date date = new Date(System.currentTimeMillis());

                    comInfo.setTime(simpleDateFormat.format(date));
                    myAdapter.addComment(comInfo);
                    myDB.saveComment(temp_username, editComment.getText().toString(), simpleDateFormat.format(date));
                }
            }
        });
        commentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(comment.this);
                final commentInfo checkInfo = data.get(position);
                Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = \"" + checkInfo.getUsername() + "\"", null, null);
                String number = "\nPhone";
                if(cursor != null && cursor.getCount() > 0){
                    number += ": ";
                    cursor.moveToFirst();
                    do {
                        number += cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) + "         ";
                    } while (cursor.moveToNext());
                }
                else{
                    number += " number not exist";
                }
                Toast.makeText(comment.this, checkInfo.getUsername(), Toast.LENGTH_SHORT).show();
                builder.setTitle("Info")
                        .setMessage("Username: " + checkInfo.getUsername() + number)
                        .setCancelable(true)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(comment.this, checkInfo.getUsername(), Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(comment.this, checkInfo.getUsername(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();


            }
        });
        commentListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(comment.this);
                final commentInfo checkInfo = data.get(position);
                if(checkInfo.getUsername().equals(temp_username)){
                    builder.setTitle("Delete Or Not")
                            .setCancelable(true)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    myDB.deleteComment(checkInfo.getUsername());
                                    myAdapter.removeComment(checkInfo);
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .show();
                }
                else{
                    builder.setTitle("Report Or Not")
                            .setCancelable(true)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // do nothing
                                }
                            })
                            .show();
                }
                return true;
            }
        });
    }
}
