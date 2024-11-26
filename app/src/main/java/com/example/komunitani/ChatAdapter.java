package com.example.komunitani;

import static android.content.Intent.getIntent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_SENT = 1;
    private static final int VIEW_TYPE_RECEIVED = 2;
    private List<ChatMessage> messages = new ArrayList<>();
    private int userId;
    private int receiverId;

    private static final String TAG = "ChatAdapter";

    public ChatAdapter(List<ChatMessage> messages, int senderId, int receiverId) {
        this.messages = messages;
        this.userId = senderId; // ID pengguna yang login
        this.receiverId = receiverId; // ID pengguna penerima pesan



    }


    public ChatAdapter(List<ChatMessage> messages) {
        this.messages = messages;
        Log.d(TAG, "ChatAdapter initialized without userId or receiverId");
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage message = messages.get(position);

        // Logika untuk menentukan jenis pesan
        if (message.getSenderId() == userId && message.getReceiverId() == receiverId) {
            return VIEW_TYPE_SENT; // Pesan dikirim oleh user saat ini ke receiver
        } else if (message.getSenderId() == receiverId && message.getReceiverId() == userId) {
            return VIEW_TYPE_RECEIVED; // Pesan diterima oleh user saat ini dari receiver
        } else {
            // Jika tidak memenuhi kondisi, log pesan untuk debugging
            Log.w("ChatAdapter", "Unknown message sender/receiver relation: " +
                    "senderId=" + message.getSenderId() +
                    ", receiverId=" + message.getReceiverId());
            return VIEW_TYPE_RECEIVED; // Default fallback
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENT) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage message = messages.get(position);

        // Log detail binding data
        Log.d(TAG, "onBindViewHolder for position " + position +
                ": message=" + message.getMessage());

        if (holder instanceof SentMessageViewHolder) {
            ((SentMessageViewHolder) holder).bind(message);
        } else if (holder instanceof ReceivedMessageViewHolder) {
            ((ReceivedMessageViewHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + messages.size());
        return messages.size();
    }

    class SentMessageViewHolder extends RecyclerView.ViewHolder {
        TextView textMessageSent;

        SentMessageViewHolder(View itemView) {
            super(itemView);
            textMessageSent = itemView.findViewById(R.id.textMessageSent);
        }

        void bind(ChatMessage message) {
            Log.d(TAG, "Binding SentMessage: " + message.getMessage());
            textMessageSent.setText(message.getMessage());
        }
    }

    class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
        TextView textMessageReceived;

        ReceivedMessageViewHolder(View itemView) {
            super(itemView);
            textMessageReceived = itemView.findViewById(R.id.textMessageReceived);
        }

        void bind(ChatMessage message) {
            Log.d(TAG, "Binding ReceivedMessage: " + message.getMessage());
            textMessageReceived.setText(message.getMessage());
        }
    }
}
