package com.example.dtcemployee.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerListener;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.example.dtcemployee.Models.AddVocation.AddVocation;
import com.example.dtcemployee.Models.GetAllVacationTypye.GetAllVacation;
import com.example.dtcemployee.Models.GetAllVacationTypye.VacationType;
import com.example.dtcemployee.Models.GetAllVcation.EmployeeVacation;
import com.example.dtcemployee.Models.GetEmployeeSubEmployee.AllSubEmployee;
import com.example.dtcemployee.Models.GetEmployeeSubEmployee.GetemployeeSubEmployee;
import com.example.dtcemployee.Models.UpDateVacationRequest.UpDateVacationRequest;
import com.example.dtcemployee.Models.VacationDetail.VacationDetail;
import com.example.dtcemployee.R;
import com.example.dtcemployee.RetrofitClient.RetrofitClientClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateVacationRequestActivity extends AppCompatActivity {

    Button button;
    Spinner spinner;
    EditText tvBeginning, tvEnd;
    EditText edtReason, empName;
    LinearLayout beginningDateLayout, endDateLayout;
    String check;
    String manager_id, emp_id, type_id, beginning_date, ending_date, Reason;
    List<String> holidays = new ArrayList<>();
    List<AllSubEmployee> allSubEmployeeList = new ArrayList<>();
    List<String> employeeList = new ArrayList<>();
    ArrayAdapter<String> spinnerArrayAdapter;
    ArrayAdapter<String> spinnerEmployeeAdapter;
    AlertDialog loadingDialog;
    String originCheck, id,employeeId = "";
    String subEmployeeName;
    TextView txtCreateVaction;
    List<VacationType> employeeVacationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vacation_request);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Paper.init(this);
        emp_id = Paper.book().read("user_id");
        txtCreateVaction = findViewById(R.id.txtCreateVaction);
        originCheck = getIntent().getStringExtra("orign");
        manager_id = Paper.book().read("manager_id");
//        Toast.makeText(this, ""+Paper.book().read("manager_id"), Toast.LENGTH_SHORT).show();
        spinner = findViewById(R.id.spinner_req);

        initViews();
        clickEvents();


        if(checkConnection())
        {
            getAllVacationTypye();
        }
        else
        {
            Toast.makeText(this, "Internet Not Available", Toast.LENGTH_SHORT).show();
        }

