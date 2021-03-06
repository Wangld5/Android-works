package com.experiment.wangld.httpapi2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button bilibili;
    private Button github;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        bilibili.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, bilibiliActivity.class);
                startActivity(intent);
            }
        });
        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, githubActivity.class);
                startActivity(intent);
            }
        });
    }
    private void findView(){
        bilibili = (Button) findViewById(R.id.bilibiliAPI);
        github = (Button) findViewById(R.id.GITHUBAPI);
    }
}
