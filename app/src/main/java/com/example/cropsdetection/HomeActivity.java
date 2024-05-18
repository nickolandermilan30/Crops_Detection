package com.example.cropsdetection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button getStartedButton = findViewById(R.id.next);

        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoadingDialog(); // Tawagin ang method na nagpapakita ng loading dialog

                // Gumawa ng Handler para maghintay ng ilang sandali bago simulan ang NoteActivity
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Lumikha ng Intent para sa NoteActivity
                        Intent intent = new Intent(HomeActivity.this, NoteActivity.class);
                        // Simulan ang NoteActivity
                        startActivity(intent);
                        dismissLoadingDialog(); // Itago ang loading dialog kapag tapos na
                    }
                }, 2000); // I-set ang delay na 2000 milliseconds o 2 seconds
            }
        });
    }

    private ProgressDialog progressDialog;

    private void showLoadingDialog() {
        progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false); // Huwag payagan ang pag-dismiss sa pamamagitan ng pag-tap sa labas ng dialog
        progressDialog.show();
    }

    private void dismissLoadingDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
