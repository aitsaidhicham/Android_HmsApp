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

public class fragmentfavoire extends Fragment {
    private FirebaseDatabase database,database1;
    private FirebaseAuth firebaseAuth;
    DatabaseReference reference,reference1;
    RecyclerView recyclerView;

    ArrayList<info> list;
    adapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentfavoire,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView= view.findViewById(R.id.recent_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        list =new ArrayList<info>();
        database = FirebaseDatabase.getInstance();
        reference= database.getReference().child("favorise");
        reference.keepSynced(true);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                firebaseAuth=FirebaseAuth.getInstance();

                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    if((dataSnapshot1.child("nomClient").getValue().toString().equals(firebaseAuth.getCurrentUser().getEmail()))&& (dataSnapshot1.child("etat").getValue().toString().equals("favorise"))){
                        info p = dataSnapshot1.getValue(info.class);
                        list.add(p);
                    }
                    adapter = new adapter(getContext(), list);
                    recyclerView.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}
