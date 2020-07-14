package com.experiment.wangld.httpapi2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class githubActivity extends AppCompatActivity {
    private EditText inputUser;
    private Button githubSearch;
    private RepoService repoService;
    ArrayList<Repo> list;
    RepoAdapter repoAdapter;
    private RecyclerView rv;

    private void findView(){
        inputUser = (EditText) findViewById(R.id.githubuserId);
        githubSearch = (Button) findViewById(R.id.githubsearch);
        rv = (RecyclerView) findViewById(R.id.githubrecyclerContent);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.github_main);
        findView();
        list = new ArrayList<Repo>();
        repoAdapter = new RepoAdapter(list);
        repoAdapter.setOnItemClickListener(new RepoAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(githubActivity.this, IssueActivity.class);
                intent.putExtra("repoName", list.get(position).getName());
                intent.putExtra("userName", inputUser.getText().toString());
                intent.putExtra("has_issues", list.get(position).getHas_issues());
                startActivity(intent);
            }
        });
        rv.setAdapter(repoAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        githubSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputUser.getText().toString();
                getUser(name);
            }
        });
        repoService = RepoServiceFactory.getInstance().getRepoService();
    }
    private void getUser(String name){
        Observable<List<Repo>> repos = repoService.getRepo(name);
        repos.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Repo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Repo> repos) {
                        list.clear();
                        if(repos.size() == 0){
                            Toast.makeText(githubActivity.this, "这个用户很懒，没有项目", Toast.LENGTH_SHORT).show();
                        }
                        for(int i=0; i<repos.size(); i++){
                            list.add(repos.get(i));
                        }
                        repoAdapter.updateData(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(githubActivity.this, "http404:Not Found", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
