package com.rbhagat.superchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class chatGpt extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView welcomeText;
    EditText messageEditText;
    ImageView sendBtn,backArrow;

    List<chatGptMessage> messageList;
    chatGptAdapter cgAdapter;


    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_gpt);

        messageList=new ArrayList<>();

        recyclerView=findViewById(R.id.chatGptRecyclerView);
        welcomeText=findViewById(R.id.welcome_text);
        messageEditText=findViewById(R.id.message_edit_text);
        sendBtn=findViewById(R.id.sendMessageBtnChatGpt);
        backArrow=findViewById(R.id.backArrow);


        // setup recyclerView

        cgAdapter=new chatGptAdapter(messageList);

        recyclerView.setAdapter(cgAdapter);

        LinearLayoutManager llm=new LinearLayoutManager(this);
        llm.setStackFromEnd(true);

        recyclerView.setLayoutManager(llm);

        sendBtn.setOnClickListener((v)->{

            String question=messageEditText.getText().toString().trim();

            addToChat(question,chatGptMessage.SEND_BY_ME);
            messageEditText.setText("");
            calAPI(question);
            welcomeText.setVisibility(View.GONE);


        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(chatGpt.this,Dashboard.class);

                startActivity(intent);
            }
        });

    }

    void addToChat(String message,String sendBy)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                messageList.add(new chatGptMessage(message,sendBy));
                cgAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(cgAdapter.getItemCount());

            }
        });


    }

    void addResponse(String response)
    {
        messageList.remove(messageList.size()-1);
        addToChat(response,chatGptMessage.SEND_BY_BOT);

    }

    void calAPI(String question){

        messageList.add(new chatGptMessage("Typing...",chatGptMessage.SEND_BY_BOT));

        JSONObject jsonBody= new JSONObject();

        try {
            jsonBody.put("model","text-davinci-003");
            jsonBody.put("prompt",question);
            jsonBody.put("max_tokens",4000);
            jsonBody.put("temperature",0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body=RequestBody.create(jsonBody.toString(),JSON);

        Request request= new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .header("Authorization","Bearer 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")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                addResponse("Failed to load response due to + "+ e.getMessage());

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.isSuccessful())
                {

                    JSONObject jsonObject= null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray= jsonObject.getJSONArray("choices");
                        String result=jsonArray.getJSONObject(0).getString("text");
                        addResponse(result.trim());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }
                else {
                    addResponse("Failed to load response due to + "+ response.body().toString());
                }


            }
        });


    }
}