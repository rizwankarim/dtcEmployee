package com.example.dtcemployee.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dtcemployee.FirebaseServices.MyFirebaseMessaging;
import com.example.dtcemployee.Models.SignIn.EmployeeId;
import com.example.dtcemployee.Models.SignIn.SignIn;
import com.example.dtcemployee.R;
import com.example.dtcemployee.RetrofitClient.RetrofitClientClass;
import com.google.firebase.iid.FirebaseInstanceId;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_READ_PHONE_STATE =1 ;
    Button loginbtn;
    EditText edtUsername,edtPassword;
    AlertDialog loadingDialog;
    String Imei_Number;
    TelephonyManager telephonyManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Paper.init(this);

        if(checkConnection())
        {
            //Toast.makeText(this, "Connected to Internet", Toast.LENGTH_SHORT).show();
            initViews();
            clickEvents();
        }else
            {
                Toast.makeText(this, "Internet Not Available", Toast.LENGTH_SHORT).show();
            }
    }

    private boolean checkConnection(){
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        return networkInfo !=null && networkInfo.isConnected();
    }

    private void initViews() {

        loginbtn = findViewById(R.id.loginbtn);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        checkPhonePermission();
        //Imei_Number=getDeviceId(getApplicationContext());
        //Toast.makeText(this, Imei_Number, Toast.LENGTH_SHORT).show();
    }

    public static String getDeviceId(Context context) {

        String deviceId;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            deviceId = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephony.getDeviceId() != null) {
                deviceId = mTelephony.getDeviceId();
            } else {
                deviceId = Settings.Secure.getString(
                        context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }
        }

        return deviceId;
    }

    private void clickEvents() {

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String user_dt= prefs.getString("device_token",null);
                String user_name = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                if(user_name.isEmpty()){
                    edtUsername.setError("Please Enter Username");
                    edtUsername.requestFocus();
                }
                else if(password.isEmpty()){
                    edtPassword.setError("Please Enter Password");
                    edtPassword.requestFocus();
                }
                else {
                    showLoadingDialog();
                    Call<SignIn> call = RetrofitClientClass.getInstance().getInterfaceInstance().SignIn(user_name,password,Imei_Number);
                    call.enqueue(new Callback<SignIn>() {
                        @Override
                        public void onResponse(Call<SignIn> call, Response<SignIn> response) {
                            if(response.code() == 200){
                                hideLoadingDialog();
                                if (response.body().getMsg().equals("Login Failed")){
                                    Toast.makeText(LoginActivity.this, "Invalid Id and Pass..", Toast.LENGTH_SHORT).show();
                                }
                                else if(response.body().getMsg().equals("Login Successfully")){
                                    if(response.body().getL_status().equals(Imei_Number)|| response.body().getL_status().equals("false")){
                                        String user_id = response.body().getEmployeeId().get(0).getId();
                                        String manager_id = response.body().getEmployeeId().get(0).getManagerId();
                                        String manager_name = response.body().getEmployeeId().get(0).getManagerName();
                                        Paper.book().write("user_id", user_id);
                                        Paper.book().write("manager_id", manager_id);
                                        Paper.book().write("manager_name",manager_name);
                                        updateLoginStatus(user_id);
                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else if(!response.body().getL_status().equals(Imei_Number)) {
                                        Toast.makeText(LoginActivity.this, "Already logged in from other device..", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(LoginActivity.this, "Something went wrong.. try later..", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else{
                                    Toast.makeText(LoginActivity.this, "Something went wrong.. try later..", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else if(response.code() == 404){

                                hideLoadingDialog();
                                Toast.makeText(LoginActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<SignIn> call, Throwable t) {

                            hideLoadingDialog();
                            Toast.makeText(LoginActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });

    }

    public void updateLoginStatus(String emp_id){

        Call<EmployeeId> call = RetrofitClientClass.getInstance().getInterfaceInstance().updateloginstatus(emp_id,Imei_Number);
        call.enqueue(new Callback<EmployeeId>() {
            @Override
            public void onResponse(Call<EmployeeId> call, Response<EmployeeId> response) {
                if (response.code()==200){
                    Toast.makeText(LoginActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                }
                else if(response.code() == 400){

                    hideLoadingDialog();
                    Toast.makeText(LoginActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EmployeeId> call, Throwable t) {
                hideLoadingDialog();
                Toast.makeText(LoginActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void checkPhonePermission(){
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        } else {
            Imei_Number=getDeviceId(this);
            Toast.makeText(this, Imei_Number, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Imei_Number=getDeviceId(this);
                    Toast.makeText(this, Imei_Number, Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }

    public void showLoadingDialog() {
        loadingDialog = new AlertDialog.Builder(LoginActivity.this).create();
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