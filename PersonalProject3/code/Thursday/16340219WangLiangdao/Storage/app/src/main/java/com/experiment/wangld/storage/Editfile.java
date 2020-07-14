package com.experiment.wangld.storage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Editfile extends AppCompatActivity {
    EditText editText;
    Button saveButton;
    Button loadButton;
    Button clearButton;

    final String filename = "testFile";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        findView();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editText.getText().toString();
                try {
                    FileOutputStream outputStream = openFileOutput(filename, MODE_PRIVATE);
                    outputStream.write(content.getBytes());
                    Toast.makeText(Editfile.this, "save successfully", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileInputStream inputStream = openFileInput(filename);
                    byte []content_bytes = new byte[inputStream.available()];
                    inputStream.read(content_bytes);
                    editText.setText(new String(content_bytes));
                    Toast.makeText(Editfile.this, "load successfully", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Editfile.this, "load failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });

    }
    private void findView(){
        editText = findViewById(R.id.edit);
        saveButton = findViewById(R.id.save);
        loadButton = findViewById(R.id.load);
        clearButton = findViewById(R.id.editClear);
    }
    @Override
    public void onBackPressed() {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.addCategory(Intent.CATEGORY_HOME);

        startActivity(home);
    }
}
