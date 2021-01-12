package com.example.dtcemployee.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dtcemployee.Models.SignIn.SignIn;
import com.example.dtcemployee.R;
import com.example.dtcemployee.RetrofitClient.RetrofitClientClass;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    Button loginbtn;
    EditText edtUsername,edtPassword;
    AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Paper.init(this);

        initViews();
        clickEvents();

    }

    private void initViews() {

        loginbtn = findViewById(R.id.loginbtn);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
    }

    private void clickEvents() {

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


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
                    Call<SignIn> call = RetrofitClientClass.getInstance().getInterfaceInstance().SignIn(user_name,password);
                    call.enqueue(new Callback<SignIn>() {
                        @Override
                        public void onResponse(Call<SignIn> call, Response<SignIn> response) {
                            if(response.code() == 200){
                                hideLoadingDialog();

                                String user_id = response.body().getEmployeeId().get(0).getId();
                                String manager_id = response.body().getEmployeeId().get(0).getManagerId();
                                String manager_name = response.body().getEmployeeId().get(0).getManagerName();
                                Paper.book().write("user_id", user_id);
                                Paper.book().write("manager_id", manager_id);
                                Paper.book().write("manager_name",manager_name);
//                                Toast.makeText(LoginActivity.this, ""+manager_name, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);

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