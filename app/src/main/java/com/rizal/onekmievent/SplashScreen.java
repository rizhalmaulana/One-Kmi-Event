package com.rizal.onekmievent;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Thread(() -> {
            try{
                Thread.sleep(3500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                runOnUiThread(() -> {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                });
            }
        }).start();
    }
}
