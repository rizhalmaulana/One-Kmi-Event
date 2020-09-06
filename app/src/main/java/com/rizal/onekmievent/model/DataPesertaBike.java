package com.rizal.onekmievent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataPesertaBike {
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
    @SerializedName("txtUrlImage")
    @Expose
    private String txtUrlImage;
    @SerializedName("txtKategoriGender")
    @Expose
    private String txtKategoriGender;
    @SerializedName("txtKategoriSepeda")
    @Expose
    private String txtKategoriSepeda;
    @SerializedName("txtWaktuTempuh")
    @Expose
    private String txtWaktuTempuh;
    @SerializedName("txtJarakTempuh")
    @Expose
    private String txtJarakTempuh;

    public DataPesertaBike(){

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

    public String getTxtUrlImage() {
        return txtUrlImage;
    }

    public void setTxtUrlImage(String txtUrlImage) {
        this.txtUrlImage = txtUrlImage;
    }

    public String getTxtKategoriGender() {
        return txtKategoriGender;
    }

    public void setTxtKategoriGender(String txtKategoriGender) {
        this.txtKategoriGender = txtKategoriGender;
    }

    public String getTxtKategoriSepeda() {
        return txtKategoriSepeda;
    }

    public void setTxtKategoriSepeda(String txtKategoriSepeda) {
        this.txtKategoriSepeda = txtKategoriSepeda;
    }

    public String getTxtWaktuTempuh() {
        return txtWaktuTempuh;
    }

    public void setTxtWaktuTempuh(String txtWaktuTempuh) {
        this.txtWaktuTempuh = txtWaktuTempuh;
    }

    public String getTxtJarakTempuh() {
        return txtJarakTempuh;
    }

    public void setTxtJarakTempuh(String txtJarakTempuh) {
        this.txtJarakTempuh = txtJarakTempuh;
    }



}
