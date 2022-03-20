package com.example.firstfirebaseapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageViewHolder extends RecyclerView.ViewHolder {

    public ImageView userImage;
    public TextView userName;
    public TextView message;

    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);

        userImage = itemView.findViewById(R.id.userImage);
        userName = itemView.findViewById(R.id.name);
        message = itemView.findViewById(R.id.messageText);
    }
}
