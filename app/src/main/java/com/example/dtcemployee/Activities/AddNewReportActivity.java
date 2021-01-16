package com.example.dtcemployee.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtcemployee.Common.FileUtils;
import com.example.dtcemployee.Models.AddReport.AddReport;
import com.example.dtcemployee.Models.GetEmployeeProject.EmployeeProject;
import com.example.dtcemployee.Models.GetEmployeeProject.GetEmployeeProject;
import com.example.dtcemployee.Models.UploadReportImages.UploadEmployeeImage;
import com.example.dtcemployee.R;
import com.example.dtcemployee.RetrofitClient.RetrofitClientClass;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewReportActivity extends AppCompatActivity {

    Button submit_btn;
    CardView getfilepicker1, getfilepicker2, getfilepicker3,
            getfilepicker4, getfilepicker5, getfilepicker6;
    AlertDialog loadingDialog;

    TextView getfilepath1, getfilepath2, getfilepath3,
            getfilepath4, getfilepath5, getfilepath6;

    TextView tv_uploaded1, tv_uploaded2, tv_uploaded3,
            tv_uploaded4, tv_uploaded5, tv_uploaded6, noData;

    ImageView ivfilepicker1, ivfilepicker2, ivfilepicker3,
            ivfilepicker4, ivfilepicker5, ivfilepicker6;

    Intent myFileIntent;
    Spinner spinner_req;

    String check;
    EditText edtTarget, edtAchivemnet, edtProblem;
    Uri imageUri, imageUri1, imageUri2, imageUri3, imageUri4, imageUri5, imageUri6;
    String  emp_id, target, achievement, problems,id, manager_id;
    FileUtils fileUtils;
    List<EmployeeProject> employeeProjectList = new ArrayList<>();
    String projectID = "";
    ArrayAdapter<String> spinnerArrayAdapter;
    public String datetime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_report);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Paper.init(this);
        emp_id = Paper.book().read("user_id");
        spinner_req = findViewById(R.id.spinner_req);
        id = Paper.book().read("user_id");
        manager_id=Paper.book().read("manager_id");
//        Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();

        Date time = Calendar.getInstance().getTime();
        SimpleDateFormat sdp = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US);
        datetime = sdp.format(time);
        initViews();
        clickEvents();

        GetEmployeeProject(id);
    }
    private void initViews() {
        submit_btn = findViewById(R.id.submit_req_btn);

//        cardviews
        getfilepicker1 = findViewById(R.id.filepicker1);
//        getfilepicker2 = findViewById(R.id.filepicker2);
//        getfilepicker3 = findViewById(R.id.filepicker3);
//        getfilepicker4 = findViewById(R.id.filepicker4);
//        getfilepicker5 = findViewById(R.id.filepicker5);
//        getfilepicker6 = findViewById(R.id.filepicker6);

//        textviews file picker
        getfilepath1 = findViewById(R.id.file_path1);
//        getfilepath2 = findViewById(R.id.file_path2);
//        getfilepath3 = findViewById(R.id.file_path3);
//        getfilepath4 = findViewById(R.id.file_path4);
//        getfilepath5 = findViewById(R.id.file_path5);
//        getfilepath6 = findViewById(R.id.file_path6);

//        textviews uploaded

        tv_uploaded1 = findViewById(R.id.tv_uploded1);
//        tv_uploaded2 = findViewById(R.id.tv_uploded2);
//        tv_uploaded3 = findViewById(R.id.tv_uploded3);
//        tv_uploaded4 = findViewById(R.id.tv_uploded4);
//        tv_uploaded5 = findViewById(R.id.tv_uploded5);
//        tv_uploaded6 = findViewById(R.id.tv_uploded6);

//        imageviews

        ivfilepicker1 = findViewById(R.id.iv_filepicker1);
//        ivfilepicker2 = findViewById(R.id.iv_filepicker2);
//        ivfilepicker3 = findViewById(R.id.iv_filepicker3);
//        ivfilepicker4 = findViewById(R.id.iv_filepicker4);
//        ivfilepicker5 = findViewById(R.id.iv_filepicker5);
//        ivfilepicker6 = findViewById(R.id.iv_filepicker6);
        edtTarget = findViewById(R.id.edtTarget);
        edtAchivemnet = findViewById(R.id.edtAchivemnet);
        edtProblem = findViewById(R.id.edtProblem);
        noData= findViewById(R.id.noData);


    }

    private void clickEvents() {
//        filePickers

        getfilepicker1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                check = "1";
                verifyPermissions();

            }
        });

