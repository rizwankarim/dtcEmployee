package com.example.dtcemployee.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dtcemployee.Models.GetAllManagerNotification.Notification;
import com.example.dtcemployee.R;

import java.util.List;

public class GetAllManagerNotificationAdatper extends RecyclerView.Adapter<GetAllManagerNotificationAdatper.ViewHolder> {
    Context context;
    List<Notification> notificationList;

    public GetAllManagerNotificationAdatper(Context context, List<Notification> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public GetAllManagerNotificationAdatper.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_notification_layout,parent,false);
        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetAllManagerNotificationAdatper.ViewHolder holder, int position) {
        final Notification notification = notificationList.get(position);
        holder.txtDate.setText(notification.getDate());
        holder.txtEmployee_Name.setText(notification.getManagerName());
        holder.txtTime.setText(notification.getTime());
        holder.txtnotification.setText(notification.getNotifications());
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtEmployee_Name,txtTime,txtDate,txtnotification;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtnotification = itemView.findViewById(R.id.txtnotification);
            txtEmployee_Name = itemView.findViewById(R.id.txtEmployee_Name);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }
}
