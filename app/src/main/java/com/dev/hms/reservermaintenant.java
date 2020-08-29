package com.dev.hms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class reservermaintenant extends AppCompatActivity {
    private TextView type,date1,date2,enterphoto,somme;
    private ImageView imageView;
    private Button reserver;
    private long dure;
    private static  final int IMAGE_PICK_CODE =1000;
    private static  final int PERMISSION_CODE =1001;
    private String[] listeitems;
    private FirebaseDatabase database,database1;
    private DatabaseReference userRef,userRef1;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    private  long difference;
    String dateone,datetwo,datedebut,datefin,typechambre;

    final ArrayList<String> listechambre = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservermaintenant);
        firebaseAuth=FirebaseAuth.getInstance();

        type=findViewById(R.id.type);
        reserver=findViewById(R.id.res);
        enterphoto=findViewById(R.id.entrerphoto);
        somme=findViewById(R.id.somme);
        date1=findViewById(R.id.date1);
        date2=findViewById(R.id.date2);
        imageView=findViewById(R.id.image);

        Calendar calendar =Calendar.getInstance();
        final  int year =calendar.get(Calendar.YEAR);
        final  int month =calendar.get(Calendar.MONTH);
        final  int day =calendar.get(Calendar.DAY_OF_MONTH);

        final  int year1 =calendar.get(Calendar.YEAR);
        final  int month1 =calendar.get(Calendar.MONTH);
        final  int day1 =calendar.get(Calendar.DAY_OF_MONTH);


        date1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog  =new DatePickerDialog(reservermaintenant.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year , int month, int day) {
                         month =month+1;
                          dateone =day+"/"+month+"/"+year;
                         date1.setText(dateone);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        date2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog  =new DatePickerDialog(reservermaintenant.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year1 , int month1, int day1) {
                        month1 =month1+1;
                         datetwo =day1+"/"+month1+"/"+year1;
                         date2.setText(datetwo);
                         calculerdifference(dateone,datetwo);
                    }
                },year1,month1,day1);
                datePickerDialog.show();


            }

        });









        database=FirebaseDatabase.getInstance();
        userRef = database.getReference("hotels");
        userRef.keepSynced(true);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String  key=getIntent().getStringExtra("nomhotel");
                firebaseAuth=FirebaseAuth.getInstance();
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    if (((firebaseAuth.getCurrentUser().getUid())!=null)) {
                        if (dataSnapshot1.child("nom").getValue().equals(key)) {

                             String duo =dataSnapshot1.child("type_chambre").child("duo").getValue().toString();
                             String solo =dataSnapshot1.child("type_chambre").child("solo").getValue().toString();
                             String studio =dataSnapshot1.child("type_chambre").child("studio").getValue().toString();
                             String triple =dataSnapshot1.child("type_chambre").child("triple").getValue().toString();

                             if (duo.equals("true")){
                                 listechambre.add("Duo");

                             }

                            if (solo.equals("true")){
                                listechambre.add("Solo");

                            }if (studio.equals("true")){
                                listechambre.add("Studio");

                            }if (triple.equals("true")){
                                listechambre.add("Triple");

                            }



                        }
                    }
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] lista = new String[listechambre.size()];
                lista =listechambre.toArray(lista);
                AlertDialog.Builder mbuilder2=new AlertDialog.Builder(reservermaintenant.this);
                mbuilder2.setTitle("Selectionner Type de chambre");
                final String[] finalLista1 = lista;
                mbuilder2.setSingleChoiceItems( lista, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        type.setText(finalLista1[which]);
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog =mbuilder2.create();
                alertDialog.show();
            }
        });



        enterphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                        String [] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE };
                        requestPermissions(permissions , PERMISSION_CODE );
                    }else {
                         pickImageFromGalery();
                    }
                }else {
                    pickImageFromGalery();

                }

            }
        });
        
        reserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 typechambre =type.getText().toString();
                 datedebut =date1.getText().toString();
                 datefin =date2.getText().toString();
                
                if (typechambre.equals("Type de chambre")){
                    type.setError("entrer le type de chambre");
                    type.requestFocus();
                    return;
                }
                
                if (datedebut.equals("Date d'entrée")){
                    date1.setError("entrer la date");
                    date1.requestFocus();
                    return;

                }
                
                if (datefin.equals("Date de Sortie")){
                    date1.setError("entrer la date");
                    date1.requestFocus();
                    return;
                }
                else {
                    ajouter();
                }
                        
            }
        });
        
        



    }

    private void ajouter() {
        final DatabaseReference rootref;
        rootref= FirebaseDatabase.getInstance().getReference();
        rootref.keepSynced(true);
        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final HashMap<String, Object> userDataMap = new HashMap<>();
                userDataMap.put("typechambre",typechambre);
                userDataMap.put("datedebut", datedebut);
                final String  key=getIntent().getStringExtra("nomhotel");
                userDataMap.put("nomHotel", key);
                userDataMap.put("datefin", datefin);
                userDataMap.put("etat", "Confirmé");
                userDataMap.put("dure",String.valueOf(dure)+" jours");
                userDataMap.put("email",firebaseAuth.getCurrentUser().getEmail());
                rootref.child("reservation").child(firebaseAuth.getCurrentUser().getUid()+key+ new Random().nextInt(10000)).updateChildren(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(reservermaintenant.this,"Votre Reservation  a été terminée avec succés",Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent(reservermaintenant.this,home.class);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void calculerdifference(String dateone, String datetwo) {
               DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/uuuu");
               String firstDate = dateone;
               String secondeDate =datetwo;

               LocalDate date1 =LocalDate.parse(firstDate,formatter);
               LocalDate date2 =LocalDate.parse(secondeDate,formatter);
               long daybetwen = ChronoUnit.DAYS.between(date1,date2);
               dure= daybetwen;
               somme.setText(String.valueOf(daybetwen));


    }

    private void pickImageFromGalery() {

        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case  PERMISSION_CODE: {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGalery();

                }else {
                    Toast.makeText(this," permission denied ..!",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            imageView.setImageURI(data.getData());
        }
    }
}