//        holidays.add("Holdays");
//        holidays.add("Sick Leaves");
//        holidays.add("Urgent Leaves");
//
//        spinnerArrayAdapter = new ArrayAdapter<String>
//                (this, android.R.layout.simple_spinner_item,
//                        holidays); //selected item will look like a spinner set from XML
//        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
//                .simple_spinner_dropdown_item);
//
//        spinner.setAdapter(spinnerArrayAdapter);


        if (originCheck.equals("AddVacation")) {

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(checkConnection())
                    {
                        SubmitRequest();
                    }
                    else
                    {
                        Toast.makeText(CreateVacationRequestActivity.this, "Internet Not Available", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else if (originCheck.equals("EditVacation")) {
            id = getIntent().getStringExtra("id");
//            Toast.makeText(this, "" + id, Toast.LENGTH_SHORT).show();
            txtCreateVaction.setText("Update Vacation");
            button.setText("Update Vacation");
            VAcationDetail(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UpDateRequest(id);

                }
            });
        }
        else if (originCheck.equals("ViewVacation")) {
            id = getIntent().getStringExtra("id");
//            Toast.makeText(this, "" + id, Toast.LENGTH_SHORT).show();
            txtCreateVaction.setText("View Vacation");
            button.setVisibility(View.GONE);
//            button.setText("Update Vacation");
            VAcationDetail(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

    }
    private boolean checkConnection(){
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        return networkInfo !=null && networkInfo.isConnected();
    }
    private void UpDateRequest(String id) {
        String beginning_date = tvBeginning.getText().toString();
        String ending_date = tvEnd.getText().toString();
        String Reason = edtReason.getText().toString();
        String type_id = employeeId;


        if (type_id.isEmpty()) {
            Toast.makeText(this, "Please Select Type", Toast.LENGTH_SHORT).show();
        } else if (beginning_date.isEmpty()) {
            tvBeginning.setError("Please Enter Start Date");
            tvBeginning.requestFocus();
        } else if (ending_date.isEmpty()) {
            tvEnd.setError("Please Enter End Date");
            tvEnd.requestFocus();
        } else if (Reason.isEmpty()) {
            edtReason.getText().toString();
            edtReason.requestFocus();
        } else {
            showLoadingDialog();
            Call<UpDateVacationRequest> call = RetrofitClientClass.getInstance().getInterfaceInstance().UpDateVacationRequest(id,type_id,beginning_date,ending_date,Reason);
            call.enqueue(new Callback<UpDateVacationRequest>() {
                @Override
                public void onResponse(Call<UpDateVacationRequest> call, Response<UpDateVacationRequest> response) {
                    if(response.code() == 200) {
                        Toast.makeText(CreateVacationRequestActivity.this, "Vacation Request Update Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                        hideLoadingDialog();

                    }
                    else if(response.code() == 400) {
                        hideLoadingDialog();
                        Toast.makeText(CreateVacationRequestActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UpDateVacationRequest> call, Throwable t) {
                    hideLoadingDialog();
                    Toast.makeText(CreateVacationRequestActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });


        }

    }

    private void getAllVacationTypye() {

        Call<GetAllVacation> call = RetrofitClientClass.getInstance().getInterfaceInstance().GetALlVacation();
        call.enqueue(new Callback<GetAllVacation>() {
            @Override
            public void onResponse(Call<GetAllVacation> call, Response<GetAllVacation> response) {
                if (response.code() == 200) {
                    employeeVacationList = response.body().getVacationType();
                    spinnerevents(employeeVacationList);
                } else if (response.code() == 400) {
                    Toast.makeText(CreateVacationRequestActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<GetAllVacation> call, Throwable t) {
                Toast.makeText(CreateVacationRequestActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void spinnerevents(List<VacationType> employeeVacationList) {


            List<String> employeeList = new ArrayList<>();

            for (int w = 0; w < employeeVacationList.size(); w++) {
                employeeList.add(employeeVacationList.get(w).getType());
            }
            spinnerArrayAdapter = new ArrayAdapter<String>
                    (this,  R.layout.spineer_layout,
                            employeeList); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spineer_layout);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    for (int w = 0; w < employeeVacationList.size(); w++) {
                        if (employeeVacationList.get(w).getType().equals(adapterView.getSelectedItem())) {
                            employeeId = employeeVacationList.get(w).getId();
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }

    private void VAcationDetail(String id) {
        Call<VacationDetail> call = RetrofitClientClass.getInstance().getInterfaceInstance().Vacationdetail(id);
        call.enqueue(new Callback<VacationDetail>() {
            @Override
            public void onResponse(Call<VacationDetail> call, Response<VacationDetail> response) {
                if (response.code() == 200) {

                    tvBeginning.setText(response.body().getVacationDetail().get(0).getBeginningDate());
                    tvEnd.setText(response.body().getVacationDetail().get(0).getEndingDate());
                    edtReason.setText(response.body().getVacationDetail().get(0).getReason());
                } else if (response.code() == 400) {
                    Toast.makeText(CreateVacationRequestActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VacationDetail> call, Throwable t) {
                Toast.makeText(CreateVacationRequestActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initViews() {

        button = findViewById(R.id.create_employee_btn);
        spinner = findViewById(R.id.spinner_req);
        tvBeginning = findViewById(R.id.tvDateBeginning);
        tvEnd = findViewById(R.id.tvDateEnd);
        beginningDateLayout = findViewById(R.id.beginningDate);
        endDateLayout = findViewById(R.id.EndDate);
        edtReason = findViewById(R.id.edtReason);
        empName= findViewById(R.id.name);

    }

    private void clickEvents() {

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        beginningDateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now= Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateVacationRequestActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        tvBeginning.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        endDateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateVacationRequestActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        tvEnd.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
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

    private void SubmitRequest() {

        Date time1 = Calendar.getInstance().getTime();
        SimpleDateFormat sdp = new SimpleDateFormat("dd/M/yyyy", Locale.US);
        SimpleDateFormat sdp1 = new SimpleDateFormat(" hh:mm a", Locale.US);
        String date = sdp.format(time1);
        String time = sdp1.format(time1);

        type_id = spinner.getSelectedItem().toString();
        String beginning_date = tvBeginning.getText().toString();
        String ending_date = tvEnd.getText().toString();
        String Reason = edtReason.getText().toString();
        String type_id = employeeId;
        String emp_name= empName.getText().toString();

        if(date.equals(beginning_date)){
            Toast.makeText(this, "Sorry, You can't make today your beginning date for leave..", Toast.LENGTH_SHORT).show();
        }

        else{

            SimpleDateFormat format = new SimpleDateFormat("dd/M/yyyy");
            try {
                Date startDateConverted = format.parse(beginning_date);
                Date endDateConverted= format.parse(ending_date);
                long diff = (endDateConverted.getDate() - startDateConverted.getDate())+1;

                if (diff<=2){
                    if (type_id.isEmpty()) {
                        Toast.makeText(this, "Please Select Type", Toast.LENGTH_SHORT).show();
                    } else if (beginning_date.isEmpty()) {
                        tvBeginning.setError("Please Enter Start Date");
                        tvBeginning.requestFocus();
                    } else if (ending_date.isEmpty()) {
                        tvEnd.setError("Please Enter End Date");
                        tvEnd.requestFocus();
                    } else if (Reason.isEmpty()) {
                        edtReason.getText().toString();
                        edtReason.requestFocus();
                    } else {
                        showLoadingDialog();
                        Call<AddVocation> call = RetrofitClientClass.getInstance().getInterfaceInstance().AddVaction(manager_id,
                                emp_id,emp_name, type_id, beginning_date, ending_date, Reason);
                        call.enqueue(new Callback<AddVocation>() {
                            @Override
                            public void onResponse(Call<AddVocation> call, Response<AddVocation> response) {
                                if (response.code() == 200) {
                                    hideLoadingDialog();
                                    finish();
                                    Toast.makeText(CreateVacationRequestActivity.this, "Add Vacation Successfully", Toast.LENGTH_SHORT).show();
                                } else if (response.code() == 400) {
                                    hideLoadingDialog();
                                    Toast.makeText(CreateVacationRequestActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<AddVocation> call, Throwable t) {
                                hideLoadingDialog();

                                Toast.makeText(CreateVacationRequestActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                else{
                    Toast.makeText(CreateVacationRequestActivity.this, "Vacation request are not allowed for more then two days..", Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

        @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }



}