package com.rbhagat.superchat;

import static android.view.View.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class otpVerifying extends AppCompatActivity {
    EditText inputNumber1,inputNumber2,inputNumber3,inputNumber4,inputNumber5,inputNumber6;
    String getOtp;
    TextView otp_timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verifying);



        inputNumber1 = findViewById(R.id.inputOtp1);
        inputNumber2 = findViewById(R.id.inputOtp2);
        inputNumber3 = findViewById(R.id.inputOtp3);
        inputNumber4 = findViewById(R.id.inputOtp4);
        inputNumber5 = findViewById(R.id.inputOtp5);
        inputNumber6 = findViewById(R.id.inputOtp6);

        otp_timer=findViewById(R.id.otp_timer);

        CountDownTimer otp_countdown= new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
                otp_timer.setText(": "+(l/1000));
            }

            @Override
            public void onFinish() {

            }
        }.start();

        TextView textView=findViewById(R.id.text_mobile_show);
        String mobile_number=getIntent().getStringExtra("mobile");
        textView.setText(String.format("+91-%s",getIntent().getStringExtra("mobile")

        ));

        getOtp=getIntent().getStringExtra("otp");

        final  ProgressBar progressBarVerify=findViewById(R.id.progressBar_verify);

        final Button submitOtpBtn=findViewById(R.id.submit_otp_btn);
        submitOtpBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!inputNumber1.getText().toString().trim().isEmpty() && !inputNumber2.getText().toString().trim().isEmpty() && !inputNumber3.getText().toString().trim().isEmpty() && !inputNumber4.getText().toString().trim().isEmpty() && !inputNumber5.getText().toString().trim().isEmpty() && !inputNumber6.getText().toString().trim().isEmpty())
                {
                    String userOtp=inputNumber1.getText().toString()+
                            inputNumber2.getText().toString()+
                            inputNumber3.getText().toString()+
                            inputNumber4.getText().toString()+
                            inputNumber5.getText().toString()+
                            inputNumber6.getText().toString();


                    if (getOtp!=null){

                        progressBarVerify.setVisibility(View.VISIBLE);
                        submitOtpBtn.setVisibility(View.INVISIBLE);

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(getOtp,userOtp);

                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBarVerify.setVisibility(View.GONE);
                                        submitOtpBtn.setVisibility(View.VISIBLE);

                                        if (task.isComplete())
                                        {
                                            //  String id =task.getResult().getUser().getUid();
                                            Intent intent =new Intent(getApplicationContext(), homePage.class);
                                            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK|intent.FLAG_ACTIVITY_CLEAR_TASK);

                                            intent.putExtra("mobile_number",mobile_number);

                                            //   intent.putExtra("id",id);

                                            startActivity(intent);

                                        }else {
                                            Toast.makeText(otpVerifying.this, "Enter the correct OTP", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    }else {
                        Toast.makeText(otpVerifying.this, "check internet connection", Toast.LENGTH_SHORT).show();
                    }

                    //Toast.makeText(otpVerifying.this, "Number Verify", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(otpVerifying.this, "Please enter all number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        numberOtpMove();

        TextView resendLabel = findViewById(R.id.resend_otp_btn);

        resendLabel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {





                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91 " + getIntent().getStringExtra("mobile")
                        , 60, TimeUnit.SECONDS, otpVerifying.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {


                                Toast.makeText(otpVerifying.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                getOtp=newOtp;

                                Toast.makeText(otpVerifying.this, "OTp resend successful", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });

    }

    private void numberOtpMove() {
        inputNumber1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(s.toString().trim().length()==1)
                {
                    inputNumber2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputNumber2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(s.toString().trim().length()==1)
                {
                    inputNumber3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputNumber3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(s.toString().trim().length()==1)
                {
                    inputNumber4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputNumber4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(s.toString().trim().length()==1)
                {
                    inputNumber5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputNumber5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(s.toString().trim().length()==1)
                {
                    inputNumber6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}