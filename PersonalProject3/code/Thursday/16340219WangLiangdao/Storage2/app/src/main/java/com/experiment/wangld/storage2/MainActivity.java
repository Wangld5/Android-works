package com.experiment.wangld.storage2;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    private RadioGroup radioGroup;
    private RelativeLayout login, register;
    private Boolean IsChange = false;
    private Button loginOK, loginclear, registerOK, registerclear;
    private ImageView imageButton;
    private EditText loginUsername, password, registerUsername, newPassword, confirmPassword;
    private String preserveUsername;
    private nyDB myDB;
    private static int RESULT_LOAD_IMAGE = 1;
    private Drawable drawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new nyDB(this);
        findView();
        if(!IsChange){
            clickLogin();
            password.setText("");
            loginOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String pw_str = password.getText().toString();
                    String un_str = loginUsername.getText().toString();
                    Boolean isCorrect_user = myDB.checkIfUserExist(un_str);
                    Boolean isCorrect_pw = myDB.checkIfPasswordCorrect(pw_str);
                    Boolean isEmpty_user = un_str.equals("");
                    Boolean isEmpty_pw = pw_str.equals("");
                    if(isEmpty_user){
                        Toast.makeText(MainActivity.this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                    else if(isEmpty_pw){
                        Toast.makeText(MainActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                    else if(!isCorrect_user){
                        Toast.makeText(MainActivity.this, "Username not existed", Toast.LENGTH_SHORT).show();
                    }
                    else if(!isCorrect_pw){
                        Toast.makeText(MainActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Intent intent = new Intent(MainActivity.this, comment.class);
                        Bundle data = new Bundle();
                        data.putString("name", un_str);
                        data.putString("password", pw_str);
                        intent.putExtras(data);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            loginclear.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    password.setText("");
                    loginUsername.setText("");
                }
            });
        }
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        if (checkedId == R.id.loginButton) {
            clickLogin();
            password.setText("");
            loginOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String pw_str = password.getText().toString();
                    String un_str = loginUsername.getText().toString();
                    Boolean isCorrect_user = myDB.checkIfUserExist(un_str);
                    Boolean isCorrect_pw = myDB.checkIfPasswordCorrect(pw_str);
                    Boolean isEmpty_user = un_str.equals("");
                    Boolean isEmpty_pw = pw_str.equals("");
                    if(isEmpty_user){
                        Toast.makeText(MainActivity.this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                    else if(isEmpty_pw){
                        Toast.makeText(MainActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                    else if(!isCorrect_user){
                        Toast.makeText(MainActivity.this, "Username not existed", Toast.LENGTH_SHORT).show();
                    }
                    else if(!isCorrect_pw){
                        Toast.makeText(MainActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Intent intent = new Intent(MainActivity.this, comment.class);
                        Bundle data = new Bundle();
                        data.putString("name", un_str);
                        data.putString("password", pw_str);
                        intent.putExtras(data);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            loginclear.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    password.setText("");
                    loginUsername.setText("");
                }
            });
        } else if (checkedId == R.id.registerButton) {
            drawable = this.getResources().getDrawable(R.drawable.me);
            clickRegister();
            newPassword.setText("");
            confirmPassword.setText("");
            registerOK.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    String pw_str = newPassword.getText().toString();
                    String confirm_str = confirmPassword.getText().toString();
                    String un_str = registerUsername.getText().toString();
                    boolean empty_pw = pw_str.equals("") ||  confirm_str.equals("");
                    boolean empty_user = un_str.equals("");
                    boolean same = pw_str.equals(confirm_str);
                    boolean exist = myDB.checkIfUserExist(un_str);
                    if(exist){
                        Toast.makeText(MainActivity.this, "username existed", Toast.LENGTH_SHORT).show();
                    }
                    else if(empty_pw){
                        Toast.makeText(MainActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                    else if(empty_user){
                        Toast.makeText(MainActivity.this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                    else if(!same){
                        Toast.makeText(MainActivity.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        byte[] img = myDB.img2byte(drawable);
                        myDB.savePasswordAndUsername(pw_str, un_str, img);
                        Intent intent = new Intent(MainActivity.this, comment.class);
                        Bundle data = new Bundle();
                        data.putString("name", un_str);
                        data.putString("password", pw_str);
                        intent.putExtras(data);
                        startActivityForResult(intent, 1);
                        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            registerclear.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    newPassword.setText("");
                    confirmPassword.setText("");
                    registerUsername.setText("");
                }
            });
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                6);
                    }
                    else{
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, RESULT_LOAD_IMAGE);
                    }
                }
            });
        }
    }
    private void findView(){
        login = (RelativeLayout) findViewById(R.id.login);
        register = (RelativeLayout) findViewById(R.id.register);
        radioGroup = (RadioGroup) findViewById(R.id.chose);
        loginOK = (Button)findViewById(R.id.loginOK);
        loginclear = (Button) findViewById(R.id.loginclear);
        registerOK = (Button) findViewById(R.id.registerOK);
        registerclear = (Button) findViewById(R.id.registerclear);
        loginUsername = (EditText) findViewById(R.id.loginUsername);
        registerUsername = (EditText) findViewById(R.id.registerUsername);
        password = (EditText) findViewById(R.id.Password);
        newPassword = (EditText) findViewById(R.id.NewPassword);
        confirmPassword = (EditText) findViewById(R.id.ComfirmPassword);
        imageButton = (ImageView) findViewById(R.id.imageButton);
    }
    private void clickLogin(){
        login.setVisibility(View.VISIBLE);
        register.setVisibility(View.INVISIBLE);
    }
    private void clickRegister(){
        login.setVisibility(View.INVISIBLE);
        register.setVisibility(View.VISIBLE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == RESULT_LOAD_IMAGE
                    && resultCode == Activity.RESULT_OK)
                showYourPic(data);
        }
    }
    private void showYourPic(Intent data) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };

        Cursor cursor = getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();

        if (picturePath.equals(""))
            return;

        String pic_path = picturePath; // 保存所添加的图片的路径

        // 缩放图片, width, height 按相同比例缩放图片
        BitmapFactory.Options options = new BitmapFactory.Options();
        // options 设为true时，构造出的bitmap没有图片，只有一些长宽等配置信息，但比较快，设为false时，才有图片
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(picturePath, options);
        int scale = (int) (options.outWidth / (float) 300);
        if (scale <= 0)
            scale = 1;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(picturePath, options);

        drawable = new BitmapDrawable(bitmap);
        imageButton.setImageDrawable(drawable);
        imageButton.setMaxHeight(400);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 6)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            } else
            {
                // Permission Denied
                Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
