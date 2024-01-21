package com.rbhagat.superchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rbhagat.superchat.Models.Users;
import com.rbhagat.superchat.databinding.ActivitySettingsBinding;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class settingsActivity extends AppCompatActivity {

    ActivitySettingsBinding binding;
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        storage=FirebaseStorage.getInstance();
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();


        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(settingsActivity.this,Dashboard.class);
                startActivity(intent);
            }
        });

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String status=binding.etStatus.getText().toString();
                String username=binding.userNameSetting.getText().toString();

                HashMap<String,Object> obj=new HashMap<>();

                obj.put("userName",username);
                obj.put("status",status);

                database.getReference().child("users").child(FirebaseAuth.getInstance().getUid())
                        .updateChildren(obj);



            }
        });

        database.getReference().child("users").child(FirebaseAuth.getInstance().getUid())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                Users users=snapshot.getValue(Users.class);
                                Picasso.get()
                                        .load(users.getProfile_image())
                                        .placeholder(R.drawable.avatar)
                                        .into(binding.profileImg);

                                    binding.etStatus.setText(users.getStatus());
                                    binding.userNameSetting.setText(users.getUserName());

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

        binding.privacyPolicyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(settingsActivity.this,privacy_policy.class);
                startActivity(intent);
            }
        });

        binding.inviteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(settingsActivity.this,shareApp.class);
                startActivity(intent);

            }
        });

        binding.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,33);

            }
        });


        binding.aboutUsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(settingsActivity.this,aboutUs.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==33)
        {
            if (data!=null)

            {
               Uri imageUri =data.getData();
                binding.profileImg.setImageURI(imageUri);

                final StorageReference reference=storage.getReference().child("profile pictures")
                        .child(FirebaseAuth.getInstance().getUid());
                reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                database.getReference().child("users").child(FirebaseAuth.getInstance().getUid())
                                        .child("profile_image").setValue(uri.toString());

                            }
                        });
                    }
                });
            }
        }
    }
}