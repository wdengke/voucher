package com.dengke.excel.voucher.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.dengke.excel.voucher.model.Voucher;

import java.util.ArrayList;
import java.util.List;

public class ExcelListener extends AnalysisEventListener {
    public ExcelListener() {
        super();
    }

    private List<Voucher>datas=new ArrayList<Voucher>();

    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        datas.add((Voucher) o);

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //datas.clear();
    }

    public List<Voucher> getDatas() {
        return datas;
    }
    public void setDatas(List<Voucher> datas) {
        this.datas = datas;
    }
}
