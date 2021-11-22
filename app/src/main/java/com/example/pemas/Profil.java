package com.example.pemas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class Profil extends AppCompatActivity {

    Button button;
    TextView userEmail, profilName, profilPhone;
    String userID;

    FirebaseAuth mAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        Button button = (Button) findViewById(R.id.button);
        final TextView userEmail = (TextView) findViewById(R.id.profilEmail);
        final TextView profilName = (TextView) findViewById(R.id.profilName);
        final TextView profilPhone = (TextView) findViewById(R.id.profilePhone);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        //Mengambil data User di Firebase
        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                profilName.setText(documentSnapshot.getString("fullname"));
                userEmail.setText(documentSnapshot.getString("dftremail"));
                profilPhone.setText(documentSnapshot.getString("dftrpwd"));
            }
        });



        //mengatur toolbar untuk menjadi action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //menghapus tittle default
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //mengambil akses textview yang ada didalam toolbar
        TextView bar = (TextView) toolbar.findViewById(R.id.txtToolbar);
        //mengambil akses arrow
        ImageView arrow = (ImageView) toolbar.findViewById(R.id.arrownotif);
        //klik arrow kembali ke menu home
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Profil.this,MenuUtama.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });

        //inisialisasi dan menambahkan variabel
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.profil);
        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MenuUtama.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.notif:
                        startActivity(new Intent(getApplicationContext(), Notif.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.profil:
                        return true;

                }
                return false;

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(this,MenuUtama.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    //Log Out User
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent a=new Intent(this,login.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        a.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
        finish();
    }
}
