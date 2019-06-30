package com.dengke.excel.voucher.controller;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.dengke.excel.voucher.listener.ExcelListener;
import com.dengke.excel.voucher.model.ImportVoucher;
import com.dengke.excel.voucher.model.Voucher;
import com.dengke.excel.voucher.util.SecondTypeUtil;
import com.dengke.excel.voucher.util.WriteExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/voucher")
public class VoucherController {

    @Autowired
    WriteExcel writeExcel;

    @RequestMapping("/")
    public String index(){
        return "voucher/voucher";
    }
    @PostMapping("/upload")
    public String upload(@RequestParam("file")MultipartFile file, Model model) {
        if (file.isEmpty()){
            model.addAttribute("message","The file is empty!");
            return "voucher/uploadStatus";
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
            String fileName=df.format(new Date())+"_"+file.getOriginalFilename();
            byte[]bytes=file.getBytes();

            Path path= Paths.get("C:\\VoucherUpload/"+ fileName);
            Files.write(path, bytes);
            model.addAttribute("message", "succes");

            InputStream inputStream= file.getInputStream();
            try {
                ExcelListener excelListener = new ExcelListener();
                EasyExcelFactory.readBySax(inputStream, new Sheet(1, 1, Voucher.class), excelListener);

                List<Voucher>vouchers= excelListener.getDatas();

                Map<String,List<Voucher>>groupBy=vouchers.stream().collect(Collectors.groupingBy(Voucher::getCompanyNo));

                for (String companyNO:groupBy.keySet()) {
                    String newFileName=companyNO.startsWith("0")?companyNO:"0"+companyNO;
                    writeExcel.write(groupBy.get(companyNO),new Date(),newFileName);

                }



            }catch(Exception e){
                e.printStackTrace();
            }
            finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }catch (Exception ex){
            ex.printStackTrace();

        }
        return "voucher/uploadStatus";
    }


    private List<ImportVoucher>createVoucher(List<Voucher>vouchers,String companyNo){
        List<ImportVoucher>importVouchers=new ArrayList<>();

//        //账簿
//        ImportVoucher voucher1=new ImportVoucher();
//        voucher1.setLoanType("账簿编码(公司代码)");
//        voucher1.setAccountId(companyNo);
//        voucher1.setSecondAccountType1("借方合计");
//
//        importVouchers.add(voucher1);

        for(Voucher voucher:vouchers){
            ImportVoucher voucher1=new ImportVoucher();
            String loanType=voucher.getLoanType()=="Credit"?"贷方":"借方";
            voucher1.setLoanType(loanType);
            voucher1.setDescript(voucher.getDescript());
            voucher1.setAccountId(voucher.getAccountId());
            importVouchers.add(voucher1);
        }
        return importVouchers;
    }
}
