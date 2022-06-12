package com.example.projectepis;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class EventosDestacadosAdapater extends ArrayAdapter<Eventos> {
    /***********************************************************************************************
     *                                          ATRIBUTOS
     **********************************************************************************************/
    public ArrayList<Eventos> eventos;

    /***********************************************************************************************
     *                                          MÉTODOS
     **********************************************************************************************/
    /**
     * Clase Interna ViewHolder que contiene los atributos que contendrá el Adapter
     */
    static class ViewHolder {
        TextView tvNombre;
        TextView tvFecha;
        TextView tvHora;
        Button btAnadir;
    }


    public EventosDestacadosAdapater(Context context, ArrayList<Eventos> eventos) {
        super(context, R.layout.row_evento, eventos);
        this.eventos =eventos;
    }

    public void setForos(ArrayList<Eventos> eventos) {
        this.eventos = eventos;
        notifyDataSetChanged();
    }

    /**
     * Método
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Eventos eventos = getItem(position);
        ViewHolder viewHolder;

        // Comprobar si ya existe la lista, si no, la crea y si existe, la actualiza
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_evento, parent, false);
            viewHolder.tvNombre = convertView.findViewById(R.id.tvNombre);
            viewHolder.tvFecha = convertView.findViewById(R.id.tvFecha);
            viewHolder.tvHora = convertView.findViewById(R.id.tvHora);
            viewHolder.btAnadir=convertView.findViewById(R.id.btAnadir);
            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvNombre.setText(eventos.getNombre());
        viewHolder.tvFecha.setText(eventos.getFecha()+" ("+eventos .getUbicacion()+")");
        viewHolder.tvHora.setText(eventos.getHoraComienzo()+" - "+eventos.getHoraFinal());



        viewHolder.btAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth mAuth=FirebaseAuth.getInstance();
                DatabaseReference UserRef =FirebaseDatabase.getInstance().getReference().child("Eventos");;
                FirebaseUser user= mAuth.getCurrentUser();

                DatabaseReference EventosRef =  UserRef.child(user.getUid());

                EventosRef.child(eventos.getNombre()).child("ubicacion").setValue(eventos.getUbicacion());
                EventosRef.child(eventos.getNombre()).child("fecha").setValue(eventos.getFecha());
                EventosRef.child(eventos.getNombre()).child("Todo el dia").setValue(eventos.getTodoeldia());
                EventosRef.child(eventos.getNombre()).child("horaComienzo").setValue(eventos.getHoraComienzo());
                EventosRef.child(eventos.getNombre()).child("horaFinal").setValue(eventos.getHoraFinal());
                EventosRef.child(eventos.getNombre()).child("descripcion").setValue(eventos.getDescripcion());



                //Toast.makeText(getContext(),eventos.getNombre(),Toast.LENGTH_SHORT).show();


                /*Intent intent = new Intent(getContext(), .class);
                intent.putExtras(bundle);
                 startActivity(intent);*/
            }
        });

        return convertView;
    }



}
