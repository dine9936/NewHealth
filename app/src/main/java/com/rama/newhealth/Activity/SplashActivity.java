package com.rama.newhealth.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rama.newhealth.CommonClass;
import com.rama.newhealth.Models.CustomerInfo;
import com.rama.newhealth.R;

public class SplashActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                /* Create an Intent that will start the Menu-Activity. */
                if (user != null) {
                    reference.child("customer").child(user.getPhoneNumber()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                CustomerInfo customerInfo = snapshot.getValue(CustomerInfo.class);
                                CommonClass.imageuri = customerInfo.getUserImage();
                                CommonClass.phone = user.getPhoneNumber();
                                CommonClass.location = customerInfo.getUserLocation();
                                CommonClass.region = customerInfo.getUserRegion();
                                CommonClass.name = customerInfo.getUserName();
                                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {

                                Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                                startActivity(mainIntent);
                                finish();

                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                } else {

                    Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}