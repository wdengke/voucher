package com.duke.drools.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int pkid;

    private String orderType;

    private String ordrerChannel;

    private int installShopId;

    private String installShopName;

    /**
     * 自营店
     */
    private boolean selfShop;

    /**
     * 工厂店
     */
    private boolean factoryShop;

    /**
     * 合作门店
     */
    private boolean cooperationShop;

    /**
     * 财务入账时间
     */
    private String outStockTime;

    private String payMethod;

    /**
     * 大客户订单
     */

    private boolean bigCustomer;

    private List<String>templateKeys;

    public List<String> getTemplateKeys() {
        return templateKeys;
    }

    public Order() {
        templateKeys=new ArrayList<>();
    }

    public int getPkid() {
        return pkid;
    }

    public void setPkid(int pkid) {
        this.pkid = pkid;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrdrerChannel() {
        return ordrerChannel;
    }

    public void setOrdrerChannel(String ordrerChannel) {
        this.ordrerChannel = ordrerChannel;
    }

    public int getInstallShopId() {
        return installShopId;
    }

    public void setInstallShopId(int installShopId) {
        this.installShopId = installShopId;
    }

    public String getInstallShopName() {
        return installShopName;
    }

    public void setInstallShopName(String installShopName) {
        this.installShopName = installShopName;
    }

    public boolean isSelfShop() {
        return selfShop;
    }

    public void setSelfShop(boolean selfShop) {
        this.selfShop = selfShop;
    }

    public boolean isFactoryShop() {
        return factoryShop;
    }

    public void setFactoryShop(boolean factoryShop) {
        this.factoryShop = factoryShop;
    }

    public boolean isCooperationShop() {
        return cooperationShop;
    }

    public void setCooperationShop(boolean cooperationShop) {
        this.cooperationShop = cooperationShop;
    }

    public String getOutStockTime() {
        return outStockTime;
    }

    public void setOutStockTime(String outStockTime) {
        this.outStockTime = outStockTime;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public boolean isBigCustomer() {
        return bigCustomer;
    }

    public void setBigCustomer(boolean bigCustomer) {
        this.bigCustomer = bigCustomer;
    }
}
