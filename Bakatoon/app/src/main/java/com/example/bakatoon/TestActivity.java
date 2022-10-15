package com.example.bakatoon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import android.widget.TextView;


import com.example.bakatoon.models.PersonalityTest;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import java.util.Random;

public class TestActivity extends AppCompatActivity {

    private Button backBtn, btnOption1, btnOption2;
    private TextView questionTv, questionNumberTv;
    private ArrayList<PersonalityTest> quizModelArrayList;
    Random random;
    int currentScore, questionAttempted = 1, currentPos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        backBtn = findViewById(R.id.backBtn);
        btnOption1 = findViewById(R.id.idBtnOption1);
        btnOption2 = findViewById(R.id.idBtnOption2);
        questionTv = findViewById(R.id.idTVQuestion);
        questionNumberTv = findViewById(R.id.idTVQuestionAttempted);
        quizModelArrayList = new ArrayList<>();
        random = new Random();
        getQuestion(quizModelArrayList);
        currentPos = random.nextInt(quizModelArrayList.size());
        setDataToView(currentPos);

        btnOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quizModelArrayList.get(currentPos).getAnswer().trim().toLowerCase().equals(btnOption1.getText().toString().trim().toLowerCase())){
                    currentScore++;
                }
                questionAttempted++;
                currentPos = random.nextInt(quizModelArrayList.size());
                setDataToView(currentPos);
            }
        });

        btnOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quizModelArrayList.get(currentPos).getAnswer().trim().toLowerCase().equals(btnOption2.getText().toString().trim().toLowerCase())){
                    currentScore++;
                }
                questionAttempted++;
                currentPos = random.nextInt(quizModelArrayList.size());
                setDataToView(currentPos);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void showBottomSheet(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(TestActivity.this);
        View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.testresult_bottom_sheet, (LinearLayout)findViewById(R.id.idLLResult));
        TextView resultTV = bottomSheetView.findViewById(R.id.idTVResult);
        Button finishBtn = bottomSheetView.findViewById(R.id.idBtnFinish);
        resultTV.setText("Your Score is \n"+currentScore+"/4");
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                
                finish();
            }
        });

        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }
    private void setDataToView(int currentPos){
        questionNumberTv.setText("Questions Attempted : " +questionAttempted+"/20");
        if (questionAttempted == 10){
            showBottomSheet();
        }else {
            questionTv.setText(quizModelArrayList.get(currentPos).getQuestion());
            btnOption1.setText(quizModelArrayList.get(currentPos).getOption1());
            btnOption2.setText(quizModelArrayList.get(currentPos).getOption2());
        }

    }
    public void getQuestion(ArrayList<PersonalityTest> quizModelArrayList){
        quizModelArrayList.add(new PersonalityTest("extroversionVsIntroversionTest", "expend energy, enjoy groups", "conserve energy, enjoy one-on-one", "expend energy, enjoy groups"));
        quizModelArrayList.add(new PersonalityTest("extroversionVsIntroversionTest", "more outgoing, think out loud", "more reserved, think to yourself", "more outgoing, think out loud"));
        quizModelArrayList.add(new PersonalityTest("extroversionVsIntroversionTest", "seek many tasks, public activities, interaction with others", "seek private, solitary activities with quiet to concentrate", "seek many tasks, public activities, interaction with others"));
        quizModelArrayList.add(new PersonalityTest("extroversionVsIntroversionTest", "external, communicative, express yourself", "internal, reticent, keep to yourself", "external, communicative, express yourself"));
        quizModelArrayList.add(new PersonalityTest("extroversionVsIntroversionTest", "active, initiate", "reflective, deliberate", "active, initiate"));

        quizModelArrayList.add(new PersonalityTest("sensingVsIntuitionTest", "practical, realistic, experiential", "imaginative, innovative, theoretical", "practical, realistic, experiential"));
        quizModelArrayList.add(new PersonalityTest("sensingVsIntuitionTest", "interpret literally", "look for meaning and possibilities", "interpret literally"));
        quizModelArrayList.add(new PersonalityTest("sensingVsIntuitionTest", "standard, usual, conventional", "different, novel, unique", "standard, usual, conventional"));
        quizModelArrayList.add(new PersonalityTest("sensingVsIntuitionTest", "focus on here-and-now", "look to the future, global perspective", "focus on here-and-now"));
        quizModelArrayList.add(new PersonalityTest("sensingVsIntuitionTest", "facts, things", "ideas, dreams, philosophical", "facts, things"));

        quizModelArrayList.add(new PersonalityTest("thinkingVsFeelingTest", "firm, tend to criticize, hold the line", "gentle, tend to appreciate, conciliate", "firm, tend to criticize, hold the line"));
        quizModelArrayList.add(new PersonalityTest("thinkingVsFeelingTest", "logical, thinking, questioning", "empathetic, feeling, accommodating", "logical, thinking, questioning"));
        quizModelArrayList.add(new PersonalityTest("thinkingVsFeelingTest", "candid, straight forward, frank", "tactful, kind, encouraging", "candid, straight forward, frank"));
        quizModelArrayList.add(new PersonalityTest("thinkingVsFeelingTest", "tough-minded, just", "tender-hearted, merciful", "tough-minded, just"));
        quizModelArrayList.add(new PersonalityTest("thinkingVsFeelingTest", "matter of fact, issue-oriented", "sensitive, people-oriented, compassionate", "matter of fact, issue-oriented"));

        quizModelArrayList.add(new PersonalityTest("judgingVsPerceivingTest", "control, govern", "latitude, freedom", "control, govern"));
        quizModelArrayList.add(new PersonalityTest("judgingVsPerceivingTest", "organized, orderly", "flexible, adaptable", "organized, orderly"));
        quizModelArrayList.add(new PersonalityTest("judgingVsPerceivingTest", "plan, schedule", "unplanned, spontaneous", "plan, schedule"));
        quizModelArrayList.add(new PersonalityTest("judgingVsPerceivingTest", "regulated, structured", "easygoing, live and let live", "regulated, structured"));
        quizModelArrayList.add(new PersonalityTest("judgingVsPerceivingTest", "control, govern", "latitude, freedom", "control, govern"));
    }

}