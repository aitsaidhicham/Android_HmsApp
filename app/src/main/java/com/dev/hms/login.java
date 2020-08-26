package com.dev.hms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    private TextView create;
    FirebaseAuth firebaseAuth;
    private EditText email,pswd;
    private Button login;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);


        create=findViewById(R.id.create_account);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),signup.class);
                startActivity(intent);
            }
        });

        login=findViewById(R.id.auth);
        email=findViewById(R.id.email);
        pswd=findViewById(R.id.password);
        firebaseAuth = FirebaseAuth.getInstance();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email.getText().toString();
                String motpasse = pswd.getText().toString();

                if (mail.length() < 13) {
                    email.setError("Ecrivez votre email");
                    email.requestFocus();
                    return;

                }

                if ((motpasse.length() < 5) && (motpasse.length() > 12)) {
                    pswd.setError("Ecrivez votre mot passe");
                    pswd.requestFocus();
                    return;

                }


                    firebaseAuth.signInWithEmailAndPassword(mail,motpasse).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(login.this, "votre Connexion est réussie", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(login.this, home.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(login.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }

            });


}

    }