//        getfilepicker2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                check = "2";
//                verifyPermissions();
//
//            }
//        });
//
//        getfilepicker3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                check = "3";
//                verifyPermissions();
//            }
//        });
//
//        getfilepicker4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                check = "4";
//                verifyPermissions();
//            }
//        });
//
//        getfilepicker5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                check = "5";
//                verifyPermissions();
//            }
//        });
//
//        getfilepicker6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                check = "6";
//                verifyPermissions();
//            }
//        });


//        SUBMIT BUTTON
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AddReportImagesTest();
               AddReport();
            }
        });

    }

    private void AddReport() {
        Date time1 = Calendar.getInstance().getTime();
        SimpleDateFormat sdp = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        SimpleDateFormat sdp1 = new SimpleDateFormat(" hh:mm a", Locale.US);
        String date = sdp.format(time1);
        String time = sdp1.format(time1);
        emp_id = Paper.book().read("user_id");
        target = edtTarget.getText().toString();
        achievement = edtAchivemnet.getText().toString();
        problems = edtProblem.getText().toString();
        String project_id = projectID;

        if (target.isEmpty()) {
            edtTarget.setError("Please Enter Target");
            edtTarget.requestFocus();
        } else if (achievement.isEmpty()) {
            edtAchivemnet.setError("Please Enter Achivement");
            edtAchivemnet.requestFocus();
        } else if (problems.isEmpty()) {
            edtProblem.setError("Please Enter Problems");
            edtProblem.requestFocus();
        } else if (imageUri == null) {
            Toast.makeText(this, "Please Upload File", Toast.LENGTH_SHORT).show();
        } else {
            showLoadingDialog();
            Call<AddReport> call = RetrofitClientClass.getInstance().getInterfaceInstance().Addreport(emp_id, project_id, target, datetime ,achievement, problems,manager_id, date, time);
            call.enqueue(new Callback<AddReport>() {
                @Override
                public void onResponse(Call<AddReport> call, Response<AddReport> response) {
                    if (response.code() == 200) {
//                        Toast.makeText(AddNewReportActivity.this, "Report Added Successfully", Toast.LENGTH_SHORT).show();
                        int report_id = response.body().getAddReportId();
                        if (imageUri != null) {
                            AddReportImages(report_id);
                        }
//                        if(imageUri1 !=  null){
//                            AddReportImages1(report_id);
//                        }
//                        if(imageUri2 !=  null){
//                            AddReportImages2(report_id);
//                        }
//                        if(imageUri3 !=  null){
//                            AddReportImages3(report_id);
//                        }
//                        if(imageUri4 !=  null){
//                            AddReportImages4(report_id);
//                        }
//                        if(imageUri5 !=  null) {
//                            AddReportImages5(report_id);
//                        }
                    } else if (response.code() == 404) {
                        hideLoadingDialog();
//                        Toast.makeText(AddNewReportActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AddReport> call, Throwable t) {
                    hideLoadingDialog();
                    Toast.makeText(AddNewReportActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }
    }

    private void AddReportImages(int report_id) {
        File file = new File(fileUtils.getRealPath(this, imageUri));

        RequestBody image = RequestBody.create(MediaType.parse(getContentResolver().getType(imageUri)), file);

        MultipartBody.Part files = MultipartBody.Part.createFormData("picture", file.getName(), image);



            Call<UploadEmployeeImage> call = RetrofitClientClass.getInstance().getInterfaceInstance().UploadReportFile(emp_id ,String.valueOf(report_id), files);
            call.enqueue(new Callback<UploadEmployeeImage>() {
                @Override
                public void onResponse(Call<UploadEmployeeImage> call, Response<UploadEmployeeImage> response) {
                    if (response.code() == 200) {
                        hideLoadingDialog();
                        Toast.makeText(AddNewReportActivity.this, "Report Added Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else if (response.code() == 404) {
                        hideLoadingDialog();
//                        Toast.makeText(AddNewReportActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {
                        hideLoadingDialog();
//                        Toast.makeText(AddNewReportActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UploadEmployeeImage> call, Throwable t) {
                    hideLoadingDialog();
//                    Toast.makeText(AddNewReportActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }


    private void AddReportImagesTest()
    {

        File file = new File(fileUtils.getRealPath(this, imageUri));

        RequestBody image = RequestBody.create(MediaType.parse(getContentResolver().getType(imageUri)), file);

        MultipartBody.Part files = MultipartBody.Part.createFormData("picture", file.getName(), image);

        showLoadingDialog();

        Call<UploadEmployeeImage> call = RetrofitClientClass.getInstance().getInterfaceInstance().UploadReportFile(emp_id ,"25", files);
        call.enqueue(new Callback<UploadEmployeeImage>() {
            @Override
            public void onResponse(Call<UploadEmployeeImage> call, Response<UploadEmployeeImage> response) {
                if (response.code() == 200) {

                    hideLoadingDialog();
                    Toast.makeText(AddNewReportActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    hideLoadingDialog();
                    Toast.makeText(AddNewReportActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {
                    hideLoadingDialog();
                    Toast.makeText(AddNewReportActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UploadEmployeeImage> call, Throwable t) {
                hideLoadingDialog();
                Toast.makeText(AddNewReportActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void AddReportImages1(int report_id) {
        File file = new File(fileUtils.getRealPath(this, imageUri1));

        RequestBody image = RequestBody.create(MediaType.parse(getContentResolver().getType(imageUri1)), file);

        MultipartBody.Part files = MultipartBody.Part.createFormData("picture", file.getName(), image);



        Call<UploadEmployeeImage> call = RetrofitClientClass.getInstance().getInterfaceInstance().UploadReportFile(emp_id ,String.valueOf(report_id), files);
        call.enqueue(new Callback<UploadEmployeeImage>() {
            @Override
            public void onResponse(Call<UploadEmployeeImage> call, Response<UploadEmployeeImage> response) {
                if (response.code() == 200) {
                    hideLoadingDialog();
//                    Toast.makeText(AddNewReportActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    hideLoadingDialog();
//                    Toast.makeText(AddNewReportActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {
                    hideLoadingDialog();
//                    Toast.makeText(AddNewReportActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UploadEmployeeImage> call, Throwable t) {
                hideLoadingDialog();
               Toast.makeText(AddNewReportActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void AddReportImages2(int report_id) {
        File file = new File(fileUtils.getRealPath(this, imageUri2));

        RequestBody image = RequestBody.create(MediaType.parse(getContentResolver().getType(imageUri2)), file);

        MultipartBody.Part files = MultipartBody.Part.createFormData("picture", file.getName(), image);



        Call<UploadEmployeeImage> call = RetrofitClientClass.getInstance().getInterfaceInstance().UploadReportFile(emp_id ,String.valueOf(report_id), files);
        call.enqueue(new Callback<UploadEmployeeImage>() {
            @Override
            public void onResponse(Call<UploadEmployeeImage> call, Response<UploadEmployeeImage> response) {
                if (response.code() == 200) {
                    hideLoadingDialog();
//                    Toast.makeText(AddNewReportActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    hideLoadingDialog();
//                    Toast.makeText(AddNewReportActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {
                    hideLoadingDialog();
//                    Toast.makeText(AddNewReportActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UploadEmployeeImage> call, Throwable t) {
                hideLoadingDialog();
                Toast.makeText(AddNewReportActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void AddReportImages3(int report_id) {
        File file = new File(fileUtils.getRealPath(this, imageUri3));

        RequestBody image = RequestBody.create(MediaType.parse(getContentResolver().getType(imageUri3)), file);

        MultipartBody.Part files = MultipartBody.Part.createFormData("picture", file.getName(), image);



        Call<UploadEmployeeImage> call = RetrofitClientClass.getInstance().getInterfaceInstance().UploadReportFile(emp_id,String.valueOf(report_id), files);
        call.enqueue(new Callback<UploadEmployeeImage>() {
            @Override
            public void onResponse(Call<UploadEmployeeImage> call, Response<UploadEmployeeImage> response) {
                if (response.code() == 200) {
                    hideLoadingDialog();
//                    Toast.makeText(AddNewReportActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    hideLoadingDialog();
//                    Toast.makeText(AddNewReportActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {
                    hideLoadingDialog();
//                    Toast.makeText(AddNewReportActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UploadEmployeeImage> call, Throwable t) {
                hideLoadingDialog();
                Toast.makeText(AddNewReportActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void AddReportImages4(int report_id) {
        File file = new File(fileUtils.getRealPath(this, imageUri4));

        RequestBody image = RequestBody.create(MediaType.parse(getContentResolver().getType(imageUri4)), file);

        MultipartBody.Part files = MultipartBody.Part.createFormData("picture", file.getName(), image);



        Call<UploadEmployeeImage> call = RetrofitClientClass.getInstance().getInterfaceInstance().UploadReportFile(emp_id,String.valueOf(report_id), files);
        call.enqueue(new Callback<UploadEmployeeImage>() {
            @Override
            public void onResponse(Call<UploadEmployeeImage> call, Response<UploadEmployeeImage> response) {
                if (response.code() == 200) {
                    hideLoadingDialog();
//                    Toast.makeText(AddNewReportActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    hideLoadingDialog();
//                    Toast.makeText(AddNewReportActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {
                    hideLoadingDialog();
//                    Toast.makeText(AddNewReportActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UploadEmployeeImage> call, Throwable t) {
                hideLoadingDialog();
                Toast.makeText(AddNewReportActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void AddReportImages5(int report_id) {
        File file = new File(fileUtils.getRealPath(this, imageUri5));

        RequestBody image = RequestBody.create(MediaType.parse(getContentResolver().getType(imageUri5)), file);

        MultipartBody.Part files = MultipartBody.Part.createFormData("picture", file.getName(), image);



        Call<UploadEmployeeImage> call = RetrofitClientClass.getInstance().getInterfaceInstance().UploadReportFile(emp_id,String.valueOf(report_id), files);
        call.enqueue(new Callback<UploadEmployeeImage>() {
            @Override
            public void onResponse(Call<UploadEmployeeImage> call, Response<UploadEmployeeImage> response) {
                if (response.code() == 200) {
                    hideLoadingDialog();
//                    Toast.makeText(AddNewReportActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    hideLoadingDialog();
//                    Toast.makeText(AddNewReportActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {
                    hideLoadingDialog();
//                    Toast.makeText(AddNewReportActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UploadEmployeeImage> call, Throwable t) {
                hideLoadingDialog();
//                Toast.makeText(AddNewReportActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void verifyPermissions() {

        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED) {
            openGallery();

        } else {
            ActivityCompat.requestPermissions(AddNewReportActivity.this, permissions, 2857);

        }
    }

    private void openGallery() {
        myFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
        myFileIntent.setType("image/*");
        startActivityForResult(myFileIntent, 1);
    }

    private void AddReportImages() {
        File file = new File(fileUtils.getRealPath(this, imageUri1));

        RequestBody image = RequestBody.create(MediaType.parse(getContentResolver().getType(imageUri1)), file);

        MultipartBody.Part files = MultipartBody.Part.createFormData("picture", file.getName(), image);

////        Call<UploadEmployeeImage> call = RetrofitClientClass.getInstance().getInterfaceInstance().UploadReportFile(emp_id,re,);
//        call.enqueue(new Callback<UploadEmployeeImage>() {
//            @Override
//            public void onResponse(Call<UploadEmployeeImage> call, Response<UploadEmployeeImage> response) {
//                if(response.code() == 200){
//                    Toast.makeText(AddNewReportActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();
//                }
//                else if(response.code() == 404){
//                    Toast.makeText(AddNewReportActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UploadEmployeeImage> call, Throwable t) {
//                Toast.makeText(AddNewReportActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });

    }

    public void showLoadingDialog() {
        loadingDialog = new AlertDialog.Builder(AddNewReportActivity.this).create();
        View view = LayoutInflater.from(this).inflate(R.layout.loading_dailoug, null, false);
        loadingDialog.setView(view);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingDialog.show();

    }

    public void hideLoadingDialog() {
        loadingDialog.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (check.equals("1")) {
            imageUri = data.getData();
//                    String path = data.getData().getPath();
//                    String filename = path.substring(path.lastIndexOf("/")+1);
//                    tv_uploaded1.setText(filename);
            getfilepath1.setVisibility(View.GONE);
            tv_uploaded1.setVisibility(View.VISIBLE);
            ImageViewCompat.setImageTintList(ivfilepicker1, ColorStateList.valueOf(getResources().getColor(R.color.hologreen)));
        }


        if (check.equals("2")) {
//                    String path = data.getData().getPath();
//                    String filename = path.substring(path.lastIndexOf("/")+1);
//                    tv_uploaded2.setText(filename);
            imageUri1 = data.getData();
            getfilepath2.setVisibility(View.GONE);
            tv_uploaded2.setVisibility(View.VISIBLE);
            ImageViewCompat.setImageTintList(ivfilepicker2, ColorStateList.valueOf(getResources().getColor(R.color.hologreen)));
        }

        if (check.equals("3")) {
            imageUri2 = data.getData();
            getfilepath3.setVisibility(View.GONE);
            tv_uploaded3.setVisibility(View.VISIBLE);
            ImageViewCompat.setImageTintList(ivfilepicker3, ColorStateList.valueOf(getResources().getColor(R.color.hologreen)));
        }

        if (check.equals("4")) {
            imageUri3 = data.getData();
            getfilepath4.setVisibility(View.GONE);
            tv_uploaded4.setVisibility(View.VISIBLE);
            ImageViewCompat.setImageTintList(ivfilepicker4, ColorStateList.valueOf(getResources().getColor(R.color.hologreen)));
        }

        if (check.equals("5")) {
            imageUri4 = data.getData();
            getfilepath5.setVisibility(View.GONE);
            tv_uploaded5.setVisibility(View.VISIBLE);
            ImageViewCompat.setImageTintList(ivfilepicker5, ColorStateList.valueOf(getResources().getColor(R.color.hologreen)));
        }

        if (check.equals("6")) {
            imageUri5 = data.getData();
            getfilepath6.setVisibility(View.GONE);
            tv_uploaded6.setVisibility(View.VISIBLE);
            ImageViewCompat.setImageTintList(ivfilepicker6, ColorStateList.valueOf(getResources().getColor(R.color.hologreen)));

        }

    }

    private void GetEmployeeProject(String id) {
        Call<GetEmployeeProject> call = RetrofitClientClass.getInstance().getInterfaceInstance().GetEmployeeProject(id);
        call.enqueue(new Callback<GetEmployeeProject>() {
            @Override
            public void onResponse(Call<GetEmployeeProject> call, Response<GetEmployeeProject> response) {
                if(response.code() == 200){
                    employeeProjectList = response.body().getEmployeeProject();
                    if(employeeProjectList.size()>0){
                        noData.setVisibility(View.GONE);
                        spinner_req.setEnabled(true);
                        spinnerevents(employeeProjectList);
                    }
                    else{
                        noData.setVisibility(View.VISIBLE);
                        spinner_req.setEnabled(false);
                    }

                }
                else if(response.code() == 400){
                    Toast.makeText(AddNewReportActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetEmployeeProject> call, Throwable t) {
                Toast.makeText(AddNewReportActivity.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void spinnerevents(List<EmployeeProject> employeeProjectList)
    {
        List<String> projectList = new ArrayList<>();

        for (int w = 0; w < employeeProjectList.size(); w++) {
            projectList.add(employeeProjectList.get(w).getProject());
        }
        spinnerArrayAdapter = new ArrayAdapter<String>(this,  R.layout.spineer_layout, projectList);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spineer_layout);
        spinner_req.setAdapter(spinnerArrayAdapter);
        spinner_req.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                for (int w = 0; w < employeeProjectList.size(); w++) {
                    if (employeeProjectList.get(w).getProject().equals(adapterView.getSelectedItem())) {
                        projectID = employeeProjectList.get(w).getProjectId();
                        //Toast.makeText(AddNewReportActivity.this, ""+projectID, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(AddNewReportActivity.this, ""+project_id, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}