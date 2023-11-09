package com.neishun.drip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ViewPager2 tutorialViewPager = findViewById(R.id.tutorialViewPager);
        Button btnContinue = findViewById(R.id.btnContinue);

        int[] tutorialImages = {
                R.drawable.t1,
                R.drawable.t2,
                R.drawable.t3
        };

        TutorialAdapter adapter = new TutorialAdapter(tutorialImages);
        tutorialViewPager.setAdapter(adapter);

        btnContinue.setOnClickListener(v -> goToHomeScreen());
    }

    private void goToHomeScreen() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
