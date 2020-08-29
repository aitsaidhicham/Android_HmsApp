package com.dev.hms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class fragmentacceuil extends Fragment {
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private FirebaseAuth firebaseAuth;
    private TextView name;
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<info> list;
    adapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentacceuil,container,false);

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name=view.findViewById(R.id.textView);
        firebaseAuth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("client");
        userRef.keepSynced(true);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                firebaseAuth=FirebaseAuth.getInstance();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (((firebaseAuth.getCurrentUser().getUid())!=null)){
                        if (dataSnapshot1.child("email").getValue().equals(firebaseAuth.getCurrentUser().getEmail())) {
                            name.setText("Salut, "+(dataSnapshot1.child("nometprenom").getValue(String.class)));
                        }}
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerView= view.findViewById(R.id.recent_recycler);
        recyclerView.setNestedScrollingEnabled(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        list =new ArrayList<info>();

        database = FirebaseDatabase.getInstance();
        reference= database.getReference().child("hotels");
        reference.keepSynced(true);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                firebaseAuth=FirebaseAuth.getInstance();

                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {


                        info p = dataSnapshot1.getValue(info.class);
                        list.add(p);




                    }

                adapter = new adapter(getContext(), list);
                recyclerView.setAdapter(adapter);

                    }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }
}
