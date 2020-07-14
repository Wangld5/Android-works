package com.experiment.wangld.httpapi2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.internal.bind.MapTypeAdapterFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.util.ArrayListSupplier;
import io.reactivex.internal.util.ListAddBiConsumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class IssueActivity extends AppCompatActivity {
    IssueService issueService;
    String repoName;
    String userName;
    Boolean has_issues;
    RecyclerView rv;
    ArrayList<Issues> list;
    IssueAdapter issueAdapter;

    private Button issueButton;
    private EditText titleEdit;
    private EditText bodyEdit;
    private EditText tokenEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.github_issues);
        findView();
        Intent intent = getIntent();
        repoName = intent.getStringExtra("repoName");
        userName = intent.getStringExtra("userName");
        has_issues = intent.getBooleanExtra("has_issues", false);
        issueService = IssueServiceFactory.getInstance().getIssueService();

        list = new ArrayList<Issues>();
        issueAdapter = new IssueAdapter(list);
        rv.setAdapter(issueAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        if(!has_issues){
            Toast.makeText(IssueActivity.this, "该项目没有issue", Toast.LENGTH_SHORT).show();
        }
        else{
            getIssues(userName, repoName);
        }
        issueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = "token 8e24fd62d28be0059ea781ffbe632b6511f4d23f";
                String title = titleEdit.getText().toString();
                String body = bodyEdit.getText().toString();
                HashMap<String, String> map = new HashMap<>();
                map.put("title", title);
                map.put("body", body);
                String json= new Gson().toJson(map);//要传递的json
                RequestBody requestBody= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json);
                postIssues(token, userName, repoName, requestBody);
            }
        });
    }
    private void findView(){
        rv = (RecyclerView) findViewById(R.id.githubIssueRecyclerContent);
        issueButton = (Button) findViewById(R.id.issueButton);
        titleEdit = (EditText) findViewById(R.id.titleEdit);
        bodyEdit = (EditText) findViewById(R.id.bodyEdit);
        tokenEdit = (EditText) findViewById(R.id.tokenEdit);
    }
    private void getIssues(String userName, String repoName){
        Observable<List<Issues>> issues = issueService.getIssue(userName, repoName);
        issues.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Issues>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Issues> issues) {
                        list.clear();
                        for(int i=0; i<issues.size(); i++){
                            list.add(issues.get(i));
                        }
                        issueAdapter.updateData(list);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    private void postIssues(String token, String userName, String repoName, RequestBody requestBody){
        Observable<Issues> issues = issueService.postIssue(token, userName, repoName, requestBody);
        issues.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Issues>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Issues issues) {
                        list.add(issues);
                        issueAdapter.updateData(list);
                        Toast.makeText(IssueActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(IssueActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
                        Log.i("IssueActivity", "onError: "+e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
