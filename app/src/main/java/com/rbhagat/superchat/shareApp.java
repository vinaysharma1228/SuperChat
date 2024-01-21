package com.rbhagat.superchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class shareApp extends AppCompatActivity {

    ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_app);

        backArrow=findViewById(R.id.backArrow);

        Button shareButton = findViewById(R.id.shareButton);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareApp();
            }
        });


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(shareApp.this,settingsActivity.class);
                startActivity(intent);
            }
        });
    }


    private void shareApp() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareMessage = "Check out this awesome app! Download it : https://drive.google.com/drive/folders/11HXPvRNbHX27qUjqRy5Tk2HOwt-F4OOx?usp=sharing";
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);

        // Start the sharing intent
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }
}