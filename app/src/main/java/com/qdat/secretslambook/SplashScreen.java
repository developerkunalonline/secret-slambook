package com.qdat.secretslambook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.qdat.secretslambook.Contract.Contract;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences(Contract.PREFRANCE_NAME,MODE_PRIVATE);
                boolean isKnow = preferences.getBoolean(Contract.IS_KNOWING,false);
                if (!isKnow){
                    startActivity(new Intent(SplashScreen.this,InstructionScreen.class));
                    finish();

                }
                else {
                    startActivity(new Intent(SplashScreen.this,MainActivity.class));
                    finish();
                }

            }
        },2000);




    }
}