package com.dev.hms;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class signup extends AppCompatActivity {
    private EditText nom,num,email,password;
    private TextView wilaya;
    private Button signup;
    private FirebaseAuth firebaseAuth;
    private String[] listeitems;
    public String    NometPrenom ,numtlphn,mail,motpasse,lawilaya;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        nom=findViewById(R.id.nom);
        num=findViewById(R.id.num);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        wilaya=findViewById(R.id.wilaya);
        signup=findViewById(R.id.signup);

        wilaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listeitems=new String[]{"Alger","Oran","Mostaganem"};
                AlertDialog.Builder mbuilder=new AlertDialog.Builder(signup.this);
                mbuilder.setTitle("Selectionner votre Wilaya");
                mbuilder.setSingleChoiceItems(listeitems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        wilaya.setText(listeitems[which]);
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog =mbuilder.create();
                alertDialog.show();

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NometPrenom=nom.getText().toString();
                numtlphn=num.getText().toString();
                lawilaya=wilaya.getText().toString();
                motpasse=password.getText().toString();
                mail=email.getText().toString();


                if (NometPrenom.length()<=6){
                   nom.setError("Ecrivez votre nom et prénom");
                   nom.requestFocus();
                   return;
                }
                if (numtlphn.length()<10){
                      num.setError("Ecrivez un numéro valide");
                      num.requestFocus();
                      return;

                }
                if (mail.length()<13){
                    email.setError("Ecrivez un email valide");
                    email.requestFocus();
                    return;

                }
                if ((motpasse.length()<5)|(motpasse.length()>12)){
                    password.setError("le mot passe doit etre entre entre 5 et 12 character");
                    password.requestFocus();
                    return;

                }

                if (lawilaya.equals("wialaya")){
                    wilaya.setError("Selectionner votre Wilaya");
                    wilaya.requestFocus();
                    return;

                }else {


                        signup(mail, motpasse);




                }
            }
        });





    }

    private void signup(final String mail, final String motpasse) {
        firebaseAuth=FirebaseAuth.getInstance();

        final DatabaseReference rootref;
        rootref= FirebaseDatabase.getInstance().getReference();
        rootref.keepSynced(true);
        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot snapshot) {
                firebaseAuth.createUserWithEmailAndPassword(mail,motpasse).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                      if (task.isSuccessful()){
                          if (!(snapshot.child("client").child(firebaseAuth.getCurrentUser().getUid()).exists())) {
                              final HashMap<String, Object> userDataMap = new HashMap<>();
                              userDataMap.put("numerotlphn", numtlphn);
                              userDataMap.put("email", mail);
                              userDataMap.put("nometprenom", NometPrenom);
                              userDataMap.put("wilaya", lawilaya);
                              userDataMap.put("pswrd", motpasse);


                              rootref.child("client").child(firebaseAuth.getCurrentUser().getUid()).updateChildren(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                  @Override
                                  public void onComplete(@NonNull Task<Void> task) {
                                      if ((task.isSuccessful())){
                                          Toast.makeText(signup.this,"Votre inscription a été terminée avec succés",Toast.LENGTH_SHORT).show();
                                          Intent intent =new Intent(signup.this,home.class);
                                          startActivity(intent);

                                      }
                                  }
                              });



                          }

                      }
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }





}
