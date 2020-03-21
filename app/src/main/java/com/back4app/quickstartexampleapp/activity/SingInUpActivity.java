package com.back4app.quickstartexampleapp.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.back4app.quickstartexampleapp.adapter.AdminTaskAdapter;
import com.back4app.quickstartexampleapp.adapter.MemberAdapter;
import com.back4app.quickstartexampleapp.R;
import com.back4app.quickstartexampleapp.modal.Task;
import com.back4app.quickstartexampleapp.modal.User;
import com.back4app.quickstartexampleapp.Utility;
import com.back4app.quickstartexampleapp.adapter.MemberTaskAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SingInUpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = SingInUpActivity.class.getSimpleName();
    private EditText etUserName, etPassword, etTaskText;
    private Button btnSignIn, btnShowCreateTask, btnShowTaskList, btnCreateTask;
    private TextView tvUserName;
    private RelativeLayout rlAdmin, rlCreateTask;
    private LinearLayout llSignIn;
    private RecyclerView rvMemberTask, rvAdminTasks, rvMembers;

    private List<User> taskMemberList;
    private List<Task> adminTaskList;

    private ParseUser currentUser;
    private MemberAdapter memberAdapter;
    private MemberTaskAdapter memberTaskAdapter;
    private List<Task> taskList;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in_up);
        init();
        currentUser = ParseUser.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(ParseUser currentUser) {
        if (currentUser == null) {
            llSignIn.setVisibility(View.VISIBLE);
            if (menu != null) menu.findItem(R.id.menu_send_message).setVisible(false);

        } else {
            if (menu != null) menu.findItem(R.id.menu_send_message).setVisible(true);
            tvUserName.setText(currentUser.getUsername());
            llSignIn.setVisibility(View.GONE);

            if (User.ADMIN == Integer.parseInt(currentUser.getString("userType"))) {
                rlAdmin.setVisibility(View.VISIBLE);
                rlCreateTask.setVisibility(View.VISIBLE);
                rvMemberTask.setVisibility(View.GONE);
                getMembers();
            } else {
                rlAdmin.setVisibility(View.GONE);
                rvMemberTask.setVisibility(View.VISIBLE);
                getMemberTask();
            }

        }
    }

    private void getMemberTask() {
        if (taskList != null) {
            taskList.clear();
            memberTaskAdapter.notifyDataSetChanged();
        }
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Task");
        query.whereEqualTo("memberId", currentUser.getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    Type type = new TypeToken<Task>() {
                    }.getType();

                    taskList = new ArrayList<>();
                    for (ParseObject item : objects) {
                        //Log.d(TAG, "done: " + new Gson().toJson(item));
                        Task task = new Task(item.getObjectId(),
                                item.getString("task"),
                                item.getString("memberName"),
                                item.getString("memberId"),
                                item.getString("adminId"),
                                item.getBoolean("isCompleted")
                        );
                        taskList.add(task);
                    }

                    memberTaskAdapter = new MemberTaskAdapter(getApplicationContext(), taskList, currentUser);
                    rvMemberTask.setAdapter(memberTaskAdapter);

                }
            }
        });

    }

    private void init() {

        taskMemberList = new ArrayList<>();

        rlAdmin = findViewById(R.id.rlAdmin);
        rlCreateTask = findViewById(R.id.rlCreateTask);
        rvMemberTask = findViewById(R.id.rvMemberTask);
        rvAdminTasks = findViewById(R.id.rvAdminTasks);
        rvMembers = findViewById(R.id.rvMembers);
        tvUserName = findViewById(R.id.tvUserName);
        llSignIn = findViewById(R.id.llSignIn);


        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        etTaskText = findViewById(R.id.etTaskText);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnShowCreateTask = findViewById(R.id.btnShowCreateTask);
        btnShowTaskList = findViewById(R.id.btnShowTaskList);
        btnCreateTask = findViewById(R.id.btnCreateTask);


        llSignIn.setVisibility(View.VISIBLE);
        rlAdmin.setVisibility(View.GONE);
        rvMemberTask.setVisibility(View.GONE);
        rvAdminTasks.setVisibility(View.GONE);

        btnSignIn.setOnClickListener(this);
        btnShowCreateTask.setOnClickListener(this);
        btnShowTaskList.setOnClickListener(this);
        btnCreateTask.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnSignIn:
                signIn(etUserName, etPassword);
                break;
            case R.id.btnCreateTask:
                createTask(etTaskText);
                break;
            case R.id.btnShowCreateTask:
                rlCreateTask.setVisibility(View.VISIBLE);
                rvAdminTasks.setVisibility(View.GONE);
                break;
            case R.id.btnShowTaskList:
                rlCreateTask.setVisibility(View.GONE);
                rvAdminTasks.setVisibility(View.VISIBLE);
                getAdminTasks();
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        if (ParseUser.getCurrentUser() != null)
            menu.findItem(R.id.menu_send_message).setVisible(true);
        else
            menu.findItem(R.id.menu_send_message).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_exit:
                ParseUser.logOut();
                updateUI(ParseUser.getCurrentUser());
                break;
            case R.id.menu_send_message:
                startActivity(new Intent(this, MessageActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getAdminTasks() {
        adminTaskList = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Task");
        try {
            List<ParseObject> list = query.find();
            for (ParseObject item : list) {
                //String objectId, String task, String memberId, String adminId, boolean isCompleted) {
                Task task = new Task(item.getObjectId(), item.getString("task"),
                        item.getString("memberName"),
                        item.getString("memberId"),
                        item.getString("adminId"),
                        item.getBoolean("isCompleted")
                );
                adminTaskList.add(task);
            }

            AdminTaskAdapter adminTaskAdapter = new AdminTaskAdapter(getApplicationContext(), adminTaskList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            rvAdminTasks.setLayoutManager(linearLayoutManager);
            rvAdminTasks.setAdapter(adminTaskAdapter);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    private void createTask(final EditText etTaskText) {
        if (!Utility.isEmpty(etTaskText)) {
            if (taskMemberList.size() > 0) {
                for (User item : taskMemberList) {
                    ParseObject task = new ParseObject("Task");
                    task.put("memberName", item.getName() + " " + item.getSurname());
                    task.put("memberId", item.getObjectId());
                    task.put("task", Utility.getText(etTaskText));
                    task.put("adminId", ParseUser.getCurrentUser().getObjectId());
                    task.put("isCompleted", false);

                    task.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(SingInUpActivity.this, "task oluşturuldu", Toast.LENGTH_SHORT).show();
                                etTaskText.setText("");
                                memberAdapter.notifyDataSetChanged();
                            }
                        }
                    });

                }
            } else
                Toast.makeText(this, "en az bir kullanıcı seçiniz", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "boş alan bırakmayınız", Toast.LENGTH_SHORT).show();
    }

    private void signIn(final EditText etUserName, final EditText etPassword) {
        if (!Utility.isEmpty(etUserName) && !Utility.isEmpty(etPassword)) {
            ParseUser.logInInBackground(Utility.getText(etUserName), Utility.getText(etPassword), new LogInCallback() {
                public void done(ParseUser user, ParseException e) {
                    if (user != null) {
                        //Log.d(TAG, new Gson().toJson(user));
                        currentUser = ParseUser.getCurrentUser();
                        updateUI(user);
                        etPassword.setText("");
                        etUserName.setText("");
                    } else {
                        Toast.makeText(SingInUpActivity.this, "kullanıcı adı vey parola hatalı", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else
            Toast.makeText(SingInUpActivity.this, "boş alan bırakmayınız", Toast.LENGTH_SHORT).show();
    }


    public void getMembers() {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereExists("userType");
        query.whereNotEqualTo("userType", "0");
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> users, ParseException e) {
                if (e == null) {
                    // The query was successful, returns the users that matches
                    // the criterias.
                    List<User> userlist = new ArrayList<>();
                    for (ParseUser item : users) {
                        User user = new User(item.getObjectId(), item.getString("name"), item.getString("surname"), item.getString("userType"));
                        userlist.add(user);
                        //Log.d(TAG, new Gson().toJson(user));
                    }
                    memberAdapter = new MemberAdapter(getApplicationContext(), userlist);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    rvMembers.setLayoutManager(linearLayoutManager);
                    rvMembers.setAdapter(memberAdapter);
                    memberAdapter.setOnItemCheckedListener(new MemberAdapter.OnCheckedListener() {
                        @Override
                        public void onItemClick(User user, boolean checked) {
                            if (checked)
                                taskMemberList.add(user);
                            else
                                taskMemberList.remove(getUserIndex(user.getObjectId(), taskMemberList));
                        }
                    });

                } else {
                    // Something went wrong.
                }
            }
        });
    }

    private int getUserIndex(String objectId, List<User> taskMemberList) {
        int index = 0;
        for (User item : taskMemberList) {
            if (item.getObjectId().equalsIgnoreCase(objectId))
                break;
            else
                index++;
        }
        return index;
    }
}
