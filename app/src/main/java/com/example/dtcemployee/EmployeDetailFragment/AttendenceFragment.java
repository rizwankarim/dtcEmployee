package com.example.dtcemployee.EmployeDetailFragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtcemployee.Adapter.EmployeeAttendenceAdapter;
import com.example.dtcemployee.Common.Common;
import com.example.dtcemployee.Models.EmployeeAttendence.EmployeeAttenndence;
import com.example.dtcemployee.Models.EmployeeAttendence.EmployeeCheckOutTime;
import com.example.dtcemployee.R;
import com.example.dtcemployee.RetrofitClient.RetrofitClientClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AttendenceFragment extends Fragment {
    ImageView ic_empty;
    TextView txtnoRecord;
    LinearLayout empty_layout;
    RecyclerView AttendanceSubEmployeeRecylerView;
    String id;
    AlertDialog loadingDialog;
    List<EmployeeCheckOutTime> employeeCheckOutTimeList = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attendence, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ic_empty = view.findViewById(R.id.ic_empty);
        txtnoRecord = view.findViewById(R.id.txtnoRecord);
        empty_layout = view.findViewById(R.id.empty_layout);
        id = Common.employeeId;
        AttendanceSubEmployeeRecylerView = view.findViewById(R.id.AttendanceSubEmployeeRecylerView);
        AttendanceSubEmployeeRecylerView.setHasFixedSize(false);
        AttendanceSubEmployeeRecylerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));


        getEmployeeAttendence(id);
    }

    private void getEmployeeAttendence(String id) {

        showLoadingDialog();

        Call<EmployeeAttenndence> call = RetrofitClientClass.getInstance().getInterfaceInstance().Employeeattendance(id);
        call.enqueue(new Callback<EmployeeAttenndence>() {
            @Override
            public void onResponse(Call<EmployeeAttenndence> call, Response<EmployeeAttenndence> response) {
                if(response.code() == 200){
                    hideLoadingDialog();
                    empty_layout.setVisibility(View.GONE);
                    employeeCheckOutTimeList = response.body().getEmployeeCheckOutTime();
                   if(employeeCheckOutTimeList.size() == 0){
                       empty_layout.setVisibility(View.VISIBLE);
                   }
                   else if(employeeCheckOutTimeList.size() >0){
                    EmployeeAttendenceAdapter employeeAttendenceAdapter = new EmployeeAttendenceAdapter(requireContext(),employeeCheckOutTimeList);
                    AttendanceSubEmployeeRecylerView.setAdapter(employeeAttendenceAdapter);
                   }
                }
                else if(response.code() ==400){
                    hideLoadingDialog();
                    empty_layout.setVisibility(View.VISIBLE);
                    AttendanceSubEmployeeRecylerView.setVisibility(View.GONE);
                }
                else if(response.code() == 404){
                    hideLoadingDialog();
                    empty_layout.setVisibility(View.VISIBLE);
                    AttendanceSubEmployeeRecylerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<EmployeeAttenndence> call, Throwable t) {
                hideLoadingDialog();
                empty_layout.setVisibility(View.VISIBLE);
                AttendanceSubEmployeeRecylerView.setVisibility(View.GONE);

                Toast.makeText(requireContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();

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