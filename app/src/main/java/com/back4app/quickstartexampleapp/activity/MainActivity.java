package com.back4app.quickstartexampleapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.back4app.quickstartexampleapp.R;
import com.google.gson.Gson;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG =MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Save the current Installation to Back4App
        ParseInstallation.getCurrentInstallation().saveInBackground();

        Button btnCreate = findViewById(R.id.btnCreate);
        Button btnGetUsers = findViewById(R.id.btnGetUsers);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser parseUser= new ParseUser();
                parseUser.setEmail("mustafa@gmail.com");
                parseUser.setPassword("123");
                parseUser.setUsername("mustafa");
                parseUser.put("user_type",1);
                parseUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e==null)
                            Toast.makeText(MainActivity.this, "saved", Toast.LENGTH_SHORT).show();
                    }
                });
                ParseObject parseObject = new ParseObject("User");
                parseObject.put("id", 0);
                parseObject.put("name", "Mustafa");
                parseObject.put("surname", "Berk");
                parseObject.put("user_name", "mustafa");
                parseObject.put("password", "123");
                parseObject.put("user_type", 1);

               /* parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null)
                            Toast.makeText(MainActivity.this, "saved", Toast.LENGTH_SHORT).show();
                    }
                });
                
                */
            }
        });

        btnGetUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e==null)
                        {
                            for(ParseObject item : objects)
                            {
                                Log.d(TAG, "done: " + new Gson().toJson(item));
                            }
                        }
                    }
                });

             }
        });


    }

}
