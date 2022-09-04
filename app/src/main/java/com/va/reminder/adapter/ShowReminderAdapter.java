package com.va.reminder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.va.reminder.R;
import com.va.reminder.model.Reminders;

import java.util.ArrayList;
import java.util.List;

public class ShowReminderAdapter extends RecyclerView.Adapter<ShowReminderAdapter.viewHolder> {

    private Context mContext;
    private List<Reminders> mReminderList = new ArrayList<>();
    private String userID;

    public ShowReminderAdapter(Context mContext, List<Reminders> mReminderList, String userID) {
        this.mContext = mContext;
        this.mReminderList = mReminderList;
        this.userID = userID;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.reminder_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Reminders food = mReminderList.get(position);
        holder.tv_content.setText(food.getContent());
        holder.tv_time.setText(food.getTime());

    }

    @Override
    public int getItemCount() {
        return mReminderList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {

        private TextView tv_content;
        private TextView tv_time;
        private LinearLayout ll_reminderLayout;
        private CheckBox ch_check;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_time = itemView.findViewById(R.id.tv_time);
            ll_reminderLayout = itemView.findViewById(R.id.ll_reminderLayout);
            ch_check = itemView.findViewById(R.id.ch_check);
        }
    }

}
