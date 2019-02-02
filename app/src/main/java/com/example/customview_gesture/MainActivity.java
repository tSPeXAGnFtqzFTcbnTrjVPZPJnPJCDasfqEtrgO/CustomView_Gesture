package com.example.customview_gesture;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.customview_gesture.view.Custom;
import com.example.customview_gesture.view.CustomGesture;

public class MainActivity extends AppCompatActivity {

    GestureDetector gestureDetector;
    Custom customView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customView = findViewById(R.id.customView);

        for(int i=1;i<=5;i++){
            customView.addDot();
        }
    }
}
