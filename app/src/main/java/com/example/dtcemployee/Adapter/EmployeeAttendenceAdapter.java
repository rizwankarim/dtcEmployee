package com.example.dtcemployee.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dtcemployee.Models.EmployeeAttendence.EmployeeCheckOutTime;
import com.example.dtcemployee.R;

import org.w3c.dom.Text;

import java.util.List;

public class EmployeeAttendenceAdapter extends RecyclerView.Adapter<EmployeeAttendenceAdapter.ViewHolder> {
    Context context;
    List<EmployeeCheckOutTime> employeeCheckOutTimeList;

    public EmployeeAttendenceAdapter(Context context, List<EmployeeCheckOutTime> employeeCheckOutTimeList) {
        this.context = context;
        this.employeeCheckOutTimeList = employeeCheckOutTimeList;
    }

    @NonNull
    @Override
    public EmployeeAttendenceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_attendance_layout,parent,false);
        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeAttendenceAdapter.ViewHolder holder, int position) {
        final EmployeeCheckOutTime employeeCheckOutTime = employeeCheckOutTimeList.get(position);
        holder.txtEmployeeName.setText(employeeCheckOutTime.getEmployeeName());
        holder.txtCheckIn.setText(employeeCheckOutTime.getCheckInTime());
        holder.txtCheckout.setText(employeeCheckOutTime.getCheckOutTime());

    }

    @Override
    public int getItemCount() {
        return employeeCheckOutTimeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtEmployeeName,txtCheckIn,txtCheckout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtEmployeeName = itemView.findViewById(R.id.txtEmployeeName);
            txtCheckIn = itemView.findViewById(R.id.txtCheckIn);
            txtCheckout = itemView.findViewById(R.id.txtCheckout);
        }
    }
}
