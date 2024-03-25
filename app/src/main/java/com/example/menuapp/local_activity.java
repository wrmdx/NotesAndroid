package com.example.menuapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class local_activity extends AppCompatActivity {

    private DBLhelper dbHelper;
    private EditText noteInput, idInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_activity);

        dbHelper = new DBLhelper(this);
        noteInput = findViewById(R.id.note_input);
        idInput = findViewById(R.id.id_input);

        Button addBtn = findViewById(R.id.add_local_btn);
        Button deleteBtn = findViewById(R.id.delete_local_btn);
        Button updateBtn = findViewById(R.id.update_local_btn);
        Button viewAllBtn = findViewById(R.id.view_local_btn);

        LinearLayout cardContainer = findViewById(R.id.card_container);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = noteInput.getText().toString().trim();
                long noteId = Long.parseLong(idInput.getText().toString().trim());
                dbHelper.addNoteWithId(note, noteId);
                refreshCards(cardContainer);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long noteIdToDelete = Long.parseLong(idInput.getText().toString().trim());
                if (dbHelper.deleteNoteById(noteIdToDelete)) {
                    refreshCards(cardContainer);
                } else {
                    Toast.makeText(local_activity.this, "Failed to delete note", Toast.LENGTH_SHORT).show();
                }
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long noteIdToUpdate = Long.parseLong(idInput.getText().toString().trim());
                String updatedNote = noteInput.getText().toString().trim();
                if (dbHelper.updateNoteById(noteIdToUpdate, updatedNote)) {
                    refreshCards(cardContainer);
                } else {
                    Toast.makeText(local_activity.this, "Failed to update note", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshCards(cardContainer);
            }
        });

        refreshCards(cardContainer);
    }

    private void refreshCards(LinearLayout cardContainer) {
        cardContainer.removeAllViews();

        List<String> notesList = dbHelper.getAllNotes();

        for (String note : notesList) {
            TextView textView = new TextView(this);
            textView.setText(note);
            cardContainer.addView(textView);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
