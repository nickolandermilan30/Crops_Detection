package com.example.cropsdetection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    private DiseaseManager diseaseManager;
    private ImageButton captureButton;
    private ImageButton cropsbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView listView = findViewById(R.id.listView);
        captureButton = findViewById(R.id.capture);
        cropsbtn = findViewById(R.id.btn2);

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the activity to navigate to upon button click
                Intent intent = new Intent(ListActivity.this, CaptureActivity.class);
                startActivity(intent); // Start the new activity
            }
        });
        cropsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the activity to navigate to upon button click
                Intent intent = new Intent(ListActivity.this, Crops.class);
                startActivity(intent); // Start the new activity
            }
        });

        // Initialize DiseaseManager
        diseaseManager = new DiseaseManager(this);

        // Retrieve the list of diseases and display them in ListView
        List<String> diseases = diseaseManager.getDiseaseList();

        // Create and set custom adapter
        CustomArrayAdapter adapter = new CustomArrayAdapter(this, diseases);
        listView.setAdapter(adapter);
    }
}