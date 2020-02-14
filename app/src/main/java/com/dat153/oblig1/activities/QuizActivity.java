package com.dat153.oblig1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dat153.oblig1.R;
import com.dat153.oblig1.utils.UserData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    TextView name;
    ImageView image;
    TextView result;
    TextView scoreText;
    Button btnText;

    private List<String> namesList; // List of names
    private List<Bitmap> imagesList; // List of images (Bitmap)
    private List<String> fileNames; // List of filenames (both name and image info)

    UserData ud;
    Integer score;

    Random rnd;
    int current; // Position of current name and image in Lists


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        // get views
        name = findViewById(R.id.guessText);
        image = findViewById(R.id.quizImage);
        result = findViewById(R.id.resultText);
        scoreText = findViewById(R.id.scoreNumberText);
        btnText = findViewById(R.id.guessButton);

        //get userdata
        ud = new UserData(this);

        namesList = ud.getUsernames();
        imagesList = ud.bitmapList();
        fileNames = ud.getFilenames();

        rnd = new Random();
        score = 0;

        // Classmates database is EMPTY
        if (namesList.size() <= 0) {
            System.out.println("Names List is empty " + namesList.size());
            name.setVisibility(View.INVISIBLE);
            score = 0;
            scoreText.setText("No Classmates");
            image.setImageResource(R.drawable.database_empty);
            btnText.setText("Return to main menu!");
            btnText.setOnClickListener(this);
        }
        // At least ONE classmate
        else {
            current = getPosition();
            setImage(current);
        }

    }


    // on -Make Guess- button click
    public void guess(View view) {
        //If the answer is correct
        if (isCorrect(current)) {
            score += 1;
            result.setText("Correct");
        }
        // If the answer is wrong
        else {
            result.setText("Wrong... The correct name was " + namesList.get(current));
        }
        System.out.println("SCORE:" + score);
        scoreText.setText(score.toString());
        name.setText("");
        current = getPosition();
        setImage(current);
    }

    // Get a random num
    public int getPosition() {
        return rnd.nextInt(imagesList.size());
    }

    //Set image view
    public void setImage(int position) {
        image.setImageBitmap(imagesList.get(position));
    }

    // Return a name that matches the image
    public String getNameAtPosition(int position) {
        return namesList.get(position);
    }

    // Check if the answer is correct
    public boolean isCorrect(int position) {
        boolean correct = false;
        String n = name.getText().toString();
        if (getNameAtPosition(position).toUpperCase().equals(n.toUpperCase())) {
            correct = true;
        }
        return correct;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
