package com.example.dtcemployee.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.dtcemployee.Models.DailyReportDeatil.ReportImage;
import com.example.dtcemployee.R;

import java.util.List;

public class DailyReportDetailAdapter extends RecyclerView.Adapter<DailyReportDetailAdapter.ViewHolder> {

    Context context;
    List<ReportImage> reportImageList;

    public DailyReportDetailAdapter(Context context, List<ReportImage> reportImageList) {
        this.context = context;
        this.reportImageList = reportImageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_daily_report_layout,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ReportImage reportImage = reportImageList.get(position);
        String image = "http://dtc.anstm.com/dtcAdmin/api/Employee/Report_Image/"+reportImage.getImage();

        holder.txtidFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent browsefile = new Intent(Intent.ACTION_VIEW, Uri.parse(image));
                    browsefile.putExtra("orign", image);
                    context.startActivity(browsefile);
                }
                catch (Exception e){
                    Toast.makeText(context, "Something goes wrong", Toast.LENGTH_SHORT).show();
                }
                //Intent intent = new Intent(context, ViewerActivity.class);


            }
        });

    }

    @Override
    public int getItemCount() {
        return reportImageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        Button txtidFile;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtidFile = itemView.findViewById(R.id.txtidFile);
        }
    }
}
