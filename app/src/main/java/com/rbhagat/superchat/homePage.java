package com.rbhagat.superchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rbhagat.superchat.Models.Users;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class homePage extends AppCompatActivity {

    FirebaseDatabase database;
    FirebaseStorage storage;
    EditText userName,gmail;
    Button set_profile;
    CircleImageView profile_photo;
    FirebaseAuth auth;
    Uri imageUri;
    String imageURI;
    String mobileNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ProgressBar progressBar_sending=findViewById(R.id.progressBar_sending);

        profile_photo=findViewById(R.id.profile_photo);




        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();

        //   @SuppressLint("WrongViewCast") ProgressBar progressBarSetUpProfile=findViewById(R.id.set_profile);
        String uid=auth.getUid();



        // id=getIntent().getStringExtra("id");
        mobileNumber=getIntent().getStringExtra("mobile_number");

        userName=findViewById(R.id.user_name);
        gmail=findViewById(R.id.user_gmail);
        set_profile=findViewById(R.id.set_profile);

        profile_photo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 10);
            }
        });


        set_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!userName.getText().toString().trim().isEmpty())
                {
                    progressBar_sending.setVisibility(View.VISIBLE);
                    set_profile.setVisibility(View.INVISIBLE);

                   DatabaseReference reference = database.getReference().child("users").child(uid);

                  StorageReference storageReference=storage.getReference().child("upload").child(uid);




                    if (imageUri!=null)
                    {
                        storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if (task.isSuccessful())
                                {
                                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            imageURI=uri.toString();

                                            Users users=new Users(userName.getText().toString(),gmail.getText().toString(),imageURI,uid);

                                            reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful())
                                                    {
                                                        Toast.makeText(homePage.this,"Profile Setup Successfully", Toast.LENGTH_SHORT).show();

                                                        Intent intent = new Intent(getApplicationContext(),Dashboard.class);
                                                        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK|intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        startActivity(intent);
                                                    }

                                                }
                                            });

                                        }
                                    });

                                }

                            }
                        });
                    } else{
                        imageURI="https://firebasestorage.googleapis.com/v0/b/superchat-royal1228.appspot.com/o/pp.png?alt=media&token=54079b62-8dc3-4268-99de-eecca92ba734";

                        Users users=new Users(userName.getText().toString(),gmail.getText().toString(),imageURI,uid);
                        reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(homePage.this,"Profile Setup Successfully", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(getApplicationContext(),Dashboard.class);
                                    startActivity(intent);
                                }

                            }
                        });

                    }






                }
                else {
                    Toast.makeText(homePage.this, "Please Enter username", Toast.LENGTH_SHORT).show();
                    progressBar_sending.setVisibility(View.GONE);
                    set_profile.setVisibility(View.VISIBLE);
                }



            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==10)
        {
            if (data!=null)

            {
                imageUri =data.getData();
                profile_photo.setImageURI(imageUri);
            }
        }
    }
}