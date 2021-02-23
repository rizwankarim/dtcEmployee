package com.example.dtcemployee.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dtcemployee.Activities.ShowEmployeeDetailActivity;
import com.example.dtcemployee.Common.Common;
import com.example.dtcemployee.Interface.ShowDetail;
import com.example.dtcemployee.Models.GetEmployeeSubEmployee.AllSubEmployee;
import com.example.dtcemployee.R;

import java.util.List;

public class SubEmployeeAdapter extends RecyclerView.Adapter<SubEmployeeAdapter.ViewHolder> {
    Context context;
    List<AllSubEmployee> allSubEmployeeList;


    public SubEmployeeAdapter(Context context, List<AllSubEmployee> allSubEmployeeList) {
        this.context = context;
        this.allSubEmployeeList = allSubEmployeeList;

    }

    @NonNull
    @Override
    public SubEmployeeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_subemployee_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubEmployeeAdapter.ViewHolder holder, int position) {
        final AllSubEmployee allSubEmployee = allSubEmployeeList.get(position);
        holder.txtEmployeeName.setText(allSubEmployee.getSubEmployeeName());

        holder.sub_employee_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setMessage("What do you Want to Do?");
                    dialog.setPositiveButton("Detail", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try{
                                Intent intent = new Intent(context, ShowEmployeeDetailActivity.class);
                                intent.putExtra("orign", "EditVehicle");

                                Common.employeeId = allSubEmployee.getSubEmpId();
                                context.startActivity(intent);
                            }
                            catch (Exception e){
                                Toast.makeText(context, "Something goes wrong", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
                catch (Exception e){
                    Toast.makeText(context, "Something goes wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return allSubEmployeeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtEmployeeName;
        LinearLayout sub_employee_Layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtEmployeeName = itemView.findViewById(R.id.txtEmployeeName);
            sub_employee_Layout = itemView.findViewById(R.id.sub_employee_Layout);
        }
    }
}
