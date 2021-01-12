package com.example.dtcemployee.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dtcemployee.Activities.CreateVacationRequestActivity;
import com.example.dtcemployee.Interface.DeleteVaction;
import com.example.dtcemployee.Models.GetAllVcation.EmployeeVacation;
import com.example.dtcemployee.Models.ModelClass;
import com.example.dtcemployee.R;

import java.util.ArrayList;
import java.util.List;

public class VacationListAdapter extends RecyclerView.Adapter<VacationListAdapter.ViewHolder> {

    Context context;
    List<EmployeeVacation> employeeVacationList;
    DeleteVaction deleteVaction;

    public VacationListAdapter(Context context, List<EmployeeVacation> employeeVacationList, DeleteVaction deleteVaction) {
        this.context = context;
        this.employeeVacationList = employeeVacationList;
        this.deleteVaction = deleteVaction;
    }

    @NonNull
    @Override
    public VacationListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.vacations_recycler_layout,parent,false);
        return new VacationListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VacationListAdapter.ViewHolder holder, int position) {
        final EmployeeVacation employeeVacation = employeeVacationList.get(position);
        holder.txt_date.setText(employeeVacation.getBeginningDate());
        holder.textView.setText(employeeVacation.getReason());
        if(employeeVacation.getStatus().equals("pending")){
            holder.Status.setText(employeeVacation.getStatus());
            holder.Status.setTextColor(Color.parseColor("#f5b625"));
        }
        else if(employeeVacation.getStatus().equals("Accepted")){
            holder.Status.setText(employeeVacation.getStatus());
            holder.Status.setTextColor(Color.parseColor("#1fbf31"));
        }
        else if(employeeVacation.getStatus().equals("Rejected")) {
            holder.Status.setText(employeeVacation.getStatus());
            holder.Status.setTextColor(Color.parseColor("#eb0c0c"));
        }
        holder.Status.setText(employeeVacation.getStatus());

        holder.employee_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setMessage("What do you Want to Do?");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder dialog1 = new AlertDialog.Builder(context);
                        dialog1.setMessage("Are you want to remove ths Vacation?");
                        dialog1.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                deleteVaction.DeleteVaction(employeeVacation.getId());
                            }

                        });
                        dialog1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        dialog1.show();
                    }

                });

                dialog.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(context, CreateVacationRequestActivity.class);
                        intent.putExtra("orign", "EditVacation");
                        intent.putExtra("id", employeeVacation.getId());
                        context.startActivity(intent);
                    }
                });
                dialog.show();
                dialog.setNeutralButton("View", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

//                        Toast.makeText(context, "Ok Ok view", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, CreateVacationRequestActivity.class);
                        intent.putExtra("orign", "ViewVacation");
                        intent.putExtra("id", employeeVacation.getId());
                        context.startActivity(intent);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }

        });
    }

    @Override
    public int getItemCount() {
        return employeeVacationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView,txt_date,Status;
        LinearLayout employee_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.subject);
            txt_date = itemView.findViewById(R.id.txt_date);
            Status=itemView.findViewById(R.id.status);
            employee_layout = itemView.findViewById(R.id.employee_layout);
        }
    }
}
