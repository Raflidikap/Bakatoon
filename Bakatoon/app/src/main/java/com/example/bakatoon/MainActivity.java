package com.example.bakatoon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        Toolbar toolbar = findViewById(R.id.toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //profile nav head
        View headView = navigationView.getHeaderView(0);
        ImageView imgProfile = headView.findViewById(R.id.image_view);
        TextView profName = headView.findViewById(R.id.profName);
        TextView profEmail = headView.findViewById(R.id.profEmail);


        profName.setText(GlobalVar.currentUser.getName());
        profEmail.setText(GlobalVar.currentUser.getEmail());
        Glide.with(getApplicationContext()).load(GlobalVar.currentUser.getImageprofileUrl()).into(imgProfile);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //setting untuk layar utama
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toProfile = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(toProfile);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.nav_termsCondition:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TermsnConditionFragment()).commit();
                break;
            case R.id.nav_privacyPolicy:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PrivacyPolicyFragment()).commit();
                break;
            case R.id.nav_version:
                Toast.makeText(this, "Version 1.0", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logoutBtn:
                mAuth.signOut();
                Toast.makeText(MainActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                Intent backToLogin = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(backToLogin);
                finish();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        super.onBackPressed();
    }
}