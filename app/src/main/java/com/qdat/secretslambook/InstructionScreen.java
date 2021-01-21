package com.qdat.secretslambook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.qdat.secretslambook.Contract.Contract;

public class InstructionScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction_screen);

        findViewById(R.id.got_it).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences(Contract.PREFRANCE_NAME,MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(Contract.IS_KNOWING,true);
                editor.apply();
                startActivity(new Intent(InstructionScreen.this,MainActivity.class));
                finish();

            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}