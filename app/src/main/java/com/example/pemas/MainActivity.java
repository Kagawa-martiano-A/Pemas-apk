package com.example.pemas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private ImageView tpemas;
    private TextView lpmtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tpemas = (ImageView) findViewById(R.id.tpemas);
        lpmtext = (TextView) findViewById(R.id.lpmtext);
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.transisi);
        tpemas.startAnimation(myanim);
        lpmtext.startAnimation(myanim);
        final Intent a = new Intent(this,login.class);
        Thread timer = new Thread() {
            @Override
            public void run() {
                try{
                    sleep(2000);
                }catch (InterruptedException e ){
                    e.printStackTrace();
                }
                finally {
                    startActivity(a);
                    finish();

                }

            }
        };
        timer.start();
    }

}
