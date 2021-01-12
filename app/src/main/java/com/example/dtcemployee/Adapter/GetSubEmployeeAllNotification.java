package com.example.dtcemployee.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dtcemployee.HomeFragments.NotificationsFragment;
import com.example.dtcemployee.Models.GetSubEmployeeAllNotification.Notification;
import com.example.dtcemployee.R;

import java.util.List;

public class GetSubEmployeeAllNotification extends RecyclerView.Adapter<GetSubEmployeeAllNotification.ViewHolder> {

    Context context;
    List<Notification> notificationList;

    public GetSubEmployeeAllNotification(Context context, List<Notification> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public GetSubEmployeeAllNotification.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_notification_layout,parent,false);
        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetSubEmployeeAllNotification.ViewHolder holder, int position) {


        final Notification notification = notificationList.get(position);
        holder.txtDate.setText(notification.getDate());
        holder.txtEmployee_Name.setText(notification.getSubEmployeeName());
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
