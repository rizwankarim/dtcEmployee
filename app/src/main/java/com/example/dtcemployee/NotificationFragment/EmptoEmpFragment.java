package com.example.dtcemployee.NotificationFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dtcemployee.Adapter.EmptoEmpNotificationAdapter;
import com.example.dtcemployee.Models.GetAllEmployeesNotification.EmpNotification;
import com.example.dtcemployee.Models.GetAllEmployeesNotification.GetAllEmployeesNotification;
import com.example.dtcemployee.Models.GetSubEmployeeAllNotification.GetSubEmployeeAllNotification;
import com.example.dtcemployee.Models.GetSubEmployeeAllNotification.Notification;
import com.example.dtcemployee.R;
import com.example.dtcemployee.RetrofitClient.RetrofitClientClass;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmptoEmpFragment extends Fragment {
    RecyclerView EmptoEmprecyclerview;
    String employee_id;
    String manager_id;
    List<EmpNotification> notificationList = new ArrayList<>();
    LinearLayout noData_layout;
    AlertDialog loadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_empto_emp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Paper.init(requireContext());
        EmptoEmprecyclerview = view.findViewById(R.id.emptoemprecyclerview);
        EmptoEmprecyclerview.setHasFixedSize(true);
        EmptoEmprecyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));
        noData_layout = view.findViewById(R.id.noData_layout);
        employee_id = Paper.book().read("user_id");
        manager_id=Paper.book().read("manager_id");
    }

    private void getData(String employee_id) {
        showLoadingDialog();

        Call<GetAllEmployeesNotification> call = RetrofitClientClass.getInstance()
                .getInterfaceInstance().getAllEmployeesnNotifications(manager_id,employee_id);
        call.enqueue(new Callback<GetAllEmployeesNotification>() {
            @Override
            public void onResponse(Call<GetAllEmployeesNotification> call, Response<GetAllEmployeesNotification> response) {
                if(response.code() == 200){
                    hideLoadingDialog();

                    noData_layout.setVisibility(View.GONE);
                    EmptoEmprecyclerview.setVisibility(View.VISIBLE);
                    notificationList = response.body().getNotifications();
                    EmptoEmpNotificationAdapter getAllNotifactiondapter = new EmptoEmpNotificationAdapter(requireContext(),notificationList);
                    EmptoEmprecyclerview.setAdapter(getAllNotifactiondapter);

                }
                else if(response.code() == 404){
                    hideLoadingDialog();
                    noData_layout.setVisibility(View.VISIBLE);
                    EmptoEmprecyclerview.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                }
                else if(response.code() == 400){
                    hideLoadingDialog();
                    noData_layout.setVisibility(View.VISIBLE);
                    EmptoEmprecyclerview.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetAllEmployeesNotification> call, Throwable t) {
                hideLoadingDialog();
                Toast.makeText(requireContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(checkConnection())
        {
            getData(employee_id);

        }else
        {
            Toast.makeText(getActivity(), "Internet Not Available", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean checkConnection(){
        ConnectivityManager connectivityManager=(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        return networkInfo !=null && networkInfo.isConnected();
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