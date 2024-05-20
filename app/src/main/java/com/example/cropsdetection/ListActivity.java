package com.example.cropsdetection;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListActivity extends AppCompatActivity {

    private DiseaseManager diseaseManager;
    private ImageButton captureButton;
    private ImageButton cropsbtn;
    private Button deleteButton;
    private Button generateButton;
    private ListView listView;
    private CustomArrayAdapter adapter;
    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.listView);
        captureButton = findViewById(R.id.capture);
        cropsbtn = findViewById(R.id.btn2);
        deleteButton = findViewById(R.id.delete);
        generateButton = findViewById(R.id.gene);
        pieChart = findViewById(R.id.pieChart);

        // Initialize DiseaseManager
        diseaseManager = new DiseaseManager(this);

        // Retrieve the list of diseases and display them in ListView
        List<String> diseases = diseaseManager.getDiseaseList();

        // Create and set custom adapter
        adapter = new CustomArrayAdapter(this, diseases);
        listView.setAdapter(adapter);

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

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the list of diseases in DiseaseManager
                diseaseManager.clearDiseases();
                // Clear the list view adapter
                adapter.clear();
                // Notify the adapter that the data set has changed
                adapter.notifyDataSetChanged();
                // Show a toast message to indicate successful deletion
                Toast.makeText(ListActivity.this, "All items deleted", Toast.LENGTH_SHORT).show();
            }
        });

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePieChart();
            }
        });
    }

    private void generatePieChart() {
        // Get data from the ListView
        Map<String, Integer> diseaseCounts = new HashMap<>();
        for (int i = 0; i < listView.getCount(); i++) {
            String disease = (String) listView.getItemAtPosition(i);
            // Remove numbers in parentheses
            disease = disease.replaceAll("\\s*\\(\\d+\\)\\s*", "");
            if (diseaseCounts.containsKey(disease)) {
                int count = diseaseCounts.get(disease);
                diseaseCounts.put(disease, count + 1);
            } else {
                diseaseCounts.put(disease, 1);
            }
        }

        // Define colors for each disease
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);  // Anthracnose
        colors.add(Color.RED);   // Bacterial Wilt
        colors.add(Color.GREEN); // White Rust
        colors.add(Color.MAGENTA);// Downy Mildew
        colors.add(Color.CYAN);  // Leaf spot
        colors.add(Color.YELLOW); // Yellow Vein Mosaic Virus

        // Prepare data for pie chart
        List<PieEntry> entries = new ArrayList<>();
        int colorIndex = 0;
        for (Map.Entry<String, Integer> entry : diseaseCounts.entrySet()) {
            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
            colorIndex++;
        }

        // Create dataset and set it to the pie chart
        PieDataSet dataSet = new PieDataSet(entries, "Disease Counts");
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate(); // refresh
    }
}
