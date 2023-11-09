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
    private final HashMap<Character, Button> characterButtons = new HashMap<>();
    private LinearLayout keyboardLayout;
    private WindowManager windowManager;
    private DrawingView drawingView;
    private char currentCharacter;

    @Override
    public View onCreateInputView() {
        // Initialize windowManager
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        // Choose either DrawingView or Keyboard Layout based on your use case
        drawingView = new DrawingView(this, null);
        return drawingView; // For Drawing View
        // return createKeyboardLayout(); // For Keyboard Layout
    }

    private View createKeyboardLayout() {
        keyboardLayout = new LinearLayout(this);
        keyboardLayout.setOrientation(LinearLayout.VERTICAL);

        for (char c = 'A'; c <= 'Z'; c++) {
            Button keyButton = createKeyButton(c);
            keyboardLayout.addView(keyButton);
            characterButtons.put(c, keyButton); // Store the button for later use
        }

        return keyboardLayout;
    }

    private Bitmap loadBitmapFromStorage(String filename) {
        try (FileInputStream in = openFileInput(filename)) {
            return BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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

        Bitmap bitmap = loadBitmapFromStorage("key_" + character + ".png");
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
        return characterButtons.get(character); // Retrieve the button associated with the character
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
