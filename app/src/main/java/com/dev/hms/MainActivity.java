package com.dev.hms;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth=FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                connexion();
            }
        }, 2000);
    }

    private void connexion() {
        if (firebaseAuth.getCurrentUser()!=null){
            Intent intent =new Intent(MainActivity.this,home.class);
            startActivity(intent);


        }else {
            Intent intent =new Intent(MainActivity.this,login.class);
            startActivity(intent);

        }
    }
}
