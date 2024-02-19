package com.example.expofirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // Accedemos a la instancia de Firestore
    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Accedemos a la instancia de Auth
    final FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Guardar información
        Button btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etUsuario = findViewById(R.id.etUsuario);
                EditText etContraseña = findViewById(R.id.etContraseña);
                String usuario = etUsuario.getText().toString();
                String contraseña = etContraseña.getText().toString();

                Map<String, String> user = new HashMap<>();
                user.put("usuario", usuario);
                user.put("contraseña", contraseña);

                // Esta es la manera más sencilla de añadir un documento a una colección pero no la mejor
                // Añadimos un nuevo documento "Usuario" a la colección "usuarios"
                // Al usar el método document, Firestore nos permite especificar un ID para el documento y no usamos
                // el método add, usamos el método set
                //db.collection("usuarios").document("Usuario").set(user);

                // Si usamos el método add, Firestore generará un ID único para el documento
                // También gestionamos el éxito y el fallo de la operación con los métodos onSuccess y onFailure
                db.collection("Usuarios")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(MainActivity.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Error inesperado", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

        // Leer información
        Button btnLeer = findViewById(R.id.btnLeer);
        btnLeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Con el método get, obtenemos todos los documentos de la colección "Usuarios"
                // En el método onSuccess, recorremos todos los documentos y mostramos el usuario y la contraseña
                db.collection("Usuarios")
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<com.google.firebase.firestore.QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    String usuario = documentSnapshot.getString("usuario");
                                    String contraseña = documentSnapshot.getString("contraseña");
                                    Toast.makeText(MainActivity.this, "Usuario: " + usuario + " Contraseña: " + contraseña, Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Error inesperado", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
}