package com.myproject_mtb.personal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Script;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference reference = database.getReference().child("Usuario");

    FirebaseAuth auth = FirebaseAuth.getInstance();

    Button btnVolverHome;

    //Enlazar los input de registro con la BD de Firebase
    EditText inputId;
    EditText inputUserName;
    EditText inputLastName;
    EditText inputPhone;
    EditText inputEmail;
    EditText inputPassword;
    Button btnSendUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnVolverHome = findViewById(R.id.btn_volver_home);
        inputId = findViewById(R.id.input_user_id);
        inputUserName = findViewById(R.id.input_user_name);
        inputLastName = findViewById(R.id.input_user_last_name);
        inputPhone = findViewById(R.id.input_user_phone);
        inputEmail = findViewById(R.id.input_user_mail);
        inputPassword = findViewById(R.id.input_user_password);
        btnSendUser = findViewById(R.id.btn_register);

        //Para llamar la funcion del boton de registro
        btnSendUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });


        btnVolverHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volverHomeDesdeRegistro();
            }
        });
    }

    public void volverHomeDesdeRegistro(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void createUser(){

        String userId = inputId.getText().toString();
        DatabaseReference newUserRef = reference.child(String.valueOf(userId));

        String name = inputUserName.getText().toString();
        newUserRef.child("Name").setValue(name);

        String lastName = inputLastName.getText().toString();
        newUserRef.child("LastName").setValue(lastName);

        String phone = inputPhone.getText().toString();
        newUserRef.child("Phone").setValue(phone);

        String email = inputEmail.getText().toString();
        newUserRef.child("Email").setValue(email);

        String password = inputPassword.getText().toString();
        newUserRef.child("Password").setValue(password);

        registrarUsuario(email, password);

        //Para que muestre un mensaje de registro exitoso
        Toast.makeText(getApplicationContext(), "Usuario registrado exitosamente", Toast.LENGTH_LONG).show();

    }
    public void registrarUsuario(String email, String password){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(Register.this, "Registro en Auth, correcto", Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.

                        }
                    }
                });

    }
}