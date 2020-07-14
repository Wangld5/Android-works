package com.experiment.wangld.storage;

import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText pw;
    EditText newPw;
    EditText confirmPw;
    Button ok;
    Button clear;

    final String PREFERENCE_NAME = "fileEditor";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isPasswordSet()){
            setContentView(R.layout.login);
            findView();
            ok.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    String pw_str = pw.getText().toString();
                    String checkPw = getPassword();
                    Boolean isCorrect = pw_str.equals(checkPw);
                    if(isCorrect){
                        Intent intent = new Intent(MainActivity.this, Editfile.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            clear.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    pw.setText("");
                }
            });
        }else{
            setContentView(R.layout.activity_main);
            findView();
            ok.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    String pw_str = newPw.getText().toString();
                    String confirm_str = confirmPw.getText().toString();
                    boolean empty = pw_str.equals("");
                    boolean same = pw_str.equals(confirm_str);
                    if(empty){
                        Toast.makeText(MainActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                    else if(!same){
                        Toast.makeText(MainActivity.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        savePassword(pw_str);
                    }
                }
            });
            clear.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    newPw.setText("");
                    confirmPw.setText("");
                }
            });
        }
    }
    private void savePassword(String pw){
        SharedPreferences sp = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("password", pw);
        editor.putBoolean("isPasswordSet", true);
        editor.commit();
    }
    private Boolean isPasswordSet(){
        SharedPreferences sp = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        return sp.getBoolean("isPasswordSet", false);
    }
    private String getPassword(){
        SharedPreferences sp = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        return sp.getString("password", "xx");
    }
    private void findView(){
        pw = findViewById(R.id.loginPass);
        newPw = findViewById(R.id.newPass);
        confirmPw = findViewById(R.id.confirmPass);
        ok = findViewById(R.id.OK);
        clear = findViewById(R.id.clear);
    }
}
