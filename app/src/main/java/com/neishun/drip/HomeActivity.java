package com.neishun.drip;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Button to start the KeyboardMaking activity
        Button btnCreateCustomKeyboard = findViewById(R.id.btnCreateCustomKeyboard);
        btnCreateCustomKeyboard.setOnClickListener(v -> {
            // Start the KeyboardMakingActivity to create the custom keyboard
            Intent intent = new Intent(HomeActivity.this, KeyboardMakingActivity.class);
            startActivity(intent);
        });

        Button btnCreateCustomFont = findViewById(R.id.btnCreateCustomFont);
        btnCreateCustomFont.setOnClickListener(v -> {
            // Start the FontCustomisationActivity
            Intent intent = new Intent(HomeActivity.this, FontCustomisationActivity.class);
            startActivity(intent);
        });


        // Button to enable the custom keyboard in settings
        Button btnEnableCustomKeyboard = findViewById(R.id.btnEnableCustomKeyboard);
        btnEnableCustomKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open keyboard settings
                Intent intent = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
                startActivity(intent);
            }
        });
    }
}
