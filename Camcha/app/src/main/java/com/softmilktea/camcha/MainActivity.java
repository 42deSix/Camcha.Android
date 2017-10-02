package com.softmilktea.camcha;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

/**
 * Created by SEJIN on 2017-10-03.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatButton detectionButton = (AppCompatButton) findViewById(R.id.detection_button);
        AppCompatButton showMapButton = (AppCompatButton) findViewById(R.id.show_map_button);

        detectionButton.setOnClickListener(
                new AppCompatButton.OnClickListener() {
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, DetectionActivity.class));
                    }
                }
        );

//        showMapButton.setOnClickListener(
//                new AppCompatButton.OnClickListener() {
//                    public void onClick(View v) {
//                        startActivity(new Intent(MainActivity.this, showMapActivity.class));
//                    }
//                }
//        );
    }

}