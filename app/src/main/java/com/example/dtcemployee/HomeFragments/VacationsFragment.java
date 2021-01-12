package com.example.dtcemployee.HomeFragments;

import android.app.AlertDialog;
import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtcemployee.Adapter.VacationListAdapter;
import com.example.dtcemployee.Activities.CreateVacationRequestActivity;
import com.example.dtcemployee.Interface.DeleteVaction;
import com.example.dtcemployee.Models.GetAllVcation.EmployeeVacation;
import com.example.dtcemployee.Models.GetAllVcation.GetAllVaction;
import com.example.dtcemployee.Models.ModelClass;
import com.example.dtcemployee.Models.RemoveVacation.RemoveVacation;
import com.example.dtcemployee.R;
import com.example.dtcemployee.RetrofitClient.RetrofitClientClass;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VacationsFragment extends Fragment {

    List<ModelClass> modelClassesList = new ArrayList<>();
    RecyclerView recyclerView;
    String emp_id;
    AlertDialog loadingDialog;
    List<EmployeeVacation> employeeVacationList = new ArrayList<>();
    TextView textnodata;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vacations, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton addButton = view.findViewById(R.id.add_button);
        Paper.init(requireContext());
        emp_id = Paper.book().read("user_id");
        textnodata = view.findViewById(R.id.textnodata);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(requireContext(), CreateVacationRequestActivity.class);
                intent.putExtra("orign", "AddVacation");
                startActivity(intent);
            }
        });

        recyclerView = view.findViewById(R.id.vacation_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));



    }

    private void getData() {
        showLoadingDialog();
        Call<GetAllVaction> call = RetrofitClientClass.getInstance().getInterfaceInstance().GetAllVaction(emp_id);
        call.enqueue(new Callback<GetAllVaction>() {
            @Override
            public void onResponse(Call<GetAllVaction> call, Response<GetAllVaction> response) {
                if (response.code() == 200) {
                    hideLoadingDialog();
                    employeeVacationList = response.body().getEmployeeVacations();
                    if(employeeVacationList.size()>0){
                        textnodata.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        VacationListAdapter vacationListAdapter = new VacationListAdapter(getActivity(), employeeVacationList, new DeleteVaction() {
                            @Override
                            public void DeleteVaction(String id) {
                                RemoveVaction(id);

                            }
                        });
                        recyclerView.setAdapter(vacationListAdapter);
                    }

                    else{
                        hideLoadingDialog();
                        textnodata.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onFailure(Call<GetAllVaction> call, Throwable t) {
                hideLoadingDialog();
                Toast.makeText(requireContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                textnodata.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);

            }
        });


    }

    private void RemoveVaction(String id) {
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

//    private void RemoveVaction() {
//        Call<RemoveVacation> call = RetrofitClientClass.getInstance().getInterfaceInstance().RemoveVacation()
//
//
//    }


    @Override
    public void onResume() {
        super.onResume();
        getData();
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