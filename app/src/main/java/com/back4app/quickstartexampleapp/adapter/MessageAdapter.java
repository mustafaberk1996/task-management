package com.back4app.quickstartexampleapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.back4app.quickstartexampleapp.R;
import com.back4app.quickstartexampleapp.modal.Message;
import com.parse.ParseUser;

import java.util.Date;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {


    private Context context;
    private List<Message> messageList;
    private ParseUser parseUser;

    public MessageAdapter(Context context, List<Message> messageList, ParseUser parseUser) {
        this.context = context;
        this.messageList = messageList;
        this.parseUser = parseUser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (viewType == Message.TYPE_RECEIVER)
            view = LayoutInflater.from(context).inflate(R.layout.receive_message, null);
        else
            view = LayoutInflater.from(context).inflate(R.layout.sent_message, null);
        view.setLayoutParams(layoutParams);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message item = messageList.get(position);
        holder.tvUserName.setText(item.getSenderUserName());
        holder.tvMessage.setText(item.getMessage());
        Date date = new Date(Long.parseLong(item.getTime()));
        holder.tvMessageTime.setText(date.toLocaleString());
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messageList.get(position);
        if (message.getSenderObjectId().equalsIgnoreCase(parseUser.getObjectId()))
            return Message.TYPE_SENDER;
        else
            return Message.TYPE_RECEIVER;
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUserName, tvMessageTime, tvMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvMessageTime = itemView.findViewById(R.id.tvMessageTime);
            tvUserName = itemView.findViewById(R.id.tvUserName);
        }
    }
}
