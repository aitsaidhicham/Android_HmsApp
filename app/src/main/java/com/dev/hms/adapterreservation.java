package com.dev.hms;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapterreservation extends RecyclerView.Adapter<adapterreservation.MyViewHolder> {
    Context context;
    ArrayList<inforeservation> reservation;
    public adapterreservation(Context c, ArrayList<inforeservation> p){
        context=c;
        reservation=p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemreservation, parent, false);
        adapterreservation.MyViewHolder holder=new adapterreservation.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nom.setText(reservation.get(position).getNomHotel());
        holder.etat.setText(reservation.get(position).getEtat());
        holder.dure.setText(reservation.get(position).getDure());
        holder.prix.setText(reservation.get(position).getPrix());
        holder.date.setText(reservation.get(position).getDatedebut());

        if (reservation.get(position).getEtat().equals("Confirmé")){
            holder.etat.setText(reservation.get(position).getEtat());
            final Drawable d = context.getResources().getDrawable(R.drawable.confirmee);
            final int r = context.getResources().getColor(R.color.white);
            holder.etat.setTextColor(r);
            holder.etat.setBackground(d);
        }
    }

    @Override
    public int getItemCount() {
        return reservation.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView nom,prix,etat,date,dure;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nom=(TextView) itemView.findViewById(R.id.nomProduit);
            prix =(TextView) itemView.findViewById(R.id.prix);
            etat=(TextView) itemView.findViewById(R.id.etat);
            date= (TextView) itemView.findViewById(R.id.date);
            dure=(TextView) itemView.findViewById(R.id.pripr);

        }
    }
}
