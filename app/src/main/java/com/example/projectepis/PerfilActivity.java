package com.example.projectepis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PerfilActivity extends AppCompatActivity {
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.perfil);

        toolbar=(Toolbar)findViewById(R.id.app_main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Perfil");


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.chat:
                    Intent intent = new Intent(PerfilActivity.this, InicioChatActivity.class);
                    startActivity(intent);
                    break;
                case R.id.calendario:
                    Intent intent2 = new Intent(PerfilActivity.this, Calendario.class);
                    startActivity(intent2);
                    break;
                case R.id.crear_evento:
                    Intent intent3 = new Intent(PerfilActivity.this, CrearEvento.class);
                    startActivity(intent3);
                    break;
                case R.id.evento_destacado:
                    Intent intent4 = new Intent(PerfilActivity.this, EventoDestacado.class);
                    startActivity(intent4);
                    break;
            }
            return true;
        }
    };
}