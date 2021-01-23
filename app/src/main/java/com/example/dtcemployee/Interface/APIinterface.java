package com.example.dtcemployee.Interface;


import com.example.dtcemployee.Models.AddReport.AddReport;
import com.example.dtcemployee.Models.AddVocation.AddVocation;
import com.example.dtcemployee.Models.CheckIn.CheckIn;
import com.example.dtcemployee.Models.CheckInchckout.CheckInCheckOut;
import com.example.dtcemployee.Models.CheckOut.CheckOut;
import com.example.dtcemployee.Models.EmployeeAttendence.EmployeeAttenndence;
import com.example.dtcemployee.Models.EmptoEmpNotification.EmptoEmpNotification;
import com.example.dtcemployee.Models.EndDateNotify.EndDateNotify;
import com.example.dtcemployee.Models.GetAllEmployees.GetAllEmployees;
import com.example.dtcemployee.Models.GetAllEmployeesNotification.GetAllEmployeesNotification;
import com.example.dtcemployee.Models.GetAllLocation.GetAllLocation;
import com.example.dtcemployee.Models.GetAllManagerNotification.GetAllManagerNotification;
import com.example.dtcemployee.Models.GetAllVacationTypye.GetAllVacation;
import com.example.dtcemployee.Models.GetAllVcation.GetAllVaction;
import com.example.dtcemployee.Models.GetEmployeeLocation.GetEmployeeLocation;
import com.example.dtcemployee.Models.GetEmployeeProject.GetEmployeeProject;
import com.example.dtcemployee.Models.GetEmployeeSubEmployee.GetemployeeSubEmployee;
import com.example.dtcemployee.Models.GetSubEmployeeAllNotification.GetSubEmployeeAllNotification;
import com.example.dtcemployee.Models.Notification.EmployeeNotification;
import com.example.dtcemployee.Models.RemoveVacation.RemoveVacation;
import com.example.dtcemployee.Models.SignIn.EmployeeId;
import com.example.dtcemployee.Models.SignIn.SignIn;
import com.example.dtcemployee.Models.SubEmployeeNotification.SubEmployeeNotification;
import com.example.dtcemployee.Models.SubemployeeDetail.SubEmployeeDetail;
import com.example.dtcemployee.Models.UpDateVacationRequest.UpDateVacationRequest;
import com.example.dtcemployee.Models.UploadReportImages.UploadEmployeeImage;
import com.example.dtcemployee.Models.VacationDetail.VacationDetail;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIinterface {
    @POST("Sign_In.php")
    Call<SignIn> SignIn(
            @Query("user_name") String user_name,
            @Query("password") String password,
            @Query("imei_number") String imei_number
    );

    @POST("end_dates_check.php")
    Call<EndDateNotify> Check_end_date(
            @Query("emp_id") String emp_id,
            @Query("current_date") String current_date
    );

    @POST("update_loginStatus.php")
    Call<EmployeeId> updateloginstatus(
            @Query("emp_id") String emp_id,
            @Query("imei_number") String imei_number
    );

    @POST("logout_update.php")
    Call<EmployeeId> updateLogout(
            @Query("emp_id") String emp_id
    );

    @POST("add_report.php")
    Call<AddReport> Addreport(
            @Query("emp_id") String emp_id,
            @Query("project_id") String project_id,
            @Query("target") String target,
            @Query("date_time") String date_time,
            @Query("achievement") String achievement,
            @Query("problems") String problems,
            @Query("manager_id") String manager_id,
            @Query("date") String date,
            @Query("time") String time
    );

    @Multipart
    @POST("upload_report_image.php")
    Call<UploadEmployeeImage> UploadReportFile(
            @Query("emp_id") String emp_id,
            @Query("report_id") String report_id,
            @Part MultipartBody.Part files
    );

    @POST("add_vacation_request.php")
    Call<AddVocation> AddVaction(
            @Query("manager_id") String manager_id,
            @Query("emp_id") String emp_id,
            @Query("emp_name")String emp_name,
            @Query("type_id") String type_id,
            @Query("beginning_date") String beginning_date,
            @Query("ending_date") String ending_date,
            @Query("Reason") String Reason
    );
    @POST("get_all_vacation_request.php")
    Call<GetAllVaction> GetAllVaction(

            @Query("emp_id") String emp_id
    );
    @POST("vacation_request_detail.php")
    Call<VacationDetail> Vacationdetail(

            @Query("id") String id
    );
    @POST("remove_vacation_request.php")
    Call<RemoveVacation> RemoveVacation(

            @Query("id") String id
    );

    @POST("get_vacation_type.php")
    Call<GetAllVacation> GetALlVacation(


    );
    @POST("get_employee_projects.php")
    Call<GetEmployeeProject> GetEmployeeProject(
            @Query("id") String id

    );

    @POST("check_in&check_out.php")
    Call<CheckInCheckOut> CheckIncheckOut(
            @Query("project_id") String project_id,
            @Query("emp_id") String emp_id,
            @Query("latitudes") String latitudes,
            @Query("longitudes") String longitudes,
            @Query("check_in_time") String check_in_time,
            @Query("check_out_time") String check_out_time

    );

    @POST("edit_vacation_request.php")
    Call<UpDateVacationRequest> UpDateVacationRequest(
            @Query("id") String id,
            @Query("type_id") String type_id,
            @Query("beginning_date") String beginning_date,
            @Query("ending_date") String ending_date,
            @Query("Reason") String Reason
    );
    @POST("get_employee_sub_employee.php")
    Call<GetemployeeSubEmployee> getEmployeeSubEmploye(
            @Query("emp_id") String emp_id

    );

    @POST("get_all_employees_for_notification.php")
    Call<GetAllEmployees> getAllEmployees(
            @Query("manager_id") String manager_id

    );

    @POST("get_employee_detail.php")
    Call<SubEmployeeDetail> SubEmployeeDetail(
            @Query("id") String id

    );

    @POST("check_in.php")
    Call<CheckIn> CheckIn(
            @Query("project_id") String project_id,
            @Query("location_id") String location_id,
            @Query("emp_id") String emp_id,
            @Query("latitudes") double latitudes,
            @Query("longitudes") double longitudes,
            @Query("check_in_time") String check_in_time

    );
    @POST("check_out.php")
    Call<CheckOut> CheckOut(
            @Query("project_id") String project_id,
            @Query("location_id") String location_id,
            @Query("emp_id") String emp_id,
            @Query("latitudes") double  latitudes,
            @Query("longitudes") double longitudes,
            @Query("check_out_time") String check_out_time,
            @Query("c_id") String c_id
    );
    @POST("get_employee_attendence_report.php")
    Call<EmployeeAttenndence> Employeeattendance(
            @Query("id") String id

    );

    @POST("get_employee_location.php")
    Call<GetEmployeeLocation> GetEmployeeLocation(
            @Query("id") String id

    );
    @POST("get_all_location.php")
    Call<GetAllLocation> GetAllManagerLocation(
            @Query("manager_id") String manager_id

    );

    @POST("update_employee_device_token.php")
    Call<ResponseBody> updateToken(
            @Query("id") String id,
            @Query("device_token") String token
    );

    @POST("employee_notifications.php")
    Call<EmployeeNotification> CreateNotification(
            @Query("manager_id") String manager_id,
            @Query("employee_id") String employee_id,
            @Query("title") String title,
            @Query("notifications") String notifications,
            @Query("date") String date,
            @Query("time") String time
    );


    @POST("sub_employee_notifications.php")
    Call<SubEmployeeNotification> subEmployeeNotification(

            @Query("employee_id") String employee_id,
            @Query("sub_emp_id") String sub_emp_id,
            @Query("title") String title,
            @Query("notifications") String notifications,
            @Query("date") String date,
            @Query("time") String time
    );

    @POST("emp_to_emp_notification.php")
    Call<EmptoEmpNotification> emptoempNotification(

            @Query("manager_id") String manager_id,
            @Query("employee_id") String employee_id,
            @Query("sub_emp_id") String sub_emp_id,
            @Query("title") String title,
            @Query("notifications") String notifications,
            @Query("date") String date,
            @Query("time") String time
    );

    @POST("get_sub_employee_notifications.php")
    Call<GetSubEmployeeAllNotification> GET_SUB_EMPLOYEE_ALL_NOTIFICATION_CALL(
            @Query("employee_id") String employee_id

    );

    @POST("get_all_emp_to_emp_notifications.php")
    Call<GetAllEmployeesNotification> getAllEmployeesnNotifications(
            @Query("manager_id") String manager_id,
            @Query("employee_id") String employee_id

    );
    @POST("get_notifications.php")
    Call<GetAllManagerNotification> GET_ALL_MANAGER_NOTIFICATION_CALL(

            @Query("employee_id") String employee_id

    );

}
