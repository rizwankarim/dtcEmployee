package com.example.dtcemployee.HomeFragments;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtcemployee.Adapter.EmployeeAttendenceAdapter;
import com.example.dtcemployee.Models.EmployeeAttendence.EmployeeAttenndence;
import com.example.dtcemployee.Models.EmployeeAttendence.EmployeeCheckOutTime;
import com.example.dtcemployee.R;
import com.example.dtcemployee.RetrofitClient.RetrofitClientClass;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovementsFragment extends Fragment {
    RecyclerView RecylerViewAttendence;
    ImageView ic_empty;
    TextView textnodata;
    ImageButton refresh;
    AlertDialog loadingDialog;
    LinearLayout recy_layout,empty_layout;
    String id;
    List<EmployeeCheckOutTime> employeeCheckOutTimeList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movements, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ic_empty = view.findViewById(R.id.ic_empty);
        textnodata = view.findViewById(R.id.textnodata);
        recy_layout = view.findViewById(R.id.recy_layout);
        refresh= view.findViewById(R.id.refresh);
        empty_layout = view.findViewById(R.id.empty_layout);
        Paper.init(requireContext());
        id = Paper.book().read("user_id");

        RecylerViewAttendence = view.findViewById(R.id.RecylerViewAttendence);
        RecylerViewAttendence.setHasFixedSize(false);
        RecylerViewAttendence.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkConnection())
                {
                    getEmployeeAttendence(id);
                }else
                {
                    Toast.makeText(getActivity(), "Internet Not Available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(checkConnection())
        {
            getEmployeeAttendence(id);
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
    private void getEmployeeAttendence(String id) {
        showLoadingDialog();

        Call<EmployeeAttenndence> call = RetrofitClientClass.getInstance().getInterfaceInstance().Employeeattendance(id);
        call.enqueue(new Callback<EmployeeAttenndence>() {
            @Override
            public void onResponse(Call<EmployeeAttenndence> call, Response<EmployeeAttenndence> response) {
                if(response.code() == 200){
                    hideLoadingDialog();
                    textnodata.setVisibility(View.GONE);
                    employeeCheckOutTimeList = response.body().getEmployeeCheckOutTime();
                    EmployeeAttendenceAdapter employeeAttendenceAdapter = new EmployeeAttendenceAdapter(requireContext(),employeeCheckOutTimeList);
                    RecylerViewAttendence.setAdapter(employeeAttendenceAdapter);

                }
                else if(response.code() ==400){
                    hideLoadingDialog();


                    textnodata.setVisibility(View.VISIBLE);
                    RecylerViewAttendence.setVisibility(View.GONE);


                }
                else if(response.code() == 404){
                    hideLoadingDialog();

                    textnodata.setVisibility(View.VISIBLE);
                    RecylerViewAttendence.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<EmployeeAttenndence> call, Throwable t) {
                hideLoadingDialog();

                RecylerViewAttendence.setVisibility(View.GONE);

                textnodata.setVisibility(View.VISIBLE);
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