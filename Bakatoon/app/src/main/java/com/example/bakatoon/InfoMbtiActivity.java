package com.example.bakatoon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class InfoMbtiActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<Integer> arrayList = new ArrayList<>();
    private Button backBtn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_mbti);

        backBtn3 = findViewById(R.id.backBtn3);
        listView = findViewById(R.id.listViewMBTI);

        backBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        arrayList.add(R.drawable.entj_img);
        arrayList.add(R.drawable.intp_img);
        arrayList.add(R.drawable.entp_img);
        arrayList.add(R.drawable.intj_img);
        arrayList.add(R.drawable.infp_img);
        arrayList.add(R.drawable.enfp_img);
        arrayList.add(R.drawable.infj_img);
        arrayList.add(R.drawable.enfj_img);
        arrayList.add(R.drawable.esfj_img);
        arrayList.add(R.drawable.estj_img);
        arrayList.add(R.drawable.isfj_img);
        arrayList.add(R.drawable.esfp_img);
        arrayList.add(R.drawable.istp_img);
        arrayList.add(R.drawable.isfp_img);
        arrayList.add(R.drawable.estp_img);

        ListAdapter listAdapter = new ListAdapter(InfoMbtiActivity.this, arrayList);
        listView.setAdapter(listAdapter);

    }
}