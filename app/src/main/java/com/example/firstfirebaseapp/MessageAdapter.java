package com.example.firstfirebaseapp;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    private List<chatMessage> messages = new ArrayList<>();

    public MessageAdapter()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("chat")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            messages = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                try {
                                    chatMessage m = new chatMessage(
                                            document.get("userPhoto").toString(),
                                            document.get("userName").toString(),
                                            document.get("userID").toString(),
                                            document.get("message").toString()
                                    );
                                    messages.add(m);
                                } catch (Exception e) {
                                }
                            }
                            notifyDataSetChanged();
                        } else {

                        }
                    }
                });
        db.collection("chat").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                messages = new ArrayList<>();
                for (DocumentSnapshot document : value.getDocuments())
                {
                    try {
                        chatMessage m = new chatMessage(
                                document.get("userPhoto").toString(),
                                document.get("userName").toString(),
                                document.get("userID").toString(),
                                document.get("message").toString()
                        );
                        messages.add(m);
                    } catch (Exception e) {
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    public void addMessage(chatMessage m)
    {
        Map<String, Object> message = new HashMap<>();
        message.put("userPhoto", m.userPhoto);
        message.put("userName", m.userName);
        message.put("userID", m.userID);
        message.put("message",m.message);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("chat").document(String.valueOf(System.currentTimeMillis()))
                .set(message);
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        chatMessage message = messages.get(position);
        holder.message.setText(message.message);
        holder.userName.setText(message.userName);
        Glide.with(holder.userImage.getContext()).load(message.userPhoto).into(holder.userImage);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
