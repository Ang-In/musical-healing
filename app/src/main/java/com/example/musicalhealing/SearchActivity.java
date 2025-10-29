package com.example.musicalhealing;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicalhealing.R;

public class SearchActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        EditText moodInput = findViewById(R.id.moodInput);
        Button go = findViewById(R.id.goBtn);
        go.setOnClickListener(v -> {
            String mood = moodInput.getText().toString().trim();
            if (mood.isEmpty()) mood = "happy";
            startActivity(new Intent(this, PlayerActivity.class)
                    .putExtra("mood", mood));
        });
    }
}
