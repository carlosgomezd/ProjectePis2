package com.example.projectepis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PerfilActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private FirebaseAuth mAuth;
    private DatabaseReference UserRef;
    private FirebaseUser user;

    private Perfil perfil=null;

    private TextView tvNombre;
    private TextView tvDireccion;
    private TextView tvCorreo;
    private TextView tvTelefono;
    private EditText tvDatosP;

    private ImageView ivPerfil;
    private Button btEditarP;




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

       tvNombre=findViewById(R.id.tvNombre);
        tvDireccion=findViewById(R.id.tvDireccion);
        tvCorreo=findViewById(R.id.tvCorreo);
        tvTelefono=findViewById(R.id.tvTelefono);
        tvDatosP=findViewById(R.id.tvDatosP);
        ivPerfil=findViewById(R.id.ivPerfil);
        btEditarP=findViewById(R.id.btEditarP);

        mAuth = FirebaseAuth.getInstance();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
         user = mAuth .getCurrentUser();

        refreshPerfil();

        btEditarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPerfil();
            }
        });
    }

    public void refreshPerfil(){

       // tvDatosP.setEnabled(false);





        if(user != null){
            Log.i("info user", user.getDisplayName());

            UserRef.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                     perfil = dataSnapshot.getValue(Perfil.class);
                    Log.i("Perfil",perfil.toString());

                    if(!perfil.getNombre().isEmpty())
                    tvNombre.setText(perfil.getNombre()+" "+perfil.getApellido());

                    //tvDireccion.setText(perfil.get());
                    tvCorreo.setText(perfil.getEmail());
                    //tvTelefono.setText(perfil.getjjkjk));
                    //tvDatosP.setText(perfil.getNombre());
                   // ivPerfil.setText(perfil.getNombre());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


        }

    public void editPerfil(){
        tvDatosP.setEnabled(true);

        btEditarP.setText("Finalizar");
        getSupportActionBar().setTitle("Editar Perfil");




        if(perfil!=null){

            perfil.setDatosP(tvDatosP.getText().toString());

            UserRef.child(user.getUid()).setValue(perfil).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task2) {
                    if(task2.isSuccessful()){

                        refreshPerfil();
                    }else{
                        Toast.makeText(PerfilActivity.this, "No se pudo actualizar los datos correctamente", Toast.LENGTH_SHORT ).show();
                    }
                }
            });
        }



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