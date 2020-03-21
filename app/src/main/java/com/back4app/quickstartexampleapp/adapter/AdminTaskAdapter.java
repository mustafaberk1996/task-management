package com.back4app.quickstartexampleapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.back4app.quickstartexampleapp.R;
import com.back4app.quickstartexampleapp.modal.Task;

import java.util.List;

public class AdminTaskAdapter extends RecyclerView.Adapter<AdminTaskAdapter.ViewHolder> {

    private Context context;
    private List<Task> taskList;

    public AdminTaskAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rl_admin_task_list_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.tvTask.setText(task.getTask());
        holder.tvMemberName.setText(task.getMemberName());
        if (task.isCompleted())
            holder.imgIsCompleted.setImageResource(R.drawable.ic_check_completed);
        else
            holder.imgIsCompleted.setImageResource(R.drawable.ic_check_waiting);

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTask, tvMemberName;
        private ImageView imgIsCompleted;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTask = itemView.findViewById(R.id.tvTask);
            tvMemberName = itemView.findViewById(R.id.tvMemberName);
            imgIsCompleted = itemView.findViewById(R.id.imgIsCompleted);

        }
    }
}
