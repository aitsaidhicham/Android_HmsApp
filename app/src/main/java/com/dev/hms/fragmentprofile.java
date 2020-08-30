package com.dev.hms;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class fragmentprofile extends Fragment {
    private EditText nom,num;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;
    public Uri imageuri;
    private TextView email,wilaya;
    private CircleImageView imageView;
    private Button valider;
    private static  final int IMAGE_PICK_CODE =1000;
    private static  final int PERMISSION_CODE =1001;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentprofile,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nom=view.findViewById(R.id.nom);
        num=view.findViewById(R.id.num);
        valider=view.findViewById(R.id.signup);
        wilaya=view.findViewById(R.id.wilaya);
        email=view.findViewById(R.id.email);
        imageView=view.findViewById(R.id.profile_image);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pickImageFromGalery();

            }
        });

        firebaseAuth=FirebaseAuth.getInstance();

        reference= FirebaseDatabase.getInstance().getReference().child("client");
        reference.keepSynced(true);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                    if (dataSnapshot1.child("email").getValue().equals(firebaseAuth.getCurrentUser().getEmail())) {
                        nom.setText(dataSnapshot1.child("nometprenom").getValue().toString());
                        email.setText(dataSnapshot1.child("email").getValue().toString());
                        wilaya.setText(dataSnapshot1.child("wilaya").getValue().toString());
                        num.setText(dataSnapshot1.child("numerotlphn").getValue().toString());

                        if (dataSnapshot1.child("urlimage").exists()){
                            Picasso.get().load(dataSnapshot1.child("urlimage").getValue().toString()).into(imageView);
                        }

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });












    }

    private void pickImageFromGalery() {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,"choisissez votre photo de profil"),IMAGE_PICK_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){

            imageuri=data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),imageuri);
                imageView.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }


    }
}
