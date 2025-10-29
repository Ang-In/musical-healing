package com.example.musicalhealing;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicalhealing.R;

public class MainActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button start = findViewById(R.id.startBtn);
        start.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SearchActivity.class))
        );
    }
}
