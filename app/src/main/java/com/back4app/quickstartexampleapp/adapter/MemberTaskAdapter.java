package com.back4app.quickstartexampleapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.back4app.quickstartexampleapp.R;
import com.back4app.quickstartexampleapp.Task;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class MemberTaskAdapter extends RecyclerView.Adapter<MemberTaskAdapter.ViewHolder> {

    private Context context;
    private List<Task> taskList;
    private ParseUser parseUser;

    public MemberTaskAdapter(Context context, List<Task> taskList, ParseUser parseUser) {
        this.context = context;
        this.taskList = taskList;
        this.parseUser = parseUser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rl_member_task_list_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Task task = taskList.get(position);
        holder.tvTask.setText(task.getTask());
        holder.cbCompltedTask.setChecked(task.isCompleted());
        holder.cbCompltedTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, final boolean b) {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Task");
                query.getInBackground(task.getObjectId(), new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            object.put("isCompleted", b);
                           // task.setCompleted(b);
                            object.saveInBackground();
                            //notifyDataSetChanged();
                            if (b)
                                Toast.makeText(context, "task tamamlandı", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTask;
        CheckBox cbCompltedTask;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTask = itemView.findViewById(R.id.tvTask);
            cbCompltedTask = itemView.findViewById(R.id.cbCompltedTask);

        }
    }
}
