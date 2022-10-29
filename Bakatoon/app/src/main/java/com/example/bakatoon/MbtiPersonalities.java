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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
    ImageView mbtiCircleImageView;
    TextView mbtiId, subMbtiId, desc, strengthId, weaknessId, atworkId, topcareerId;
    Button backToInfoBtn;


    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mbti_personalities);

        backToInfoBtn = findViewById(R.id.backToInfoBtn);
        mbtiCircleImageView = findViewById(R.id.mbtiCircleImageView);
        topcareerId = findViewById(R.id.topcareerId);
        atworkId = findViewById(R.id.atworkId);
        strengthId = findViewById(R.id.strengthId);
        weaknessId = findViewById(R.id.weaknessId);
        mbtiId = findViewById(R.id.mbtiId);
        subMbtiId = findViewById(R.id.subMbtiId);
        desc = findViewById(R.id.desc);


        prefs = getApplicationContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        id = prefs.getString("personalityId", "");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Personalities");

        mDatabase.orderByChild("id").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    mbtiId.setText(String.valueOf(child.child("mbti").getValue()));
                    subMbtiId.setText(String.valueOf(child.child("sub_mbti").getValue()));
                    if (child.hasChild("mbticircleimg_url")) {
                        Glide.with(getApplicationContext()).load(child.child("mbticircleimg_url").getValue().toString()).into(mbtiCircleImageView);
                    }
                    if (child.hasChild("desc")) {
                        desc.setText(child.child("desc").getValue().toString().replace("\\n", "\n"));
                    }
                    if (child.hasChild("strengths")) {
                        strengthId.setText(child.child("strengths").getValue().toString().replace("\\n", "\n"));
                    }
                    if (child.hasChild("weaknesses")) {
                        weaknessId.setText(child.child("weaknesses").getValue().toString().replace("\\n", "\n"));
                    }
                    if (child.hasChild("atwork")) {
                        atworkId.setText(child.child("atwork").getValue().toString().replace("\\n", "\n"));
                    }
                    if (child.hasChild("topcareers")) {
                        topcareerId.setText(child.child("topcareers").getValue().toString().replace("\\n", "\n"));
                    }

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