package com.example.projectepis;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PerfilActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private FirebaseAuth mAuth;
    private DatabaseReference UserRef;
    private FirebaseUser user;

    private Perfil perfil = null;

    private EditText tvNombre;
    private EditText tvDireccion;
    private EditText tvCorreo;
    private EditText tvTelefono;
    private EditText tvDatosP;

    private ImageView ivPerfil;
    private Button btEditarP;

    private Context context=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);



        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.perfil);

        toolbar = (Toolbar) findViewById(R.id.app_main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Perfil");

        tvNombre = findViewById(R.id.tvNombre);
        tvDireccion = findViewById(R.id.tvDireccion);
        tvCorreo = findViewById(R.id.tvCorreo);
        tvTelefono = findViewById(R.id.tvTelefono);
        tvDatosP = findViewById(R.id.tvDatosP);
        ivPerfil = findViewById(R.id.ivPerfil);
        btEditarP = findViewById(R.id.btEditarP);

        mAuth = FirebaseAuth.getInstance();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        user = mAuth.getCurrentUser();

        context=PerfilActivity.this;

        refreshPerfil();


        btEditarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (btEditarP.isActivated()) {
                    editPerfil();
                } else {
                    updatePerfil();
                }


            }
        });

        ivPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(PerfilActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent mData) {
        super.onActivityResult(requestCode, resultCode, mData);
        if (resultCode == Activity.RESULT_OK) {
            // There are no request codes
            Intent data = mData;

            Uri imaUri = data.getData();

            uploadImageFirebase(imaUri);
        }
    }

    public void refreshPerfil() {

        getSupportActionBar().setTitle("Perfil");
        btEditarP.setText("Editar Perfil");

        btEditarP.setActivated(true);

        tvNombre.setEnabled(false);
        tvDireccion.setEnabled(false);
        tvCorreo.setEnabled(false);
        tvTelefono.setEnabled(false);
        tvDatosP.setEnabled(false);
        ivPerfil.setEnabled(false);

        if (user != null) {
            //Log.i("info user", user.getDisplayName());

            loadImageFirebase();

            UserRef.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    perfil = dataSnapshot.getValue(Perfil.class);
                    Log.i("Perfil", perfil.toString());

                    if (!perfil.getNombre().isEmpty())
                        tvNombre.setText(perfil.getNombre());
                    tvDireccion.setText(perfil.getDireccion());
                    tvCorreo.setText(perfil.getEmail());
                    tvTelefono.setText(perfil.getTelefono());
                    tvDatosP.setText(perfil.getDatosP());
                    //ivPerfil.setText(perfil.getNombre());




                    /*Glide.with(getApplicationContext())
                            .load(perfil.getImagen())
                            .placeholder(R.drawable.user)
                            .into(ivPerfil);*/
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


    }


    public void editPerfil() {

        getSupportActionBar().setTitle("Editar Perfil");
        btEditarP.setText("Finalizar");

        btEditarP.setActivated(false);

        tvNombre.setEnabled(true);
        tvDireccion.setEnabled(true);
        tvCorreo.setEnabled(true);
        tvTelefono.setEnabled(true);
        tvDatosP.setEnabled(true);
        ivPerfil.setEnabled(true);

    }

    public void updatePerfil() {
        if (perfil != null) {

            perfil.setNombre(tvNombre.getText().toString());
            perfil.setDireccion(tvDireccion.getText().toString());
            perfil.setEmail(tvCorreo.getText().toString());
            perfil.setTelefono(tvTelefono.getText().toString());
            perfil.setDatosP(tvDatosP.getText().toString());


            UserRef.child(user.getUid()).setValue(perfil).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task2) {
                    if (task2.isSuccessful()) {

                        refreshPerfil();
                    } else {
                        Toast.makeText(PerfilActivity.this, "No se pudo actualizar los datos correctamente", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void uploadImageFirebase(Uri filePath){

        ProgressDialog pd = new ProgressDialog(context);
        pd.setIndeterminate(true);
        pd.setTitle(getString(R.string.proceso_de_carga));
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();



        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imagesRef = storageRef.child("images/"+user.getUid()+".jpg");

        UploadTask uploadTask = imagesRef.putFile(filePath);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();

                        Glide.with(context )
                                .load(filePath)
                                .placeholder(R.drawable.user)
                                .into(ivPerfil);

                        Toast.makeText(context, getString(R.string.subida_exitosa), Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        //Log.e("fire",e.toString());
                        Glide.with(context )
                                .load(R.drawable.user)
                                .placeholder(R.drawable.user)
                                .into(ivPerfil);
                        Toast.makeText(context, getString(R.string.subida_fallida) + e, Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                });
    }


    public void loadImageFirebase(){

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imagesRef = storageRef.child("images/"+user.getUid()+".jpg");

        imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context )
                        .load(uri.toString())
                        .placeholder(R.drawable.user)
                        .into(ivPerfil);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors

                Glide.with(context )
                        .load(R.drawable.user)
                        .placeholder(R.drawable.user)
                        .into(ivPerfil);
            }
        });

    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
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

    private void selectImage(Context context) {
        final CharSequence[] options = { getString(R.string.buscar_galeria),
                getString(R.string.cancelar)};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.elige_foto);

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals(getString(R.string.buscar_galeria))) {
                    Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 10);

                } else if (options[item].equals(getString(R.string.cancelar))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
}