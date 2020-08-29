package com.dev.hms;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class adapterreservation extends RecyclerView.Adapter<adapterreservation.MyViewHolder> {
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView nom,prix,etat,date,dure;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nom=(TextView) itemView.findViewById(R.id.nomProduit);
            prix =(TextView) itemView.findViewById(R.id.wilaya);
            etat=(TextView) itemView.findViewById(R.id.prix);
            date= (TextView) itemView.findViewById(R.id.rating);
            dure=(TextView) itemView.findViewById(R.id.localisation);

        }
    }
}
