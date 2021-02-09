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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtcemployee.Adapter.SubEmployeeAdapter;
import com.example.dtcemployee.Models.GetEmployeeSubEmployee.AllSubEmployee;
import com.example.dtcemployee.Models.GetEmployeeSubEmployee.GetemployeeSubEmployee;
import com.example.dtcemployee.R;
import com.example.dtcemployee.RetrofitClient.RetrofitClientClass;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeFragment extends Fragment {
    RecyclerView recyclerViewSubEmployees;
    ProgressBar progressBar1;
    String manager_id;
    ImageButton refresh;
    AlertDialog loadingDialog;
    List<AllSubEmployee> allSubEmployeeList = new ArrayList<>();
    String emp_id;
    TextView textnodata;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Paper.init(requireContext());
        emp_id = Paper.book().read("user_id");
        textnodata = view.findViewById(R.id.textnodata);
        refresh= view.findViewById(R.id.refresh);
        recyclerViewSubEmployees = view.findViewById(R.id.recyclerViewSubEmployees);
        recyclerViewSubEmployees.setHasFixedSize(true);
        recyclerViewSubEmployees.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkConnection())
                {
                    GetallSubEmployee(emp_id);

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
            GetallSubEmployee(emp_id);

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
    private void GetallSubEmployee(String emp_id) {
        showLoadingDialog();
        Call<GetemployeeSubEmployee> call = RetrofitClientClass.getInstance().getInterfaceInstance().getEmployeeSubEmploye(emp_id);
        call.enqueue(new Callback<GetemployeeSubEmployee>() {
            @Override
            public void onResponse(Call<GetemployeeSubEmployee> call, Response<GetemployeeSubEmployee> response) {
                if(response.code() == 200) {hideLoadingDialog();
                    allSubEmployeeList = response.body().getAllSubEmployees();
                    SubEmployeeAdapter subEmployeeAdapter = new SubEmployeeAdapter(requireContext(),allSubEmployeeList);
                    recyclerViewSubEmployees.setAdapter(subEmployeeAdapter);
                }
                else if(response.code() == 400) {
                    hideLoadingDialog();
                    textnodata.setVisibility(View.VISIBLE);
                    recyclerViewSubEmployees.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<GetemployeeSubEmployee> call, Throwable t) {
                hideLoadingDialog();
                textnodata.setVisibility(View.VISIBLE);
                recyclerViewSubEmployees.setVisibility(View.GONE);
                Toast.makeText(requireContext(), "ef "+t.getMessage(), Toast.LENGTH_SHORT).show();

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