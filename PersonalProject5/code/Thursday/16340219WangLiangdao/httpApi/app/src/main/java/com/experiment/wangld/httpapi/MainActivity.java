package com.experiment.wangld.httpapi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Observer;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.util.ArrayListSupplier;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private DataService dataService = DataServiceFactory.getInstance().getDataService();
    private ArrayList<RecyclerObj> list;
    private RecycleAdapter recycleAdapter;
    private RecyclerView rv;

    private EditText inputUserId;
    private Button search;
    private ProgressBar progressBar;
    private MyImageView myImageView;
    private SeekBar seekBar;
    private TextView playContent, commentContent, timeContent, createTimeContent, titleContent, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        list = new ArrayList<RecyclerObj>();
        recycleAdapter = new RecycleAdapter(list);
        rv.setAdapter(recycleAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = inputUserId.getText().toString();
                Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
                if(!pattern.matcher(userID).matches()){
                    Toast.makeText(MainActivity.this, "需要整数数据类型", Toast.LENGTH_SHORT).show();
                }
                else if (!isNetworkConnected(MainActivity.this)){
                    Toast.makeText(MainActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                }
                else if(!isDataBaseExist(userID)){
                    Toast.makeText(MainActivity.this, "数据库中不存在记录", Toast.LENGTH_SHORT).show();
                }else{

                    getUser(userID);
                }
            }
        });
    }
    private void getUser(String userId){

        dataService.getUser(userId).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.Observer<RecyclerObj>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RecyclerObj value) {
                        list.add(value);
                        recycleAdapter.updateData(list);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    private boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
             NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
             if (mNetworkInfo != null) {
                //mNetworkInfo.isAvailable();
                return true;//有网
             }
        }
        return false;//没有网
    }
    private boolean isDataBaseExist(String userID){
        int id = Integer.valueOf(userID);
        if (id > 40) return false;
        //[2,7,10,19,20,24,32]都存在数据，需要正确显示
        if(id != 2 && id != 7 && id != 10 && id != 19 && id != 20 && id != 24 && id != 32) return false;
        return true;
    }
    private void findView(){
        inputUserId = (EditText) findViewById(R.id.userId);
        search = (Button) findViewById(R.id.search);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        myImageView = (MyImageView) findViewById(R.id.picture);
        seekBar = (SeekBar) findViewById(R.id.seek);
        playContent = (TextView) findViewById(R.id.playcontent);
        commentContent = (TextView) findViewById(R.id.commentcontent);
        timeContent = (TextView) findViewById(R.id.timecontent);
        createTimeContent = (TextView) findViewById(R.id.createTimeContent);
        titleContent = (TextView) findViewById(R.id.titleContent);
        content = (TextView) findViewById(R.id.picContent);
        rv = (RecyclerView) findViewById(R.id.recyclerContent);
    }
}
