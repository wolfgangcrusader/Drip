package com.neishun.drip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btnCreateCustomKeyboard = findViewById(R.id.btnCreateCustomKeyboard);
        btnCreateCustomKeyboard.setOnClickListener(v -> {
            // Start the KeyboardCreationActivity for customizing the keyboard
            Intent intent = new Intent(HomeActivity.this, KeyboardCreationActivity.class);
            startActivity(intent);
        });

        Button btnEnableKeyboard = findViewById(R.id.btnEnableKeyboard);
        btnEnableKeyboard.setOnClickListener(v -> {
            // Intent to open keyboard settings
            Intent intent = new Intent(android.provider.Settings.ACTION_INPUT_METHOD_SETTINGS);
            startActivity(intent);
        });
    }
}
