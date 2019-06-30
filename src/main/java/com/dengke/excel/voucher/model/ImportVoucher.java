package com.dengke.excel.voucher.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class ImportVoucher extends BaseRowModel {

    @ExcelProperty(index = 0)
    private String loanType;

    @ExcelProperty(index = 1)
    private String accountId;

    @ExcelProperty(index = 2)
    private String descript;

    @ExcelProperty(index = 3)
    private double amount;

    @ExcelProperty(index = 4)
    private String secondAccountType1;

    @ExcelProperty(index = 5)
    private String secondAccountId1;

    @ExcelProperty(index = 6)
    private String secondAccountType2;

    @ExcelProperty(index = 7)
    private String secondAccountId2;

    @ExcelProperty(index = 8)
    private String secondAccountType3;

    @ExcelProperty(index = 9)
    private String secondAccountId3;

    @ExcelProperty(index = 10)
    private String secondAccountType4;

    @ExcelProperty(index = 11)
    private String secondAccountId4;

    @ExcelProperty(index = 12)
    private String secondAccountType5;

    @ExcelProperty(index = 13)
    private String secondAccountId5;

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSecondAccountType1() {
        return secondAccountType1;
    }

    public void setSecondAccountType1(String secondAccountType1) {
        this.secondAccountType1 = secondAccountType1;
    }

    public String getSecondAccountId1() {
        return secondAccountId1;
    }

    public void setSecondAccountId1(String secondAccountId1) {
        this.secondAccountId1 = secondAccountId1;
    }

    public String getSecondAccountType2() {
        return secondAccountType2;
    }

    public void setSecondAccountType2(String secondAccountType2) {
        this.secondAccountType2 = secondAccountType2;
    }

    public String getSecondAccountId2() {
        return secondAccountId2;
    }

    public void setSecondAccountId2(String secondAccountId2) {
        this.secondAccountId2 = secondAccountId2;
    }

    public String getSecondAccountType3() {
        return secondAccountType3;
    }

    public void setSecondAccountType3(String secondAccountType3) {
        this.secondAccountType3 = secondAccountType3;
    }

    public String getSecondAccountId3() {
        return secondAccountId3;
    }

    public void setSecondAccountId3(String secondAccountId3) {
        this.secondAccountId3 = secondAccountId3;
    }

    public String getSecondAccountType4() {
        return secondAccountType4;
    }

    public void setSecondAccountType4(String secondAccountType4) {
        this.secondAccountType4 = secondAccountType4;
    }

    public String getSecondAccountId4() {
        return secondAccountId4;
    }

    public void setSecondAccountId4(String secondAccountId4) {
        this.secondAccountId4 = secondAccountId4;
    }

    public String getSecondAccountType5() {
        return secondAccountType5;
    }

    public void setSecondAccountType5(String secondAccountType5) {
        this.secondAccountType5 = secondAccountType5;
    }

    public String getSecondAccountId5() {
        return secondAccountId5;
    }

    public void setSecondAccountId5(String secondAccountId5) {
        this.secondAccountId5 = secondAccountId5;
    }
}
