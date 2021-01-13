package com.example.dtcemployee.HomeFragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerListener;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.example.dtcemployee.Activities.ManagerNoticationActivity;
import com.example.dtcemployee.Activities.ShowEmployeeDetailActivity;
import com.example.dtcemployee.Activities.SubEmployeeNotificationActivity;
import com.example.dtcemployee.Adapter.FragmentAdapter;
import com.example.dtcemployee.Adapter.SubEmployeeAdapter;
import com.example.dtcemployee.EmployeDetailFragment.AttendenceFragment;
import com.example.dtcemployee.EmployeDetailFragment.DetailFragment;
import com.example.dtcemployee.EmployeDetailFragment.ReportFragment;
import com.example.dtcemployee.Models.GetEmployeeSubEmployee.AllSubEmployee;
import com.example.dtcemployee.Models.GetEmployeeSubEmployee.GetemployeeSubEmployee;
import com.example.dtcemployee.NotificationFragment.ManagerFragment;
import com.example.dtcemployee.NotificationFragment.SubEmployeeFragment;
import com.example.dtcemployee.R;
import com.example.dtcemployee.RetrofitClient.RetrofitClientClass;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsFragment extends Fragment {
    EditText textNoti;
    android.app.AlertDialog loadingDialog;
    MultiSpinnerSearch employeeListSpinner;
    Button create_employee_btn;
    String emp_id;
    List<AllSubEmployee> allSubEmployeeList = new ArrayList<>();
    List<String> employeeList = new ArrayList<>();

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        create_employee_btn = view.findViewById(R.id.create_employee_btn);
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);

        FragmentAdapter fragmentAdapter = new FragmentAdapter(getChildFragmentManager(), requireContext());
        fragmentAdapter.addFrag(new SubEmployeeFragment(), "Employee");
        fragmentAdapter.addFrag(new ManagerFragment(), "Manager");


        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);

        create_employee_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder dialog = new androidx.appcompat.app.AlertDialog.Builder(requireContext());
                dialog.setMessage("Send Notification:");
                dialog.setPositiveButton("Sub Employee", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                       startActivity(new Intent(requireContext(),SubEmployeeNotificationActivity.class));

                    }
                });
                dialog.setNegativeButton("Manager", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(new Intent(requireContext(), ManagerNoticationActivity.class));

                    }
                });

                dialog.show();
            }
        });
    }

    public void showLoadingDialog() {
        loadingDialog = new AlertDialog.Builder(requireContext()).create();
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.loading_dailoug, null, false);
        loadingDialog.setView(view);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingDialog.show();

    }

    public void hideLoadingDialog() {
        loadingDialog.dismiss();
    }
}