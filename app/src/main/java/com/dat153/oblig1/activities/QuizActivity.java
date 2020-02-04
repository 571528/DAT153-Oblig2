package com.dat153.oblig1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dat153.oblig1.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    SharedPreferences sp;
    TextView name;
    ImageView image;
    TextView result;
    TextView scoreText;
    ArrayList<Object> images;
    ArrayList<Object> names;
    Random rnd;
    Integer score;
    List<Bitmap> imageDatabase;
    String currImg;
    Button btnText;

    public static final String PREF = "pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        name = findViewById(R.id.guessText);
        image = findViewById(R.id.quizImage);
        result = findViewById(R.id.resultText);
        scoreText = findViewById(R.id.scoreNumberText);
        btnText = findViewById(R.id.guessButton);
        sp = getSharedPreferences(PREF, Context.MODE_PRIVATE);
        score = 0;
        // Edits view for when there are no classmates
        if (sp.getAll().isEmpty()) {
            System.out.println("sp is empty " + sp.getAll().size());
            name.setText("No Classmates!");
            image.setImageBitmap(bitmapImage("database_empty"));
            btnText.setText("Return to main menu");
        } else {
            images = getImages();
            names = getNames();
            getRandomImage();
        }

    }

    // Creates two lists, one for image filenames and another for actual bitmap images
    public ArrayList<Object> getImages() {
        System.out.println("SP: " + sp.getAll().toString());
        Map<String, ?> keys = sp.getAll();
        images = new ArrayList<>(); // Filename list
        imageDatabase = new ArrayList<>(); // Bitmap images list
        int i = 0;
        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            images.add(i, entry.getKey());
            imageDatabase.add(bitmapImage(images.get(i).toString()));
            System.out.println("image:" + entry.getKey() + " Name: " + entry.getValue().toString());
            i++;
        }
        return images;
    }

    // Creates a list with classmate names
    public ArrayList<Object> getNames() {
        Map<String, ?> keys = sp.getAll();
        names = new ArrayList<>();
        int i = 0;
        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            names.add(i, entry.getValue().toString());
            i++;
            System.out.println("image:" + entry.getKey() + " Name: " + entry.getValue().toString());
        }
        return names;
    }

    // Finds a random image and add it to imageView in quiz activity
    public void getRandomImage() {
        images = getImages();
        rnd = new Random();
        int random = rnd.nextInt(images.size());

        image.setImageBitmap(imageDatabase.get(random));
        currImg = images.get(random).toString();

    }

    // The action that hanppen when the guess button is activated
    // Checks and alerts whether the user input is correct and updates score
    // Calls on getRandomImage to receive a new image
    public void guess(View view) {
        String n = name.getText().toString();
        String img = currImg;
        Boolean found = false;
        int i = 0;
        images = getImages();
        names = getNames();
        sp = getSharedPreferences(PREF,
                Context.MODE_PRIVATE);
        if (sp.contains(img)) {
            while (!found) {
                if (images.get(i).equals(img)) {
                    found = true;
                } else {
                    i++;
                }
            }
            if (names.get(i).toString().toUpperCase().equals(n.toUpperCase())) {
                score += 1;
                result.setText("Correct!");
            } else {
                result.setText("Wrong.. The correct name was " + names.get(i).toString());
            }
        }
        System.out.println("SCORE:" + score);
        scoreText.setText(score.toString());
        name.setText("");
        getRandomImage();
    }

    //Makes drawable image to Bitmap
    public Bitmap bitmapImage(String imgFileName) {
        Bitmap b = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(imgFileName, "drawable", getPackageName()));
        Bitmap scaled = Bitmap.createScaledBitmap(b, 350, 400, true);
        return scaled;
    }
}
