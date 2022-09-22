package com.example.bakatoon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity {
    private Button backBtn;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private  Button buttonSubmit;
    private TextView textView, textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        backBtn = findViewById(R.id.backBtn);
        radioGroup = findViewById(R.id.btnGroup1);
        textView = findViewById(R.id.tv_test1);
        textView1 = findViewById(R.id.tv_test2);
        buttonSubmit = findViewById(R.id.submitBtn);

        //set tombol back ke main activities
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                startActivity(new Intent(TestActivity.this, TestResultActivity.class));
            }
        });
    }

    public void checkButton(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
    }
}