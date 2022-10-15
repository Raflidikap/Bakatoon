package com.example.bakatoon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private TextView toLogin, registerBtn;
    private EditText et_fullname, et_email, et_password, et_confpassword;
    FirebaseAuth mAuth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toLogin = findViewById(R.id.tv_login);
        registerBtn = findViewById(R.id.registerBtn);
        et_fullname = findViewById(R.id.et_fullname);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_confpassword = findViewById(R.id.et_confpassword);
        mAuth =FirebaseAuth.getInstance();

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strName = et_fullname.getText().toString();
                String strEmail = et_email.getText().toString();
                String strPass = et_password.getText().toString();
                String strConPass = et_confpassword.getText().toString();

                if(strName.isEmpty()){
                    et_fullname.setError("Name cannot empty");
                    et_fullname.requestFocus();
                }else if (strEmail.isEmpty()){
                    et_email.setError("Email cannot empty");
                    et_email.requestFocus();
                }else if (strPass.isEmpty()){
                    et_password.setError("Password cannot empty");
                    et_password.requestFocus();
                }else if (strConPass.isEmpty()){
                    et_confpassword.setError("Confirmation password cannot empty");
                    et_confpassword.requestFocus();
                }else if(!strPass.equals(strConPass)){
                    et_confpassword.setError("Password doesn't matches");
                    et_confpassword.requestFocus();
                }else {
                    createUser(strName, strEmail, strPass);
                }
            }
        });
    }

    private void createUser(String strName, String strEmail, String strPass){

            mAuth.createUserWithEmailAndPassword(strEmail, strPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //butuh progress dialog
                        Toast.makeText(RegisterActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        String userid = firebaseUser.getUid();

                        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("id", userid);
                        hashMap.put("name", strName);
                        hashMap.put("email", strEmail);
                        hashMap.put("mbti", "");

                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        });
                    } else {
                        Toast.makeText(RegisterActivity.this, "Error " + task.getException(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
}
