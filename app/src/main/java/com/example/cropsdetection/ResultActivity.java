package com.example.cropsdetection;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView resultTextView = findViewById(R.id.resultTextView);
        ImageView resultImageView = findViewById(R.id.resultImageView);
        TextView infoTextView1 = findViewById(R.id.infoTextView1);
        TextView infoTextView2 = findViewById(R.id.infoTextView2);
        TextView infoTextView3 = findViewById(R.id.infoTextView3);

        // Get the classification result and image from the intent
        Intent intent = getIntent();
        String result = intent.getStringExtra("classification_result");
        byte[] byteArray = intent.getByteArrayExtra("image");
        Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        // Set the result and image to the views
        resultTextView.setText(result);
        resultImageView.setImageBitmap(image);

        // Set specific texts for each disease
        switch (result) {
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

        // Set onClickListener for the Done button
        findViewById(R.id.doneButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to return to CaptureActivity
                Intent returnIntent = new Intent(ResultActivity.this, CaptureActivity.class);
                // Set flags to clear the back stack
                returnIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                // Start the CaptureActivity
                startActivity(returnIntent);
                // Finish the current activity
                finish();
            }
        });

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected disease from resultTextView
                String disease = resultTextView.getText().toString();

                // Add the disease to DiseaseManager
                DiseaseManager diseaseManager = new DiseaseManager(ResultActivity.this);
                diseaseManager.addDisease(disease);

                // Navigate to ListActivity
                Intent listIntent = new Intent(ResultActivity.this, ListActivity.class);
                startActivity(listIntent);
            }
        });


    }
}
