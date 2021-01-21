package com.qdat.secretslambook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SyncRequest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.qdat.secretslambook.Contract.Contract;
import com.qdat.secretslambook.adapter.ImageRecyclerViewAdapter;
import com.qdat.secretslambook.utility.ImageFile;
import com.qdat.secretslambook.utility.MyDate;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class AddArticleActivity extends AppCompatActivity {


    AlertDialog.Builder dailog;

    // Wedgit

    EditText title,body;
    Button saveData;
    DatePicker datePicker;


    Button imageChooser;
    ImageView imageViewer,close_btn,viewModeImage , delete_btn;
    RelativeLayout imageViewerForImage;
    private final int REQUEST_CODE = 101;
    RecyclerView recyclerView;
    ImageRecyclerViewAdapter adapter;

    boolean isSave=false;


    int current_position = -1;


    List<ImageFile> imageFileList = null;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_page_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.done){
            saveData();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);

        init();


        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);


        imageChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent();
                it.setType("image/*");
                it.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                it.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(it,REQUEST_CODE);

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        OutputStream out = null;

        if (requestCode == REQUEST_CODE && data!=null) {

            File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/SecretSlambook/");
            if(!dir.exists()){
                dir.mkdir();
            }

            if (data.getData()!=null){
                imageViewer.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);


                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));

                    String name = "DairyImage"+System.currentTimeMillis()+".jpg";
                    imageFileList = new ArrayList<>();
                    imageFileList.add(new ImageFile(name,bitmap,new File(dir,name)));
                    imageViewer.setImageBitmap(bitmap);






                } catch (Exception e) {
                    e.printStackTrace();
                }


            }else{

                if (data.getClipData()!=null){
                    imageViewer.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    imageFileList = new ArrayList<>();

                    for (int i=0;i<data.getClipData().getItemCount();i++){

                        String name = "DairyImage"+System.currentTimeMillis()+".jpg";
                        ClipData.Item items = data.getClipData().getItemAt(i);

                        try {
                            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(items.getUri()));

                            imageFileList.add(new ImageFile(name,bitmap,new File(dir,name)));


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        adapter.setList(retriveList(imageFileList));
                        recyclerView.setAdapter(adapter);

                    }

                    

                }

            }
        }


    }

    private boolean authenticate(){

        if (title.getText().toString().length()!=0 &&
                body.getText().toString().length()!=0)
        {
            return true;
        }
        else{
            return false;
        }

    }

    private void saveData(){
        if (isSave){
            return;
        }
        if (!authenticate()){
            Toast.makeText(this, "Please Fill All The field Correctly", Toast.LENGTH_SHORT).show();
            return;
        }
        String images = "";
        if(imageFileList!=null){

            if (imageFileList.size()!=0){

                images +=""+imageFileList.get(0).getName();
                try {
                    OutputStream out = new FileOutputStream(imageFileList.get(0).getFile());
                    imageFileList.get(0).getBitmap().compress(Bitmap.CompressFormat.JPEG,100,out);
                    out.flush();
                    out.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (int i =1 ; i < imageFileList.size();i++){
                    images +=","+imageFileList.get(i).getName();
                    OutputStream myout = null;
                    try {
                        myout = new FileOutputStream(imageFileList.get(i).getFile());
                        imageFileList.get(i).getBitmap().compress(Bitmap.CompressFormat.JPEG,100,myout);
                        myout.flush();
                        myout.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
            else {
                images = "no_image";
            }




        }else {
            images = "no_image";
        }
        String titleDairy = title.getText().toString();
        String bodyDairy = body.getText().toString();
        String date = new MyDate(datePicker.getDayOfMonth(),datePicker.getMonth(),datePicker.getYear()).toString();

        ContentValues value = new ContentValues();
        value.put(Contract.PAGE_TITLE,titleDairy);
        value.put(Contract.PAGE_BODY,bodyDairy);
        value.put(Contract.PAGE_DATE,date);
        value.put(Contract.PAGE_IMAGE,images);

        Uri u = this.getContentResolver().insert(Contract.PATH_PAGES,value);
//        Toast.makeText(this, ""+ContentUris.parseId(u), Toast.LENGTH_SHORT).show();
        if (ContentUris.parseId(u)==-1){
            Toast.makeText(this, "Failed To Store", Toast.LENGTH_SHORT).show();
        }
        else{
            isSave = true;
            finish();

        }
    }



    private void init(){


        title = findViewById(R.id.title);
        body = findViewById(R.id.body);
        datePicker = findViewById(R.id.date_picker);
        saveData = findViewById(R.id.save_data);




        imageChooser = findViewById(R.id.select_image);
        imageViewer = findViewById(R.id.Image_viewer);
        recyclerView = findViewById(R.id.image_recycler_view);
        imageViewerForImage = findViewById(R.id.viewer_for_image);
        close_btn = findViewById(R.id.close_image);
        delete_btn = findViewById(R.id.delete_image);
        viewModeImage = findViewById(R.id.view_mode_image_viewer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        adapter = new ImageRecyclerViewAdapter(this);




        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        adapter.setOnItemsClickListner(new ImageRecyclerViewAdapter.OnItemClickListner() {
            @Override
            public void onItemClicked(int position, Bitmap map) {
                imageViewerForImage.setVisibility(View.VISIBLE);
                viewModeImage.setImageBitmap(map);
                current_position = position;
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageFileList.remove(current_position);
                adapter.setList(retriveList(imageFileList));
                recyclerView.setAdapter(adapter);
                imageViewerForImage.setVisibility(View.GONE);
                Toast.makeText(AddArticleActivity.this, "Image Removed", Toast.LENGTH_SHORT).show();




            }
        });

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewerForImage.setVisibility(View.GONE);
            }
        });

    }

    public List<Bitmap> retriveList(List<ImageFile> list){
        List<Bitmap> newList = new ArrayList<>();
        for (ImageFile imageFile : list){
            newList.add(imageFile.getBitmap());
        }
        return newList;
    }


    @Override
    public void onBackPressed() {

            dailog = new AlertDialog.Builder(this).setTitle("Exit")
                        .setMessage("You haven't saved this dairy page till now .\n\nDo you want to save it ?")
                    .setIcon(R.drawable.appicondairy).setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                saveData();
                        }
                    }).setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            dailog.show();

    }
}