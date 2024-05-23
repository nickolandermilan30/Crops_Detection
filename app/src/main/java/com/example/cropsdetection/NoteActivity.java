package com.example.cropsdetection;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class NoteActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView textTextView;
    private ImageButton captureButton;
    private ImageButton cropsbtn,piesbtn,mostdata ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        captureButton = findViewById(R.id.capture);
        cropsbtn = findViewById(R.id.btn2);
        piesbtn = findViewById(R.id.btn3);
        mostdata = findViewById(R.id.btn4);

        mostdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Build AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(NoteActivity.this);
                builder.setTitle("Alert");
                builder.setMessage("You need To Generate Piechart to see most data");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Proceed to CaptureActivity
                        Intent intent = new Intent(NoteActivity.this, CaptureActivity.class);
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
                Intent intent = new Intent(NoteActivity.this, ListActivity.class);
                startActivity(intent); // Start the new activity
            }
        });
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
