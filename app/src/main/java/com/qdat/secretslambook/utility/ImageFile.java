package com.qdat.secretslambook.utility;

import android.graphics.Bitmap;

import java.io.File;

public class ImageFile {


    private String name;
    private Bitmap bitmap;
    private File file;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }


    public ImageFile(String name, Bitmap bitmap, File file) {
        this.name = name;
        this.bitmap = bitmap;
        this.file = file;
    }
}
