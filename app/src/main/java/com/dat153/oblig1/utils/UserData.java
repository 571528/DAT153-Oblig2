package com.dat153.oblig1.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.util.ArrayList;
import java.util.List;


public class UserData {


    Context context;

    public UserData(Context context) {
        this.context = context;

    }


    public List<String> getFilenames() {
        String[] fromDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).list();
        List<String> filenames = new ArrayList<>();
        for (int i = 0; i < fromDir.length; i++) {
            filenames.add(fromDir[i]);
        }
        return filenames;

    }

    public List<String> getUsernames() {
        List<String> imageFiles = getFilenames();
        List<String> userNames = new ArrayList<>();
        for (String files : imageFiles) {
            String[] split = files.split("-");
            userNames.add(split[0]);
        }
        return userNames;
    }


    // List of Bitmap of images
    public List<Bitmap> bitmapList() {
        List<String> imageFiles = getFilenames();
        List<Bitmap> bmList = new ArrayList<>();
        for (String file : imageFiles) {
            String path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/" + file;
            System.out.println("path: " + path);
            Bitmap b = BitmapFactory.decodeFile(path);
            Bitmap scaled = Bitmap.createScaledBitmap(b, 450, 500, true);
            bmList.add(scaled);
        }
        return bmList;
    }



}
