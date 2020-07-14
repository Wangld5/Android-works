package com.example.administrator.helloandroid;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText editText;
    private RadioButton radioButton;
    private RadioGroup radioGroup;
    private String buttonText = "图片";
    public void dialog(String message){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("提示").setMessage(message).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String backInfo = "对话框“确定”按钮被点击";
                Toast.makeText(MainActivity.this, backInfo, Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String backInfo = "对话框“取消”按钮被点击";
                Toast.makeText(MainActivity.this, backInfo, Toast.LENGTH_SHORT).show();
            }
        }).create().show();
    }
    public void getNameOfRadioButton(){
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                buttonText = radioButton.getText().toString();
                Toast.makeText(MainActivity.this, buttonText+"被选中", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.btn0);
        editText = (EditText) findViewById(R.id.Edit0);
        getNameOfRadioButton();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btn0:
                        String inputText = editText.getText().toString();
                        //Toast.makeText(MainActivity.this, inputText, Toast.LENGTH_SHORT).show();
                        Log.d("test", inputText);
                        String message = "";
                        if(inputText.equals("")){
                            String tip = "搜索内容不能为空";
                            Toast.makeText(MainActivity.this,tip,Toast.LENGTH_SHORT).show();
                        }
                        else if (inputText.equals("Health")){
                            message = buttonText + "搜索成功";
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                            dialog(message);
                        }
                        else {
                            message = "搜索失败";
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                            dialog(message);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
