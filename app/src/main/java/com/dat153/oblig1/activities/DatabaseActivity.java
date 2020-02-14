package com.dat153.oblig1.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dat153.oblig1.R;
import com.dat153.oblig1.adapter.AdapterDB;
import com.dat153.oblig1.utils.Permissions;
import com.dat153.oblig1.utils.UserData;


import java.util.List;


public class DatabaseActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    Permissions perm = new Permissions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        recyclerView = findViewById(R.id.recycler_view);
        // this setting improve performance if you know that changes in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        perm.verifyStorageReadPermissions(this);
        // Get User Data
        UserData ud = new UserData(this);
        //Get complete filename
        List<String> fileNames = ud.getFilenames();
        // Get all names
        List<String> names = ud.getUsernames();
        // Get all image filenames
        List<Bitmap> bm = ud.bitmapList();
        // define an adapter
        mAdapter = new AdapterDB(names, bm, fileNames, this);
        recyclerView.setAdapter(mAdapter);
    }


    // To add a new classmate page
    public void toAdd(View view) {
        Intent intent = new Intent (this, AddActivity.class);
        startActivity(intent);
    }

}

