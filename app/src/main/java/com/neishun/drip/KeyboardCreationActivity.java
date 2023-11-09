package com.neishun.drip;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;

public class KeyboardCreationActivity extends AppCompatActivity {
    private char currentKey = 'A';
    private DrawingView drawingView;
    private Button btnNext, btnPrevious, btnClear, btnSaveKey, btnUploadBackground;
    private static final int PICK_IMAGE = 1;
    private ActivityResultLauncher<Intent> mGetContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard_creation);

        drawingView = findViewById(R.id.drawingView);
        btnNext = findViewById(R.id.btnNext);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnClear = findViewById(R.id.btnClear);
        btnSaveKey = findViewById(R.id.btnSaveKey);
        btnUploadBackground = findViewById(R.id.btnUploadBackground);

        setupButtons();
    }

    private void setupButtons() {
        btnNext.setOnClickListener(v -> {
            if (currentKey < 'Z') {
                currentKey++;
                updateKeyDisplay();
            }
        });

        btnPrevious.setOnClickListener(v -> {
            if (currentKey > 'A') {
                currentKey--;
                updateKeyDisplay();
            }
        });

        btnClear.setOnClickListener(v -> drawingView.clearCanvas());

        btnSaveKey.setOnClickListener(v -> {
            Bitmap keyDesign = drawingView.getBitmap();
            saveKeyDesign(keyDesign, String.valueOf(currentKey));
        });

        btnUploadBackground.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            mGetContent.launch(intent);
        });
    }

    private void updateKeyDisplay() {
        drawingView.setCurrentKey(currentKey);
        drawingView.invalidate();
    }

    private void saveKeyDesign(Bitmap design, String keyName) {
        String filename = keyName + "_key_design.png";
        saveDesign(design, filename);
    }

    private void saveDesign(Bitmap design, String filename) {
        try (FileOutputStream out = openFileOutput(filename, Context.MODE_PRIVATE)) {
            design.compress(Bitmap.CompressFormat.PNG, 100, out);
            Toast.makeText(this, filename + " saved!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("KeyboardCreationActivity", "Error saving design", e);
            Toast.makeText(this, "Error saving " + filename, Toast.LENGTH_SHORT).show();
        }
    }
}
