package com.rbhagat.superchat.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rbhagat.superchat.Adapter.UsersAdapter;
import com.rbhagat.superchat.Models.Users;
import com.rbhagat.superchat.databinding.FragmentChatsFragmentsBinding;

import java.util.ArrayList;


public class chatsFragments extends Fragment {



    public chatsFragments() {
        // Required empty public constructor
    }

    FragmentChatsFragmentsBinding binding;
    ArrayList<Users> list=new ArrayList<>();
    FirebaseDatabase database;

    UsersAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        database=FirebaseDatabase.getInstance();
        binding=FragmentChatsFragmentsBinding.inflate(inflater, container, false);

        adapter=new UsersAdapter(list,getContext());

        binding.chatRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());

        binding.chatRecyclerView.setLayoutManager(layoutManager);

        database.getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();

                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                        Users users=dataSnapshot.getValue(Users.class);
                         users.setUid(dataSnapshot.getKey());
                  //  String mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            if (!users.getUID().equals(FirebaseAuth.getInstance().getUid()))

                            {
                                list.add(users);

                            }



                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       return binding.getRoot();
    }
}