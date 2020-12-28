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
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    TextView sign_in;
    EditText editnom, editprenom, editemail, editmdp;
    Button btninscrit;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sign_in = findViewById(R.id.sign_in);
        editemail = findViewById(R.id.editemail);
        editmdp = findViewById(R.id.editmdp);
        editnom = findViewById(R.id.editnom);
        editprenom = findViewById(R.id.editprenom);
        btninscrit=findViewById(R.id.btninscrit);
        mAuth = FirebaseAuth.getInstance();
        btninscrit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editemail.getText().toString().trim();
                String password = editmdp.getText().toString().trim();
                String nom = editnom.getText().toString().trim();
                String prenom = editprenom.getText().toString().trim();

                // contrôle sur le champ nom
                if (TextUtils.isEmpty(nom)) {
                    editnom.setError("Nom is Required!");
                    editnom.requestFocus();
                    return;
                }
                //contrôle sur le champ prenom
                if (TextUtils.isEmpty(prenom)) {
                    editprenom.setError("Prenom is Required!");
                    editprenom.requestFocus();
                    return;
                }
                //contrôle sur le champ email
                if (TextUtils.isEmpty(email)) {
                    editemail.setError("Email is Required!");
                    editemail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(editemail.getText().toString()).matches()) {
                    editemail.setError("Please enter a Valid E-Mail Address!");
                    editemail.requestFocus();
                    return;
                }
                //contrôle sur le champ mot de passe
                if (TextUtils.isEmpty(password)) {
                    editmdp.setError("Password is Required!");
                    editmdp.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    editmdp.setError("password Must be >= 6 characters!");
                    editmdp.requestFocus();
                    return;
                }
                // ajouter un utilisateur au firebase
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(nom, prenom, email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), home.class));
                                    } else {
                                        Toast.makeText(MainActivity.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }}
                });
            }
        });
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), login.class));
            }
        });
    }
}