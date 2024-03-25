package com.example.menuapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;


public class hybrid_activity extends AppCompatActivity {
    private LinearLayout notesContainer; // Declare notesContainer as a class-level variable

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hybrid_activity);

        Button viewBtn = findViewById(R.id.view_hybrid_btn);

        notesContainer = findViewById(R.id.card_hybrid_server_container);

        // Get the ID and note from the intent extras
        String id = getIntent().getStringExtra("id");
        String note = getIntent().getStringExtra("note");

        String[] noteArray = note.split(";");
        for (String notes : noteArray) {
            TextView textView = new TextView(this);
            textView.setText(notes); // Set the text to the current individual note

            // Define layout parameters for the TextView
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            // Set margins for the TextView (optional)
            layoutParams.setMargins(0, 0, 0, 16); // Example margins: 0px top, 0px left, 0px bottom, 16px right

            // Set the layout parameters for the TextView
            textView.setLayoutParams(layoutParams);

            // Add the TextView to the notesContainer
            notesContainer.addView(textView);
        }
    }

}
