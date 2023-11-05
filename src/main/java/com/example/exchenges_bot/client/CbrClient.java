package com.example.exchenges_bot.client;

import com.example.exchenges_bot.exception.ServiceException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class CbrClient {

    @Autowired
    private OkHttpClient client;

    @Value("${cbr.currency.rates.xml.url}")
    private String cbrCurrencyRatesXmlUrl;


    public String getCurrencyRatesXML() throws ServiceException {
        var request = new Request.Builder()
                .url(cbrCurrencyRatesXmlUrl)
                .build();
        try (var responce = client.newCall(request).execute()){

            var body = responce.body();

            return body == null ? null : body.string();

        }catch (IOException e){
            throw new ServiceException("Ошибка получения курсов валют", e);
        }
    }
}
