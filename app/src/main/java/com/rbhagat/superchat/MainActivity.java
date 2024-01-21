package com.rbhagat.superchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i;
                if (user!=null)
                {
                    i = new Intent(MainActivity.this, Dashboard.class);
                }
                else
                {
                    i = new Intent(MainActivity.this, loginActivity.class);
                }
                startActivity(i);

                finish();
            }
        }, 2500);
    }
}