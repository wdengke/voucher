package com.dengke.excel.voucher.util;

import com.dengke.excel.voucher.model.SecondAccount;
import com.dengke.excel.voucher.model.Voucher;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class WriteExcel {


    private SecondTypeUtil secondTypeUtil;

    @Autowired
    public WriteExcel(SecondTypeUtil secondTypeUtil){
        this.secondTypeUtil=secondTypeUtil;
    }

    public void write(List<Voucher> vouchers, Date date, String companyNo) throws IOException {

        System.out.println("开始写文件....");
        //读取模板
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("static/template.xlsx");

        XSSFWorkbook workbook = new XSSFWorkbook(input);
        input.close();
        XSSFSheet sheet = workbook.getSheetAt(0);

        CellAddress address = new CellAddress("B1");
        XSSFRow row = sheet.getRow(address.getRow());//得到行
        XSSFCell cell = row.getCell(address.getColumn());//得到列
        cell.setCellValue(companyNo);//改变数据

        CellAddress businessDate = new CellAddress("B2");
        row = sheet.getRow(businessDate.getRow());
        cell = row.getCell(businessDate.getColumn());

        cell.setCellValue(date);
        String uuid = UUID.randomUUID().toString();
        int i = 5;
        for (Voucher voucher : vouchers) {
            XSSFRow row1 = sheet.createRow(i);
            row1.createCell(0).setCellValue(voucher.getLoanType().equals("Debit") ? "借方" : "贷方");
            row1.createCell(1).setCellValue(voucher.getAccountId());
            row1.createCell(2).setCellValue(voucher.getDescript());
            row1.createCell(3).setCellValue(voucher.getAmount());
            if (voucher.getSecondAccountType() != null && voucher.getSecondAccountType().length() != 0) {
                //核算维度1
                String type1 = getSecondType(voucher, 1);
                if (type1 != null && type1.length() != 0) {
                    row1.createCell(4).setCellFormula("VLOOKUP(B" + (i + 1) + ",科目!E:F,2,0)");
                    XSSFCell cell1= row1.createCell(5);
                    cell1.setCellType(CellType.STRING);
                    cell1.setCellValue(getAccountId(type1, voucher));
                }
                //核算维度2
                String type2 = getSecondType(voucher, 2);
                if (type2 != null && type2.length() != 0) {
                    row1.createCell(6).setCellFormula("VLOOKUP(B" + (i + 1) + ",科目!G:H,2,0)");
                    XSSFCell cell2=row1.createCell(7);
                    cell2.setCellType(CellType.STRING);
                    cell2.setCellValue(getAccountId(type2, voucher));
                }
                //核算维度3
                String type3 = getSecondType(voucher, 3);
                if (type3 != null && type3.length() != 0) {
                    row1.createCell(8).setCellFormula("VLOOKUP(B" + (i + 1) + ",科目!I:J,2,0)");
                    XSSFCell cell3=row1.createCell(8);
                    cell3.setCellType(CellType.STRING);
                    cell3.setCellValue(getAccountId(type3, voucher));
                }

                //核算维度4
                String type4 = getSecondType(voucher, 4);
                if (type4 != null && type4.length() != 0) {
                    row1.createCell(10).setCellFormula("VLOOKUP(B" + (i + 1) + ",科目!K:L,2,0)");
                    String temp = getAccountId(type4, voucher);
                    XSSFCell cell4=row1.createCell(8);
                    cell4.setCellType(CellType.STRING);
                    cell4.setCellValue(getAccountId(type4, voucher));
                }
            }


            i++;
        }
        sheet.setForceFormulaRecalculation(true);
        OutputStream out = new FileOutputStream("C:\\VoucherUpload/" + companyNo.replace(".", "_") + "_" + uuid + ".xlsx");
        workbook.write(out);
        out.close();

    }


    public void write(List<Voucher>vouchers) throws IOException{
        System.out.println("开始写文件....");
        //读取模板
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("static/template_source.xlsx");

        XSSFWorkbook workbook = new XSSFWorkbook(input);
        input.close();
        XSSFSheet sheet = workbook.getSheetAt(0);
        int i=1;
        for (Voucher voucher:vouchers){
            XSSFRow row = sheet.createRow(i);
            row.createCell(0,CellType.STRING).setCellValue(voucher.getPkid());
            row.createCell(1,CellType.STRING).setCellValue("0"+voucher.getCompanyNo());
            row.createCell(2,CellType.STRING).setCellValue(voucher.getVoucherType());
            row.createCell(3,CellType.STRING).setCellValue(voucher.getVoucherNo());
            row.createCell(4,CellType.STRING).setCellValue(voucher.getDescript());
            row.createCell(5,CellType.STRING).setCellValue(voucher.getAccountId());
            row.createCell(6,CellType.STRING).setCellValue(voucher.getLoanType());
            row.createCell(7,CellType.NUMERIC).setCellValue(voucher.getAmount());
            row.createCell(8,CellType.STRING).setCellValue(voucher.getSecondAccountType());
            row.createCell(9,CellType.STRING).setCellValue(voucher.getSecondAccountName());
            row.createCell(10,CellType.STRING).setCellValue(voucher.getSecondAccountId().startsWith("1.")?"0"+voucher.getSecondAccountId():voucher.getSecondAccountId());
            i++;

        }
        String uuid = UUID.randomUUID().toString();
        sheet.setForceFormulaRecalculation(true);
        OutputStream out = new FileOutputStream("C:\\VoucherUpload/" + "summary_" + uuid + ".xlsx");
        workbook.write(out);
        out.close();
    }

    private String getSecondType(Voucher voucher, int sort) {
        List<SecondAccount> secondAccounts = new ArrayList<>();
        switch (sort) {
            case 1:
                secondAccounts = secondTypeUtil.getSecondAccounts1();
                break;
            case 2:
                secondAccounts = secondTypeUtil.getSecondAccounts2();
                break;
            case 3:
                secondAccounts = secondTypeUtil.getSecondAccounts3();
                break;
            case 4:
                secondAccounts = secondTypeUtil.getSecondAccounts4();
                break;
            default:
                //异常信息
        }
        List<SecondAccount> models = secondAccounts.stream().filter(s -> s.getAccountId().equals(voucher.getAccountId())).collect(Collectors.toList());
        if (models.size() > 0) {
            return models.get(0).getType();
        }
        return "";
    }

    private static String getAccountId(String type, Voucher voucher) {
        if (type == null || type.equals("")) return "";
        Hashtable<String, String> dictionary = new Hashtable<String, String>();
        dictionary.put("Supplier", "供应商");
        dictionary.put("Customer", "客户");
        dictionary.put("Store", "门店");
        dictionary.put("ProductCategory", "物料分组");
        dictionary.put("BankAccount", "银行账号");
        dictionary.put("Department", "部门");
        dictionary.put("SaleChannel", "销售渠道");
        dictionary.put("WareHouse", "仓库");
        dictionary.put("Tax", "税率");
        dictionary.put("Employee", "员工");
        dictionary.put("Company", "组织机构");
        dictionary.put("Group", "组别");
        dictionary.put("PromotionArea", "推广地区");
        dictionary.put("PromotionChannel", "推广渠道");
        dictionary.put("PublicRelation", "公关项目");
        dictionary.put("SalesPlatform", "销售平台");
        dictionary.put("MaterialGrouping", "物料分组");

        String[] types = voucher.getSecondAccountType().split(",");
        String[] ids = voucher.getSecondAccountId().split(",");
        try {
            for (int i = 0; i < types.length; i++) {
                if (type.equals(dictionary.get(types[i]))) {
                    String accountId="";
                    if ("Company".equals(type)){
                        String[]company= ids[i].split(".");
                        String companyNo=company[1].length()==3?company[1]+"0":company[1];
                        accountId="01."+companyNo;
                        System.out.println(accountId);
                    }else {
                        accountId=ids[i];
                    }
                    return accountId;
                }
            }
        }catch (Exception ex){
            System.out.println(voucher.toString());
        }
        return "";
    }
}
