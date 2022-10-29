package com.example.bakatoon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bakatoon.models.Message;
import com.example.bakatoon.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GroupChat extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference reference;
    private MessageAdapter messageAdapter;
    TextView mbtiGroupId;
    private String group;
    SharedPreferences prefs;
    User u;
    List<Message> messageList;
    RecyclerView rvMessage;
    EditText messageEditText;
    Button backButton;
    ImageButton sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        prefs = getApplicationContext().getSharedPreferences("MBTI", Context.MODE_PRIVATE);
        group = prefs.getString("mbtiGroup", "");
        mbtiGroupId = findViewById(R.id.mbtiGroupId);
        mbtiGroupId.setText(group);

        init();
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        u = new User();
        rvMessage = (RecyclerView) findViewById(R.id.messageRv);
        messageEditText = (EditText) findViewById(R.id.messageEditText);
        sendButton = (ImageButton) findViewById(R.id.sendBtn);
        backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        sendButton.setOnClickListener(this);
        messageList = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuLogout) {
            mAuth.signOut();
            finish();
            startActivity(new Intent(GroupChat.this, LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (!TextUtils.isEmpty(messageEditText.getText().toString())) {
            Message message = new Message(messageEditText.getText().toString(), u.getName());
            messageEditText.setText("");
            reference.push().setValue(message);
        } else {
            Toast.makeText(this, "Message cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        u.setId(currentUser.getUid());
        u.setEmail(currentUser.getEmail());
        u.setName(currentUser.getDisplayName());

        mDatabase.getReference("Users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                u = snapshot.getValue(User.class);
                u.setId(currentUser.getUid());
                AllMethods.name = u.getName();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        reference = mDatabase.getReference(group);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message message = snapshot.getValue(Message.class);
                message.setKey(snapshot.getKey());
                messageList.add(message);
                displayMessage(messageList);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message message = snapshot.getValue(Message.class);
                message.setKey(snapshot.getKey());

                List<Message> newMessages = new ArrayList<Message>();

                for (Message m : messageList) {
                    if (m.getKey().equals(message.getKey())) {
                        newMessages.add(message);
                    } else {
                        newMessages.add(m);
                    }
                }
                messageList = newMessages;
                displayMessage(messageList);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Message message = snapshot.getValue(Message.class);
                message.setKey(snapshot.getKey());

                List<Message> newMessages = new ArrayList<Message>();

                for (Message m : messageList) {
                    if (!m.getKey().equals(message.getKey())) {
                        newMessages.add(m);
                    }
                }
                messageList = newMessages;
                displayMessage(messageList);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        messageList = new ArrayList<>();
    }

    private void displayMessage(List<Message> messages) {
        rvMessage.setLayoutManager(new LinearLayoutManager(GroupChat.this));
        messageAdapter = new MessageAdapter(GroupChat.this, messages, reference);
        rvMessage.setAdapter(messageAdapter);
    }
}