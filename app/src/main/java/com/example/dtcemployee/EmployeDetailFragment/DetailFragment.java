package com.example.dtcemployee.EmployeDetailFragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtcemployee.Activities.ShowEmployeeDetailActivity;
import com.example.dtcemployee.Activities.ViewerActivity;
import com.example.dtcemployee.Common.Common;
import com.example.dtcemployee.Models.SubemployeeDetail.SubEmployeeDetail;
import com.example.dtcemployee.R;
import com.example.dtcemployee.RetrofitClient.RetrofitClientClass;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailFragment extends Fragment {
    String id;
    Toolbar ChildProfiletoolbar;
    public TextView txtUserName, txtPassword, txtposition, txtPhone, txtUiqueId, txtUniqueID, txtPassportNumber, txtPassportEndDate, txtJoiningDate, txtBasicSalary,
            txtExpenses, txtallowed, txtContractDate;
    AlertDialog loadingDialog;
    ImageView emp_image;

    Button txtidFile, txtJoiningFile, txtImage, txtPassportFile, callemployee, resetPhone;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ChildProfiletoolbar = view.findViewById(R.id.ChildProfiletoolbar);
        ChildProfiletoolbar.setTitle("");

        emp_image= view.findViewById(R.id.emp_image);
        txtUserName = view.findViewById(R.id.txtUserName);
        txtPassword = view.findViewById(R.id.txtPassword);
        txtposition = view.findViewById(R.id.txtposition);
        txtPhone = view.findViewById(R.id.txtPhone);
        txtUiqueId = view.findViewById(R.id.txtUiqueId);
        txtUniqueID = view.findViewById(R.id.txtUniqueID);
        txtPassportNumber = view.findViewById(R.id.txtPassportNumber);
        txtPassportEndDate = view.findViewById(R.id.txtPassportEndDate);
        txtJoiningDate = view.findViewById(R.id.txtJoiningDate);
        txtContractDate=view.findViewById(R.id.txtContractDate);
        txtBasicSalary = view.findViewById(R.id.txtBasicSalary);
        txtExpenses = view.findViewById(R.id.txtExpenses);
        txtallowed = view.findViewById(R.id.txtOverTime);
        callemployee=view.findViewById(R.id.callbutton);

        txtidFile = view.findViewById(R.id.txtidFile);
        txtJoiningFile = view.findViewById(R.id.txtJoiningFile);
        //txtImage = view.findViewById(R.id.txtImage);
        txtPassportFile = view.findViewById(R.id.txtPassportFile);

        id = Common.employeeId;
        EmployeeDetail(id);
    }

    private void EmployeeDetail(String id) {

        Call<SubEmployeeDetail> call = RetrofitClientClass.getInstance().getInterfaceInstance().SubEmployeeDetail(id);
        call.enqueue(new Callback<SubEmployeeDetail>() {
            @Override
            public void onResponse(Call<SubEmployeeDetail> call, Response<SubEmployeeDetail> response) {
                if(response.code() == 200) {
//                    txtusername,txtposition,txtallowed,txtemployeeId,txtjoiningDate,txtPassportNumber,txtPassportEnddate;
                    if(response.body().getEmployeeDetail().size() > 0) {
                        txtUserName.setText(response.body().getEmployeeDetail().get(0).getUserName());
                        txtPassword.setText(response.body().getEmployeeDetail().get(0).getPassword());
                        txtposition.setText(response.body().getEmployeeDetail().get(0).getPosition());
                        txtPhone.setText(response.body().getEmployeeDetail().get(0).getPhone());
                        txtUiqueId.setText(response.body().getEmployeeDetail().get(0).getEmployeeId());
                        txtUniqueID.setText(response.body().getEmployeeDetail().get(0).getEndDate());
                        txtPassportNumber.setText(response.body().getEmployeeDetail().get(0).getPassportNo());
                        txtPassportEndDate.setText(response.body().getEmployeeDetail().get(0).getPassportEndDate());
                        txtJoiningDate.setText(response.body().getEmployeeDetail().get(0).getJoiningDate());
                        txtContractDate.setText(response.body().getEmployeeDetail().get(0).getContract_end_date());
                        txtBasicSalary.setText(response.body().getEmployeeDetail().get(0).getBasicSalary());
                        txtExpenses.setText(response.body().getEmployeeDetail().get(0).getExpenses());
                        if(response.body().getEmployeeDetail().get(0).getOverTime().equals("0")){
                            txtallowed.setText("Not Allowed");
                        }
                        if(!response.body().getEmployeeDetail().get(0).getOverTime().equals("0")){
                            txtallowed.setText("Allowed");
                        }
                        String File1 = response.body().getEmployeeDetail().get(0).getFile();
                        String Joining_File1 = response.body().getEmployeeDetail().get(0).getJoiningFile();
                        String Image1 = response.body().getEmployeeDetail().get(0).getImage();
                        String Passport_File1 = response.body().getEmployeeDetail().get(0).getPassportFile();

                        String File = "http://dtc.anstm.com/dtcAdmin/api/Manager/Employee/ID/" + response.body().getEmployeeDetail().get(0).getFile();
                        String Joining_File = response.body().getEmployeeDetail().get(0).getJoiningFile();
                        String Image = "http://dtc.anstm.com/dtcAdmin/api/Manager/Employee/Joining_Image/" + response.body().getEmployeeDetail().get(0).getImage();
                        String Passport_File = "http://dtc.anstm.com/dtcAdmin/api/Manager/Employee/PassPort/" + response.body().getEmployeeDetail().get(0).getPassportFile();

                        Picasso.get().load(Image)
                                .into(emp_image);

                        callemployee.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                makePhoneCall(txtPhone.getText().toString());
                            }
                        });

                        txtidFile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                try{
                                    if (File1 == null || File1.equals("")){
                                        Toast.makeText(requireContext(), "No File Attached", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Intent intent = new Intent(getContext(), ViewerActivity.class);
                                        intent.putExtra("orign", File);
                                        startActivity(intent);
                                    }
                                }
                                catch(Exception e){
                                    Toast.makeText(getContext(), "Something goes wrong", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                        txtJoiningFile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try{
                                    if (Joining_File1== null || Joining_File1.equals("")){

                                        Toast.makeText(requireContext(), "No File Attached", Toast.LENGTH_SHORT).show();

                                    }
                                    else {
                                        Intent browsefile = new Intent(Intent.ACTION_VIEW, Uri.parse(Joining_File));
                                        browsefile.putExtra("orign", Joining_File);
                                        startActivity(browsefile);
                                    }
                                }
                                catch(Exception e){
                                    Toast.makeText(getContext(), "Something goes wrong", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });

                        txtPassportFile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                try{
                                    if (Passport_File1== null  || Passport_File1.equals("")){

                                        Toast.makeText(requireContext(), "No File Attached", Toast.LENGTH_SHORT).show();

                                    }
                                    else {

                                        Intent intent = new Intent(getContext(), ViewerActivity.class);
                                        intent.putExtra("orign", Passport_File);
                                        startActivity(intent);
                                    }
                                }
                                catch (Exception e){
                                    Toast.makeText(getContext(), "Something goes wrong", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
                    }
                    else {
                        Toast.makeText(requireContext(), "No Sub Employee Here", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(response.code() == 400) {
                    Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SubEmployeeDetail> call, Throwable t) {
                Toast.makeText(requireContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void makePhoneCall(String number){
        if(number.trim().length()>0){
            if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(requireActivity(),new String[]{
                        Manifest.permission.CALL_PHONE
                },1);
            }
            else{
                String dial= "tel:"+number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
        else{
            Toast.makeText(requireContext(), "No phone number here..", Toast.LENGTH_SHORT).show();
        }
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