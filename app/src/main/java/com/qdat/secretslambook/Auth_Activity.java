package com.qdat.secretslambook;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.qdat.secretslambook.Contract.Contract;

public class Auth_Activity extends AppCompatActivity {

    private String temp_password = "12345";
    String pass="";
    String xlen="";


    MediaPlayer player;
    Button clear,ok;
    TextView password_Text_view;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        init();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String realPass = preferences.getString(Contract.APP_PASSWORD,"12345");
                if (authenticate(pass,realPass)){
                    Toast.makeText(Auth_Activity.this, "Password Accepted"
                            , Toast.LENGTH_SHORT).show();
                    player = MediaPlayer.create(Auth_Activity.this,R.raw.kung_fu);
                    player.start();

                    finish();
                }
                else{
                    password_Text_view.setText("Wrong Password !");
                    Toast.makeText(Auth_Activity.this, "Login Failed !", Toast.LENGTH_SHORT).show();

                }

                pass="";
                xlen="";

            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password_Text_view.setText("Enter Password");
                xlen = "";
                pass = "";
            }
        });



    }


    public void num_click(View view){

        pass +=view.getTag().toString();
        xlen +="X";
        password_Text_view.setText(xlen);

        if (!player.isPlaying()){
            player.seekTo(340);
            player.start();
        }
        else{
            player.seekTo(340);

        }


    }


    private void init(){

        clear = findViewById(R.id.clear_all);
        ok = findViewById(R.id.ok);
        player = MediaPlayer.create(this,R.raw.punch_effect);
        password_Text_view = findViewById(R.id.password_entry);
        preferences = this.getSharedPreferences(Contract.PREFRANCE_NAME,MODE_PRIVATE);

    }

    private boolean authenticate(String yourPass,String realPass){

        if (realPass.length()!=yourPass.length()){

//            Toast.makeText(this, "Missmatch Start", Toast.LENGTH_SHORT).show();

            return false;
        }

        for (int i = 0;i<realPass.length();i++){
            if (realPass.charAt(i)!=yourPass.charAt(i)){

//                Toast.makeText(this, "real --> "+realPass.charAt(i)+" your --> "+yourPass.charAt(i), Toast.LENGTH_SHORT).show();

                return false;
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {

    }
}