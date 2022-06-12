package com.example.projectepis;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvNombre.setText(eventos.getNombre());
        viewHolder.tvFecha.setText(eventos.getFecha());
        viewHolder.tvHora.setText(eventos.getHoraComienzo());


        // Establecer el listener para que cada vez que se cliquee lleve a la nueva Activity
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                /*Intent intent = new Intent(getContext(), .class);
                intent.putExtras(bundle);
                 startActivity(intent);*/
            }
        });

        return convertView;
    }



}
