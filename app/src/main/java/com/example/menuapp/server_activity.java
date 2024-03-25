package com.example.menuapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.widget.LinearLayout;


public class server_activity extends AppCompatActivity {

    private static final String TAG = server_activity.class.getSimpleName();
    private static final String SERVER_URL = "http://10.0.2.2/notes/";
    private EditText idInput, noteInput;
    private LinearLayout notesContainer; // Declare notesContainer as a class-level variable


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server_activity);

        idInput = findViewById(R.id.id_input);
        noteInput = findViewById(R.id.note_input);

        Button addBtn = findViewById(R.id.add_server_btn);
        Button deleteBtn = findViewById(R.id.delete_server_btn);
        Button updateBtn = findViewById(R.id.update_server_btn);
        Button viewBtn = findViewById(R.id.view_server_btn);

        notesContainer = findViewById(R.id.card_server_container); // Initialize notesContainer


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNote();
            }
        });

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewAllNotes();
            }
        });
        viewAllNotes();
    }

    private void addNote() {
        String id = idInput.getText().toString().trim(); // Get the id from EditText
        String note = noteInput.getText().toString().trim();

        String url = SERVER_URL + "insert.php";
        Log.d(TAG, "Sending request to: " + url); // Debugging line to check connection


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            if (success == 1) {
                                Toast.makeText(getApplicationContext(), "Note added successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Failed to add note", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "JSON parsing error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error adding note: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Error adding note", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("note", note);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void deleteNote() {
        String id = idInput.getText().toString().trim();

        String url = SERVER_URL + "delete.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            if (success == 1) {
                                Toast.makeText(getApplicationContext(), "Note deleted successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Failed to delete note", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "JSON parsing error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error deleting note: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Error deleting note", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void updateNote() {
        String id = idInput.getText().toString().trim();
        String note = noteInput.getText().toString().trim();

        String url = SERVER_URL + "update.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            if (success == 1) {
                                Toast.makeText(getApplicationContext(), "Note updated successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Failed to update note", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "JSON parsing error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error updating note: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Error updating note", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("note", note);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void viewAllNotes() {
        notesContainer.removeAllViews();
        String url = SERVER_URL + "viewList.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            if (success == 1) {
                                JSONArray notesArray = jsonObject.getJSONArray("notes");
                                // Process the JSON array containing notes
                                processNotesArray(notesArray);
                            } else {
                                Toast.makeText(getApplicationContext(), "No notes found", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "JSON parsing error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error viewing notes: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Error viewing notes", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void processNotesArray(JSONArray notesArray) {
        try {
            for (int i = 0; i < notesArray.length(); i++) {
                JSONObject noteObject = notesArray.getJSONObject(i);
                String id = noteObject.getString("id");
                String note = noteObject.getString("note");
                Log.d(TAG, "Note ID: " + id + ", Note: " + note);
                // Display the id and note in a TextView
                displayNote(id, note);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void displayNote(String id, String note) {
        // Create a TextView for each note and add it to a layout container
        TextView textView = new TextView(this);
        textView.setText("ID: " + id + ", Note: " + note);
        // Assuming you have a layout container named 'notesContainer'
        notesContainer.addView(textView);

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
