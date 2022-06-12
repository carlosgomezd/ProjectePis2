package com.example.projectepis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.CharSequenceTransformation;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.developer.kalert.KAlertDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Calendario extends AppCompatActivity implements CalendarView.OnDateChangeListener {
    private CalendarView calendarView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.calendario);

        toolbar=(Toolbar)findViewById(R.id.app_main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Calendari");

        calendarView=(CalendarView) findViewById(R.id.calendarioView);
        calendarView.setOnDateChangeListener(this);

    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        CharSequence[] items = new CharSequence[3];
        items[0]="Agregar evento";
        items[1]="Ver Eventos";
        items[2]="Cancelar";
        final int dia,mes,año;
        año=i;
        mes=i1+1;
        dia=i2;


        builder.setTitle("Seleccione una tarea")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==0){
                            //agregar eventos

                            Intent intent = new Intent(getApplication(),CrearEvento.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("dia",dia);
                            bundle.putInt("mes",mes);
                            bundle.putInt("año",año);
                            intent.putExtras(bundle);
                            startActivity(intent);

                        }else if (i==1){
                            //ver eventos
                          //  Intent intent = new Intent(getApplication(),VerEventosActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("dia",dia);
                            bundle.putInt("mes",mes);
                            bundle.putInt("año",año);
                        //    intent.putExtras(bundle);
                          //  startActivity(intent);

                            // 13/06/2022  dd/mm/aaaa

                            String formatMes= mes<10 ? "0"+mes : mes+"";

                            String fecha=dia+"/"+formatMes+"/"+año;
                            mostrarListaEventos(fecha);






                        }else{
                            return;
                        }
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();



    }

    public void mostrarListaEventos(String fecha){

        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("Eventos");
        FirebaseUser user= mAuth.getCurrentUser();



        Query query = UserRef.child(user.getUid());

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<String> listaEventos = new ArrayList<>();

                String eventos = "";

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Eventos ev = ds.getValue(Eventos.class);
                    //ev.setNombre();
                    if(ev.getFecha().equals(fecha))
                    eventos+=ds.getKey()+"\n";
                }

                //mostrar lista
                new KAlertDialog(calendarView.getContext())
                        .setTitleText("Eventos!")
                        .setContentText(eventos.isEmpty() ? "No hay eventos" : eventos)
                        .show();





            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        query.addListenerForSingleValueEvent(valueEventListener);

    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.chat:
                    Intent intent = new Intent(Calendario.this, InicioChatActivity.class);
                    startActivity(intent);
                    break;
                case R.id.crear_evento:
                    Intent intent2 = new Intent(Calendario.this, CrearEvento.class);
                    startActivity(intent2);
                    break;
                case R.id.evento_destacado:
                    Intent intent3 = new Intent(Calendario.this, EventoDestacado.class);
                    startActivity(intent3);
                    break;
                case R.id.perfil:
                    Intent intent4 = new Intent(Calendario.this, PerfilActivity.class);
                    startActivity(intent4);
                    break;
            }
            return true;
        }
    };


}