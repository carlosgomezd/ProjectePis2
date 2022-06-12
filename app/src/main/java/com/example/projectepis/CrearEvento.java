package com.example.projectepis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CrearEvento extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private DatabaseReference EventosRef, RootRef;
    private FirebaseAuth auth;
    private EditText nombreEvento, ubicacion, fecha, horaComienzo, horaFinal, descripcion;
    private TextView tDuracion, tPublico;
    private Switch sDuracion, sPublico;
    private Button cancelar, guardar;
    private String UserId;
    private int dia, mes, año, hComienzo, mComienzo, hFinal, mFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_evento);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.crear_evento);

        toolbar=(Toolbar)findViewById(R.id.app_main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Crear Event");

        auth=FirebaseAuth.getInstance();
        UserId=auth.getCurrentUser().getUid();
        EventosRef = FirebaseDatabase.getInstance().getReference().child("Eventos").child(UserId);
        RootRef = FirebaseDatabase.getInstance().getReference();

        nombreEvento=(EditText) findViewById(R.id.edtNombreEvento);
        ubicacion=(EditText) findViewById(R.id.edtUbicacion);
        fecha=(EditText) findViewById(R.id.edtFecha);
        horaComienzo=(EditText) findViewById(R.id.edtComienzo);
        horaFinal=(EditText) findViewById(R.id.edtFinal);
        descripcion=(EditText) findViewById(R.id.edtDescripcion);
        tDuracion=(TextView) findViewById(R.id.edtDuracion);
        sDuracion=(Switch) findViewById(R.id.switchDuracion);
        tPublico=(TextView) findViewById(R.id.edtPublico);
        sPublico=(Switch) findViewById(R.id.switchPublico);
        cancelar=(Button) findViewById(R.id.btnCancelar);
        guardar=(Button) findViewById(R.id.btnGuardar);
        guardar.setOnClickListener(this);
        cancelar.setOnClickListener(this);

        fecha.setOnClickListener(this);
        horaComienzo.setOnClickListener(this);
        horaFinal.setOnClickListener(this);

    }



    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.chat:
                    Intent intent = new Intent(CrearEvento.this, InicioChatActivity.class);
                    startActivity(intent);
                    break;
                case R.id.calendario:
                    Intent intent2 = new Intent(CrearEvento.this, Calendario.class);
                    startActivity(intent2);
                    break;
                case R.id.evento_destacado:
                    Intent intent3 = new Intent(CrearEvento.this, EventoDestacado.class);
                    startActivity(intent3);
                    break;
                case R.id.perfil:
                    Intent intent4 = new Intent(CrearEvento.this, PerfilActivity.class);
                    startActivity(intent4);
                    break;
            }
            return true;
        }
    };

// as
    @Override
    public void onClick(View view) {
        boolean correcto = true;
        boolean todoElDia = false;
        boolean publico = false;
        if (view.getId()==guardar.getId()){
            if(sPublico.isChecked()){
                publico = true;
            }
            if(sDuracion.isChecked()) {
                todoElDia = true;
            }
            if(nombreEvento.getText().toString().isEmpty()){
                Toast.makeText(this, "Escribe el nombre de el evento", Toast.LENGTH_SHORT).show();
                correcto = false;
                this.finish();
                return;
            }if(ubicacion.getText().toString().isEmpty()){
                Toast.makeText(this, "Escribe la ubicacion", Toast.LENGTH_SHORT).show();
                correcto = false;
                this.finish();
                return;
            }if(fecha.getText().toString().isEmpty()){
                Toast.makeText(this, "Escribe la fecha", Toast.LENGTH_SHORT).show();
                correcto = false;
                this.finish();
                return;
            }if(horaComienzo.getText().toString().isEmpty() && !todoElDia){
                Toast.makeText(this, "Escribe la hora de comienzo", Toast.LENGTH_SHORT).show();
                correcto = false;
                this.finish();
                return;
            }if(horaFinal.getText().toString().isEmpty() && !todoElDia){
                Toast.makeText(this, "Escribe la hora final", Toast.LENGTH_SHORT).show();
                correcto = false;
                this.finish();
                return;
            }if(correcto==true){
                if(publico){
                    EventosRef = RootRef.child("Eventos").child("Publico");
                    EventosRef.child(nombreEvento.getText().toString()).child("ubicacion").setValue(ubicacion.getText().toString());
                    EventosRef.child(nombreEvento.getText().toString()).child("fecha").setValue(fecha.getText().toString());
                    EventosRef.child(nombreEvento.getText().toString()).child("Todo el dia").setValue(sDuracion.isChecked());
                    EventosRef.child(nombreEvento.getText().toString()).child("horaComienzo").setValue(horaComienzo.getText().toString());
                    EventosRef.child(nombreEvento.getText().toString()).child("horaFinal").setValue(horaFinal.getText().toString());
                    EventosRef.child(nombreEvento.getText().toString()).child("descripcion").setValue(descripcion.getText().toString());
                }
                EventosRef = RootRef.child("Eventos").child(UserId);
                EventosRef.child(nombreEvento.getText().toString()).child("ubicacion").setValue(ubicacion.getText().toString());
                EventosRef.child(nombreEvento.getText().toString()).child("fecha").setValue(fecha.getText().toString());
                EventosRef.child(nombreEvento.getText().toString()).child("Todo el dia").setValue(sDuracion.isChecked());
                EventosRef.child(nombreEvento.getText().toString()).child("horaComienzo").setValue(horaComienzo.getText().toString());
                EventosRef.child(nombreEvento.getText().toString()).child("horaFinal").setValue(horaFinal.getText().toString());
                EventosRef.child(nombreEvento.getText().toString()).child("descripcion").setValue(descripcion.getText().toString());

                this.finish();
                return;
            }

        }else if(view.getId()==cancelar.getId()){
            this.finish();
            return;
        }
        if(view.getId()==fecha.getId()){
            final Calendar calendar = Calendar.getInstance();
            dia=calendar.get(Calendar.DAY_OF_MONTH);
            mes=calendar.get(Calendar.MONTH);
            año=calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    if(i1<10){
                        fecha.setText(i2+"/0"+(i1+1)+"/"+i);
                    }else{
                        fecha.setText(i2+"/"+(i1+1)+"/"+i);
                    }

                }
            }
            ,dia,mes,año);
            datePickerDialog.show();
        }
        if (view.getId()==horaComienzo.getId()){
            final Calendar calendar = Calendar.getInstance();
            hComienzo=calendar.get(Calendar.HOUR_OF_DAY);
            mComienzo=calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    horaComienzo.setText(i+":"+i1);
                }
            },hComienzo,mComienzo,false);
            timePickerDialog.show();

        }if(view.getId()==horaFinal.getId()){
            final Calendar calendar = Calendar.getInstance();
            hFinal=calendar.get(Calendar.HOUR_OF_DAY);
            mFinal=calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    horaFinal.setText(i+":"+i1);
                }
            },hFinal,mFinal,false);
            timePickerDialog.show();
        }
    }
}