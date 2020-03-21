package com.back4app.quickstartexampleapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.back4app.quickstartexampleapp.R;
import com.back4app.quickstartexampleapp.User;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    private static final String TAG = MemberAdapter.class.getSimpleName();
    private Context context;
    private List<User> userList;
    private OnCheckedListener listener;

    public MemberAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rl_member_list_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User user = userList.get(position);
        holder.tvMemberName.setText(user.getName() + " " + user.getSurname());

        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d(TAG, "onCheckedChanged: " + b + " " + user.getSurname());
                listener.onItemClick(user,b);
            }
        });
    }

    public interface OnCheckedListener {
        void onItemClick(User user,boolean checked);
    }

    public void setOnItemCheckedListener(OnCheckedListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox cb;
        TextView tvMemberName;

        public ViewHolder(View itemView) {
            super(itemView);
            cb = itemView.findViewById(R.id.cb);
            tvMemberName = itemView.findViewById(R.id.tvMemberName);
        }
    }
}
