package com.qdat.secretslambook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.ContentUris;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.qdat.secretslambook.Contract.Contract;
import com.qdat.secretslambook.adapter.PhotoAdapter;
import com.qdat.secretslambook.utility.Dairy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DairyViewer extends AppCompatActivity {

    Switch aSwitch;
    Dairy dairy;
    RelativeLayout root;
    TextView title,body,date,bye,dear,dark;
    ViewPager pager;
    List<Bitmap> bitmaps;
    PhotoAdapter adapter;
    ImageView delete_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dairy_viewer);

        init();
        loadData();



        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    enableDarkMode();
                }
                else {
                    disableDarkMode();
                }
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getContentResolver().delete(ContentUris.withAppendedId(Contract.PATH_PAGES,dairy.getId()),null,null);
                if(!dairy.getImages().equals("no_image")){

                    String arr[] = dairy.getImages().split(",");
                    for (String str:arr){
                        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/SecretSlambook/"+str);
                        if (file.exists()){
                            file.delete();
                        }
                    }


                }
                finish();

            }
        });

    }
    private void init(){
        root = findViewById(R.id.root);

        root.setBackgroundColor(Color.rgb(255,255,255));

        delete_button = findViewById(R.id.delete_icon);
        dairy = MainActivity.sharedDairy;
        aSwitch = findViewById(R.id.modeSwitch);
        title = findViewById(R.id.title);
        body = findViewById(R.id.body);
        date = findViewById(R.id.date);
        bye = findViewById(R.id.bye);
        dear = findViewById(R.id.dear);
        dark = findViewById(R.id.dark);
        pager = findViewById(R.id.view_pager);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
    }
    private void enableDarkMode(){
        delete_button.setImageResource(R.drawable.ic_baseline_delete_24);
        root.setBackgroundColor(Color.rgb(0,0,0));
        title.setTextColor(Color.rgb(255,255,255));
        body.setTextColor(Color.rgb(255,255,255));
        date.setTextColor(Color.rgb(255,255,255));
        bye.setTextColor(Color.rgb(255,255,255));
        dear.setTextColor(Color.rgb(255,255,255));
        dark.setTextColor(Color.rgb(255,255,255));

    }
    private void disableDarkMode(){
        delete_button.setImageResource(R.drawable.ic_baseline_delete_black);
        root.setBackgroundColor(Color.rgb(255,255,255));
        title.setTextColor(Color.rgb(0,0,0));
        body.setTextColor(Color.rgb(0,0,0));
        date.setTextColor(Color.rgb(0,0,0));
        bye.setTextColor(Color.rgb(0,0,0));
        dear.setTextColor(Color.rgb(0,0,0));
        dark.setTextColor(Color.rgb(0,0,0));

    }
    private void loadData(){
        title.setText(dairy.getTitle());
        body.setText(dairy.getBody());
        date.setText(dairy.getDate().getDate());

    }
    private List<Bitmap> bitmapCreator(String data){
        String arr[] = data.split(",");
        List<Bitmap> bitmapList = new ArrayList<>();
        for (String str : arr){

            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/SecretSlambook/"+str);
            if (!file.exists()){
                continue;
            }
            Bitmap map = BitmapFactory.decodeFile(file.getAbsolutePath());
            bitmapList.add(map);


        }


        return bitmapList;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        bitmaps = bitmapCreator(dairy.getImages());
        if (bitmaps.size()==0){
            pager.setVisibility(View.GONE);
            return;
        }
        List<Fragment> fragments = new ArrayList<>();
        for (Bitmap map:bitmaps){
            fragments.add(new PhotoFragment(map));
        }
        adapter = new PhotoAdapter(getSupportFragmentManager(),fragments);
        pager.setAdapter(adapter);

    }
}