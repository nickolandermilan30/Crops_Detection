// ListActivity2.java
package com.example.cropsdetection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ListActivity2 extends AppCompatActivity {

    private ImageView diseaseImageView;
    private TextView diseaseNameTextView;
    private TextView percentageTextView;
    private TextView infoTextView1;
    private TextView infoTextView2;
    private TextView infoTextView3;
    private ImageButton captureButton, homeButton, piesbtn,cropsbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);

        // Initialize views
        diseaseImageView = findViewById(R.id.diseaseImageView);
        diseaseNameTextView = findViewById(R.id.diseaseNameTextView);
        percentageTextView = findViewById(R.id.percentageTextView);
        infoTextView1 = findViewById(R.id.additionalInfo1TextView);
        infoTextView2 = findViewById(R.id.additionalInfo2TextView);
        infoTextView3 = findViewById(R.id.additionalInfo3TextView);

        captureButton = findViewById(R.id.capture);
        cropsbtn = findViewById(R.id.btn2);
        homeButton = findViewById(R.id.btn1);
        piesbtn = findViewById(R.id.btn3);

        piesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the activity to navigate to upon button click
                Intent intent = new Intent(ListActivity2.this, ListActivity.class);
                startActivity(intent); // Start the new activity
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the activity to navigate to upon button click
                Intent intent = new Intent(ListActivity2.this, NoteActivity.class);
                startActivity(intent); // Start the new activity
            }
        });

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the activity to navigate to upon button click
                Intent intent = new Intent(ListActivity2.this, CaptureActivity.class);
                startActivity(intent); // Start the new activity
            }
        });

        cropsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the activity to navigate to upon button click
                Intent intent = new Intent(ListActivity2.this, Crops.class);
                startActivity(intent); // Start the new activity
            }
        });

        // Get the data from the intent
        String diseaseName = getIntent().getStringExtra("diseaseName");
        String percentage = getIntent().getStringExtra("percentage");

        // Set the data to the TextViews
        diseaseNameTextView.setText(diseaseName);
        percentageTextView.setText(percentage);

        // Set additional information based on the disease name (if needed)
        setAdditionalInformation(diseaseName);

        // Set the image based on the disease name
        switch (diseaseName) {
            case "Anthracnose":
                diseaseImageView.setImageResource(R.drawable.img1);
                break;
            case "Bacterial Wilt":
                diseaseImageView.setImageResource(R.drawable.img2);
                break;
            case "White Rust":
                diseaseImageView.setImageResource(R.drawable.img3);
                break;
            case "Downy Mildew":
                diseaseImageView.setImageResource(R.drawable.img4);
                break;
            case "Leaf spot":
                diseaseImageView.setImageResource(R.drawable.img5);
                break;
            case "Yellow Vein Mosaic Virus":
                diseaseImageView.setImageResource(R.drawable.img6);
                break;
        }
        // Tawagin ang setAdditionalInformation method dito
        if (diseaseName != null) {
            setAdditionalInformation(diseaseName);
        }
    }

    // Method to set additional information based on the disease name
    private void setAdditionalInformation(String diseaseName) {
        // Set additional information based on the disease name
        switch (diseaseName) {
            case "Anthracnose":
                infoTextView1.setText("Avoid Overhead Irrigation: Water at the base of the plants to keep foliage dry. Overhead watering can spread fungal spores.");
                infoTextView2.setText("Balanced Fertilizer: Use the right fertilizer for your plants. Too much nitrogen can make them grow too much and get sick from anthracnose.");
                infoTextView3.setText("Adequate Light: Ensure the plant receives adequate sunlight according to its species requirements. Full sun to partial shade is generally recommended.");
                break;
            case "Bacterial Wilt":
                infoTextView1.setText("Root Zone Watering: Water plants at the base to avoid wetting foliage, as the bacteria causing wilt can enter through wounds or natural openings in leaves.");
                infoTextView2.setText("Balanced Nutrition: Give plants the right amount of fertilizer, but be careful not to use too much nitrogen, as it can make them grow too fast and get sick more easily.");
                infoTextView3.setText("Optimal Light: Ensure plants receive adequate sunlight according to their specific requirements. Healthy plants are better equipped to resist diseases like bacterial wilt.");
                break;
            case "White Rust":
                infoTextView1.setText("Morning Watering: Water in the morning to allow foliage to dry quickly during the day, reducing the duration of leaf wetness.");
                infoTextView2.setText("Avoid Late-Season Fertilization: Refrain from late-season fertilization that encourages tender growth, making plants more vulnerable to disease.");
                infoTextView3.setText("Optimal Light Exposure: Ensure plants receive adequate sunlight based on their species requirements. Adequate sunlight promotes plant vigor and can help suppress fungal growth.");
                break;
            case "Downy Mildew":
                infoTextView1.setText("Morning Watering: Water plants in the morning to allow foliage to dry quickly during the day, reducing the period of leaf wetness.");
                infoTextView2.setText("Avoid Excessive Fertilization: While providing adequate nutrients, avoid excessive fertilization, particularly with nitrogen, which can promote tender growth.");
                infoTextView3.setText("Optimal Light Exposure: Ensure plants receive adequate sunlight according to their species requirements. Sufficient sunlight promotes plant vigor and can help suppress Downy Mildew growth.");
                break;
            case "Leaf spot":
                infoTextView1.setText("Avoid Overhead Irrigation: Minimize or avoid overhead irrigation to prevent water splashing, which can spread fungal spores or bacteria associated with leaf spot diseases.");
                infoTextView2.setText("Avoid Late-Season Fertilization: Refrain from applying fertilizers late in the growing season, as this can stimulate tender growth that is more vulnerable to disease.");
                infoTextView3.setText("Optimal Light Exposure: Ensure plants receive adequate sunlight according to their species requirements. Sufficient sunlight promotes plant vigor and can help suppress leaf spot disease development.");
                break;
            case "Yellow Vein Mosaic Virus":
                infoTextView1.setText("Moderate Watering: Provide plants with moderate and consistent watering to maintain adequate soil moisture levels.");
                infoTextView2.setText("Balanced Fertilization: Apply balanced fertilization appropriate for the specific needs of the affected plants. ");
                infoTextView3.setText("Optimal Light Exposure: Ensure plants receive adequate sunlight according to their species requirements. ");
                break;
        }
    }

}
