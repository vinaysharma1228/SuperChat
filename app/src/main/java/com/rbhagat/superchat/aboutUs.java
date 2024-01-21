package com.rbhagat.superchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.facebook.shimmer.ShimmerFrameLayout;

public class aboutUs extends AppCompatActivity {

    ShimmerFrameLayout shimmerLayout;
    LinearLayout layout;
    ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        shimmerLayout=findViewById(R.id.shimmerView);
        layout=findViewById(R.id.dataView);


        layout.setVisibility(View.INVISIBLE);
        shimmerLayout.startShimmerAnimation();

        Handler handler=new Handler();

        handler.postDelayed(() -> {
            layout.setVisibility(View.VISIBLE);
            shimmerLayout.stopShimmerAnimation();
            shimmerLayout.setVisibility(View.INVISIBLE);
        }, 2500);

        backArrow=findViewById(R.id.backArrow);


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(aboutUs.this,settingsActivity.class);
                startActivity(intent);
            }
        });


    }

}