package com.rbhagat.superchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rbhagat.superchat.Adapter.ChatAdaptar;
import com.rbhagat.superchat.Models.MessageModel;
import com.rbhagat.superchat.Models.Users;
import com.rbhagat.superchat.databinding.ActivityChatDetailBinding;
import com.squareup.picasso.Picasso;
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService;
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton;
import com.zegocloud.uikit.service.defines.ZegoUIKitUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class chatDetailActivity extends AppCompatActivity {

    ActivityChatDetailBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    String username;
//    EditText calls;
//    ZegoSendCallInvitationButton voiceCall,videoCall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

//        voiceCall=findViewById(R.id.voiceCall);
//        videoCall=findViewById(R.id.videoCall);
//        calls=findViewById(R.id.calls);



        Users users=new Users();
        username=users.getUserName();

        final String senderId=auth.getUid();

        // audio video call

        String recieverId=getIntent().getStringExtra("usr_ID");

        String userName=getIntent().getStringExtra("usr_name");
        String profile_pic=getIntent().getStringExtra("usr_pic");

        binding.userNameChat.setText(userName);

        Picasso.get().load(profile_pic).placeholder(R.drawable.avatar).into(binding.profileImg);

        startService(senderId);

      binding.calls.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                setVoiceCall(recieverId);
                setVideoCall(recieverId);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(chatDetailActivity.this,Dashboard.class);
                startActivity(intent);
            }
        });

        final ArrayList<MessageModel> messageModels=new ArrayList<>();
        final ChatAdaptar chatAdaptar=new ChatAdaptar(messageModels,this,recieverId);

        binding.chatRecyclerView.setAdapter(chatAdaptar);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);

        binding.chatRecyclerView.setLayoutManager(layoutManager);

        final String senderRoom=senderId + recieverId;
        final String receiverRoom=recieverId + senderId;

        database.getReference().child("chats")
                        .child(senderRoom)
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        messageModels.clear();

                                        for (DataSnapshot dataSnapshot :snapshot.getChildren())
                                        {
                                            MessageModel model=dataSnapshot.getValue(MessageModel.class);

                                            model.setMessageId(dataSnapshot.getKey());

                                            messageModels.add(model);

                                        }
                                        chatAdaptar.notifyDataSetChanged();

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

        binding.sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String message=binding.etMessage.getText().toString();

                    final MessageModel model=new MessageModel(senderId,message);

                    model.setTimeStamp(new Date().getTime());
                    binding.etMessage.setText("");

                    database.getReference().child("chats")
                            .child(senderRoom)
                            .push()
                            .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    database.getReference().child("chats")
                                            .child(receiverRoom)
                                            .push()
                                            .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {


                                                }
                                            });

                                }
                            });

            }
        });




    }



    // here is audio and video call logic....

    void startService(String userid)
  {

      Application application = getApplication(); // Android's application context
      long appID = 1868891517; // yourAppID
      String appSign ="0349233c6646395a711900cfdd4ce80d4b8363df63d77029078cd7dd2678f004";  // yourAppSign
      String userID = userid; // yourUserID, userID should only contain numbers, English characters, and '_'.
      String userName =userid;   // yourUserName

      ZegoUIKitPrebuiltCallInvitationConfig callInvitationConfig = new ZegoUIKitPrebuiltCallInvitationConfig();
      callInvitationConfig.notifyWhenAppRunningInBackgroundOrQuit = true;
      ZegoNotificationConfig notificationConfig = new ZegoNotificationConfig();
      notificationConfig.sound = "zego_uikit_sound_call";
      notificationConfig.channelID = "CallInvitation";
      notificationConfig.channelName = "CallInvitation";
      ZegoUIKitPrebuiltCallInvitationService.init(application, appID, appSign, userID, userName,callInvitationConfig);


  }



    void setVoiceCall(String targetUserID)
    {
       binding.voiceCall.setIsVideoCall(true);
        binding.voiceCall.setResourceID("zego_uikit_call");
        binding.voiceCall.setInvitees(Collections.singletonList(new ZegoUIKitUser(targetUserID)));

    }

    void setVideoCall(String targetUserID)
    {
        binding.videoCall.setIsVideoCall(true);
        binding.videoCall.setResourceID("zego_uikit_call");
        binding.videoCall.setInvitees(Collections.singletonList(new ZegoUIKitUser(targetUserID)));


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ZegoUIKitPrebuiltCallInvitationService.unInit();
    }


}