package com.example.dtcemployee.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.example.dtcemployee.Models.AddVocation.AddVocation;
import com.example.dtcemployee.Models.GetAllVacationTypye.GetAllVacation;
import com.example.dtcemployee.Models.GetAllVacationTypye.VacationType;
import com.example.dtcemployee.Models.GetAllVcation.EmployeeVacation;
import com.example.dtcemployee.Models.UpDateVacationRequest.UpDateVacationRequest;
import com.example.dtcemployee.Models.VacationDetail.VacationDetail;
import com.example.dtcemployee.R;
import com.example.dtcemployee.RetrofitClient.RetrofitClientClass;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateVacationRequestActivity extends AppCompatActivity {

    Button button;
    Spinner spinner;
    EditText tvBeginning, tvEnd;
    EditText edtReason;
    LinearLayout beginningDateLayout, endDateLayout;
    String check;
    String manager_id, emp_id, type_id, beginning_date, ending_date, Reason;
    List<String> holidays = new ArrayList<>();
    ArrayAdapter<String> spinnerArrayAdapter;
    AlertDialog loadingDialog;
    String originCheck, id,employeeId = "";
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
        getAllVacationTypye();

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
                    SubmitRequest();
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


    }

    private void clickEvents() {

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        beginningDateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateVacationRequestActivity.this, new DatePickerDialog.OnDateSetListener() {
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

        type_id = spinner.getSelectedItem().toString();
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
            Call<AddVocation> call = RetrofitClientClass.getInstance().getInterfaceInstance().AddVaction(manager_id,
                    emp_id, type_id, beginning_date, ending_date, Reason);
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }



}