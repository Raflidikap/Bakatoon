package com.example.bakatoon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bakatoon.models.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class ProfileActivity extends AppCompatActivity {
    private TextView logoutBtn, profileName, profileEmail, mbtiProfile;
    private Button backBtn, saveChangeBtn;
    private ImageView profilePic;
    private String destinationUri = UUID.randomUUID().toString() + ".jpg";
    Uri databaseUri;

    StorageTask uploadTask;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        storageReference = FirebaseStorage.getInstance().getReference("profilePic");

        mbtiProfile = findViewById(R.id.mbtiProfile);
        logoutBtn = findViewById(R.id.logoutBtn);
        profileEmail = findViewById(R.id.profileEmail);
        profileName = findViewById(R.id.profileName);
        backBtn = findViewById(R.id.backBtn);
        profilePic = findViewById(R.id.profilePic);

        profileEmail.setText(GlobalVar.currentUser.getEmail());
        profileName.setText(GlobalVar.currentUser.getName());
        Glide.with(getApplicationContext()).load(GlobalVar.currentUser.getImageprofileUrl()).into(profilePic);

        mbtiProfile.setText(GlobalVar.currentUser.getMbti());

        mbtiProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!GlobalVar.currentUser.getMbti().isEmpty()){
                    SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("MBTI", MODE_PRIVATE).edit();
                    editor.putString("mbtiGroup", GlobalVar.currentUser.getMbti());
                    editor.apply();
                    startActivity(new Intent(ProfileActivity.this, GroupChat.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                }else{
                    Toast.makeText(ProfileActivity.this, "Please take your mbti test before join MBTI groupChat", Toast.LENGTH_SHORT).show();
                }
                
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                finish();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });


    }

    private String getFilesExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadImageProfile() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading Image...");
        progressDialog.show();

        if (databaseUri != null) {
            final StorageReference mStorage = storageReference.child(System.currentTimeMillis()
                    + "." + getFilesExtension(databaseUri));
            uploadTask = mStorage.putFile(databaseUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return mStorage.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String myUrl = downloadUri.toString();
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid());
                        HashMap<String, Object> hashMap = new HashMap<>();

                        hashMap.put("imageprofileUrl", "" + myUrl);

                        reference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        GlobalVar.currentUser = snapshot.getValue(User.class);
                                        GlobalVar.currentUser.setImageprofileUrl(myUrl);
                                        progressDialog.dismiss();
                                        Toast.makeText(ProfileActivity.this, "Complete", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        });


                    } else {
                        Toast.makeText(ProfileActivity.this, "Failed upload image", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });

        } else {
            Toast.makeText(this, "Image not Found", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Select Image Profile");
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Gallery")) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 100);
            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            final Uri uri = data.getData();
            UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationUri)))
                    .withAspectRatio(1, 1)
                    .start(this);
        } else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            Uri resultUri = UCrop.getOutput(data);
            databaseUri = resultUri;

            profilePic.setImageURI(databaseUri);
            profilePic.setBackgroundResource(0);

            uploadImageProfile();

        } else {
            Toast.makeText(this, "Failed to take picture", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            finish();
        }
    }
}