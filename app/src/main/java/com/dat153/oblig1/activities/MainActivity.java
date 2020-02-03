package com.dat153.oblig1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dat153.oblig1.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toDatabase(View view) {
        Intent intent = new Intent (this, DatabaseActivity.class);
        startActivity(intent);
    }

    public void toQuiz(View view) {
        Intent intent = new Intent (this, QuizActivity.class);
        startActivity(intent);
    }

}
