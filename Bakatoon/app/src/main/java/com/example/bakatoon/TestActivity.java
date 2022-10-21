package com.example.bakatoon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import android.widget.TextView;


import com.example.bakatoon.models.PersonalityTest;
import com.example.bakatoon.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;
import java.util.ArrayList;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class TestActivity extends AppCompatActivity {

    private Button backBtn, btnOption1, btnOption2;
    private TextView questionTv, questionNumberTv;
    private ArrayList<PersonalityTest> quizModelArrayList;
    private Integer iore, sori, torf, jorp;
    private ArrayList<Integer> tempMbti = new ArrayList<Integer>();
    private ArrayList<Character> resultMbti = new ArrayList<>();
    private String result;
    DatabaseReference reference;
    FirebaseAuth mAuth;

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
        random.setSeed(1);
        getQuestion(quizModelArrayList);
        currentPos = 0;
//        currentPos = random.nextInt(quizModelArrayList.size());
        setDataToView(currentPos);
        mAuth = FirebaseAuth.getInstance();

        reference = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid());

        btnOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(quizModelArrayList.get(currentPos).getAnswer().trim().toLowerCase().equals(btnOption1.getText().toString().trim().toLowerCase())){
//                    currentScore++;
//                }
                tempMbti.add(quizModelArrayList.get(currentPos).getMbti1());
                questionAttempted++;
                currentPos++;
//                currentPos = random.nextInt(quizModelArrayList.size());
                setDataToView(currentPos);
            }
        });

        btnOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(quizModelArrayList.get(currentPos).getAnswer().trim().toLowerCase().equals(btnOption2.getText().toString().trim().toLowerCase())){
//                    currentScore++;
//                }
                tempMbti.add(quizModelArrayList.get(currentPos).getMbti2());
                questionAttempted++;
                currentPos++;
//                currentPos = random.nextInt(quizModelArrayList.size());
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

    //Algoritma Test MBTI

    private void countMbti(){
        //sorting
        Collections.sort(tempMbti);
        ArrayList<Integer> ei = new ArrayList<Integer>(tempMbti.subList(0,4));
        ArrayList<Integer> si = new ArrayList<Integer>(tempMbti.subList(5,9));
        ArrayList<Integer> tf = new ArrayList<Integer>(tempMbti.subList(10,14));
        ArrayList<Integer> jp = new ArrayList<Integer>(tempMbti.subList(15,19));

        iore = mostFreq(ei);
        sori = mostFreq(si);
        torf = mostFreq(tf);
        jorp = mostFreq(jp);

    }

    private String getResult(Integer iore, Integer sori, Integer torf, Integer jorp){
        HashMap<Integer, Character> hashMap = new HashMap<>();

        hashMap.put(1, 'E');
        hashMap.put(2, 'I');
        hashMap.put(3, 'S');
        hashMap.put(4, 'N');
        hashMap.put(5, 'T');
        hashMap.put(6, 'F');
        hashMap.put(7, 'J');
        hashMap.put(8, 'P');

        resultMbti.add(hashMap.get(iore));
        resultMbti.add(hashMap.get(sori));
        resultMbti.add(hashMap.get(torf));
        resultMbti.add(hashMap.get(jorp));

        StringBuilder builder = new StringBuilder(resultMbti.size());

        for (Character ch: resultMbti){
            builder.append(ch);
        }

        return builder.toString();
    }

    private int mostFreq(ArrayList<Integer> vals){
        int most = vals.get(0);
        int count = 0;
        for(int i=0; i<vals.size(); i++){
            int cnt = 0;
            for(int j=i+1; j<vals.size(); j++){
                if(vals.get(i) == vals.get(i)){
                    cnt++;
                    if (count<cnt){
                        most = vals.get(i);
                        count=cnt;
                    }
                }
            }
        }
        return most;
    }

    private void showBottomSheet(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(TestActivity.this);
        View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.testresult_bottom_sheet, (LinearLayout)findViewById(R.id.idLLResult));
        TextView resultTV = bottomSheetView.findViewById(R.id.idTVResult);
        Button finishBtn = bottomSheetView.findViewById(R.id.idBtnFinish);
        countMbti();
        result = getResult(iore, sori, torf, jorp);
        HashMap hashMap = new HashMap();
        hashMap.put("mbti", result);
        reference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                resultTV.setText("Your MBTI is \n"+ result);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        GlobalVar.currentUser = snapshot.getValue(User.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

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
        if (questionAttempted == 20){
            showBottomSheet();
        }else {
            questionTv.setText(quizModelArrayList.get(currentPos).getQuestion());
            btnOption1.setText(quizModelArrayList.get(currentPos).getOption1());
            btnOption2.setText(quizModelArrayList.get(currentPos).getOption2());
        }

    }
    public void getQuestion(ArrayList<PersonalityTest> quizModelArrayList){
        quizModelArrayList.add(new PersonalityTest("extroversionVsIntroversionTest", "expend energy, enjoy groups", "conserve energy, enjoy one-on-one", 1,2));
        quizModelArrayList.add(new PersonalityTest("extroversionVsIntroversionTest", "more outgoing, think out loud", "more reserved, think to yourself",  1,2));
        quizModelArrayList.add(new PersonalityTest("extroversionVsIntroversionTest", "seek many tasks, public activities, interaction with others", "seek private, solitary activities with quiet to concentrate",  1,2));
        quizModelArrayList.add(new PersonalityTest("extroversionVsIntroversionTest", "external, communicative, express yourself", "internal, reticent, keep to yourself",  1,2));
        quizModelArrayList.add(new PersonalityTest("extroversionVsIntroversionTest", "active, initiate", "reflective, deliberate",  1,2));

        quizModelArrayList.add(new PersonalityTest("sensingVsIntuitionTest", "practical, realistic, experiential", "imaginative, innovative, theoretical",  3,4));
        quizModelArrayList.add(new PersonalityTest("sensingVsIntuitionTest", "interpret literally", "look for meaning and possibilities", 3,4));
        quizModelArrayList.add(new PersonalityTest("sensingVsIntuitionTest", "standard, usual, conventional", "different, novel, unique", 3,4));
        quizModelArrayList.add(new PersonalityTest("sensingVsIntuitionTest", "focus on here-and-now", "look to the future, global perspective", 3,4));
        quizModelArrayList.add(new PersonalityTest("sensingVsIntuitionTest", "facts, things", "ideas, dreams, philosophical", 3,4));

        quizModelArrayList.add(new PersonalityTest("thinkingVsFeelingTest", "firm, tend to criticize, hold the line", "gentle, tend to appreciate, conciliate", 5,6));
        quizModelArrayList.add(new PersonalityTest("thinkingVsFeelingTest", "logical, thinking, questioning", "empathetic, feeling, accommodating", 5,6));
        quizModelArrayList.add(new PersonalityTest("thinkingVsFeelingTest", "candid, straight forward, frank", "tactful, kind, encouraging", 5,6));
        quizModelArrayList.add(new PersonalityTest("thinkingVsFeelingTest", "tough-minded, just", "tender-hearted, merciful", 5,6));
        quizModelArrayList.add(new PersonalityTest("thinkingVsFeelingTest", "matter of fact, issue-oriented", "sensitive, people-oriented, compassionate", 5,6));

        quizModelArrayList.add(new PersonalityTest("judgingVsPerceivingTest", "control, govern", "latitude, freedom", 7,8));
        quizModelArrayList.add(new PersonalityTest("judgingVsPerceivingTest", "organized, orderly", "flexible, adaptable", 7,8));
        quizModelArrayList.add(new PersonalityTest("judgingVsPerceivingTest", "plan, schedule", "unplanned, spontaneous", 7,8));
        quizModelArrayList.add(new PersonalityTest("judgingVsPerceivingTest", "regulated, structured", "easygoing, live and let live", 7,8));
        quizModelArrayList.add(new PersonalityTest("judgingVsPerceivingTest", "control, govern", "latitude, freedom", 7,8));
    }

}