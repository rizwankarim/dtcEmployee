package com.example.dtcemployee.EmployeDetailFragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtcemployee.Activities.ShowEmployeeDetailActivity;
import com.example.dtcemployee.Common.Common;
import com.example.dtcemployee.Models.SubemployeeDetail.SubEmployeeDetail;
import com.example.dtcemployee.R;
import com.example.dtcemployee.RetrofitClient.RetrofitClientClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailFragment extends Fragment {
    String id;
    Toolbar ChildProfiletoolbar;
    TextView txtusername,txtposition,txtallowed,txtemployeeId,txtjoiningDate,txtPassportNumber,txtPassportEnddate,txtPassword,txtUniqueId;
    AlertDialog loadingDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ChildProfiletoolbar = view.findViewById(R.id.ChildProfiletoolbar);
        ChildProfiletoolbar.setTitle("");

        txtusername = view.findViewById(R.id.txtusername);
        txtposition = view.findViewById(R.id.txtposition);
        txtallowed = view.findViewById(R.id.txtallowed);
        txtemployeeId = view.findViewById(R.id.txtemployeeId);
        txtjoiningDate = view.findViewById(R.id.txtjoiningDate);
        txtPassportNumber = view.findViewById(R.id.txtPassportNumber);
        txtPassportEnddate = view.findViewById(R.id.txtPassportEnddate);
        txtPassword = view.findViewById(R.id.txtPassword);
        txtUniqueId = view.findViewById(R.id.txtUniqueId);

        id = Common.employeeId;
        EmployeeDetail(id);
    }

    private void EmployeeDetail(String id) {

        Call<SubEmployeeDetail> call = RetrofitClientClass.getInstance().getInterfaceInstance().SubEmployeeDetail(id);
        call.enqueue(new Callback<SubEmployeeDetail>() {
            @Override
            public void onResponse(Call<SubEmployeeDetail> call, Response<SubEmployeeDetail> response) {
                if(response.code() == 200) {
//                    txtusername,txtposition,txtallowed,txtemployeeId,txtjoiningDate,txtPassportNumber,txtPassportEnddate;
                    if(response.body().getEmployeeDetail().size() > 0) {
                        txtusername.setText(response.body().getEmployeeDetail().get(0).getUserName());
                        txtposition.setText(response.body().getEmployeeDetail().get(0).getPosition());
                        txtemployeeId.setText(response.body().getEmployeeDetail().get(0).getEmployeeId());
                        txtjoiningDate.setText(response.body().getEmployeeDetail().get(0).getJoiningDate());
                        txtPassportNumber.setText(response.body().getEmployeeDetail().get(0).getPassportNo());
                        txtPassportEnddate.setText(response.body().getEmployeeDetail().get(0).getPassportEndDate());
                        txtUniqueId.setText(response.body().getEmployeeDetail().get(0).getId());
                        txtPassword.setText(response.body().getEmployeeDetail().get(0).getPassword());
                        if(response.body().getEmployeeDetail().get(0).getOverTime().equals("0")){
                            txtallowed.setText("Not Allowed");
                        }
                        if(!response.body().getEmployeeDetail().get(0).getOverTime().equals("0")){
                            txtallowed.setText("Allowed");
                        }

                    }
                    else {
                        Toast.makeText(requireContext(), "No Sub Employee Here", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(response.code() == 400) {
                    Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SubEmployeeDetail> call, Throwable t) {
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