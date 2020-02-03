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

    //Lager to lister, en til filnavn og en til de faktiske bildene
    public ArrayList<Object> getImages() {
        System.out.println("SP: " + sp.getAll().toString());
        Map<String, ?> keys = sp.getAll();
        images = new ArrayList<>();
        imageDatabase = new ArrayList<>();
        int i = 0;
        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            images.add(i, entry.getKey());
            imageDatabase.add(bitmapImage(images.get(i).toString()));
            System.out.println("image:" + entry.getKey() + " Name: " + entry.getValue().toString());
            i++;
        }


        return images;
    }

    //Lager en liste med navnene til bildene.
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

    //Finner et tilfeldig bilde, og setter det i imageView på quiz aktiviteten.
    public void getRandomImage() {
        images = getImages();
        rnd = new Random();
        int random = rnd.nextInt(images.size());

        image.setImageBitmap(imageDatabase.get(random));
        currImg = images.get(random).toString();

    }

    //Handlingen som skjer når man trykker på knappen for å gjette.
    //Sjekker om brukeren har svart rett, gir tilbakemelding, oppdaterer poengsum
    //Og kaller på metoden for å få et nytt tilfeldig bilde.
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
                result.setText("Wrong..");
            }
        }
        System.out.println("SCORE:" + score);
        scoreText.setText(score.toString());
        name.setText("");
        getRandomImage();
    }

    //Gjør om drawable bilde til en bitmap.
    public Bitmap bitmapImage(String imgFileName) {
        Bitmap b = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(imgFileName, "drawable", getPackageName()));
        Bitmap scaled = Bitmap.createScaledBitmap(b, 350, 400, true);
        return scaled;
    }
}
