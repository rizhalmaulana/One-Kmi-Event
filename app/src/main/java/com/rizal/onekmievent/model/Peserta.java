package com.rizal.onekmievent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Peserta {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("intNo")
    @Expose
    private String intNo;
    @SerializedName("varcharEmployeeID")
    @Expose
    private String varcharEmployeeID;
    @SerializedName("txtEmployeeName")
    @Expose
    private String txtEmployeeName;
    @SerializedName("txtJobTitle")
    @Expose
    private String txtJobTitle;
    @SerializedName("txtGroupName")
    @Expose
    private String txtGroupName;
    @SerializedName("dateJoinDate")
    @Expose
    private String dateJoinDate;

    public Peserta(){

    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getIntNo() {
        return intNo;
    }

    public void setIntNo(String intNo) {
        this.intNo = intNo;
    }

    public String getVarcharEmployeeID() {
        return varcharEmployeeID;
    }

    public void setVarcharEmployeeID(String varcharEmployeeID) {
        this.varcharEmployeeID = varcharEmployeeID;
    }

    public String getTxtEmployeeName() {
        return txtEmployeeName;
    }

    public void setTxtEmployeeName(String txtEmployeeName) {
        this.txtEmployeeName = txtEmployeeName;
    }

    public String getTxtJobTitle() {
        return txtJobTitle;
    }

    public void setTxtJobTitle(String txtJobTitle) {
        this.txtJobTitle = txtJobTitle;
    }

    public String getTxtGroupName() {
        return txtGroupName;
    }

    public void setTxtGroupName(String txtGroupName) {
        this.txtGroupName = txtGroupName;
    }

    public String getDateJoinDate() {
        return dateJoinDate;
    }

    public void setDateJoinDate(String dateJoinDate) {
        this.dateJoinDate = dateJoinDate;
    }

}
