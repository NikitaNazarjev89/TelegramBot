package com.example.exchenges_bot.service;

public class XMLparser {


    public static String parseUSD(String str){
        String result = "Не получилось распарсить";
        int a = str.indexOf("<Valute ID=\"R01235\">");
        String val = "<Value>";
        int startValue = str.indexOf(val,a);
        int startTeg = str.indexOf(">",startValue)+1;
        int endTeg = str.indexOf("<",startTeg);
        result = str.substring(startTeg,endTeg);
        //System.out.println(str.substring(startTeg,endTeg));
     return result;
    }

    public static String parseEUR(String str){
        String result = "Не получилось распарсить";
        int a = str.indexOf("<Valute ID=\"R01239\">");
        String val = "<Value>";
        int startValue = str.indexOf(val,a);
        int startTeg = str.indexOf(">",startValue)+1;
        int endTeg = str.indexOf("<",startTeg);
        result = str.substring(startTeg,endTeg);
        //System.out.println(str.substring(startTeg,endTeg));
        return result;
    }

}
