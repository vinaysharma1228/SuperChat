package com.rbhagat.superchat.Adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rbhagat.superchat.Models.Users;
import com.rbhagat.superchat.R;
import com.rbhagat.superchat.chatDetailActivity;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.viewHolder> {

        ArrayList<Users> list;
        Context context;



    public UsersAdapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;



    }




    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view =LayoutInflater.from(context).inflate(R.layout.sample_show_user, parent, false);
        return new viewHolder(view);

    }





    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        Users users=list.get(position);


            Picasso.get().load(users.getProfile_image()).placeholder(R.drawable.avatar).into(holder.image);
            holder.userName.setText(users.getUserName());

        FirebaseDatabase.getInstance().getReference().child("chats")
                        .child(FirebaseAuth.getInstance().getUid() + users.getUID())
                                .orderByChild("timeStamp")
                                        .limitToLast(1)
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        if (snapshot.hasChildren())
                                                        {
                                                            for (DataSnapshot dataSnapshot: snapshot.getChildren())
                                                            {
                                                                holder.lastMessage.setText(dataSnapshot.child("message").getValue().toString());


                                                            }

                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, chatDetailActivity.class);

                    intent.putExtra("usr_name",users.getUserName());
                    intent.putExtra("usr_pic",users.getProfile_image());
                    intent.putExtra("usr_ID",users.getUID());
                    context.startActivity(intent);
                }
            });



    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView userName,lastMessage;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.profile_img);
            userName=itemView.findViewById(R.id.userNameList);
            lastMessage=itemView.findViewById(R.id.lastMessage);


        }
    }


}
