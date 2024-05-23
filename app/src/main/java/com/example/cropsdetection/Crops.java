package com.example.cropsdetection;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Crops extends AppCompatActivity {
    private ImageButton captureButton;
    private ImageButton homebtn, piesbtn,mostdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crops);

        captureButton = findViewById(R.id.capture);
        homebtn = findViewById(R.id.btn1);

        Button c1 = findViewById(R.id.c1);
        Button c2 = findViewById(R.id.c2);
        Button c3 = findViewById(R.id.c3);

        piesbtn = findViewById(R.id.btn3);
        mostdata = findViewById(R.id.btn4);

        mostdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Build AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(Crops.this);
                builder.setTitle("Alert");
                builder.setMessage("You need To Generate Piechart to see most data");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Proceed to CaptureActivity
                        Intent intent = new Intent(Crops.this, CaptureActivity.class);
                        startActivity(intent);
                    }
                });

                // Show AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        piesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the activity to navigate to upon button click
                Intent intent = new Intent(Crops.this, ListActivity.class);
                startActivity(intent); // Start the new activity
            }
        });

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