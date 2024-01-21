package com.rbhagat.superchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rbhagat.superchat.Adapter.ChatAdaptar;
import com.rbhagat.superchat.Models.MessageModel;
import com.rbhagat.superchat.databinding.ActivityGroupChatBinding;

import java.util.ArrayList;
import java.util.Date;

public class groupChatActivity extends AppCompatActivity {

    ActivityGroupChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGroupChatBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());


        getSupportActionBar().hide();

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(groupChatActivity.this,Dashboard.class);
                startActivity(intent);
            }
        });

        final FirebaseDatabase database=FirebaseDatabase.getInstance();

        final String senderId = FirebaseAuth.getInstance().getUid();
        binding.userNameChat.setText("Friends Chat");

        final ArrayList<MessageModel> messageModels=new ArrayList<>();
        final ChatAdaptar adaptar =new ChatAdaptar(messageModels,this);

        binding.chatRecyclerView.setAdapter(adaptar);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.chatRecyclerView.setLayoutManager(layoutManager);

        database.getReference().child("Group Chats")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                messageModels.clear();

                                for (DataSnapshot dataSnapshot :snapshot.getChildren())
                                {
                                    MessageModel model=dataSnapshot.getValue(MessageModel.class);

                                    messageModels.add(model);

                                }
                                adaptar.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

        binding.sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final  String message=binding.etMessage.getText().toString();

                final MessageModel model=new MessageModel(senderId,message);
                model.setTimeStamp(new Date().getTime());
                binding.etMessage.setText("");

                database.getReference().child("Group Chats")
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });


            }
        });
    }
}