package com.example.bakatoon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {
    private TextView logoutBtn, profileName, profileEmail, mbtiProfile;
    private Button backBtn;
    private ImageView profilePic;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        mbtiProfile = findViewById(R.id.mbtiProfile);
        logoutBtn = findViewById(R.id.logoutBtn);
        profileEmail = findViewById(R.id.profileEmail);
        profileName = findViewById(R.id.profileName);
        backBtn = findViewById(R.id.backBtn);
        profilePic = findViewById(R.id.profilePic);

        profileEmail.setText(GlobalVar.currentUser.getEmail());
        profileName.setText(GlobalVar.currentUser.getName());
        Glide.with(getApplicationContext()).load(GlobalVar.currentUser.getImageprofileUrl()).into(profilePic);

        mbtiProfile.setText(GlobalVar.currentUser.getMbti());
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                finish();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}