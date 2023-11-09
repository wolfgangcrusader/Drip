package com.neishun.drip;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

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
                // All characters completed
                // TODO: Finish or start over
            } else {
                setupDrawingView(); // Set up the next character
            }
        });
    }

    private void setupDrawingView() {
        if (drawingView != null) {
            drawingArea.removeView(drawingView); // Remove the previous DrawingView if exists
        }
        drawingView = new DrawingView(this);
        drawingArea.addView(drawingView);
        // Set the listener or any other initial properties for the DrawingView
    }

    private void saveCharacterBitmap(Bitmap bitmap, char character) {
        // TODO: Implement bitmap saving logic
        // You can use the saveCharacterBitmap method from CustomKeyboardService as an example
    }
}
