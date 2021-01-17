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

public class SubEmployeeFragment extends Fragment {
    RecyclerView SubEmpNotiRecylerView;
    String employee_id;
    List<Notification> notificationList = new ArrayList<>();
    LinearLayout noData_layout;
    AlertDialog loadingDialog;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sub_employee, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Paper.init(requireContext());
        SubEmpNotiRecylerView = view.findViewById(R.id.SubEmpNotiRecylerView);
        SubEmpNotiRecylerView.setHasFixedSize(true);
        SubEmpNotiRecylerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        noData_layout = view.findViewById(R.id.noData_layout);
        employee_id = Paper.book().read("user_id");


    }

    private void getData(String employee_id) {
        showLoadingDialog();

        Call<GetSubEmployeeAllNotification> call = RetrofitClientClass.getInstance()
                .getInterfaceInstance().GET_SUB_EMPLOYEE_ALL_NOTIFICATION_CALL(employee_id);
        call.enqueue(new Callback<GetSubEmployeeAllNotification>() {
            @Override
            public void onResponse(Call<GetSubEmployeeAllNotification> call, Response<GetSubEmployeeAllNotification> response) {
                if(response.code() == 200){
                hideLoadingDialog();
                    noData_layout.setVisibility(View.GONE);
                    SubEmpNotiRecylerView.setVisibility(View.VISIBLE);
                    notificationList = response.body().getNotifications();
                    com.example.dtcemployee.Adapter.GetSubEmployeeAllNotification getAllNotifactiondapter = new com.example.dtcemployee.Adapter.GetSubEmployeeAllNotification(requireContext(),notificationList);
                    SubEmpNotiRecylerView.setAdapter(getAllNotifactiondapter);

                }
                else if(response.code() == 404){
                    hideLoadingDialog();
                    noData_layout.setVisibility(View.VISIBLE);
                    SubEmpNotiRecylerView.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                }
                else if(response.code() == 400){
                    hideLoadingDialog();
                    noData_layout.setVisibility(View.VISIBLE);
                    SubEmpNotiRecylerView.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetSubEmployeeAllNotification> call, Throwable t) {
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