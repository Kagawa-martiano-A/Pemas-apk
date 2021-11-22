package com.example.pemas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

    EditText Logemail, Logpwd;
    Button btnmasuk;
    TextView daftar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Logemail = (EditText) findViewById(R.id.logemail);
        Logpwd = (EditText) findViewById(R.id.logpwd);
        btnmasuk = (Button) findViewById(R.id.btnmasuk);
        daftar = (TextView) findViewById(R.id.txdftar);
        //Firebase
        mAuth = FirebaseAuth.getInstance();


        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dftr = new Intent(login.this, register.class);
                startActivity(dftr);
            }
        });

        btnmasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Logemail.getText().toString().trim();
                String password = Logpwd.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Logemail.setError("Email is Required!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Logpwd.setError("Password is Required!");
                    return;
                }

                if (password.length() < 6) {
                    Logemail.setError("Harus Lebih dari 6 Karakter");
                    return;
                }

                //autentikasi user
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(login.this, "Login sukses", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MenuUtama.class));
                        }else {
                            Toast.makeText(login.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
