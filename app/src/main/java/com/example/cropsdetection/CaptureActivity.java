package com.example.cropsdetection;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cropsdetection.ml.Dataset5;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class CaptureActivity extends AppCompatActivity {

    private static final float THRESHOLD_HIGH = 50.0f;
    private static final float THRESHOLD_LOW = 10.0f;

    private ImageButton captureButton;
    ImageButton camera, gallery, cropsbtn ,piesbtn,mostdata;
    ImageView imageView, temperatureImageView;
    TextView result, temperatureTextView;

    int imageSize = 32;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

        captureButton = findViewById(R.id.btn1);
        cropsbtn = findViewById(R.id.btn2);

        camera = findViewById(R.id.button);
        gallery = findViewById(R.id.button2);
        result = findViewById(R.id.result);
        imageView = findViewById(R.id.imageView);

        temperatureTextView = findViewById(R.id.temperatureTextView); // Initialize TextView for temperature
        temperatureImageView = findViewById(R.id.temperatureImageView); // Idagdag ito
        // You might want to update the temperature at regular intervals or when the user interacts with the app.
        updateTemperature();
        
        
        result.setVisibility(View.VISIBLE);
        piesbtn = findViewById(R.id.btn3);
        mostdata = findViewById(R.id.btn4);

        mostdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Build AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(CaptureActivity.this);
                builder.setTitle("Alert");
                builder.setMessage("You need To Generate Piechart to see most data");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Proceed to CaptureActivity
                        Intent intent = new Intent(CaptureActivity.this, CaptureActivity.class);
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
                Intent intent = new Intent(CaptureActivity.this, ListActivity.class);
                startActivity(intent); // Start the new activity
            }
        });

        cropsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the activity to navigate to upon button click
                Intent intent = new Intent(CaptureActivity.this, Crops.class);
                startActivity(intent); // Start the new activity
            }
        });

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the activity to navigate to upon button click
                Intent intent = new Intent(CaptureActivity.this, NoteActivity.class);
                startActivity(intent); // Start the new activity
            }
        });


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 3);
                } else {
                    // Kung walang pahintulot, humiling ng pahintulot
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                }
            }
        });



        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(cameraIntent, 1);
            }
        });
    }

    // Method to update the temperature TextView and ImageView
    private void updateTemperature() {
        // TODO: Implement logic to read the temperature from the device's sensors or other available sources.
        // For example, you can use the battery temperature as a placeholder.
        float temperature = getBatteryTemperature();

        // Update the TextView with the temperature
        temperatureTextView.setText(String.format("%.2f°C", temperature));


        // Update the ImageView based on temperature
        if (temperature > THRESHOLD_HIGH) {
            temperatureImageView.setImageResource(R.drawable.high);
        } else if (temperature < THRESHOLD_LOW) {
            temperatureImageView.setImageResource(R.drawable.low);
        } else {
            temperatureImageView.setImageResource(R.drawable.mid);
        }
    }




    // Method to get the battery temperature as a placeholder
    private float getBatteryTemperature() {
        Intent intent = this.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        if (intent != null) {
            int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
            return temperature / 10.0f; // The temperature is returned in tenths of a degree Celsius
        } else {
            return 0.0f; // Default value if the temperature is not available
        }
    }

    public void classifyImage(Bitmap image){
        try {
            Dataset5 model = Dataset5.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 32, 32, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            int pixel = 0;
            //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
            for(int i = 0; i < imageSize; i ++){
                for(int j = 0; j < imageSize; j++){
                    int val = intValues[pixel++]; // RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 1));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 1));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 1));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            Dataset5.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            // find the index of the class with the biggest confidence.
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidences.length; i++) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }
            String[] classes = {"Anthracnose", "Bacterial Wilt", "White Rust", "Downy Mildew", "Leaf spot", "Yellow Vein Mosaic Virus"};
            result.setText(classes[maxPos]);

            String resultText = classes[maxPos];

            // Convert the image to a byte array
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            // Start ResultActivity and pass the result and the image
            Intent intent = new Intent(CaptureActivity.this, ResultActivity.class);
            intent.putExtra("classification_result", resultText);
            intent.putExtra("image", byteArray);
            startActivity(intent);

            // Releases model resources if no longer used.
            model.close();


        } catch (IOException e) {
            // TODO Handle the exception
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == 3){
                Bitmap image = (Bitmap) data.getExtras().get("data");
                int dimension = Math.min(image.getWidth(), image.getHeight());
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                imageView.setImageBitmap(image);

                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                classifyImage(image);
            }else{
                Uri dat = data.getData();
                Bitmap image = null;
                try {
                    image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageView.setImageBitmap(image);

                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                classifyImage(image);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}