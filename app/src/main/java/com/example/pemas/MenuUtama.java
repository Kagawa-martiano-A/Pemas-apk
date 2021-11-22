package com.example.pemas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MenuUtama extends AppCompatActivity {

    private Toolbar toolbar;
    private long backPressedTime;
    CardView cardView;
    ImageView imageView;
    RelativeLayout relativeLayout;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        //mengatur toolbar untuk menjadi action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //menghapus tittle default
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //mengambil akses textview yang ada didalam toolbar
        TextView bar = (TextView) toolbar.findViewById(R.id.txtToolbar);
        CardView cardviewimg = (CardView) findViewById(R.id.carviewimg);
        ImageView imgcarview = (ImageView) findViewById(R.id.imgcarview);
        RelativeLayout cardviewtext = (RelativeLayout) findViewById(R.id.cardviewtext);


        //inisialisasi dan menambahkan variabel
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home);
        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        return true;
                    case R.id.notif:
                        startActivity(new Intent(getApplicationContext(), Notif.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.profil:
                        startActivity(new Intent(getApplicationContext(), Profil.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
