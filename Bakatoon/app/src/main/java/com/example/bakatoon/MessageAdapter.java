package com.example.bakatoon;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakatoon.models.Message;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageAdapterViewHolder> {
    Context context;
    List<Message> messageList;
    DatabaseReference reference;

    public MessageAdapter(Context context, List<Message> messageList, DatabaseReference reference) {
        this.context = context;
        this.messageList = messageList;
        this.reference = reference;
    }

    @NonNull
    @Override
    public MessageAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
        return new MessageAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapterViewHolder holder, int position) {
        Message message = messageList.get(position);
        if (message.getName().equals(AllMethods.name)) {
            holder.tvTitle.setText("You " + message.getMessage());
            holder.tvTitle.setGravity(Gravity.START);
            holder.ll.setBackgroundColor(Color.parseColor("#EF9E73"));
        } else {
            holder.tvTitle.setText(message.getName() + ":" + message.getMessage());
            holder.ibDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MessageAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageButton ibDelete;
        LinearLayout ll;

        public MessageAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            ibDelete = (ImageButton) itemView.findViewById(R.id.ibDelete);
            ll = (LinearLayout) itemView.findViewById(R.id.llMessage);

            ibDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reference.child(messageList.get(getAdapterPosition()).getKey()).removeValue();
                }
            });
        }
    }
}