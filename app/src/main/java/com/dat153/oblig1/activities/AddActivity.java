package com.dat153.oblig1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dat153.oblig1.R;


public class AddActivity extends AppCompatActivity {

    SharedPreferences sp;
    TextView name;
    TextView image;

    public static final String PREF = "pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        name = findViewById(R.id.newName);
        image = findViewById(R.id.newImage);
        sp = getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }

    //Fil navn til image er key og skal være unique
    public void save(View view) {
        String n = name.getText().toString();
        String key = image.getText().toString();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, n);
        editor.commit();
        getList(view);
        clear(view);

    }

    public void clear(View view) {
        name = findViewById(R.id.newName);
        image = findViewById(R.id.newImage);
        name.setText("");
        image.setText("");
    }


    public void remove(View view) {
        String key = image.getText().toString();
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        getList(view);
        clear(view);
    }

    public void getList(View view) {
        sp = getSharedPreferences(PREF,
                Context.MODE_PRIVATE);
        System.out.println("SP: " + sp.getAll().toString());
    }

}
