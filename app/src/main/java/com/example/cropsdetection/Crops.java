package com.example.cropsdetection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Crops extends AppCompatActivity {
    private ImageButton captureButton;
    private ImageButton homebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crops);

        captureButton = findViewById(R.id.capture);
        homebtn = findViewById(R.id.btn1);

        Button c1 = findViewById(R.id.c1);
        Button c2 = findViewById(R.id.c2);
        Button c3 = findViewById(R.id.c3);


        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the activity to navigate to upon button click
                Intent intent = new Intent(Crops.this, CaptureActivity.class);
                startActivity(intent); // Start the new activity
            }
        });

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the activity to navigate to upon button click
                Intent intent = new Intent(Crops.this, NoteActivity.class);
                startActivity(intent); // Start the new activity
            }
        });

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Crops.this, Disease1.class);
                startActivity(intent);
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Crops.this, Disease2.class);
                startActivity(intent);
            }
        });

        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Crops.this, Disease3.class);
                startActivity(intent);
            }
        });
    }
}