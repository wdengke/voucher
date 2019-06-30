package com.dengke.excel.voucher.util;

import com.dengke.excel.voucher.model.SecondAccount;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public  class  SecondTypeUtil{

    //核算维度1
    private static  List<SecondAccount> SECOND_ACCOUNTS_1 = new ArrayList<>();

    //核算维度2
    private static  List<SecondAccount> SECOND_ACCOUNTS_2 = new ArrayList<>();

    //核算维度3
    private static  List<SecondAccount> SECOND_ACCOUNTS_3 = new ArrayList<>();

    //核算维度4
    private static  List<SecondAccount> SECOND_ACCOUNTS_4 = new ArrayList<>();

    public SecondTypeUtil() {
        SECOND_ACCOUNTS_1=getSecondAccount("SecondAccountType1.json");
        SECOND_ACCOUNTS_2=getSecondAccount("SecondAccountType2.json");
        SECOND_ACCOUNTS_3=getSecondAccount("SecondAccountType3.json");
        SECOND_ACCOUNTS_4=getSecondAccount("SecondAccountType4.json");
        System.out.println("SecondTypeUtil Init");
    }

    public static List<SecondAccount> getSecondAccounts1() {
        return SECOND_ACCOUNTS_1;
    }

    public static List<SecondAccount> getSecondAccounts2() {
        return SECOND_ACCOUNTS_2;
    }

    public static List<SecondAccount> getSecondAccounts3() {
        return SECOND_ACCOUNTS_3;
    }

    public static List<SecondAccount> getSecondAccounts4() {
        return SECOND_ACCOUNTS_4;
    }

    private List<SecondAccount> getSecondAccount(String fileName) {
        List<SecondAccount>result=new ArrayList<>();
        InputStream stream=Thread.currentThread().getContextClassLoader().getResourceAsStream("static/"+fileName);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node= null;
        try {
            node = mapper.readTree(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonNode nodes= node.path("list");
        Iterator<JsonNode> iterator=nodes.elements();
        while (iterator.hasNext()){
            JsonNode temp = iterator.next();
            String accountId= temp.path("AccountId").asText("");
            String type=temp.path("Type").asText("");
            SecondAccount secondAccount=new SecondAccount();
            secondAccount.setAccountId(accountId);
            secondAccount.setType(type);
            result.add(secondAccount);
        }
        System.out.println("getSecondAccount:"+fileName+result.size());

        return result;
    }
}
