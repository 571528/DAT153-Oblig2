package com.dat153.oblig1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dat153.oblig1.R;

public class MainActivity extends AppCompatActivity {

    TextView welcomeTxt;
    EditText insertName;
    Button saveOwnerBtn;
    SharedPreferences sp;
    public static final String WELCOME_PREF = "welcomePref";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences(WELCOME_PREF, Context.MODE_PRIVATE);
        welcomeTxt = findViewById(R.id.welcomeText);
        insertName = findViewById(R.id.ownerText);
        saveOwnerBtn = findViewById(R.id.saveOwnerBtn);
        if (!sp.getAll().isEmpty()) {
            insertName.setVisibility(View.INVISIBLE);
            saveOwnerBtn.setVisibility(View.INVISIBLE);
            welcomeTxt.setText("Welcome back " + sp.getAll().get("owner").toString());
        }

    }

    // Save owners name to database
    public void saveOwner(View view) {
        String n = insertName.getText().toString();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("owner", n);
        editor.commit();
        insertName.setVisibility(View.INVISIBLE);
        saveOwnerBtn.setVisibility(View.INVISIBLE);
        welcomeTxt.setText("Welcome back " + sp.getAll().get("owner").toString());
    }


    public void toDatabase(View view) {
        Intent intent = new Intent(this, DatabaseActivity.class);
        startActivity(intent);
    }

    public void toQuiz(View view) {
        Intent intent = new Intent(this, QuizActivity.class);
        startActivity(intent);
    }

}
