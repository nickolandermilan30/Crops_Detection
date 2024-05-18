package com.example.cropsdetection;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Itakda ang window flags para itago ang notification bar at iba pang UI elements
        requestWindowFeature(Window.FEATURE_NO_TITLE); // Itago ang title bar ng activity
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // Itago ang notification bar

        setContentView(R.layout.activity_main);

        // Get reference to the ImageView
        ImageView logoImageView = findViewById(R.id.logo);

        // Load the custom bounce animation
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.bounce_animation);
        animator.setTarget(logoImageView);

        // Set listener to start the next activity when animation ends
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) { }

            @Override
            public void onAnimationEnd(Animator animator) {
                // Animation ended, start next activity
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                finish(); // Finish current activity
            }

            @Override
            public void onAnimationCancel(Animator animator) { }

            @Override
            public void onAnimationRepeat(Animator animator) { }
        });

        // Start the animation
        animator.start();
    }
}
