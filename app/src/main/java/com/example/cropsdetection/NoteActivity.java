package com.example.cropsdetection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NoteActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView textTextView;
    private ImageButton captureButton;
    private ImageButton cropsbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        captureButton = findViewById(R.id.capture);
        cropsbtn = findViewById(R.id.btn2);


        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the activity to navigate to upon button click
                Intent intent = new Intent(NoteActivity.this, CaptureActivity.class);
                startActivity(intent); // Start the new activity
            }
        });
        cropsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the activity to navigate to upon button click
                Intent intent = new Intent(NoteActivity.this, Crops.class);
                startActivity(intent); // Start the new activity
            }
        });


    }
}
