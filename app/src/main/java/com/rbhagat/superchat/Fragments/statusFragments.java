package com.rbhagat.superchat.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.rbhagat.superchat.R;
import com.rbhagat.superchat.chatGpt;
import com.rbhagat.superchat.databinding.FragmentStatusFragmentsBinding;


public class statusFragments extends Fragment {


    public statusFragments() {

    }

    FragmentStatusFragmentsBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentStatusFragmentsBinding.inflate(inflater,container,false);

        binding.tryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( statusFragments.this.getActivity(),chatGpt.class );
                startActivity(intent);

            }
        });


        return binding.getRoot();
    }
}