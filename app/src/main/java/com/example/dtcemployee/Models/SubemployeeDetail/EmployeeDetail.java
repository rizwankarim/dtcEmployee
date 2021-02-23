
package com.example.dtcemployee.Models.SubemployeeDetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeDetail {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("manager_id")
    @Expose
    private String managerId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("basic_salary")
    @Expose
    private String basicSalary;
    @SerializedName("expenses")
    @Expose
    private String expenses;
    @SerializedName("over_time")
    @Expose
    private String overTime;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("Employee_id")
    @Expose
    private String employeeId;
    @SerializedName("File")
    @Expose
    private String file;
    @SerializedName("End_Date")
    @Expose
    private String endDate;
    @SerializedName("Joining_Date")
    @Expose
    private String joiningDate;
    @SerializedName("Joining_File")
    @Expose
    private String joiningFile;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("Passport_No")
    @Expose
    private String passportNo;
    @SerializedName("Passport_File")
    @Expose
    private String passportFile;
    @SerializedName("Passport_End_Date")
    @Expose
    private String passportEndDate;
    @SerializedName("Contract_End_Date")
    @Expose
    private String contract_end_date;

    @SerializedName("Sub_Employee")
    @Expose
    private List<Object> subEmployee = null;

    public String getContract_end_date() {
        return contract_end_date;
    }

    public void setContract_end_date(String contract_end_date) {
        this.contract_end_date = contract_end_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(String basicSalary) {
        this.basicSalary = basicSalary;
    }

    public String getExpenses() {
        return expenses;
    }

    public void setExpenses(String expenses) {
        this.expenses = expenses;
    }

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getJoiningFile() {
        return joiningFile;
    }

    public void setJoiningFile(String joiningFile) {
        this.joiningFile = joiningFile;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getPassportFile() {
        return passportFile;
    }

    public void setPassportFile(String passportFile) {
        this.passportFile = passportFile;
    }

    public String getPassportEndDate() {
        return passportEndDate;
    }

    public void setPassportEndDate(String passportEndDate) {
        this.passportEndDate = passportEndDate;
    }

    public List<Object> getSubEmployee() {
        return subEmployee;
    }

    public void setSubEmployee(List<Object> subEmployee) {
        this.subEmployee = subEmployee;
    }

}
