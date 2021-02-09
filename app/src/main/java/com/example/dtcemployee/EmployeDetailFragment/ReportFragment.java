package com.example.dtcemployee.EmployeDetailFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtcemployee.Adapter.EmployeeReportsAdapter;
import com.example.dtcemployee.Common.Common;
import com.example.dtcemployee.Models.EmployeeDailyReportModel.EmployeeDailyReport;
import com.example.dtcemployee.Models.EmployeeDailyReportModel.GetEmployeeDailyReport;
import com.example.dtcemployee.R;
import com.example.dtcemployee.RetrofitClient.RetrofitClientClass;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReportFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    TextView noData;
    String id;
    RecyclerView rvReports;
    EditText edtDate;
    private DatePickerDialog dpd;
    Calendar now;
    List<EmployeeDailyReport> employeeDailyReports = new ArrayList<>();
    Date date;
    public ReportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noData = view.findViewById(R.id.noData);
        edtDate = view.findViewById(R.id.edtDate);
        id = Common.employeeId;
        rvReports = view.findViewById(R.id.rvReports);
        rvReports.setHasFixedSize(true);
        rvReports.setLayoutManager(new LinearLayoutManager(requireContext()));

        Calendar currentDate = Calendar.getInstance();
        date = currentDate.getTime();
        SimpleDateFormat sdp = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        String current_date = sdp.format(date);
        edtDate.setText(current_date);


        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePicker();

            }
        });

    }

    private void getEmployeeAttendance() {

        //Log.i("TAG", "getEmployeeAttendance: " + Comon.employeeId);
        String date = edtDate.getText().toString();
        Call<GetEmployeeDailyReport> call = RetrofitClientClass.getInstance().getInterfaceInstance()
                .employeeDailyReport(id, date);

        call.enqueue(new Callback<GetEmployeeDailyReport>() {
            @Override
            public void onResponse(Call<GetEmployeeDailyReport> call, Response<GetEmployeeDailyReport> response) {

                int code = response.code();

                if (code == 200)
                {
                    employeeDailyReports = response.body().getEmployeeDailyReport();

                    if (employeeDailyReports.size() > 0)
                    {
                        noData.setVisibility(View.GONE);
                        rvReports.setVisibility(View.VISIBLE);

                        EmployeeReportsAdapter adapter = new EmployeeReportsAdapter(requireContext(),employeeDailyReports);
                        rvReports.setAdapter(adapter);
                    }
                    else {
                        noData.setVisibility(View.VISIBLE);
                        rvReports.setVisibility(View.GONE);
                    }
                }
                else {
                    Toast.makeText(requireContext(), ""+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetEmployeeDailyReport> call, Throwable t) {

                Toast.makeText(requireContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        getEmployeeAttendance();
    }

    private void DatePicker() {
        now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                ReportFragment.this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Initial day selection
        );
//        dpd.setMinDate(now);
//        dpd.setMinDate(now);



        dpd.setThemeDark(true);
        dpd.setVersion(DatePickerDialog.Version.VERSION_1);
        dpd.setAccentColor(getResources().getColor(R.color.colorPrimaryDark));
        dpd.show(getChildFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        now = Calendar.getInstance();
        now.set(Calendar.YEAR, year);
        now.set(Calendar.MONTH, monthOfYear);
        now.set(Calendar.DATE, dayOfMonth);

        Calendar currentDate = Calendar.getInstance();
        Date currentDateTime = currentDate.getTime();

        date = now.getTime();

//        if (selectedDate.equals(currentDateTime)) {
//            current = true;
//        } else {
//            current = false;
//        }



        edtDate.setText(sdf.format(date));
        getEmployeeAttendance();

    }
}