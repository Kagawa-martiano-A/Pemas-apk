package com.example.pemas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText dftremail, dftrpwd, konpwd, fullname;
    Button btndaftar;
    private FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText dftremail = (EditText) findViewById(R.id.dftremail);
        final EditText dftrpwd = (EditText) findViewById(R.id.dftrpwd);
        final EditText konpwd = (EditText) findViewById(R.id.konpwd);
        final EditText fullname = (EditText) findViewById(R.id.fullname);
        Button btndaftar = (Button) findViewById(R.id.btndaftar);

        //inisialisasi firebase Auth
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MenuUtama.class));
            finish();
        }

        btndaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = dftremail.getText().toString().trim();
                String password = konpwd.getText().toString().trim();
                final String fullName = fullname.getText().toString();
                final String phone = dftrpwd.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    dftremail.setError("Email is Required!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    konpwd.setError("Password is Required!");
                    return;
                }

                if (password.length() < 6) {
                    konpwd.setError("Harus Lebih dari 6 Karakter");
                    return;
                }


                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(register.this, "User Created", Toast.LENGTH_SHORT).show();
                            userID = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("fullname", fullName);
                            user.put("dftremail", email);
                            user.put("dftrpwd", phone);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user profile is created for"+userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), MenuUtama.class));
                        }else {
                            Toast.makeText(register.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
