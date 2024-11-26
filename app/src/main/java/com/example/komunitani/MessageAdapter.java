package com.example.komunitani;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.example.model.Message;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private Context context;
    private List<Message> messageList;

    public MessageAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.contactName.setText(message.getName());
        holder.lastMessage.setText(message.getLastMessage());

//        // Jika Anda menggunakan Glide atau Picasso untuk memuat gambar
//        Glide.with(holder.itemView.getContext())
//                .load(message.getProfilePicture())
//                .into(holder.profilePicture);

        int Id = message.getId();
        android.util.Log.d("MessageAdapter", "onBindViewHolder: userId = " + Id);


        // Set OnClickListener
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, chat.class);
            intent.putExtra("receiver_id", message.getId());
            intent.putExtra("receiver_name", message.getName());
            context.startActivity(intent);
        });

//        // Set unread count if applicable
//        holder.unreadCount.setText("0"); // Ganti dengan logika yang sesuai
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView contactName, lastMessage, unreadCount;
        ImageView profilePicture;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            contactName = itemView.findViewById(R.id.contact_name);
            lastMessage = itemView.findViewById(R.id.last_message);
            profilePicture = itemView.findViewById(R.id.profile_picture);
        }
    }
}
