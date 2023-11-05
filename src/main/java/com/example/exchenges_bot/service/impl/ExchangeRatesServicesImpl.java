package com.example.exchenges_bot.service.impl;

import com.example.exchenges_bot.client.CbrClient;
import com.example.exchenges_bot.exception.ServiceException;
import com.example.exchenges_bot.service.ExchangeRateService;
import com.example.exchenges_bot.service.XMLparser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import org.w3c.dom.Document;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;


@Service
public class ExchangeRatesServicesImpl implements ExchangeRateService {

    private static final String USD_XPATH = "/ValCurs//Valute[@ID='R01235']/Value";
    private static final String EUR_XPATH = "/ValCurs//Valute[@ID='R01239']/Value";
    @Autowired
    CbrClient client;


    @Override
    public String getUSDExchangeRate() throws ServiceException {
        var xmlOptional = client.getCurrencyRatesXML();
        System.out.println(xmlOptional);
        return extractCurrencyValueFromXML(xmlOptional,USD_XPATH);
    }

    @Override
    public String getEURExchangeRate() throws ServiceException {
        var xmlOptional = client.getCurrencyRatesXML();
        return extractCurrencyValueFromXML(xmlOptional,EUR_XPATH);
    }

    private static String extractCurrencyValueFromXML(String xml, String xpathExpression) throws ServiceException{
         //var source = new InputSource(new StringReader(xml));
        String urlCTB = "http://www.cbr.ru/scripts/xml_daily.asp";
        String result = "";
        try {
//             var xpath = XPathFactory.newInstance().newXPath();
//             var document = (Document)xpath.evaluate("/",source, XPathConstants.NODE);
//             return xpath.evaluate(xpathExpression,document);
//             //}catch (XPathExpressionException e){
              URL url = new URL(urlCTB);
              InputStream input =  url.openStream();
            while (input.available()>0){
                result+=(char)input.read();
            }
            return XMLparser.parseEUR(result);
         }catch (IOException e){
             throw new ServiceException("Не удалось распарсить XML", e);
         }
    }
}
