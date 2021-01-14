package com.qdat.secretslambook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AddArticleActivity extends AppCompatActivity {

    Button imageChooser;
    ImageView imageViewer;
    private final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);

        init();

        Picasso.get()
                .load(Uri.parse("content://com.android.providers.media.documents/document/image%3A297646"))
                .into(imageViewer);

        imageChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent();
                it.setType("image/*");
                it.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(it,REQUEST_CODE);

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && data!=null) {

            Log.d("hello","Uri is --> "+data.getData());
            Picasso.get().load(data.getData()).into(imageViewer);


            try {
                Bitmap map = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    private void init(){

        imageChooser = findViewById(R.id.select_image);
        imageViewer = findViewById(R.id.Image_viewer);

    }
    private void checkDirectories(Intent data){
        OutputStream out;
        String root = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
        File dir = new File(root+"SecretSlambook"+File.separator);
        if (!dir.exists()){
            dir.mkdir();

        }
        File file = new File(root+"SecretSlambook"+File.separator+"Image_.png");
        try {
            file.createNewFile();
            out = new FileOutputStream(file);

            out.flush();




        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}