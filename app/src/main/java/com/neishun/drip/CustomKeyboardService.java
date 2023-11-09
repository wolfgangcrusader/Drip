package com.neishun.drip;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.InputMethodService;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class CustomKeyboardService extends InputMethodService {
    private final HashMap<Character, Bitmap> characterBitmaps = new HashMap<>();
    private LinearLayout keyboardLayout;
    private WindowManager windowManager;
    private DrawingView drawingView;

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        drawingView = new DrawingView(this);
    }

    @Override
    public View onCreateInputView() {
        keyboardLayout = new LinearLayout(this);
        keyboardLayout.setOrientation(LinearLayout.VERTICAL);

        // Example for adding A-Z buttons, you should add all necessary buttons similarly
        for (char c = 'A'; c <= 'Z'; c++) {
            Button keyButton = createKeyButton(c);
            keyboardLayout.addView(keyButton);
        }

        return keyboardLayout;
    }

    private Button createKeyButton(final char character) {
        Button keyButton = new Button(this);
        keyButton.setText(String.valueOf(character));
        keyButton.setOnLongClickListener(v -> {
            showDrawingView(character);
            return true;
        });

        keyButton.setOnClickListener(v -> {
            getCurrentInputConnection().commitText(String.valueOf(character), 1);
        });

        Bitmap bitmap = loadBitmapFromStorage(character);
        if (bitmap != null) {
            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
            keyButton.setBackground(drawable);
        }

        return keyButton;
    }

    private void showDrawingView(char character) {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);

        drawingView.setOnDrawingDoneListener(bitmap -> {
            saveCharacterBitmap(bitmap, character);
            updateKeyWithBitmap(character, bitmap);
            windowManager.removeView(drawingView);
        });

        windowManager.addView(drawingView, params);
    }

    private void saveCharacterBitmap(Bitmap bitmap, char character) {
        String filename = "key_" + character + ".png";
        try (FileOutputStream out = openFileOutput(filename, Context.MODE_PRIVATE)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            characterBitmaps.put(character, bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateKeyWithBitmap(char character, Bitmap bitmap) {
        Button keyButton = findButtonByCharacter(character);
        if (keyButton != null) {
            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
            keyButton.setBackground(drawable);
        }
    }

    private Button findButtonByCharacter(char character) {
        // Implement this method based on your button storage logic
        return null; // Placeholder
    }

    private Bitmap loadBitmapFromStorage(char character) {
        String filename = "key_" + character + ".png";
        try (FileInputStream in = openFileInput(filename)) {
            return BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
