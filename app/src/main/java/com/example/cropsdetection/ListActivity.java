package com.example.cropsdetection;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
    private ImageButton captureButton, homeButton, barButton, mostdata;
    private ImageButton cropsbtn;
    private Button deleteButton;
    private Button generateButton;
    private ListView listView;
    private CustomArrayAdapter adapter;
    private PieChart pieChart;

    private String highestDiseaseName;
    private float highestPercentage;

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
        homeButton = findViewById(R.id.btn1);
        barButton = findViewById(R.id.bar);

        // Initialize DiseaseManager
        diseaseManager = new DiseaseManager(this);

        // Retrieve the list of diseases and display them in ListView
        List<String> diseases = diseaseManager.getDiseaseList();

        // Create and set custom adapter
        adapter = new CustomArrayAdapter(this, diseases);
        listView.setAdapter(adapter);
        mostdata = findViewById(R.id.btn4);

        mostdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Build AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                builder.setTitle("Alert");
                builder.setMessage("You need To Generate Piechart to see most data");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Proceed to CaptureActivity
                        Intent intent = new Intent(ListActivity.this, CaptureActivity.class);
                        startActivity(intent);
                    }
                });

                // Show AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the activity to navigate to upon button click
                Intent intent = new Intent(ListActivity.this, NoteActivity.class);
                startActivity(intent); // Start the new activity
            }
        });

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

        // Inside ListActivity.java
        barButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Determine the disease with the highest percentage
                String highestDisease = null;
                float highestPercentage = 0;

                // Calculate percentages and find the highest one
                Map<String, Integer> diseaseCounts = new HashMap<>();
                int totalDiseases = listView.getCount();
                for (int i = 0; i < totalDiseases; i++) {
                    String diseaseItem = (String) listView.getItemAtPosition(i);
                    String diseaseName = diseaseItem.replaceAll("\\s*\\(\\d+\\)\\s*", "");
                    int count = 1;
                    if (diseaseItem.matches(".*\\(\\d+\\).*")) {
                        count = Integer.parseInt(diseaseItem.replaceAll(".*\\((\\d+)\\).*", "$1"));
                    }
                    diseaseCounts.put(diseaseName, diseaseCounts.getOrDefault(diseaseName, 0) + count);
                }

                for (Map.Entry<String, Integer> entry : diseaseCounts.entrySet()) {
                    float percentage = (entry.getValue() / (float) totalDiseases) * 100;
                    if (percentage > highestPercentage) {
                        highestPercentage = percentage;
                        highestDisease = entry.getKey();
                    }
                }

                // Start ListActivity2 and pass the data
                Intent intent = new Intent(ListActivity.this, ListActivity2.class);
                intent.putExtra("diseaseName", highestDisease);
                intent.putExtra("percentage", String.format("%.2f%%", highestPercentage));
                startActivity(intent);
            }
        });

    }

    private void generatePieChart() {
        // Get data from the ListView
        Map<String, Integer> diseaseCounts = new HashMap<>();
        int maxCount = 0;
        for (int i = 0; i < listView.getCount(); i++) {
            String diseaseItem = (String) listView.getItemAtPosition(i);
            String diseaseName = diseaseItem.replaceAll("\\s*\\(\\d+\\)\\s*", "");
            int count = 1;

            // Extract the number from parentheses if present
            if (diseaseItem.matches(".*\\(\\d+\\).*")) {
                count = Integer.parseInt(diseaseItem.replaceAll(".*\\((\\d+)\\).*", "$1"));
            }

            int existingCount = diseaseCounts.getOrDefault(diseaseName, 0);
            diseaseCounts.put(diseaseName, existingCount + count);
            maxCount = Math.max(maxCount, diseaseCounts.get(diseaseName)); // Get the maximum count
        }

        // Define colors for each disease
        Map<String, Integer> diseaseColors = new HashMap<>();
        diseaseColors.put("Anthracnose", Color.parseColor("#800080")); // purple
        diseaseColors.put("Bacterial Wilt", Color.parseColor("#008000")); // green
        diseaseColors.put("White Rust", Color.parseColor("#808080")); // grey
        diseaseColors.put("Downy Mildew", Color.parseColor("#FF00FF")); // magenta
        diseaseColors.put("Leaf spot", Color.parseColor("#A52A2A")); // brown
        diseaseColors.put("Yellow Vein Mosaic Virus", Color.parseColor("#FFFF00")); // yellow

        // Prepare data for pie chart
        List<PieEntry> entries = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();
        highestPercentage = 0f;
        highestDiseaseName = null;

        for (Map.Entry<String, Integer> entry : diseaseCounts.entrySet()) {
            float value = entry.getValue(); // Get the count
            if (value == maxCount) {
                // Adjust the value for the disease with the maximum count
                value *= 1.2f; // Increase the value by 20% for emphasis
            }
            entries.add(new PieEntry(value, entry.getKey()));
            colors.add(diseaseColors.get(entry.getKey())); // Assign specific color to each disease

            // Update highest disease name and percentage
            if (value > highestPercentage) {
                highestPercentage = value;
                highestDiseaseName = entry.getKey();
            }
        }

        // Create dataset and set it to the pie chart
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);
        dataSet.setDrawValues(true); // Enable values on the chart
        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate(); // refresh

        // Disable legends and labels
        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);
    }
}
