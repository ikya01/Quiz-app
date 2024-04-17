package com.example.quizzapp_ikramelhaji;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signin extends AppCompatActivity {
    // Déclaration des éléments de l'interface
    EditText etLogin, etPassword;
    Button bLogin;
    TextView tvRegister;
    FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        // Récupération des IDs des éléments de l'interface
        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        bLogin = findViewById(R.id.bLogin);
        tvRegister = findViewById(R.id.tvRegister);
        mAuth=FirebaseAuth.getInstance();

        // Ajout d'un OnClickListener au bouton "signin"
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Récupération des valeurs des champs login et password
                String login = etLogin.getText().toString();
                String password = etPassword.getText().toString();

                // Vérification si les champs ne sont pas vides
                if (login.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Signin.this, "Please enter both login and password", Toast.LENGTH_SHORT).show();
                    return;
                }


                mAuth.signInWithEmailAndPassword(login, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(Signin.this, "Login Successful.",
                                            Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Signin.this, Quiz1.class));

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(Signin.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }


        });


        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("SigninActivity", "tvRegister clicked");
                // Redirection vers l'activité d'inscription
                Intent intent = new Intent(Signin.this, signup.class);
                startActivity(intent);
            }
        });

    }

}
