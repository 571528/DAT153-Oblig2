package com.dat153.oblig1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dat153.oblig1.R;
import com.dat153.oblig1.utils.UserData;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView welcomeTxt;
    EditText insertName;
    Button saveOwnerBtn;
    SharedPreferences sp;
    ImageButton dbButton;
    ImageButton quizButton;
    public static final String WELCOME_PREF = "welcomePref";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Define preferences and views
        sp = getSharedPreferences(WELCOME_PREF, Context.MODE_PRIVATE);
        welcomeTxt = findViewById(R.id.welcomeText);
        insertName = findViewById(R.id.ownerText);
        saveOwnerBtn = findViewById(R.id.saveOwnerBtn);
        dbButton = findViewById(R.id.dbButton);
        quizButton = findViewById(R.id.quizButton);
        //Make database and quiz button invisible unless there already is an owner
        dbButton.setVisibility(View.INVISIBLE);
        quizButton.setVisibility(View.INVISIBLE);
        if (!sp.getAll().isEmpty()) {
            // If there is an owner make only welcome message AND quiz and db buttons visible
            insertName.setVisibility(View.INVISIBLE);
            saveOwnerBtn.setVisibility(View.INVISIBLE);
            welcomeTxt.setText("Welcome back " + sp.getAll().get("owner").toString());
            dbButton.setVisibility(View.VISIBLE);
            quizButton.setVisibility(View.VISIBLE);
        }
    }

    // Save owners name to shared preferences
    public void saveOwner(View view) {
        String n = insertName.getText().toString();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("owner", n);
        editor.commit();
        // Make only welcome message visible
        insertName.setVisibility(View.INVISIBLE);
        saveOwnerBtn.setVisibility(View.INVISIBLE);
        dbButton.setVisibility(View.VISIBLE);
        quizButton.setVisibility(View.VISIBLE);
        welcomeTxt.setText("Welcome back " + sp.getAll().get("owner").toString());

    }

    // To database activity
    public void toDatabase(View view) {
        Intent intent = new Intent(this, DatabaseActivity.class);
        startActivity(intent);
    }

    // To quiz activity
    public void toQuiz(View view) {
        Intent intent = new Intent(this, QuizActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.view_sp_menu_item:
                toStringSP();
                return true;
            case R.id.removeFrom_sp_menu_item:
                removeFromSP();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void toStringSP() {
        if (sp.getAll().isEmpty()) {
           System.out.println("sp in empty! add a user to start");
        } else {
            System.out.println("current owner: " + sp.getAll().toString());
        }
    }

    public void removeFromSP() {
        if (sp.getAll().isEmpty()) {
            System.out.println("sp in empty! No owner to remove");
        } else {
            sp.edit().remove("owner").commit();
            System.out.println("sp : " + sp.getAll());
            reload();
        }

    }
    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }
}
