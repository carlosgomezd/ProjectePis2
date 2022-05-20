package com.example.projectepis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class EventoDestacado extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ArrayList<Evento> arrEvent;
    private EventAdapter eventAdapter;
    private FirebaseFirestore firestore;
    private Context parentContext;
    String TAG;

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

        recyclerView = (RecyclerView) findViewById(R.id.recyclerEvent);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firestore = FirebaseFirestore.getInstance();
        arrEvent = new ArrayList<Evento>();
        eventAdapter = new EventAdapter(arrEvent, this);
        recyclerView.setAdapter(eventAdapter);
        getCollection();
        setLiveDataObservers();
    }

    private void getCollection() {
        firestore.collection("Series").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.get("title") + " => " + document.get("photo"));
                                // Firebase mirar
                                //arrEvent.(new Evento(document.getData().get("title").toString(),document.getData().get("photo").toString()));

                            }
                            Log.d(TAG, "==> update collection");
                            //updateCollection();
                            eventAdapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void setLiveDataObservers() {
        final Observer observer = new Observer() {
            @Override
            public void update(Observable observable, Object o) {

            }

            public void onChanged(ArrayList<Evento> ac) {
                EventAdapter newAdapter = new EventAdapter(ac, parentContext);
                recyclerView.swapAdapter(newAdapter, false);
                newAdapter.notifyDataSetChanged();
            }
        };
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
