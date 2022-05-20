package com.example.projectepis;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {
    private ArrayList<Evento> arrEvent;
    private Context contextEvent;

    public EventAdapter(ArrayList<Evento> arr,Context con){
        this.arrEvent = arr;
        this.contextEvent = con;
    }
    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contextEvent)
                .inflate(R.layout.card_evento, parent, false);
        return new EventAdapter.EventHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {
        Log.d("===> RV", "onBind " + position);
        holder.title.setText(this.arrEvent.get(position).getTitol());
        holder.photo.setImageURI(Uri.parse(arrEvent.get(position).getPhoto()));
        Picasso.get().load(arrEvent.get(position).getPhoto()).into(holder.photo);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contextEvent, EventoDestacado.class);
                intent.putExtra("id", holder.title.getText());
                contextEvent.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrEvent.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView photo;
        public EventHolder(@NonNull View itemView) {
            super(itemView);
            Log.d("===> RV", "Setting info for element: " + R.id.nom);
            title = itemView.findViewById(R.id.nom);
            photo = itemView.findViewById(R.id.iconImage);
        }
    }
}
