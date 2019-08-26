package com.duke.drools.controller;

import com.duke.drools.model.*;
import com.duke.drools.model.fact.AddressCheckResult;
import com.duke.drools.model.fact.CheckResult;
import com.duke.drools.model.fact.VoucherCheckResult;
import com.duke.drools.service.ReloadDroolsRulesService;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/test")
@Controller
public class TestController {
    @Resource
    private ReloadDroolsRulesService rules;

    @Resource
    private KieContainer kieContainer;

    @ResponseBody
    @RequestMapping("/address")
    public void test(int num){
        Address address = new Address();
        address.setPostcode(generateRandom(num));
        KieSession kieSession = ReloadDroolsRulesService.kieContainer.newKieSession();

        AddressCheckResult result = new AddressCheckResult();
        kieSession.insert(address);
        kieSession.insert(result);
        int ruleFiredCount = kieSession.fireAllRules();
        kieSession.destroy();
        System.out.println("触发了" + ruleFiredCount + "条规则");

        if(result.isPostCodeResult()){
            System.out.println("规则校验通过");
        }

    }
    @ResponseBody
    @RequestMapping("/voucher")
    public void voucher(){
        KieSession kieSession =kieContainer.newKieSession();
        ThOrder order=new ThOrder();
        order.setOrderType("1普通");
        order.setSelfShop(true);

        FactHandle handle = kieSession.insert(order);

        VoucherCheckResult result=new VoucherCheckResult();
        kieSession.insert(order);
        kieSession.insert(result);

        System.out.println(handle.toExternalForm());
        int ruleFiredCount = kieSession.fireAllRules();

        kieSession.destroy();
        System.out.println("触发了" + ruleFiredCount + "条规则");

        if(result.isVoucherResult()){
            System.out.println("规则校验通过");
            order.getTemplateKeys().forEach(s-> System.out.println(s));
        }
    }

    @ResponseBody
    @RequestMapping("/test")
    public void demo(){
        KieSession kieSession= kieContainer.newKieSession();
       // Item item=new Item(1l,"test",100.00,200.00);
        Order order=generateOrder();
        kieSession.insert(order);
        kieSession.insert(order.getCustomer());
        kieSession.insert(order.getOrderLines().get(0));
        kieSession.insert(order.getOrderLines().get(1));
        kieSession.insert(order.getOrderLines().get(2));
        kieSession.insert(order.getOrderLines().get(3));
        kieSession.insert(order.getOrderLines().get(4));
        kieSession.insert(order.getOrderLines().get(5));
        kieSession.insert(order.getOrderLines().get(0).getItem());
        kieSession.insert(order.getOrderLines().get(1).getItem());
        kieSession.insert(order.getOrderLines().get(2).getItem());
        kieSession.insert(order.getOrderLines().get(3).getItem());
        kieSession.insert(order.getOrderLines().get(4).getItem());
        kieSession.insert(order.getOrderLines().get(5).getItem());
        CheckResult checkResult=new CheckResult();
        kieSession.insert(checkResult);
        int count= kieSession.fireAllRules();
        kieSession.destroy();
        System.out.println("触发了"+count+"规则");
        System.out.println(order.getDiscount());
        if (!checkResult.getRules().isEmpty()){
            System.out.println("触发了以下规则！");
            checkResult.getRules().forEach(r->{
                System.out.println(r);
            });
        }
    }

    /**
     * 从数据加载最新规则
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/reload")
    public String reload() throws IOException {
        rules.reload();
        return "ok";
    }


    /**
     * 生成随机数
     * @param num
     * @return
     */
    public String generateRandom(int num) {
        String chars = "0123456789";
        StringBuffer number=new StringBuffer();
        for (int i = 0; i < num; i++) {
            int rand = (int) (Math.random() * 10);
            number=number.append(chars.charAt(rand));
        }
        return number.toString();
    }

    private Order generateOrder(){
        Order order=new Order();
        OrderLine orderLine=new OrderLine();
        List<OrderLine>items=new ArrayList<>();
        orderLine.setItem(new Item(1l,"item 1",30.0,30.0));
        items.add(orderLine);
        orderLine.setItem(new Item(2l,"item 2",40.0,50.0));
        items.add(orderLine);
        orderLine.setItem(new Item(3l,"item 3",50.0,60.0));
        items.add(orderLine);
        orderLine.setItem(new Item(4l,"item 4",30.0,30.0));
        items.add(orderLine);
        orderLine.setItem(new Item(5l,"item 5",40.0,50.0));
        items.add(orderLine);
        orderLine.setItem(new Item(6l,"item 6",50.0,60.0));
        items.add(orderLine);
        order.setItems(items);
        Customer customer=new Customer();
        order.setCustomer(customer);
        return order;
    }

}
