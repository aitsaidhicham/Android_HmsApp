package com.dev.hms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class reservation extends AppCompatActivity {
    private Button reserver,apperler,localisation;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private FirebaseAuth firebaseAuth;
    private ImageView cheque;
    private TextView information;
    private String loc,num,nom,rating,prix,wilaya,emailhotel,info,url;
    private ImageView favorise,defavorise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);



        reserver=findViewById(R.id.reserver);
        cheque=findViewById(R.id.image);
        information=findViewById(R.id.information);

        apperler=findViewById(R.id.appeler);
        localisation=findViewById(R.id.localisation);
        favorise=findViewById(R.id.favorise);
        defavorise=findViewById(R.id.defavorise);

        firebaseAuth=FirebaseAuth.getInstance();

        reserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),reservermaintenant.class);
                intent.putExtra("emailHotel",emailhotel);

                intent.putExtra("nomhotel",nom);
                startActivity(intent);
            }
        });

        database=FirebaseDatabase.getInstance();
        userRef = database.getReference("hotels");
        userRef.keepSynced(true);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String  key=getIntent().getStringExtra("nomhotel");

                firebaseAuth=FirebaseAuth.getInstance();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (((firebaseAuth.getCurrentUser().getUid())!=null)){
                        if (dataSnapshot1.child("nom").getValue().equals(key)) {
                             num =dataSnapshot1.child("numero_telephone").getValue().toString();
                             loc =dataSnapshot1.child("localisation").getValue().toString();
                             nom=dataSnapshot1.child("nom").getValue().toString();
                             emailhotel=dataSnapshot1.child("email").getValue().toString();
                             info=dataSnapshot1.child("description").getValue().toString();
                             url=dataSnapshot1.child("image").getValue().toString();




                            rating=dataSnapshot1.child("Rating").getValue(String.class).toString();
                             prix=dataSnapshot1.child("prix_par_nuit").getValue(String.class).toString();
                             wilaya=dataSnapshot1.child("wilaya").getValue(String.class).toString();
                            information.setText(info);
                            Picasso.get().load(url).into(cheque);


                        }
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        final DatabaseReference rootref;
        rootref= FirebaseDatabase.getInstance().getReference("favorise");
        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    if (((firebaseAuth.getCurrentUser().getUid()) != null)) {
                        if ((dataSnapshot1.child("nomClient").getValue().equals(firebaseAuth.getCurrentUser().getEmail()))&& (dataSnapshot1.child("nom").getValue().equals(nom)) ){
                            if (dataSnapshot1.child("etat").getValue().toString().equals("favorise")) {
                            favorise.setVisibility(View.GONE);
                            defavorise.setVisibility(View.VISIBLE);


                            }
                            if (dataSnapshot1.child("etat").getValue().toString().equals("non favorise")) {
                                favorise.setVisibility(View.VISIBLE);
                                defavorise.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        localisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:"+loc));
                Intent.createChooser(intent,"Launch Maps");
                startActivity(intent);
            }
        });

        apperler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+num));
                startActivity(intent);
            }
        });

        favorise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference rootref;
                rootref= FirebaseDatabase.getInstance().getReference("favorise");
                rootref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (((firebaseAuth.getCurrentUser().getUid()) != null)) {

                                favoriserhotel(nom);







                                }
                            }




                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });

        defavorise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference rootref;
                rootref= FirebaseDatabase.getInstance().getReference("favorise");
                rootref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (((firebaseAuth.getCurrentUser().getUid()) != null)) {

                            defavorise(nom);







                        }
                    }




                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


    }

    private void defavorise(final String nom) {
        final DatabaseReference rootref;
        rootref= FirebaseDatabase.getInstance().getReference("favorise");
        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    if (((firebaseAuth.getCurrentUser().getUid()) != null)) {
                        if ((dataSnapshot1.child("nomClient").getValue().equals(firebaseAuth.getCurrentUser().getEmail())) && (dataSnapshot1.child("nom").getValue().equals(nom))) {
                            String id=dataSnapshot1.child("ID").getValue().toString();
                            final HashMap<String, Object> userDataMap = new HashMap<>();
                            userDataMap.put("etat","non favorise");

                            rootref.child(id).updateChildren(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        defavorise.setVisibility(View.INVISIBLE);
                                        Toast.makeText(reservation.this,"défavorisé",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void favoriserhotel(final String nom) {
        final DatabaseReference rootref;
        rootref= FirebaseDatabase.getInstance().getReference();
        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                        final HashMap<String, Object> userDataMap = new HashMap<>();
                        userDataMap.put("nom",nom);
                        userDataMap.put("etat","favorise");
                        userDataMap.put("nomClient",firebaseAuth.getCurrentUser().getEmail());
                        userDataMap.put("localisation",loc);
                        userDataMap.put("Rating",rating);
                        userDataMap.put("wilaya",wilaya);
                        userDataMap.put("prix_par_nuit",prix);
                        userDataMap.put("image",url);





                String pr= firebaseAuth.getCurrentUser().getUid().substring(1,4);
                        userDataMap.put("ID",nom+pr);


                rootref.child("favorise").child(nom+pr).updateChildren(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    favorise.setVisibility(View.INVISIBLE);
                                    Toast.makeText(reservation.this,"favorisé",Toast.LENGTH_SHORT).show();
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