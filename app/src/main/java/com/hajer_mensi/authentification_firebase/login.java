package com.hajer_mensi.authentification_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    TextView sign_up;
    EditText eemail, emdp;
    Button btnlogin;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sign_up = findViewById(R.id.sign_up);
        eemail = findViewById(R.id.eemail);
        emdp = findViewById(R.id.emdp);
        mAuth = FirebaseAuth.getInstance();
        btnlogin = findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = eemail.getText().toString().trim();
                String password = emdp.getText().toString().trim();
                // contrôle sur le champ nom
                if (TextUtils.isEmpty(email)) {
                    eemail.setError("email is Required!");
                    eemail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(eemail.getText().toString()).matches()) {
                    eemail.setError("Please enter a Valid E-Mail Address!");
                    eemail.requestFocus();
                    return;
                }
                //contrôle sur le champ mot de passe
                if (TextUtils.isEmpty(password)) {
                    emdp.setError("Password is Required!");
                    emdp.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    emdp.setError("password Must be >= 6 characters!");
                    emdp.requestFocus();
                    return;
                }
                // authentification de l'utilisateur
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(login.this, "Logged in ssuccessful!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), home.class));
                        }else {
                            Toast.makeText(login.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }    });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

}