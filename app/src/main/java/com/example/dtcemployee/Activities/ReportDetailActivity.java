package com.example.dtcemployee.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dtcemployee.Adapter.DailyReportDetailAdapter;
import com.example.dtcemployee.Models.DailyReportDeatil.DailyReportDetail;
import com.example.dtcemployee.Models.DailyReportDeatil.ReportImage;
import com.example.dtcemployee.R;
import com.example.dtcemployee.RetrofitClient.RetrofitClientClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportDetailActivity extends AppCompatActivity {

    String id;
    Toolbar ChildProfiletoolbar;
    TextView txtTarget, txtEmployeeId, txtProjectId, txtProblems, txtDate, txtTargetAchivement;
    RecyclerView DailyReportRecylerView;
    List<ReportImage> reportImageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);
        id = getIntent().getStringExtra("id");

        initView();
        ClickAble();
        getData(id);
    }


    private void initView() {
        ChildProfiletoolbar = findViewById(R.id.ChildProfiletoolbar);
        ChildProfiletoolbar.setTitle("");
        setSupportActionBar(ChildProfiletoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtTarget = findViewById(R.id.txtTarget);
        txtEmployeeId = findViewById(R.id.txtEmployeeId);
        txtProjectId = findViewById(R.id.txtProjectId);
        txtProblems = findViewById(R.id.txtProblems);
        txtDate = findViewById(R.id.txtDate);

        txtTargetAchivement = findViewById(R.id.txtTargetAchivement);
        DailyReportRecylerView = findViewById(R.id.DailyReportRecylerView);
        DailyReportRecylerView.setHasFixedSize(true);
        DailyReportRecylerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));


    }

    private void ClickAble() {

    }

    private void getData(String id) {

        Call<DailyReportDetail> call = RetrofitClientClass.getInstance().getInterfaceInstance().DailyReportDetail(id);
        call.enqueue(new Callback<DailyReportDetail>() {
            @Override
            public void onResponse(Call<DailyReportDetail> call, Response<DailyReportDetail> response) {
                if (response.code() == 200) {
                    reportImageList = response.body().getReportDetail().get(0).getReportImage();
//                    TextView txtTarget,txtEmployeeId,txtProjectId,txtProblems,txtDate,txtTargetAchivement;
                    txtTarget.setText(response.body().getReportDetail().get(0).getTarget());
                    txtEmployeeId.setText(response.body().getReportDetail().get(0).getEmpId());
                    txtProjectId.setText(response.body().getReportDetail().get(0).getProjectId());
                    txtProblems.setText(response.body().getReportDetail().get(0).getProblems());
                    txtDate.setText(response.body().getReportDetail().get(0).getDateTime());
                    txtTargetAchivement.setText(response.body().getReportDetail().get(0).getTarget());

                    DailyReportDetailAdapter dailyReportDetailAdapter = new DailyReportDetailAdapter(ReportDetailActivity.this, reportImageList);
                    DailyReportRecylerView.setAdapter(dailyReportDetailAdapter);

                } else if (response.code() == 404) {
//                    Toast.makeText(ReportDetailActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DailyReportDetail> call, Throwable t) {
                Toast.makeText(ReportDetailActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}