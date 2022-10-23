package com.example.bakatoon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bakatoon.models.Personalities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MbtiPersonalities extends AppCompatActivity {
    SharedPreferences prefs;
    String id;
    TextView mbtiId, subMbtiId;
    Button backToInfoBtn;

    String mbti, sub_mbti;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mbti_personalities);

        backToInfoBtn = findViewById(R.id.backToInfoBtn);
        mbtiId = findViewById(R.id.mbtiId);
        subMbtiId = findViewById(R.id.subMbtiId);
        prefs = getApplicationContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        id = prefs.getString("personalityId", "");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Personalities");

        mDatabase.orderByChild("id").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child:snapshot.getChildren()){
                    mbtiId.setText(String.valueOf(child.child("mbti").getValue()));
                    subMbtiId.setText(String.valueOf(child.child("sub_mbti").getValue()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        backToInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}