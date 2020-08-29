package com.dev.hms;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.MyViewHolder> {

    Context context;
    ArrayList<info> hotels;
    public adapter(Context c, ArrayList<info> p){
        context=c;
        hotels=p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.nom.setText(hotels.get(position).getNom());
        holder.wilaya.setText(hotels.get(position).getWilaya());
        holder.Prix.setText(hotels.get(position).getPrix_par_nuit()+" DA");

        float etoile= Float.parseFloat(hotels.get(position).getRating().toString());
        holder.ratingBar.setRating(etoile);

        holder.localisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:"+hotels.get(position).getLocalisation()));
                Intent.createChooser(intent,"Launch Maps");
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context,reservation.class);
                intent.putExtra("nomhotel",hotels.get(position).getNom());
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nom,wilaya,Prix,localisation;
        RatingBar ratingBar;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nom=(TextView) itemView.findViewById(R.id.place_name);
            wilaya =(TextView) itemView.findViewById(R.id.wilaya);
            Prix=(TextView) itemView.findViewById(R.id.prix);
            ratingBar= (RatingBar) itemView.findViewById(R.id.rating);
            localisation=(TextView) itemView.findViewById(R.id.localisation);


        }
    }
}
