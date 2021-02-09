package com.example.dtcemployee.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dtcemployee.Activities.ReportDetailActivity;
import com.example.dtcemployee.Models.EmployeeDailyReportModel.EmployeeDailyReport;
import com.example.dtcemployee.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public  class EmployeeReportsAdapter extends RecyclerView.Adapter<EmployeeReportsAdapter.ViewHolder> {
    Context context;
    List<EmployeeDailyReport> dailyReports;

    public EmployeeReportsAdapter(Context context, List<EmployeeDailyReport> dailyReports) {
        this.context = context;
        this.dailyReports = dailyReports;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_emloyee_report_layout,parent,false);
        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        EmployeeDailyReport dailyReport = dailyReports.get(position);
        holder.txtTarget.setText(dailyReport.getTarget());
        holder.txtAchievement.setText(dailyReport.getAchievement());
        holder.txtProblems.setText(dailyReport.getProblems());
        holder.txtDateTime.setText(dailyReport.getDateTime());
        if(dailyReport.getReportImage().size() >0){
        String image = "http://dtc.anstm.com/dtcAdmin/api/Employee/Report_Image/"+dailyReport.getReportImage().get(0).getImage();
       //String image ="http://test.proglabs.org/BabyRon/api/Babysitter/Babysitter_Profile_Image/" + babySitter.getProfileImage();
        if (dailyReport.getReportImage() != null) {
//            Picasso.get().load("http://test.proglabs.org/DTC/api/Employee/Report_Image/".concat(dailyReport.getReportImage()))
//                    .placeholder(R.drawable.place_holder).error(R.drawable.place_holder).into(holder.imgReport);
//        }


            Picasso.get().load(image).fit().centerCrop().into(holder.imgReport, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    if (holder.progressbar != null) {
                        holder.progressbar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReportDetailActivity.class);
                intent.putExtra("id", dailyReport.getId());
                context.startActivity(intent);
            }
        });


    }
    @Override
    public int getItemCount() {
        return dailyReports.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTarget,txtAchievement,txtProblems,txtDateTime;
        ImageView imgReport;
        ProgressBar progressbar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            txtTarget = itemView.findViewById(R.id.txtTarget);
            txtAchievement = itemView.findViewById(R.id.txtAchievement);
            txtProblems = itemView.findViewById(R.id.txtProblems);
            txtDateTime = itemView.findViewById(R.id.txtDateTime);
            imgReport = itemView.findViewById(R.id.imgReport);
            progressbar = itemView.findViewById(R.id.progressbar);



        }
    }
}
