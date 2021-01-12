package com.example.dtcemployee.TabFragments;

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
import android.widget.Toast;

import com.example.dtcemployee.Adapter.VacationListAdapter;
import com.example.dtcemployee.Interface.DeleteVaction;
import com.example.dtcemployee.Models.GetAllVcation.EmployeeVacation;
import com.example.dtcemployee.Models.GetAllVcation.GetAllVaction;
import com.example.dtcemployee.Models.RemoveVacation.RemoveVacation;
import com.example.dtcemployee.R;
import com.example.dtcemployee.RetrofitClient.RetrofitClientClass;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Vacation_Home_Fragment extends Fragment {
    RecyclerView VacationRecylerView;
    String emp_id;
    List<EmployeeVacation> employeeVacationList = new ArrayList<>();
    AlertDialog loadingDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vacation__home_, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Paper.init(requireContext());
        emp_id = Paper.book().read("user_id");

        VacationRecylerView = view.findViewById(R.id.VacationRecylerView);
        VacationRecylerView.setHasFixedSize(false);
        VacationRecylerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL,false));

    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        Call<GetAllVaction> call = RetrofitClientClass.getInstance().getInterfaceInstance().GetAllVaction(emp_id);
        call.enqueue(new Callback<GetAllVaction>() {
            @Override
            public void onResponse(Call<GetAllVaction> call, Response<GetAllVaction> response) {
                if (response.code() == 200) {
                    employeeVacationList = response.body().getEmployeeVacations();
                    VacationListAdapter vacationListAdapter = new VacationListAdapter(getActivity(), employeeVacationList, new DeleteVaction() {
                        @Override
                        public void DeleteVaction(String id) {
                            RemoveVaction(id);

                        }
                    });
                    VacationRecylerView.setAdapter(vacationListAdapter);
                } else if (response.code() == 400) {
//                    Toast.makeText(requireContext(), "SomeThing Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetAllVaction> call, Throwable t) {
                Toast.makeText(requireContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void RemoveVaction(String id)
    {
        showLoadingDialog();
        Call<RemoveVacation> call = RetrofitClientClass.getInstance().getInterfaceInstance().RemoveVacation(id);
        call.enqueue(new Callback<RemoveVacation>() {
            @Override
            public void onResponse(Call<RemoveVacation> call, Response<RemoveVacation> response) {
                if(response.code() == 200){
                    hideLoadingDialog();
                    getData();
                    Toast.makeText(requireContext(), "Vacation Delete successfully", Toast.LENGTH_SHORT).show();
                }
                else if(response.code() == 400){
                    hideLoadingDialog();
                    Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RemoveVacation> call, Throwable t) {
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