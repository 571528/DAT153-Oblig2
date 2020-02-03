package com.dat153.oblig1.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dat153.oblig1.R;
import com.dat153.oblig1.adapter.AdapterDB;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    SharedPreferences sp;
    public static final String PREF = "pref";
    ArrayList<String> names;
    ArrayList<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        recyclerView = findViewById(R.id.recycler_view);
        // this setting improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // Get all names from sp
        List<String> nameDatabase = getNames();
        // Get all image names from sp
        List<String> imgFileName = getImages();
        List<Bitmap> imageDatabase = new ArrayList<>();

        // Check if there are any classmates
        if (getNames().size() <= 0) {
            nameDatabase.add("No Classmates!");
            imageDatabase.add(bitmapImage("database_empty"));
        } else {
            for (int i = 0; i < imgFileName.size(); i ++) {
                imageDatabase.add(bitmapImage(imgFileName.get(i)));
            }
        }
        // define an adapter
        mAdapter = new AdapterDB(getApplicationContext(), nameDatabase, imageDatabase, imgFileName);
        recyclerView.setAdapter(mAdapter);
    }

    // get Names of classmates from sp
    public ArrayList<String> getNames() {
        sp = getSharedPreferences(PREF,
                Context.MODE_PRIVATE);
        Map<String, ?> keys = sp.getAll();
        names = new ArrayList<>();
        int i = 0;
        if (keys.size() > 0) {
            for (Map.Entry<String, ?> entry : keys.entrySet()) {
                names.add(i, entry.getValue().toString());
                i++;
                System.out.println("image:" + entry.getKey() + " Name: " + entry.getValue().toString());
            }
        }

        return names;
    }

    //get name of image from sp
    public ArrayList<String> getImages() {
        sp = getSharedPreferences(PREF,
                Context.MODE_PRIVATE);
        Map<String,?> keys = sp.getAll();
        images = new ArrayList<>();
        int i = 0;
        for(Map.Entry<String,?> entry : keys.entrySet()){
            images.add(i, entry.getKey());
            i++;
            System.out.println("image:" + entry.getKey() + " Name: " + entry.getValue().toString());
        }
        return images;
    }

    // To add a new classmate page
    public void toAdd(View view) {
        Intent intent = new Intent (this, AddActivity.class);
        startActivity(intent);
    }


    //Bitmap Image
    public Bitmap bitmapImage(String imgFileName) {
        //String res = "R.mitmap." + img;
        Bitmap b = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(imgFileName, "drawable", getPackageName()));
        Bitmap scaled = Bitmap.createScaledBitmap(b, 350, 400, true);
        return scaled;
    }

}

