package com.qdat.secretslambook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.qdat.secretslambook.Contract.Contract;
import com.qdat.secretslambook.adapter.HomeRecyclerAdapter;
import com.qdat.secretslambook.utility.Dairy;
import com.qdat.secretslambook.utility.Reader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    FloatingActionButton actionButton;
    RelativeLayout layout;
    ImageView imageView;
    RecyclerView recyclerView;
    HomeRecyclerAdapter adapter;
    List<Dairy> dairies;
    public static Dairy sharedDairy = null;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id==R.id.setting_home){

            startActivity(new Intent(MainActivity.this,SettingActivity.class));

        }else if (id == R.id.delete_all){

            getContentResolver().delete(Contract.PATH_PAGES,null,null);
            adapter.setDairyList(new ArrayList<>());
            Toast.makeText(this, "All dairies have been deleted succesfully", Toast.LENGTH_SHORT).show();
            recyclerView.setAdapter(adapter);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startActivity(new Intent(MainActivity.this , Auth_Activity.class));

        init();
        adapter.setOnItemsClickListner(new HomeRecyclerAdapter.OnItemClickListner() {
            @Override
            public void onItemClicked(int position) {
                startActivity(new Intent(MainActivity.this,DairyViewer.class));
                sharedDairy = dairies.get(position);
            }
        });

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddArticleActivity.class));
            }
        });



    }

    private void init(){

        layout = findViewById(R.id.root);
        recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HomeRecyclerAdapter(MainActivity.this);
        actionButton = findViewById(R.id.myactionbar);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);

    }

    @Override
    protected void onStart() {
        super.onStart();
        getLoaderManager().initLoader(1,null,this).forceLoad();

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this , Contract.PATH_PAGES,null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        dairies = Reader.extractDateFromCursor(data);
        adapter.setDairyList(dairies);

        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/SecretSlambook/");
        if (!dir.exists()){
            dir.mkdir();
        }
        File wDir = new File(dir,"wallpaper/");
        if (!wDir.exists()){
            wDir.mkdir();
        }
        if (wDir.list().length==0){
//            Toast.makeText(this, "Empty", Toast.LENGTH_LONG).show();
            layout.setBackgroundResource(R.drawable.default_bg);

        }
        else{
                String str[] = wDir.list();
//                Toast.makeText(this, ""+str[0], Toast.LENGTH_SHORT).show();
                File paper = new File(wDir+"/"+str[0]);
                Drawable drawable = BitmapDrawable.createFromPath(paper.getAbsolutePath());
//                Toast.makeText(this, ""+paper.getAbsolutePath(), Toast.LENGTH_LONG).show();

                layout.setBackground(drawable);

        }
    }
}