package com.dat153.oblig1.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.dat153.oblig1.R;
import com.dat153.oblig1.utils.Camera;
import com.dat153.oblig1.utils.Permissions;
import com.dat153.oblig1.utils.UserData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class AddActivity extends AppCompatActivity {

    TextView name;
    ImageButton photoBtn;
    ImageView mImageView;
    Button addBtn;

    Permissions perm = new Permissions(); // READ, WRITE and use CAMERA permission
    static final int REQUEST_IMAGE_CAPTURE = 1;

    File file;
    Camera camera;
    Bitmap bm;

    UserData UD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set views
        setContentView(R.layout.activity_add);
        name = findViewById(R.id.newName);
        photoBtn = findViewById(R.id.takePhoto);
        mImageView = findViewById(R.id.avatar);
        addBtn = findViewById(R.id.addButton);

        // When no photo is taken, make ADD button invisible
        addBtn.setVisibility(View.INVISIBLE);

        // Get Camera
        camera = new Camera(this, this, name);
        // Get user data
        UD = new UserData(this);
        // Verify permission
        perm.verifyCameraPermissions(this);

        // onClick create capture image intent and send to onActivityResult()
        photoBtn.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         if (name.getText().length() == 0) {
                                             alertNoNameEntered();
                                         } else {
                                             camera.dispatchTakePictureIntent();
                                         }
                                     }
                                 }
        );



    }

    //Creates an alert dialog box if user tries to take a photo before entering a name
    public void alertNoNameEntered() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No name provided! ");
        builder.setMessage("Enter a name before inserting an image");
        builder.setCancelable(true);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    // get image from intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                // Create a unique path name
               String path = camera.createFilename();
               //Create a new file with path in sdcard Pictures
               file = camera.createImageFile(path);
               Uri photoURI = FileProvider.getUriForFile(this, this.getPackageName() + ".provider", file);
               // Get from image from camera in Intent
               data.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
               bm = (Bitmap) data.getExtras().get("data");
               // Add tp view
               mImageView.setImageBitmap(scaledBM(bm));

               // Photo is taken -> Show add button and make take photo button invisible
               photoBtn.setVisibility(View.INVISIBLE);
               addBtn.setVisibility(View.VISIBLE);
           } catch (IOException ex) {
               ex.printStackTrace();
           }
        }
    }


    // save image from camera to file created on ADD button click
    public void save(View view) {
        if (file != null) {
            try {
                OutputStream os = new FileOutputStream(file);
                bm.compress(Bitmap.CompressFormat.JPEG, 85, os);
                os.flush();
                os.close();
                clear(view);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Clears insert text box and imageV
    public void clear(View view) {
        name = findViewById(R.id.newName);
        mImageView = findViewById(R.id.avatar);
        mImageView.setImageBitmap(null);
        name.setText("");
        // Restart so ADD button is set to invisible again
        addBtn.setVisibility(View.INVISIBLE);
        photoBtn.setVisibility(View.VISIBLE);
    }

    // Scale bitmap
    public Bitmap scaledBM(Bitmap bm) {
        Bitmap scaled = Bitmap.createScaledBitmap(bm, 450, 500, true);
        return scaled;
    }

}
