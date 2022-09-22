package com.example.bakatoon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class TestResultActivity extends AppCompatActivity {

    private Button backBtn, retestBtn;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);

        backBtn = findViewById(R.id.backBtn2);
        retestBtn = findViewById(R.id.retestBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestResultActivity.this, MainActivity.class));
            }
        });

        retestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestResultActivity.this, TestActivity.class));
            }
        });
    }
}