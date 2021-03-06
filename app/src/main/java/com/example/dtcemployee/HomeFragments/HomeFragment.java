package com.example.dtcemployee.HomeFragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.location.LocationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtcemployee.Activities.LoginActivity;
import com.example.dtcemployee.Adapter.HomeFragmentAdapter;
import com.example.dtcemployee.Activities.AddNewReportActivity;
import com.example.dtcemployee.Models.CheckIn.CheckIn;
import com.example.dtcemployee.Models.CheckOut.CheckOut;
import com.example.dtcemployee.Models.EmployeePic.EmployeePic;
import com.example.dtcemployee.Models.GetAllLocation.AllLocation;
import com.example.dtcemployee.Models.GetAllLocation.GetAllLocation;
import com.example.dtcemployee.Models.GetEmployeeLocation.EmployeeLocation;
import com.example.dtcemployee.Models.GetEmployeeLocation.GetEmployeeLocation;
import com.example.dtcemployee.Models.GetEmployeeProject.EmployeeProject;
import com.example.dtcemployee.Models.GetEmployeeProject.GetEmployeeProject;
import com.example.dtcemployee.Models.SignIn.EmployeeId;
import com.example.dtcemployee.Models.SubemployeeDetail.SubEmployeeDetail;
import com.example.dtcemployee.RetrofitClient.RetrofitClientClass;
import com.example.dtcemployee.TabFragments.All_Home_Fragment;
import com.example.dtcemployee.TabFragments.Overtime_Home_Fragment;
import com.example.dtcemployee.TabFragments.Permenance_Home_Fragment;
import com.example.dtcemployee.TabFragments.Vacation_Home_Fragment;
import com.example.dtcemployee.R;
import com.example.dtcemployee.Utils.WrapContentViewPager;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.TextViewWithCircularIndicator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;


import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    LinearLayout check_in, check_out;
    Window window;
    Timer timer;
    AlertDialog loadingDialog;
    ImageView emp_image;
    TextView txtCheckOut, txtCheckIn,txtManagerName, noData;

    ImageButton back_profile;
    ImageButton refresh;
    ArrayAdapter<String> spinnerArrayAdapter;
    List<EmployeeProject> employeeProjectList = new ArrayList<>();
    List<EmployeeLocation> employeeLocationList = new ArrayList<>();
    List<AllLocation> allLocationList = new ArrayList<>();

    Spinner Spinner_home;
    String emp_id, id, project_id = "",location_id = "",manager_id="";
    public double longitudes, latitudes;


    private FusedLocationProviderClient fusedLocationProviderClient;


    final LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {

            latitudes = locationResult.getLastLocation().getLatitude();
            longitudes = locationResult.getLastLocation().getLongitude();

//            Address = getCompleteAddress(currentLatitude, currentLongitude);
//            CureentAddress = getCompleteAddress(currentLatitude, currentLongitude);

//            getData();


            if(checkConnection())
            {
                //Toast.makeText(getActivity(), "Connected to Internet", Toast.LENGTH_SHORT).show();
                GetEmployeeLocation();
                GetManagerLocation();
                EmployeeDetail(id);
            }else
                {
                    //Toast.makeText(getActivity(), Double.toString(latitudes)+","+Double.toString(longitudes), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), "Internet Not Available", Toast.LENGTH_SHORT).show();
                }

        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Paper.init(requireContext());

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());

        if (checkPermissions()) {

            enableLocationSettings();

        } else {

            requestPermission();

        }

        TabLayout TabLayout = view.findViewById(R.id.TabLyout);
        WrapContentViewPager Viewpager = view.findViewById(R.id.Viewpager);
        LinearLayout addreportbtn = view.findViewById(R.id.add_report);
//        Spinner aSpinner = view.findViewById(R.id.Spinner_home);
        check_out = view.findViewById(R.id.check_out);
        check_in = view.findViewById(R.id.check_in);
        txtCheckOut = view.findViewById(R.id.txtCheckOut);
        txtCheckIn = view.findViewById(R.id.txtCheckIn);
        back_profile = view.findViewById(R.id.back_profile);
        Spinner_home = view.findViewById(R.id.Spinner_home);
        txtManagerName = view.findViewById(R.id.txtManagerName);
        emp_image=view.findViewById(R.id.emp_image);
        noData= view.findViewById(R.id.noData);
        refresh=view.findViewById(R.id.refresh);
        emp_id = Paper.book().read("user_id");
        id = Paper.book().read("user_id");
        manager_id = Paper.book().read("manager_id");
        getphoto(emp_id);

        TabLayout.setupWithViewPager(Viewpager);
        //setUpViewPager(Viewpager);

