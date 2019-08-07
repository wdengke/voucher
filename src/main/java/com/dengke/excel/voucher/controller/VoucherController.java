package com.dengke.excel.voucher.controller;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.dengke.excel.voucher.listener.ExcelListener;
import com.dengke.excel.voucher.model.ImportVoucher;
import com.dengke.excel.voucher.model.Voucher;
import com.dengke.excel.voucher.util.WriteExcel;
import com.dengke.excel.voucher.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/voucher")
public class VoucherController {


    private WriteExcel writeExcel;
    private RedisUtil redisUtil;

    @Autowired
    public  VoucherController(WriteExcel writeExcel,RedisUtil redisUtil){
        this.writeExcel=writeExcel;
        this.redisUtil=redisUtil;
    }

    @RequestMapping("/")
    public String index(){
        redisUtil.set("test","1234");
        System.out.printf(redisUtil.get("test").toString());
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

                Map<Optional<String>,List<Voucher>>groupBy=vouchers.stream().collect(Collectors.groupingBy(v-> Optional.ofNullable(v.getCompanyNo())));

                for (Optional<String> companyNO:groupBy.keySet()) {
                    if (companyNO.isPresent()){
                        String newFileName=companyNO.get().startsWith("0")?companyNO.get():"0"+companyNO;
                        writeExcel.write(groupBy.get(companyNO),new Date(),newFileName);
                    }
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


    @RequestMapping("/summary/")
    public String summary(){
        return "voucher/test";
    }

    @PostMapping("/upload/summary")
    public String summaryUpload(@RequestParam("file")MultipartFile file,@RequestParam("summary") boolean summary, Model model){
        if (file.isEmpty()){
            model.addAttribute("message","The file is empty!");
            return "voucher/uploadStatus";
        }
        boolean isSuccess=true;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
            String fileName=df.format(new Date())+"_"+file.getOriginalFilename();
            byte[]bytes=file.getBytes();

            Path path= Paths.get("C:\\VoucherUpload/"+ fileName);
            Files.write(path, bytes);


            InputStream inputStream= file.getInputStream();
            try {
                ExcelListener excelListener = new ExcelListener();
                EasyExcelFactory.readBySax(inputStream, new Sheet(1, 1, Voucher.class), excelListener);

                List<Voucher>vouchers= excelListener.getDatas();
                List<Voucher>summaryVouchers=new ArrayList<>();
                vouchers.stream()
                        .collect(Collectors
                        .groupingBy(
                                voucher -> new Voucher(
                                        voucher.getPkid(),
                                        voucher.getCompanyNo(),
                                        voucher.getVoucherType(),
                                        voucher.getVoucherNo(),
                                        voucher.getDescript(),
                                        voucher.getAccountId(),
                                        voucher.getLoanType(),
                                        voucher.getSecondAccountType(),
                                        voucher.getSecondAccountName(),
                                        voucher.getSecondAccountId()),
                                Collectors.summarizingDouble(voucher->voucher.getAmount())
                        )).forEach((k,v)->{
                            k.setAmount(v.getSum());
                            System.out.println(k);
                            summaryVouchers.add(k);
                        });

                //写入excel输出

                if (summary){
                    writeExcel.write(summaryVouchers);
                }else {
                    writeExcel.write(summaryVouchers,new Date(),"0"+summaryVouchers.get(0).getCompanyNo());
                }

                //writeExcel.write(summaryVouchers,new Date(),"0"+summaryVouchers.get(0).getCompanyNo());

            }catch(Exception e){
                e.printStackTrace();
                isSuccess=false;
            }
            finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    isSuccess=false;
                }
            }


        }catch (Exception ex){
            ex.printStackTrace();
            isSuccess=false;
        }
        model.addAttribute("message", isSuccess?"success":"Exception");
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
