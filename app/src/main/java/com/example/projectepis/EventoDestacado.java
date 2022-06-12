package com.example.projectepis;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

public class EventoDestacado extends AppCompatActivity {
    private Toolbar toolbar;

    private ListView lvEventosDestacados;
    public static EventosDestacadosAdapater eventoAdapter;
    public static ArrayList<Eventos> listaEventos = new ArrayList<Eventos>();

    private FirebaseAuth mAuth;
    private DatabaseReference UserRef;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_destacado);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.evento_destacado);

        toolbar=(Toolbar)findViewById(R.id.app_main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Event Destacat");

        this.lvEventosDestacados = findViewById(R.id.lvEventosDestacados);

        mAuth = FirebaseAuth.getInstance();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Eventos");
        user = mAuth.getCurrentUser();

        updateList();

    }


    public void updateList(){

        this.eventoAdapter = new EventosDestacadosAdapater(this, listaEventos);
        this.lvEventosDestacados.setAdapter(eventoAdapter);

        listaEventos.clear();


        Query query = UserRef.child("uZFAqxEt9cX9Vr9xyf0BPG7ChYy2");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Eventos ev = ds.getValue(Eventos.class);
                    ev.setNombre(ds.getKey());
                    listaEventos.add(ev);
                }

                eventoAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        query.addListenerForSingleValueEvent(valueEventListener);


       /* listaEventos.add(new Eventos("asad","asad","asad","asad",
                "asad","asad","asad"));
        listaEventos.add(new Eventos("asad","asad","asad","asad",
                "asad","asad","asad"));
        listaEventos.add(new Eventos("asad","asad","asad","asad",
                "asad","asad","asad"));
        listaEventos.addAll(objects);*/


    }



    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.chat:
                    Intent intent = new Intent(EventoDestacado.this, InicioChatActivity.class);
                    startActivity(intent);
                    break;
                case R.id.calendario:
                    Intent intent2 = new Intent(EventoDestacado.this, Calendario.class);
                    startActivity(intent2);
                    break;
                case R.id.crear_evento:
                    Intent intent3 = new Intent(EventoDestacado.this, CrearEvento.class);
                    startActivity(intent3);
                    break;
                case R.id.perfil:
                    Intent intent4 = new Intent(EventoDestacado.this, PerfilActivity.class);
                    startActivity(intent4);
                    break;
            }
            return true;
        }
    };
}
