package com.dennis_brink.android.myhigherlower;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;

    Animation animationImage, animationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);

        animationImage = AnimationUtils.loadAnimation(this, R.anim.image_animation);
        animationText = AnimationUtils.loadAnimation(this, R.anim.text_animation);

        imageView.setAnimation(animationImage);
        textView.setAnimation(animationText);

        // start a countdown after which the splash screen will be done and the main
        // activity will be started. We make it one second longer than the animation
        // which is set to a duration of 4000 milliseconds (animation xml's)
        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {
                // at each second this will happen
            }

            @Override
            public void onFinish() {
                // at the end of the countdown this happens
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish(); // remove splash
            }
        }.start();

    }
}