package com.back4app.quickstartexampleapp.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.back4app.quickstartexampleapp.R;
import com.back4app.quickstartexampleapp.Utility;
import com.back4app.quickstartexampleapp.adapter.MessageAdapter;
import com.back4app.quickstartexampleapp.modal.Message;
import com.google.gson.Gson;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.internal.Util;

public class MessageActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = MessageActivity.class.getSimpleName();

    private RecyclerView rvMessages;
    private EditText etMessage;
    private ImageButton imgBtnChatMessage;
    private List<Message> messageList;
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        // Save the current Installation to Back4App
        ParseInstallation.getCurrentInstallation().saveInBackground();

        init();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Messages");
        getMessages();

    }

    private void getMessages() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Message");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    //Log.d(TAG, "done: " + new Gson().toJson(objects));
                    messageList = new ArrayList<>();
                    for (ParseObject item : objects) {
                        Message message = new Message(
                                item.getString("message"),
                                item.getString("senderUserName"),
                                String.valueOf(item.getInt("time")),
                                item.getString("senderObjectId")
                        );
                        //Log.d(TAG, "done: " + message.getTime());
                        messageList.add(message);
                    }

                    messageAdapter = new MessageAdapter(getApplicationContext(), messageList, ParseUser.getCurrentUser());
                    rvMessages.setAdapter(messageAdapter);
                    rvMessages.scrollToPosition(messageList.size()-1);
                }
            }
        });
    }

    private void init() {
        rvMessages = findViewById(R.id.rvMessages);
        etMessage = findViewById(R.id.etMessage);
        imgBtnChatMessage = findViewById(R.id.imgBtnChatMessage);
        imgBtnChatMessage.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.imgBtnChatMessage:
                sendMessage();
                break;
        }

    }

    private void sendMessage() {
        if (!Utility.isEmpty(etMessage)) {
            final ParseObject message = new ParseObject("Message");
            //public Message(String message, String senderUserName, String time, String senderObjectId) {
            String userFullName = ParseUser.getCurrentUser().get("name") + " " + ParseUser.getCurrentUser().get("surname");
            final Message msg = new Message(Utility.getText(etMessage), userFullName,
                    String.valueOf(new Date().getTime()), ParseUser.getCurrentUser().getObjectId());

            message.put("message", msg.getMessage());
            message.put("senderUserName", msg.getSenderUserName());
            message.put("time", Long.parseLong(msg.getTime()));
            message.put("senderObjectId", msg.getSenderObjectId());

            message.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        etMessage.setText("");
                        messageList.add(msg);
                        messageAdapter.notifyItemInserted(messageList.size() - 1);
                        rvMessages.scrollToPosition(messageList.size()-1);
                    }
                }
            });

        } else {
            Toast.makeText(this, "boş bırakmayınız", Toast.LENGTH_SHORT).show();
        }
    }
}
