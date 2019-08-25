package com.duke.drools.model.fact;

public class VoucherCheckResult {

    private boolean voucherResult=false;

    private String templateKey;

    public boolean isVoucherResult() {
        return voucherResult;
    }

    public void setVoucherResult(boolean voucherResult) {
        this.voucherResult = voucherResult;
    }

    public String getTemplateKey() {
        return templateKey;
    }

    public void setTemplateKey(String templateKey) {
        this.templateKey = templateKey;
    }
}