//        GetEmployeeProject(id);

        addreportbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), AddNewReportActivity.class);
                startActivity(intent);
            }
        });

        refresh. setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkConnection())
                {
                    //Toast.makeText(getActivity(), "Connected to Internet", Toast.LENGTH_SHORT).show();
                    GetEmployeeLocation();
                    GetManagerLocation();
                    EmployeeDetail(id);
                    getphoto(emp_id);
                }else
                {
                    //Toast.makeText(getActivity(), Double.toString(latitudes)+","+Double.toString(longitudes), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), "Internet Not Available", Toast.LENGTH_SHORT).show();
                }
            }
        });
        check_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkConnection()){
                    if(location_id.equals("")){

                        Toast.makeText(requireContext(), "No Location Assigned", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        CheckOut();
                    }
                }
                else{
                    Store_CheckoutTime_on_offline();
                    String checkOutTime_offline=Get_CheckoutTime_on_offline();
                    LatLng loc_offline=new LatLng(latitudes,longitudes);
                    Paper.book().write("latLong_offline_checkout_lat", Double.toString(loc_offline.latitude));
                    Paper.book().write("latLong_offline_checkout_lon", Double.toString(loc_offline.longitude));
                    Paper.book().write("checkedOut_time_offline",checkOutTime_offline);
                    check_out.setEnabled(false);
                    txtCheckOut.setEnabled(false);
                    check_in.setEnabled(true);
                    txtCheckOut.setText("Checked out offline");
                    txtCheckIn.setText("Check in");
                    txtCheckIn.setEnabled(false);
                }

            }
        });
        check_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // check internet connection
                if(checkConnection())
                {
                    if(location_id.equals("")){

                        Toast.makeText(requireContext(), "No Location Assigned", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        //  if available
                        CheckIn();
                    }
                }
                else
                {
                    //else not available
                    // paper db store latlong and time
                    // paper.book("key","value");
                    Store_CheckInTime_on_offline();
                    String checkInTime_offline=Get_CheckInTime_on_offline();
                    LatLng loc_offline=new LatLng(latitudes,longitudes);
                    Paper.book().write("latLong_offline_lat", Double.toString(loc_offline.latitude));
                    Paper.book().write("latLong_offline_lon", Double.toString(loc_offline.longitude));
                    Paper.book().write("checkedIn_time_offline",checkInTime_offline);
                    check_out.setEnabled(true);
                    txtCheckOut.setEnabled(true);
                    check_in.setEnabled(false);
                    txtCheckIn.setText("Checked in offline");
                    txtCheckOut.setText("Check out");
                    txtCheckIn.setEnabled(false);
                }
            }
        });
        back_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                androidx.appcompat.app.AlertDialog.Builder dialog = new androidx.appcompat.app.AlertDialog.Builder(requireContext());
                dialog.setMessage("Do you really want to logout?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //String emp_id= Paper.book().read("user_id");
                        Paper.book().destroy();
                        Intent intent = new Intent(requireContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        //updateLogouStatus(emp_id);

                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });



                dialog.show();
            }
        });
    }



    @Override
    public void onResume() {
        super.onResume();

        String status = Paper.book().read("status");
//        Toast.makeText(requireContext(), ""+status, Toast.LENGTH_SHORT).show();

        if(status != null){
        if(status.equals( "Checkedin")){
//            Toast.makeText(requireContext(), ""+"Checkedin", Toast.LENGTH_SHORT).show();

            txtCheckIn.setText("Checked In");
            check_in.setBackgroundColor(Color.parseColor("#ff669900"));
            check_in.setEnabled(false);
            txtCheckIn.setEnabled(false);
            txtCheckOut.setText("Check Out");
            check_out.setBackgroundColor(Color.parseColor("#CF3827"));
            check_out.setEnabled(true);
            txtCheckOut.setEnabled(true);

        }
        else  if(status.equals( "Checkedout")){
//            Toast.makeText(requireContext(), ""+"Checkedout", Toast.LENGTH_SHORT).show();
            txtCheckOut.setText("Checked Out");
            check_out.setBackgroundColor(Color.parseColor("#A7A7A7"));
            txtCheckOut.setEnabled(false);
            check_out.setEnabled(false);
            txtCheckIn.setText("Check In");
            txtCheckIn.setEnabled(true);
            check_in.setEnabled(true);
            check_in.setBackgroundColor(Color.parseColor("#CF3827"));

            }
        }
    }

    private void GetManagerLocation() {

        Call<GetAllLocation> call = RetrofitClientClass.getInstance()
                .getInterfaceInstance().GetAllManagerLocation(manager_id);
        call.enqueue(new Callback<GetAllLocation>() {
            @Override
            public void onResponse(Call<GetAllLocation> call, Response<GetAllLocation> response) {
                if(response.code() == 200) {
                    allLocationList = response.body().getAllLocations();
                    Log.i("TAG", "onResponse: " + allLocationList);

                    if (allLocationList.size() > 0)
                    {
                        noData.setVisibility(View.GONE);
                        Spinner_home.setEnabled(true);
                        check_in.setEnabled(true);
                        spinnerevents(allLocationList);
                        String time_ci=Paper.book().read("checkedIn_time_offline");
                        String time_co=Paper.book().read("checkedOut_time_offline");
                        String lat_ci= Paper.book().read("latLong_offline_lat");
                        String lon_ci=Paper.book().read("latLong_offline_lon");
                        String lat_co=Paper.book().read("latLong_offline_checkout_lat");
                        String lon_co=Paper.book().read("latLong_offline_checkout_lon");
                        if(time_ci!= null &&time_co!=null&&lat_ci!=null&&lon_ci!=null&&lat_co!=null&&lon_co!=null){
                            insertBothCheckInAndOutOffline();
                        }
                        else if(time_ci!= null&&lat_ci!=null&&lon_ci!=null){
                            insertOfflinedata();
                        }
                        else if(time_co!=null&&lat_co!=null&&lon_co!=null){
                            insertOfflineCheckOutdata();
                        }
                    }
                    else {
                        noData.setVisibility(View.VISIBLE);
                        Spinner_home.setEnabled(false);
                        check_in.setEnabled(false);
                        project_id = "";
                        location_id = "";
                    }
                }
                else if(response.code() == 404){
                    Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                }
                else if(response.code() == 400){
                    Toast.makeText(requireContext(), "No Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetAllLocation> call, Throwable t) {
                Toast.makeText(requireContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void getphoto(String employee_id){
        Call<EmployeePic> call = RetrofitClientClass.getInstance().getInterfaceInstance().getEmployeePic(employee_id);
        call.enqueue(new Callback<EmployeePic>() {
            @Override
            public void onResponse(Call<EmployeePic> call, Response<EmployeePic> response) {

                String image_url= "http://dtc.anstm.com/dtcAdmin/api/Manager/Employee/Joining_Image/"+response.body().getPhoto();
                Picasso
                        .get()
                        .load(image_url)
                        .into(emp_image);

            }

            @Override
            public void onFailure(Call<EmployeePic> call, Throwable t) {
                Log.d("Employee pic","No pic");
            }
        });
    }

    public void insertOfflinedata(){
        String time_offline=Paper.book().read("checkedIn_time_offline");
        String latlong_offline_lat=Paper.book().read("latLong_offline_lat");
        String latlong_offline_lon=Paper.book().read("latLong_offline_lon");
        // store db data here

        //Toast.makeText(getActivity(), "Last Checked in at "+time_offline, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getActivity(), "Last Checked in coords "+latlong_offline_lat+", "+latlong_offline_lon,
                //Toast.LENGTH_SHORT).show();

        if(time_offline!=null && latlong_offline_lat!=null && latlong_offline_lon!=null){
            Double initlat= Double.parseDouble(latlong_offline_lat);
            Double initlong=Double.parseDouble(latlong_offline_lon);

            for (int i=0;i<allLocationList.size();i++){
                Double finallat=Double.parseDouble(allLocationList.get(i).getLatitudes());
                Double finallong=Double.parseDouble(allLocationList.get(i).getLongitudes());
                Double distance= CalculationByDistance(initlat,initlong,finallat,finallong);
                if (distance<=100){
                    String loc_id=allLocationList.get(i).getId();
                    //Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    CheckIn(loc_id,initlat,initlong,time_offline);
                    Paper.book().delete("checkedIn_time_offline");
                    Paper.book().delete("latLong_offline_lat");
                    Paper.book().delete("latLong_offline_lon");
                    break;
                }
                else{
                    Log.d("Offline data","Failed");
                    Paper.book().delete("checkedIn_time_offline");
                    Paper.book().delete("latLong_offline_lat");
                    Paper.book().delete("latLong_offline_lon");
                    //Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
            Toast.makeText(getActivity(), "Check in status checked", Toast.LENGTH_SHORT).show();
        }
        else{
            Log.d("Offline data","No data available");
        }
    }

    public void insertOfflineCheckOutdata(){
        String time_offline=Paper.book().read("checkedOut_time_offline");
        String latlong_offline_lat=Paper.book().read("latLong_offline_checkout_lat");
        String latlong_offline_lon=Paper.book().read("latLong_offline_checkout_lon");
        // store db data here

        //Toast.makeText(getActivity(), "Last Checked in at "+time_offline, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getActivity(), "Last Checked in coords "+latlong_offline_lat+", "+latlong_offline_lon,
        //Toast.LENGTH_SHORT).show();

        if(time_offline!=null && latlong_offline_lat!=null && latlong_offline_lon!=null){
            Double initlat= Double.parseDouble(latlong_offline_lat);
            Double initlong=Double.parseDouble(latlong_offline_lon);

            for (int i=0;i<allLocationList.size();i++){
                Double finallat=Double.parseDouble(allLocationList.get(i).getLatitudes());
                Double finallong=Double.parseDouble(allLocationList.get(i).getLongitudes());
                Double distance= CalculationByDistance(initlat,initlong,finallat,finallong);
                if (distance<=100){
                    String loc_id=allLocationList.get(i).getId();
                    //Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    CheckOut(loc_id,initlat,initlong,time_offline);
                    Paper.book().delete("checkedOut_time_offline");
                    Paper.book().delete("latLong_offline_lat");
                    Paper.book().delete("latLong_offline_lon");
                    break;
                }
                else{
                    Log.d("Offline data","Failed");
                    Paper.book().delete("checkedOut_time_offline");
                    Paper.book().delete("latLong_offline_checkout_lat");
                    Paper.book().delete("latLong_offline_checkout_lon");
                    //Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
            Toast.makeText(getActivity(), "Check in status checked", Toast.LENGTH_SHORT).show();
        }
        else{
            Log.d("Offline data","No data available");
        }
    }

    public void insertBothCheckInAndOutOffline(){
        String time_offline_checkin=Paper.book().read("checkedIn_time_offline");
        String time_offline_checkout=Paper.book().read("checkedOut_time_offline");
        String lat_checkin_offline= Paper.book().read("latLong_offline_lat");
        String lon_checkin_offline=Paper.book().read("latLong_offline_lon");
        String checkout_offline_lat=Paper.book().read("latLong_offline_checkout_lat");
        String checkout_offline_lon=Paper.book().read("latLong_offline_checkout_lon");
        // store db data here

        //Toast.makeText(getActivity(), "Last Checked in at "+time_offline, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getActivity(), "Last Checked in coords "+latlong_offline_lat+", "+latlong_offline_lon,
        //Toast.LENGTH_SHORT).show();

        if(time_offline_checkin!=null && time_offline_checkout!=null && lat_checkin_offline!=null&& lon_checkin_offline!=null
        && checkout_offline_lat!=null && checkout_offline_lon!=null)
        {
            Double initlat= Double.parseDouble(lat_checkin_offline);
            Double initlong=Double.parseDouble(lon_checkin_offline);
            Double finalLat_co=Double.parseDouble(checkout_offline_lat);
            Double finalLon_co=Double.parseDouble(checkout_offline_lon);

            for (int i=0;i<allLocationList.size();i++){
                Double finallat=Double.parseDouble(allLocationList.get(i).getLatitudes());
                Double finallong=Double.parseDouble(allLocationList.get(i).getLongitudes());
                Double distance= CalculationByDistance(initlat,initlong,finallat,finallong);
                if (distance<=100){
                    for (int j=0;j<allLocationList.size();j++){
                        Double finallat2=Double.parseDouble(allLocationList.get(i).getLatitudes());
                        Double finallong2=Double.parseDouble(allLocationList.get(i).getLongitudes());
                        Double distance2= CalculationByDistance(finalLat_co,finalLon_co,finallat2,finallong2);
                        if(distance2<=100){
                            String loc_id=allLocationList.get(i).getId();
                            CheckInAndOut(loc_id,initlat,initlong,time_offline_checkin,time_offline_checkout);
                            Paper.book().delete("checkedIn_time_offline");
                            Paper.book().delete("checkedOut_time_offline");
                            Paper.book().delete("latLong_offline_lat");
                            Paper.book().delete("latLong_offline_lon");
                            Paper.book().delete("latLong_offline_checkout_lat");
                            Paper.book().delete("latLong_offline_checkout_lon");
                            break;
                        }
                        else{
                            Log.d("Offline data","Failed");
                        }
                    }
                    break;
                }
                else{
                    Log.d("Offline data","Failed");
                    Paper.book().delete("checkedIn_time_offline");
                    Paper.book().delete("checkedOut_time_offline");
                    Paper.book().delete("latLong_offline_lat");
                    Paper.book().delete("latLong_offline_lon");
                    Paper.book().delete("latLong_offline_checkout_lat");
                    Paper.book().delete("latLong_offline_checkout_lon");
                }
            }
            Toast.makeText(getActivity(), "Check in status checked", Toast.LENGTH_SHORT).show();
        }
        else{
            Log.d("Offline data","No data available");
        }
    }

    private void CheckIn() {
        showLoadingDialog();
        Date time = Calendar.getInstance().getTime();
        SimpleDateFormat sdp = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US);
        String check_in_time = sdp.format(time);


        Log.i("TAG", "CheckIn: " + "\n" + project_id + "\n" + emp_id + "\n" + latitudes
                + "\n" + longitudes + "\n" + check_in_time);

        Call<CheckIn> call = RetrofitClientClass.getInstance().getInterfaceInstance().CheckIn("17",location_id, emp_id,  latitudes,  longitudes, check_in_time);
        call.enqueue(new Callback<CheckIn>() {
            @Override
            public void onResponse(Call<CheckIn> call, Response<CheckIn> response) {
                if (response.code() == 200) {
                    hideLoadingDialog();
                    txtCheckIn.setText("Checked In");
                    check_in.setBackgroundColor(Color.parseColor("#ff669900"));
                    check_in.setEnabled(false);
                    txtCheckIn.setEnabled(false);
                    Paper.book().write("check_in_id",response.body().getC_id());
                    Paper.book().write("status", "Checkedin");
                    txtCheckOut.setText("Check Out");
                    check_out.setBackgroundColor(Color.parseColor("#CF3827"));
                    check_out.setEnabled(true);
                    txtCheckOut.setEnabled(true);

                } else if (response.code() == 400) {
                    hideLoadingDialog();
                    Toast.makeText(requireContext(), "Please Reached at Project Site First", Toast.LENGTH_SHORT).show();
                }
                else if(response.code() ==404) {
                    hideLoadingDialog();
                    Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<CheckIn> call, Throwable t) {
                hideLoadingDialog();
                Toast.makeText(requireContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void CheckInAndOut(String loc_id,Double lat,Double lon,String checkintime,String checkouttime) {
        showLoadingDialog();
        Call<CheckIn> call = RetrofitClientClass.getInstance().getInterfaceInstance().CheckInAndOut("17",loc_id, emp_id,  lat,  lon, checkintime,checkouttime);
        call.enqueue(new Callback<CheckIn>() {
            @Override
            public void onResponse(Call<CheckIn> call, Response<CheckIn> response) {
                if (response.code() == 200) {
                    hideLoadingDialog();
                    Log.d("Offlinecheckinandoutid",response.body().getC_id());
                    txtCheckIn.setText("Check In");
                    check_in.setEnabled(true);
                    txtCheckIn.setEnabled(true);
                    txtCheckOut.setText("Checked Out");
                    check_out.setEnabled(false);
                    txtCheckOut.setEnabled(false);

                } else if (response.code() == 400) {
                    hideLoadingDialog();
                    Toast.makeText(requireContext(), "Please Reached at Project Site First", Toast.LENGTH_SHORT).show();
                }
                else if(response.code() ==404) {
                    hideLoadingDialog();
                    Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<CheckIn> call, Throwable t) {
                hideLoadingDialog();
                Toast.makeText(requireContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void CheckIn(String loc_id,Double lat,Double lon,String time) {
        showLoadingDialog();

        Call<CheckIn> call = RetrofitClientClass.getInstance().getInterfaceInstance().CheckIn("17",loc_id, emp_id,  lat,  lon, time);
        call.enqueue(new Callback<CheckIn>() {
            @Override
            public void onResponse(Call<CheckIn> call, Response<CheckIn> response) {
                if (response.code() == 200) {
                    hideLoadingDialog();
                    Paper.book().write("check_in_id",response.body().getC_id());
                    txtCheckIn.setText("Checked In");
                    check_in.setBackgroundColor(Color.parseColor("#ff669900"));
                    check_in.setEnabled(false);
                    txtCheckIn.setEnabled(false);
                    Paper.book().write("status", "Checkedin");
                    txtCheckOut.setText("Check Out");
                    check_out.setBackgroundColor(Color.parseColor("#CF3827"));
                    check_out.setEnabled(true);
                    txtCheckOut.setEnabled(true);

                } else if (response.code() == 400) {
                    hideLoadingDialog();
                    Toast.makeText(requireContext(), "Please Reached at Project Site First", Toast.LENGTH_SHORT).show();
                }
                else if(response.code() ==404) {
                    hideLoadingDialog();
                    Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<CheckIn> call, Throwable t) {
                hideLoadingDialog();
                Toast.makeText(requireContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateLogouStatus(String emp_id){

        Call<EmployeeId> call = RetrofitClientClass.getInstance().getInterfaceInstance().updateLogout(emp_id);
        call.enqueue(new Callback<EmployeeId>() {
            @Override
            public void onResponse(Call<EmployeeId> call, Response<EmployeeId> response) {
                if (response.code()==200){
                    Paper.book().destroy();
                    Intent intent = new Intent(requireContext(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                            | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                else if(response.code() == 400){

                    hideLoadingDialog();

                }
            }

            @Override
            public void onFailure(Call<EmployeeId> call, Throwable t) {
                hideLoadingDialog();
                //Toast.makeText(LoginActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void CheckOut(String loc_id,Double lat,Double lon,String checkouttime) {
       showLoadingDialog();
        String c_id= Paper.book().read("check_in_id");
        Date time = Calendar.getInstance().getTime();
        SimpleDateFormat sdp = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.UK);
        String check_out_time = sdp.format(time);
        Call<CheckOut> call = RetrofitClientClass.getInstance().getInterfaceInstance().CheckOut("",loc_id,emp_id,
                lat, lon, checkouttime,c_id);
        call.enqueue(new Callback<CheckOut>() {
            @Override
            public void onResponse(Call<CheckOut> call, Response<CheckOut> response) {
                if(response.code() == 200) {
                    txtCheckIn.setText("Check In");
                    check_in.setBackgroundColor(Color.parseColor("#CF3827"));
                    txtCheckIn.setEnabled(true);
                    check_in.setEnabled(true);
                    txtCheckOut.setText("Checked Out");
                    txtCheckOut.setEnabled(false);
                    check_out.setEnabled(false);
                    check_out.setBackgroundColor(Color.parseColor("#A7A7A7"));
                    Paper.book().delete("check_in_id");
                    Paper.book().write("status", "Checkedout");
//                   Paper.book().destroy();
//                   startActivity(new Intent(requireContext(),LoginActivity.class));
//                    hideLoadingDialog();
                    hideLoadingDialog();
                }
                else if(response.code() == 400){
                    hideLoadingDialog();
                    Toast.makeText(requireContext(), "Please Reach at Project Site First", Toast.LENGTH_SHORT).show();
                }
                else if(response.code() == 404) {
                    hideLoadingDialog();
                    Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<CheckOut> call, Throwable t) {
                hideLoadingDialog();
                Toast.makeText(requireContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void CheckOut() {
        showLoadingDialog();
        String c_id= Paper.book().read("check_in_id");
        Date time = Calendar.getInstance().getTime();
        SimpleDateFormat sdp = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.UK);
        String check_out_time = sdp.format(time);
        Call<CheckOut> call = RetrofitClientClass.getInstance().getInterfaceInstance().CheckOut("",location_id,emp_id,
                latitudes, longitudes, check_out_time,c_id);
        call.enqueue(new Callback<CheckOut>() {
            @Override
            public void onResponse(Call<CheckOut> call, Response<CheckOut> response) {
                if(response.code() == 200) {
                    txtCheckIn.setText("Check In");
                    check_in.setBackgroundColor(Color.parseColor("#CF3827"));
                    txtCheckIn.setEnabled(true);
                    check_in.setEnabled(true);
                    txtCheckOut.setText("Checked Out");
                    txtCheckOut.setEnabled(false);
                    check_out.setEnabled(false);
                    check_out.setBackgroundColor(Color.parseColor("#A7A7A7"));
                    Paper.book().delete("check_in_id");
                    Paper.book().write("status", "Checkedout");
//                   Paper.book().destroy();
//                   startActivity(new Intent(requireContext(),LoginActivity.class));
//                    hideLoadingDialog();
                    hideLoadingDialog();
                }
                else if(response.code() == 400){
                    hideLoadingDialog();
                    Toast.makeText(requireContext(), "Please Reach at Project Site First", Toast.LENGTH_SHORT).show();
                }
                else if(response.code() == 404) {
                    hideLoadingDialog();
                    Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<CheckOut> call, Throwable t) {
                hideLoadingDialog();
                Toast.makeText(requireContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

//    private void setUpViewPager(ViewPager viewpager) {
//        HomeFragmentAdapter adapter = new HomeFragmentAdapter(this.getChildFragmentManager());
//        adapter.addFragment(new All_Home_Fragment(), "All");
//        adapter.addFragment(new Permenance_Home_Fragment(), "Permance");
//        adapter.addFragment(new Vacation_Home_Fragment(), "Vacation");
//        adapter.addFragment(new Overtime_Home_Fragment(), "OverTime");
//        viewpager.setAdapter(adapter);
//    }

    public void showLoadingDialog() {
        loadingDialog = new AlertDialog.Builder(requireContext()).create();
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.loading_dailoug, null, false);
        loadingDialog.setView(view);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingDialog.show();

    }

    private void EmployeeDetail( String id) {

        Call<SubEmployeeDetail> call = RetrofitClientClass.getInstance().getInterfaceInstance().SubEmployeeDetail(id);
        call.enqueue(new Callback<SubEmployeeDetail>() {
            @Override
            public void onResponse(Call<SubEmployeeDetail> call, Response<SubEmployeeDetail> response) {
                if(response.code() == 200) {
//                    txtusername,txtposition,txtallowed,txtemployeeId,txtjoiningDate,txtPassportNumber,txtPassportEnddate;
                    if(response.body().getEmployeeDetail().size() > 0) {
                        txtManagerName.setText(response.body().getEmployeeDetail().get(0).getUserName());
                        Paper.book().write("emp_name",response.body().getEmployeeDetail().get(0).getUserName());
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

    public void hideLoadingDialog() {
        loadingDialog.dismiss();
    }

    private void GetEmployeeLocation (){

        Call<GetEmployeeLocation> call = RetrofitClientClass.getInstance().getInterfaceInstance().GetEmployeeLocation(id);
        call.enqueue(new Callback<GetEmployeeLocation>() {
            @Override
            public void onResponse(Call<GetEmployeeLocation> call, Response<GetEmployeeLocation> response) {
                if(response.code() == 200){
                    employeeLocationList = response.body().getEmployeeLocations();
//                    Toast.makeText(requireContext(), ""+employeeLocationList, Toast.LENGTH_SHORT).show();
                    if (employeeLocationList.size() > 0)
                    {
//                       spinnerevents(employeeLocationList);
                    }
                    else {
                        project_id = "";
                        location_id = "";
                    }
                }
                else if(response.code() == 400) {
                    Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<GetEmployeeLocation> call, Throwable t) {
                Toast.makeText(requireContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void GetEmployeeProject(String id) {
        Call<GetEmployeeProject> call = RetrofitClientClass.getInstance().getInterfaceInstance().GetEmployeeProject(id);
        call.enqueue(new Callback<GetEmployeeProject>() {
            @Override
            public void onResponse(Call<GetEmployeeProject> call, Response<GetEmployeeProject> response) {
                if (response.code() == 200) {
                    employeeProjectList = response.body().getEmployeeProject();
//                    spinnerevents(employeeProjectList);
                } else if (response.code() == 400) {
                    Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<GetEmployeeProject> call, Throwable t) {
                Toast.makeText(requireContext(), "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void spinnerevents(List<AllLocation> allLocationList) {
        List<String> locationList = new ArrayList<>();

        for (int w = 0; w < allLocationList.size(); w++) {
            locationList.add(allLocationList.get(w).getTitle());
        }

        spinnerArrayAdapter = new ArrayAdapter<String>(requireContext(), R.layout.spineer_layout, locationList);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spineer_layout);
        Spinner_home.setAdapter(spinnerArrayAdapter);


        Spinner_home.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                for (int w = 0; w < allLocationList.size(); w++) {
                    if (allLocationList.get(w).getTitle().equals(adapterView.getSelectedItem())) {
                        project_id = allLocationList.get(w).getId();
                        location_id = allLocationList.get(w).getId();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    void enableLocationSettings() {
        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setNumUpdates(1);


        if (!isLocationEnabled()) {
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            SettingsClient client = LocationServices.getSettingsClient(requireActivity());

            Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

            task.addOnSuccessListener(requireActivity(), new OnSuccessListener<LocationSettingsResponse>() {
                @Override
                public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                    // All location settings are satisfied. The client can initialize
                    // location requests here.
                    // ...

                    requestNewLocation();
                }
            });

            task.addOnFailureListener(requireActivity(), new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (e instanceof ResolvableApiException) {
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            resolvable.startResolutionForResult(requireActivity(),
                                    2857);

                        } catch (IntentSender.SendIntentException sendEx) {
                            // Ignore the error.
                        }
                    }
                }
            });


        } else {

            requestNewLocation();

        }


    }

    boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);
        return LocationManagerCompat.isLocationEnabled(locationManager);
    }

    boolean checkPermissions() {

        return ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);

    }

    void requestPermission() {

        int PERMISSION_ID = 44;
        ActivityCompat.requestPermissions(requireActivity(),
                new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
                },
                PERMISSION_ID);

    }


    void requestNewLocation() {

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setNumUpdates(1);


        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED)

            fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest, locationCallback, Looper.myLooper());


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2857) {
            switch (resultCode) {
                case Activity.RESULT_OK:

                    requestNewLocation();

                    break;
                case Activity.RESULT_CANCELED:

                    Toast.makeText(requireContext(),
                            "You need to enable the location services", Toast.LENGTH_SHORT).show();

                    break;
                default:
                    break;
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 44) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (isLocationEnabled()) {

                    requestNewLocation();

                } else {

                    enableLocationSettings();

                }
            }
        }
    }

    public void Store_CheckInTime_on_offline()
    {
        Toast.makeText(getActivity(), "Storing Time While Offline", Toast.LENGTH_SHORT).show();
        Date time = Calendar.getInstance().getTime();
        SimpleDateFormat sdp = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US);
        String check_in_time = sdp.format(time);


        SharedPreferences.Editor editor = getActivity().getSharedPreferences("offline_Data", Context.MODE_PRIVATE).edit();
        editor.putString("check_in_time", check_in_time);
        editor.apply();

    }

    public void Store_CheckoutTime_on_offline()
    {
        Toast.makeText(getActivity(), "Storing Time While Offline", Toast.LENGTH_SHORT).show();
        Date time = Calendar.getInstance().getTime();
        SimpleDateFormat sdp = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US);
        String check_out_time = sdp.format(time);


        SharedPreferences.Editor editor = getActivity().getSharedPreferences("offline_Data_checkout", Context.MODE_PRIVATE).edit();
        editor.putString("check_out_time", check_out_time);
        editor.apply();

    }

    public String Get_CheckInTime_on_offline()
    {
        String checkInTime = null;
        SharedPreferences prefs = getActivity().getSharedPreferences("offline_Data", Context.MODE_PRIVATE);
        if(prefs.contains("check_in_time"))
        {
            String time = prefs.getString("check_in_time", "No time defined");
            checkInTime=time;
            Toast.makeText(getActivity(), "Time offline "+time, Toast.LENGTH_SHORT).show();
        }
        else{
            checkInTime="No time defined";
        }
        return checkInTime;
    }

    public String Get_CheckoutTime_on_offline()
    {
        String checkOutTime = null;
        SharedPreferences prefs = getActivity().getSharedPreferences("offline_Data_checkout", Context.MODE_PRIVATE);
        if(prefs.contains("check_out_time"))
        {
            String time = prefs.getString("check_out_time", "No time defined");
            checkOutTime=time;
            Toast.makeText(getActivity(), "Time offline "+time, Toast.LENGTH_SHORT).show();
        }
        else{
            checkOutTime="No time defined";
        }
        return checkOutTime;
    }

    private boolean checkConnection(){
        ConnectivityManager connectivityManager=(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        return networkInfo !=null && networkInfo.isConnected();
    }

    public double CalculationByDistance(double initialLat, double initialLong,
                                        double finalLat, double finalLong){
        int R = 6371000; // km (Earth radius)
        double dLat = toRadians(finalLat-initialLat);
        double dLon = toRadians(finalLong-initialLong);
        initialLat = toRadians(initialLat);
        finalLat = toRadians(finalLat);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(initialLat) * Math.cos(finalLat);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }

    public double toRadians(double deg) {
        return deg * (Math.PI/180);
    }
}