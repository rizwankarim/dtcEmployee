package com.example.dtcemployee.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtcemployee.Adapter.FragmentAdapter;
import com.example.dtcemployee.EmployeDetailFragment.AttendenceFragment;
import com.example.dtcemployee.EmployeDetailFragment.DetailFragment;
import com.example.dtcemployee.EmployeDetailFragment.ReportFragment;
import com.example.dtcemployee.Models.SubemployeeDetail.SubEmployeeDetail;
import com.example.dtcemployee.R;
import com.example.dtcemployee.RetrofitClient.RetrofitClientClass;
import com.google.android.material.tabs.TabLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowEmployeeDetailActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_employee_detail);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), ShowEmployeeDetailActivity.this);
        fragmentAdapter.addFrag(new DetailFragment(), "Details");
        fragmentAdapter.addFrag(new AttendenceFragment(), "Attendance");
        fragmentAdapter.addFrag(new ReportFragment(), "Report");

        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }





}
