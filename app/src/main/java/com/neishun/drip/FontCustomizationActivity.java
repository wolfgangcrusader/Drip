package com.neishun.drip;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;

public class FontCustomizationActivity extends AppCompatActivity {
    private LinearLayout drawingArea;
    private DrawingView drawingView;
    private Button btnSaveCharacter;
    private char currentCharacter = 'A'; // Start with 'A'

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font_customization);

        drawingArea = findViewById(R.id.drawingArea);
        btnSaveCharacter = findViewById(R.id.btnSaveCharacter);

        setupDrawingView();

        btnSaveCharacter.setOnClickListener(v -> {
            Bitmap characterBitmap = drawingView.getBitmap();
            saveCharacterBitmap(characterBitmap, currentCharacter);
            // Proceed to the next character or finish if done
            currentCharacter++;
            if (currentCharacter > 'Z') {
                Toast.makeText(this, "Character customization complete!", Toast.LENGTH_SHORT).show();
                // Option 1: Close the activity
                finish();
                // Intent intent = new Intent(this, MainMenuActivity.class);
                // startActivity(intent);
            } else {
                setupDrawingView(); // Set up the next character
            }
        });
    }

    private void setupDrawingView() {
        if (drawingView != null) {
            drawingArea.removeView(drawingView); // Remove the previous DrawingView if exists
        }
        drawingView = new DrawingView(this, null);
        drawingArea.addView(drawingView);
        // Set the listener or any other initial properties for the DrawingView
    }


    private void saveCharacterBitmap(Bitmap bitmap, char character) {
        String filename = character + "_custom_char.png";
        try (FileOutputStream out = openFileOutput(filename, Context.MODE_PRIVATE)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            // PNG is a lossless format, the compression factor (100) is ignored
            Toast.makeText(this, "Character " + character + " saved!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("FontCustomizationActivity", "Error saving character " + character, e);
            Toast.makeText(this, "Error saving character " + character, Toast.LENGTH_SHORT).show();
        }
    }

}
