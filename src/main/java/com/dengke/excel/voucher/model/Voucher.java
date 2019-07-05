package com.dengke.excel.voucher.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.util.Objects;

public class Voucher extends BaseRowModel {
    @ExcelProperty(index = 0)
    private int pkid;

    @ExcelProperty(index = 1)
    private String companyNo;

    @ExcelProperty(index = 2)
    private String voucherType;

    @ExcelProperty(index = 3)
    private String voucherNo;

    @ExcelProperty(index = 4)
    private String descript;

    @ExcelProperty(index = 5)
    private String accountId;

    @ExcelProperty(index = 6)
    private String loanType;

    @ExcelProperty(index = 7)
    private double amount;

    @ExcelProperty(index = 8)
    private String secondAccountType;

    @ExcelProperty(index = 9)
    private String secondAccountName;

    @ExcelProperty(index = 10)
    private String secondAccountId;

    public int getPkid() {
        return pkid;
    }

    public void setPkid(int pkid) {
        this.pkid = pkid;
    }

    public String getCompanyNo() {
        return companyNo;
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSecondAccountType() {
        return secondAccountType;
    }

    public void setSecondAccountType(String secondAccountType) {
        this.secondAccountType = secondAccountType;
    }

    public String getSecondAccountName() {
        return secondAccountName;
    }

    public void setSecondAccountName(String secondAccountName) {
        this.secondAccountName = secondAccountName;
    }

    public String getSecondAccountId() {
        return secondAccountId;
    }

    public void setSecondAccountId(String secondAccountId) {
        this.secondAccountId = secondAccountId;
    }

    public Voucher(){

    }

    public Voucher(int pkid, String companyNo, String voucherType, String voucherNo, String descript, String accountId, String loanType, String secondAccountType, String secondAccountName, String secondAccountId) {
        this.pkid = pkid;
        this.companyNo = companyNo;
        this.voucherType = voucherType;
        this.voucherNo = voucherNo;
        this.descript = descript;
        this.accountId = accountId;
        this.loanType = loanType;
        this.secondAccountType = secondAccountType;
        this.secondAccountName = secondAccountName;
        this.secondAccountId = secondAccountId;
    }

    @Override
    public String toString() {
        return "Voucher{" +
                "pkid=" + pkid +
                ", companyNo='" + companyNo + '\'' +
                ", voucherType='" + voucherType + '\'' +
                ", voucherNo='" + voucherNo + '\'' +
                ", descript='" + descript + '\'' +
                ", accountId='" + accountId + '\'' +
                ", loanType='" + loanType + '\'' +
                ", amount=" + amount +
                ", secondAccountType='" + secondAccountType + '\'' +
                ", secondAccountName='" + secondAccountName + '\'' +
                ", secondAccountId='" + secondAccountId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voucher voucher = (Voucher) o;
        return companyNo.equals(voucher.companyNo) &&
                voucherType.equals(voucher.voucherType) &&
                voucherNo.equals(voucher.voucherNo) &&
                accountId.equals(voucher.accountId) &&
                loanType.equals(voucher.loanType) &&
                Objects.equals(secondAccountType, voucher.secondAccountType) &&
                Objects.equals(secondAccountName, voucher.secondAccountName) &&
                Objects.equals(secondAccountId, voucher.secondAccountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyNo, voucherType, voucherNo, accountId, loanType, secondAccountType, secondAccountName, secondAccountId);
    }
}
