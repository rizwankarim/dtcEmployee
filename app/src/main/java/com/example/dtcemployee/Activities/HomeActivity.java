package com.example.dtcemployee.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.location.LocationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtcemployee.HomeFragments.EmployeeFragment;
import com.example.dtcemployee.HomeFragments.HomeFragment;
import com.example.dtcemployee.HomeFragments.VacationsFragment;
import com.example.dtcemployee.HomeFragments.MovementsFragment;
import com.example.dtcemployee.HomeFragments.NotificationsFragment;
import com.example.dtcemployee.R;
import com.example.dtcemployee.RetrofitClient.RetrofitClientClass;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import io.paperdb.Paper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout notificationbtn, vactionbtn, employeesbtn, movementsbtn, homebtn;
    ImageView notificationIcon, vactionIcon, employeesIcon, movementsIcon, homeIcon;
    TextView txtnotifcation, txtvaction, txtemployees, txtmovments, txthome;

    private static int PERMISSION_ID = 44;
    private FusedLocationProviderClient fusedLocationProviderClient;
//    LocationCallback locationCallback = new LocationCallback() {
//        @Override
//        public void onLocationResult(LocationResult locationResult) {
//
//
//
//            Log.i("TAG", "latitude:" + "hahahahaa");
//
//            Toast.makeText(HomeActivity.this, "hello", Toast.LENGTH_SHORT).show();
//
//        }
//    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//
//        if (checkPermissions()) {
//
//            enableLocationSettings();
//
//        } else {
//
//            requestPermission();
//
//        }

        if(checkConnection())
        {
            Toast.makeText(this, "Connected to Internet", Toast.LENGTH_SHORT).show();

            initViews();
            clickEvents();
        }
        else
        {
            initViews();
            clickEvents();
            Toast.makeText(this, "Internet Not Available", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean checkConnection(){
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        return networkInfo !=null && networkInfo.isConnected();
    }
    private void initViews() {

        notificationbtn = findViewById(R.id.noti_btn);
        vactionbtn = findViewById(R.id.vaction_btn);
        employeesbtn = findViewById(R.id.employees_btn);
        movementsbtn = findViewById(R.id.movment_btn);
        homebtn = findViewById(R.id.home_btn);
//        icons
        notificationIcon = findViewById(R.id.noti_icon);
        vactionIcon = findViewById(R.id.vac_icon);
        employeesIcon = findViewById(R.id.employee_icon);
        movementsIcon = findViewById(R.id.movment_icon);
        homeIcon = findViewById(R.id.home_icon);

//        textviews

        txtnotifcation = findViewById(R.id.txtnoti);
        txtvaction = findViewById(R.id.txtvac);
        txtemployees = findViewById(R.id.txtemployees);
        txtmovments = findViewById(R.id.txtmovments);
        txthome = findViewById(R.id.txthome);

        if(checkConnection())
        {
              FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.i("Hello", "getInstanceId failed", task.getException());
                            return;
                        }
                        Log.i("Hello", "onComplete: "+task.getResult().getToken());
                        UpdateToken(task.getResult().getToken());
                    }
                });

        }else
            {

            }


    }

    private void clickEvents() {

        Fragment newFragment = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        homebtn.setOnClickListener(this);

        employeesbtn.setOnClickListener(this);

        movementsbtn.setOnClickListener(this);

        notificationbtn.setOnClickListener(this);

        vactionbtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.noti_btn:
                changeNotificationsFragment();
            break;
            case R.id.vaction_btn:
                changeVactionsFragment();
                break;
            case R.id.employees_btn:
                changeEmployeesFragment();
                break;
            case R.id.movment_btn:
                changeMovmementsFragment();
                break;
            case R.id.home_btn:
                changeHomeFragment();
                break;
        }

    }

    private void changeHomeFragment() {

        Fragment newFragment = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        homeIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.app_red), android.graphics.PorterDuff.Mode.SRC_IN);
        employeesIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.button_blue), android.graphics.PorterDuff.Mode.SRC_IN);
        movementsIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.button_blue), android.graphics.PorterDuff.Mode.SRC_IN);
        notificationIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.button_blue), android.graphics.PorterDuff.Mode.SRC_IN);
        vactionIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.button_blue), android.graphics.PorterDuff.Mode.SRC_IN);


        txthome.setVisibility(View.VISIBLE);
        txtemployees.setVisibility(View.GONE);
        txtmovments.setVisibility(View.GONE);
        txtnotifcation.setVisibility(View.GONE);
        txtvaction.setVisibility(View.GONE);
    }

    private void changeMovmementsFragment() {

        Fragment newFragment = new MovementsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        homeIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.button_blue), android.graphics.PorterDuff.Mode.SRC_IN);
        employeesIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.button_blue), android.graphics.PorterDuff.Mode.SRC_IN);
        movementsIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.app_red), android.graphics.PorterDuff.Mode.SRC_IN);
        notificationIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.button_blue), android.graphics.PorterDuff.Mode.SRC_IN);
        vactionIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.button_blue), android.graphics.PorterDuff.Mode.SRC_IN);


        txthome.setVisibility(View.GONE);
        txtemployees.setVisibility(View.GONE);
        txtmovments.setVisibility(View.VISIBLE);
        txtnotifcation.setVisibility(View.GONE);
        txtvaction.setVisibility(View.GONE);
    }

    private void changeEmployeesFragment() {

        Fragment newFragment = new EmployeeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        homeIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.button_blue), android.graphics.PorterDuff.Mode.SRC_IN);
        employeesIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.app_red), android.graphics.PorterDuff.Mode.SRC_IN);
        movementsIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.button_blue), android.graphics.PorterDuff.Mode.SRC_IN);
        notificationIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.button_blue), android.graphics.PorterDuff.Mode.SRC_IN);
        vactionIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.button_blue), android.graphics.PorterDuff.Mode.SRC_IN);

        txthome.setVisibility(View.GONE);
        txtemployees.setVisibility(View.VISIBLE);
        txtmovments.setVisibility(View.GONE);
        txtnotifcation.setVisibility(View.GONE);
        txtvaction.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    private void changeVactionsFragment() {

        Fragment newFragment = new VacationsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        homeIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.button_blue), android.graphics.PorterDuff.Mode.SRC_IN);
        employeesIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.button_blue), android.graphics.PorterDuff.Mode.SRC_IN);
        movementsIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.button_blue), android.graphics.PorterDuff.Mode.SRC_IN);
        notificationIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.button_blue), android.graphics.PorterDuff.Mode.SRC_IN);
        vactionIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.app_red), android.graphics.PorterDuff.Mode.SRC_IN);


        txthome.setVisibility(View.GONE);
        txtemployees.setVisibility(View.GONE);
        txtmovments.setVisibility(View.GONE);
        txtnotifcation.setVisibility(View.GONE);
        txtvaction.setVisibility(View.VISIBLE);
    }

    private void changeNotificationsFragment() {

        Fragment newFragment = new NotificationsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        homeIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.button_blue), android.graphics.PorterDuff.Mode.SRC_IN);
        employeesIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.button_blue), android.graphics.PorterDuff.Mode.SRC_IN);
        movementsIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.button_blue), android.graphics.PorterDuff.Mode.SRC_IN);
        notificationIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.app_red), android.graphics.PorterDuff.Mode.SRC_IN);
        vactionIcon.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.button_blue), android.graphics.PorterDuff.Mode.SRC_IN);


        txthome.setVisibility(View.GONE);
        txtemployees.setVisibility(View.GONE);
        txtmovments.setVisibility(View.GONE);
        txtnotifcation.setVisibility(View.VISIBLE);
        txtvaction.setVisibility(View.GONE);
    }

    private void UpdateToken(String token) {

        String userId = Paper.book().read("user_id");

        Call<ResponseBody> call = RetrofitClientClass.getInstance().getInterfaceInstance().updateToken(userId
                ,token);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                int code = response.code();

                if (code == 200)
                {
                    Log.i("OK", "onResponse: "+"Success");
                }
                else if (code == 400)
                {
                    Toast.makeText(HomeActivity.this, ""+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(HomeActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//    void enableLocationSettings() {
//        LocationRequest locationRequest = new LocationRequest();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setInterval(1000);
//        locationRequest.setFastestInterval(1000);
//        locationRequest.setNumUpdates(1);
//
//
//        if (!isLocationEnabled()) {
//            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//                    .addLocationRequest(locationRequest);
//
//            SettingsClient client = LocationServices.getSettingsClient(this);
//
//            Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
//
//            task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
//                @Override
//                public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
//                    // All location settings are satisfied. The client can initialize
//                    // location requests here.
//                    // ...
//
//                    requestNewLocation();
//                }
//            });
//
//            task.addOnFailureListener(this, new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    if (e instanceof ResolvableApiException) {
//                        // Location settings are not satisfied, but this can be fixed
//                        // by showing the user a dialog.
//                        try {
//                            // Show the dialog by calling startResolutionForResult(),
//                            // and check the result in onActivityResult().
//                            ResolvableApiException resolvable = (ResolvableApiException) e;
//                            resolvable.startResolutionForResult(HomeActivity.this,
//                                    2857);
//
//                        } catch (IntentSender.SendIntentException sendEx) {
//                            // Ignore the error.
//                        }
//                    }
//                }
//            });
//
//
//        } else {
//
//            requestNewLocation();
//
//        }
//
//
//    }
//
//    boolean isLocationEnabled() {
//        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        return LocationManagerCompat.isLocationEnabled(locationManager);
//    }
//
//    boolean checkPermissions() {
//
//        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
//                && (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
//
//    }
//
//    void requestPermission() {
//
//        ActivityCompat.requestPermissions(this,
//                new String[]{
//                        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
//                },
//                PERMISSION_ID);
//
//    }
//
//
//    void requestNewLocation() {
//
//        LocationRequest locationRequest = new LocationRequest();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setInterval(1000);
//        locationRequest.setFastestInterval(1000);
//        locationRequest.setNumUpdates(1);
//
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
//
//            fusedLocationProviderClient.requestLocationUpdates(
//                    locationRequest, locationCallback, Looper.myLooper());
//
//
//    }
//
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == 2857) {
//            switch (resultCode) {
//                case Activity.RESULT_OK:
//
//                    requestNewLocation();
//
//                    break;
//                case Activity.RESULT_CANCELED:
//
//                    Toast.makeText(this,
//                            "You need to enable the location services", Toast.LENGTH_SHORT).show();
//
//                    break;
//                default:
//                    break;
//            }
//        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

//        if (requestCode == 44) {
//
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                if (isLocationEnabled()) {
//
//                    requestNewLocation();
//
//                } else {
//
//                    enableLocationSettings();
//
//                }
//            }
//        }
    }


}