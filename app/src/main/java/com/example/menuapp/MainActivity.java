package com.example.menuapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get references to the buttons
        Button localBtn = findViewById(R.id.local_btn);
        Button serverBtn = findViewById(R.id.server_btn);
        Button hybridBtn = findViewById(R.id.hybrid_btn);

        // Set click listeners for the buttons
        localBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start LocalActivity when Local button is clicked
                Intent intent = new Intent(MainActivity.this, local_activity.class);
                startActivity(intent);
            }
        });

        serverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start ServerActivity when Server button is clicked
                Intent intent = new Intent(MainActivity.this, server_activity.class);
                startActivity(intent);
            }
        });

        hybridBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start HybridActivity when Hybrid button is clicked
                Intent intent = new Intent(MainActivity.this, hybrid_activity.class);
                startActivity(intent);
            }
        });
    }
}
