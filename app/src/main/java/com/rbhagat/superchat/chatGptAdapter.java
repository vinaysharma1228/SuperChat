package com.rbhagat.superchat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class chatGptAdapter extends RecyclerView.Adapter<chatGptAdapter.myViewHolder> {

    List<chatGptMessage> messageList;

    public chatGptAdapter(List<chatGptMessage> messageList) {
        this.messageList = messageList;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View chatGptView= LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item,null);

        myViewHolder myViewHolder=new myViewHolder(chatGptView);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        chatGptMessage GptMessage=messageList.get(position);

        if (GptMessage.getSendBy().equals(chatGptMessage.SEND_BY_ME))
        {

            holder.leftChatView.setVisibility(View.GONE);
            holder.rightChatView.setVisibility(View.VISIBLE);
            holder.rightTextView.setText(GptMessage.getMessage());


        }
        else {

            holder.rightChatView.setVisibility(View.GONE);
            holder.leftChatView.setVisibility(View.VISIBLE);
            holder.leftTextView.setText(GptMessage.getMessage());


        }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }



    public class myViewHolder extends RecyclerView.ViewHolder{

        LinearLayout leftChatView,rightChatView;
        TextView leftTextView,rightTextView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            leftChatView=itemView.findViewById(R.id.left_chat_view);
            rightChatView=itemView.findViewById(R.id.right_chat_view);

            leftTextView=itemView.findViewById(R.id.left_chat_text_view);
            rightTextView=itemView.findViewById(R.id.right_chat_text_view);





        }
    }



}
