package com.dat153.oblig1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dat153.oblig1.R;

public class RemoveActivity extends AppCompatActivity {

    SharedPreferences sp;
    TextView image;

    public static final String PREF = "pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);
        image = findViewById(R.id.rmImage);
        sp = getSharedPreferences(PREF, Context.MODE_PRIVATE);
        System.out.println("SP BEFORE REMOVE: " + sp.getAll().toString());
    }

    //Fil navn til image er key og skal være unique
    public void remove(View view) {
        getList(view);
        SharedPreferences.Editor editor = sp.edit();
        String key = image.getText().toString();

        if(sp.contains(key)) {
            editor.remove(key);
            editor.commit();
        } else {
            System.out.println(key + " does not exist!");
        }
        getList(view);
        clear(view);
    }


    public void clear(View view) {
        image = findViewById(R.id.rmImage);
        image.setText("");
    }
    public void getList(View view) {
        sp = getSharedPreferences(PREF,
                Context.MODE_PRIVATE);
        System.out.println("SP: " + sp.getAll().toString());
    }
}
