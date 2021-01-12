package com.example.dtcemployee.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dtcemployee.Models.Notification.EmployeeNotification;
import com.example.dtcemployee.Models.SubEmployeeNotification.SubEmployeeNotification;
import com.example.dtcemployee.R;
import com.example.dtcemployee.RetrofitClient.RetrofitClientClass;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerNoticationActivity extends AppCompatActivity {
    EditText textNoti;
    android.app.AlertDialog loadingDialog;
    Button create_employee_btn;
    String manager_id,employee_id,emp_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_notication);
        Paper.init(this);

        employee_id = Paper.book().read("user_id");
        emp_id = Paper.book().read("user_id");
        manager_id = Paper.book().read("manager_id");
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
        }   else {


                Call<EmployeeNotification> call = RetrofitClientClass.getInstance().getInterfaceInstance()
                        .CreateNotification(manager_id,employee_id,  "Notification", notifications, date, time);
                call.enqueue(new Callback<EmployeeNotification>() {
                    @Override
                    public void onResponse(Call<EmployeeNotification> call, Response<EmployeeNotification> response) {
                        if (response.code() == 200) {

                            textNoti.setText("");
                        } else if (response.code() == 404) {

                            Toast.makeText(ManagerNoticationActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 400) {

                            Toast.makeText(ManagerNoticationActivity.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<EmployeeNotification> call, Throwable t) {


                        Toast.makeText(ManagerNoticationActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }


}