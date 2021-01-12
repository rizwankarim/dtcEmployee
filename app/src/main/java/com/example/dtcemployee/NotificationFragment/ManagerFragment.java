package com.example.dtcemployee.NotificationFragment;

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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dtcemployee.Adapter.GetAllManagerNotificationAdatper;
import com.example.dtcemployee.Models.GetAllManagerNotification.GetAllManagerNotification;
import com.example.dtcemployee.Models.GetAllManagerNotification.Notification;
import com.example.dtcemployee.Models.GetSubEmployeeAllNotification.GetSubEmployeeAllNotification;
import com.example.dtcemployee.R;
import com.example.dtcemployee.RetrofitClient.RetrofitClientClass;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ManagerFragment extends Fragment {
    RecyclerView ManagerNotiRecylerView;
    String employee_id;
    List<Notification> notificationList = new ArrayList<>();
    LinearLayout noData_layout;
    AlertDialog loadingDialog;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Paper.init(requireContext());
        ManagerNotiRecylerView = view.findViewById(R.id.ManagerNotiRecylerView);
        ManagerNotiRecylerView.setHasFixedSize(true);
        ManagerNotiRecylerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        noData_layout = view.findViewById(R.id.noData_layout);
        employee_id = Paper.book().read("user_id");

    }


    @Override
    public void onResume() {
        super.onResume();
        getData(employee_id);
    }

    private void getData(String employee_id) {
        showLoadingDialog();

        Call<GetAllManagerNotification> call = RetrofitClientClass.getInstance()
                .getInterfaceInstance().GET_ALL_MANAGER_NOTIFICATION_CALL(employee_id);
        call.enqueue(new Callback<GetAllManagerNotification>() {
            @Override
            public void onResponse(Call<GetAllManagerNotification> call, Response<GetAllManagerNotification> response) {
                if(response.code() == 200){
                    hideLoadingDialog();
                    noData_layout.setVisibility(View.GONE);
                    ManagerNotiRecylerView.setVisibility(View.VISIBLE);
                    notificationList = response.body().getNotifications();
                    GetAllManagerNotificationAdatper getAllManagerNotificationAdatper = new GetAllManagerNotificationAdatper(requireContext(),notificationList);
                    ManagerNotiRecylerView.setAdapter(getAllManagerNotificationAdatper);

                }
                else if(response.code() == 404){
                    hideLoadingDialog();
                    noData_layout.setVisibility(View.VISIBLE);
                    ManagerNotiRecylerView.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                }
                else if(response.code() == 400){
                    hideLoadingDialog();
                    noData_layout.setVisibility(View.VISIBLE);
                    ManagerNotiRecylerView.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetAllManagerNotification> call, Throwable t) {
                hideLoadingDialog();
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
