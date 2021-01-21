package com.qdat.secretslambook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qdat.secretslambook.Contract.Contract;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class SettingActivity extends AppCompatActivity {

    LinearLayout passwordView , backgroundImageSelector;
    Button importButton,exportButton,done , removeBackground;
    RelativeLayout passwordEnterance;
    ImageView backgroundImage,close;
    EditText input;
    TextView passwordText , backgroundImageText;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle("Settings");

        init();
        backgroundImageText.setText("Click to choose");
        passwordText.setText(preferences.getString(Contract.APP_PASSWORD,"12345"));
        passwordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordEnterance.setVisibility(View.VISIBLE);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordEnterance.setVisibility(View.GONE);
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.getText().toString().length()!=0){
                    String newPass = input.getText().toString();
                    editor.putString(Contract.APP_PASSWORD,newPass);
                    passwordText.setText(newPass);
                    editor.apply();
                    passwordEnterance.setVisibility(View.GONE);
                }else {
                    Toast.makeText(SettingActivity.this, "Please fill the password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backgroundImageSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                it.setType("image/*");
                it.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(it,REQUEST_CODE);
            }
        });
        removeBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/SecretSlambook/wallpaper/");
                File file = new File(dir.getAbsolutePath()+"/bgImage.jpg/");
                boolean bol = file.delete();
                Toast.makeText(SettingActivity.this, "Restart Your app", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSucces = export();
                if (isSucces){
                    Toast.makeText(SettingActivity.this, "Exported Succesfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SettingActivity.this, "Export Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSucces = importDairy();
                if (isSucces){
                    Toast.makeText(SettingActivity.this, "Imported Succesfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SettingActivity.this, "Import Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init(){
        passwordText = findViewById(R.id.password_text);
        passwordView = findViewById(R.id.password_view);
        removeBackground = findViewById(R.id.remove_background);
        backgroundImageText = findViewById(R.id.background_image_text);
        backgroundImage = findViewById(R.id.background_image);
        backgroundImageSelector = findViewById(R.id.background_image_selector);
        importButton = findViewById(R.id.import_dairy);
        exportButton = findViewById(R.id.export_dairy);
        input = findViewById(R.id.password_taker);
        done = findViewById(R.id.doneButton);
        passwordEnterance = findViewById(R.id.password_enterance);
        close = findViewById(R.id.close);
        preferences = this.getSharedPreferences(Contract.PREFRANCE_NAME,MODE_PRIVATE);
        editor = preferences.edit();

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==REQUEST_CODE && resultCode == RESULT_OK && data.getData()!=null){

            try{

                Bitmap map = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));

                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/SecretSlambook/wallpaper/");
                if(!dir.exists()){
                    dir.mkdir();
                }
                File file = new File(dir,"bgImage.jpg");
                backgroundImage.setImageBitmap(map);
                map.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(file));


            }catch (Exception e){}


        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);



        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/SecretSlambook/wallpaper/");
        if (dir.exists() == false || dir.list().length==0){
            backgroundImage.setImageResource(R.drawable.default_bg);
        }
        else{
            String arr[] = dir.list();

            Bitmap bitmap = BitmapFactory.decodeFile(dir.getAbsolutePath()+"/"+arr[0]);
            backgroundImage.setImageBitmap(bitmap);
        }



    }

    private boolean export(){

        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/SecretSlambook/export/");
        if (!dir.exists()){
            dir.mkdir();
        }
        File file = new File(dir,"exportedDairy.quanta");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Cursor cursor = getContentResolver().query(Contract.PATH_PAGES,null,null,null,null);
        try {
            PrintWriter writer = new PrintWriter(file);

            if (cursor.moveToFirst()){
                do {

                    String title = cursor.getString(cursor.getColumnIndex(Contract.PAGE_TITLE));
                    String body = cursor.getString(cursor.getColumnIndex(Contract.PAGE_BODY));
                    String date = cursor.getString(cursor.getColumnIndex(Contract.PAGE_DATE));
                    String image = cursor.getString(cursor.getColumnIndex(Contract.PAGE_IMAGE));

                    String query = ""+title+"x//xx//--//b-"+body+"x//xx//--//b-"+date+"x//xx//--//b-"+image;
                    writer.println(query);
                    writer.flush();

                }while (cursor.moveToNext());
                return true;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }



        return false;
    }

    private boolean importDairy(){
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/SecretSlambook/export/");
        if (!dir.exists()){
            return false;
        }
        File file = new File(dir,"exportedDairy.quanta");
        if (!file.exists()){
            return false;
        }
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                String str = scanner.nextLine();
                String arr[] = str.split("x//xx//--//b-");
                if (arr.length<4){
                    return false;
                }
                ContentValues values = new ContentValues();
                values.put(Contract.PAGE_TITLE,arr[0]);
                values.put(Contract.PAGE_BODY,arr[1]);
                values.put(Contract.PAGE_DATE,arr[2]);
                values.put(Contract.PAGE_IMAGE,arr[3]);

                getContentResolver().insert(Contract.PATH_PAGES,values);





            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


}