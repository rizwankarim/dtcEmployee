package com.example.dtcemployee.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerListener;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.example.dtcemployee.Models.GetEmployeeSubEmployee.AllSubEmployee;
import com.example.dtcemployee.Models.GetEmployeeSubEmployee.GetemployeeSubEmployee;
import com.example.dtcemployee.Models.Notification.EmployeeNotification;
import com.example.dtcemployee.Models.SubEmployeeNotification.SubEmployeeNotification;
import com.example.dtcemployee.R;
import com.example.dtcemployee.RetrofitClient.RetrofitClientClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubEmployeeNotificationActivity extends AppCompatActivity {
    EditText textNoti;
    android.app.AlertDialog loadingDialog;
    MultiSpinnerSearch employeeListSpinner;
    Button create_employee_btn;
    String employee_id;
    List<AllSubEmployee> allSubEmployeeList = new ArrayList<>();
    List<String> employeeList = new ArrayList<>();
    String manager_id, emp_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_employee_notification);
        Paper.init(this);
        employee_id = Paper.book().read("user_id");
        emp_id = Paper.book().read("user_id");
        manager_id = Paper.book().read("manager_id");
        employeeListSpinner = findViewById(R.id.employeeListSpinner);
        create_employee_btn = findViewById(R.id.create_employee_btn);

        textNoti = findViewById(R.id.textNoti);
        create_employee_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendNotification();
            }
        });


    }

    private void SendNotification() {
        Date time1 = Calendar.getInstance().getTime();
        SimpleDateFormat sdp = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        SimpleDateFormat sdp1 = new SimpleDateFormat(" hh:mm a", Locale.US);
        String date = sdp.format(time1);
        String time = sdp1.format(time1);
        String notifications = textNoti.getText().toString();
        if (notifications.isEmpty()) {
            textNoti.setError("Please Enter Notification");
            textNoti.requestFocus();
        } else if (employeeList.isEmpty()) {

            Toast.makeText(this, "Please Select Employee", Toast.LENGTH_SHORT).show();
        } else {

            sendNotificationToSubEmployee(notifications,date,time);
            showLoadingDialog();
        }
    }

    private void sendNotificationToSubEmployee(String notifications, String date, String time) {

        if (employeeList.size() > 0)
        {
            Log.i("TAG", "sendNotificationToSubEmployee:  " + employeeList.get(0)  + " " + employeeList.size());

            Call<SubEmployeeNotification> call = RetrofitClientClass.getInstance().getInterfaceInstance()
                    .subEmployeeNotification(employee_id, employeeList.get(0), "Notification by Head", notifications, date, time);
            call.enqueue(new Callback<SubEmployeeNotification>() {
                @Override
                public void onResponse(Call<SubEmployeeNotification> call, Response<SubEmployeeNotification> response) {
                    if (response.code() == 200) {
                        employeeList.remove(0);
                        sendNotificationToSubEmployee(notifications,date,time);
                    } else if (response.code() == 404) {
                        hideLoadingDialog();
                        Toast.makeText(SubEmployeeNotificationActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {
                        hideLoadingDialog();
                        Toast.makeText(SubEmployeeNotificationActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SubEmployeeNotification> call, Throwable t) {
                    hideLoadingDialog();

                    Toast.makeText(SubEmployeeNotificationActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
        else {
            textNoti.setText("");
            hideLoadingDialog();
        }


    }

    private void GetallSubEmployee(String emp_id) {
        showLoadingDialog();
        Call<GetemployeeSubEmployee> call = RetrofitClientClass.getInstance().getInterfaceInstance().getEmployeeSubEmploye(emp_id);
        call.enqueue(new Callback<GetemployeeSubEmployee>() {
            @Override
            public void onResponse(Call<GetemployeeSubEmployee> call, Response<GetemployeeSubEmployee> response) {
                if (response.code() == 200) {
                    hideLoadingDialog();
                    allSubEmployeeList = response.body().getAllSubEmployees();
                    if (allSubEmployeeList.size() > 0) {
                        setLocationInSpinner(allSubEmployeeList);
                    } else {
                        employeeListSpinner.setVisibility(View.GONE);
                        Toast.makeText(SubEmployeeNotificationActivity.this, "No Sub Employee Assigned", Toast.LENGTH_SHORT).show();
                    }
//                    SubEmployeeAdapter subEmployeeAdapter = new SubEmployeeAdapter(requireContext(),allSubEmployeeList);
//                    recyclerViewSubEmployees.setAdapter(subEmployeeAdapter);
                } else if (response.code() == 400) {
                    hideLoadingDialog();
//                    textnodata.setVisibility(View.VISIBLE);
//                    recyclerViewSubEmployees.setVisibility(View.GONE);
                } else if (response.code() == 404) {
                    hideLoadingDialog();
//                    textnodata.setVisibility(View.VISIBLE);
//                    recyclerViewSubEmployees.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<GetemployeeSubEmployee> call, Throwable t) {
                hideLoadingDialog();
//                textnodata.setVisibility(View.VISIBLE);
//                recyclerViewSubEmployees.setVisibility(View.GONE);
                Toast.makeText(SubEmployeeNotificationActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        GetallSubEmployee(emp_id);
    }

    private void setLocationInSpinner(List<AllSubEmployee> allSubEmployeeList) {
        List<KeyPairBoolData> data = new ArrayList<>();

        for (int i = 0; i < allSubEmployeeList.size(); i++) {

            KeyPairBoolData boolData = new KeyPairBoolData();
            boolData.setId(Integer.parseInt(allSubEmployeeList.get(i).getSubEmpId()));
            boolData.setName(allSubEmployeeList.get(i).getSubEmployeeName());
            boolData.setSelected(false);


//            for (int j = 0; j < employeeList.size(); j++) {
//                if (allSubEmployeeList.get(i).getId().equals(employeeList.get(j))) {
//                    boolData.setSelected(true);
//                }
//            }

            data.add(boolData);

        }


        employeeListSpinner.setItems(data, new MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> selectedItems) {
                employeeList.clear();

                for (int i = 0; i < selectedItems.size(); i++) {

                    employeeList.add(String.valueOf(selectedItems.get(i).getId()));
//                    Toast.makeText(CreateNewProjectActivity.this, ""+employeeList, Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    public void showLoadingDialog() {
        loadingDialog = new AlertDialog.Builder(this).create();
        View view = LayoutInflater.from(this).inflate(R.layout.loading_dailoug, null, false);
        loadingDialog.setView(view);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingDialog.show();

    }

    public void hideLoadingDialog() {
        loadingDialog.dismiss();
    }

}