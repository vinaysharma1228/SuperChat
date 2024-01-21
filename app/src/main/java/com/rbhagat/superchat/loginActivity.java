package com.rbhagat.superchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;


import java.util.concurrent.TimeUnit;

public class loginActivity extends AppCompatActivity {

    EditText phoneNumber;
    Button getOtpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phoneNumber=findViewById(R.id.phone_number);
        getOtpBtn=findViewById(R.id.get_otp_btn);



        ProgressBar progressBar=findViewById(R.id.progressBar_sending);

        getOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!phoneNumber.getText().toString().trim().isEmpty()){
                    if (phoneNumber.getText().toString().trim().length()== 10){

                        progressBar.setVisibility(View.VISIBLE);
                        getOtpBtn.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91 " + phoneNumber.getText().toString()
                                , 60, TimeUnit.SECONDS, loginActivity.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                        progressBar.setVisibility(View.GONE);
                                        getOtpBtn.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {

                                        progressBar.setVisibility(View.GONE);
                                        getOtpBtn.setVisibility(View.VISIBLE);
                                        Toast.makeText(loginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String otp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBar.setVisibility(View.GONE);
                                        getOtpBtn.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(getApplicationContext(), otpVerifying.class);
                                        intent.putExtra("mobile",phoneNumber.getText().toString());
                                        intent.putExtra("otp",otp);
                                        startActivity(intent);
                                    }
                                }
                        );


                    }
                    else {
                        Toast.makeText(loginActivity.this, "Please enter correct number", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(loginActivity.this, "Enter mobile number", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}