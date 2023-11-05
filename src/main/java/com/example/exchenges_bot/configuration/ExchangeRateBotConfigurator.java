package com.example.exchenges_bot.configuration;

import com.example.exchenges_bot.bot.ExchangeRateBot;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class ExchangeRateBotConfigurator {


    @Bean
    public TelegramBotsApi telegramBotsApi(ExchangeRateBot exchangeRateBot)  throws TelegramApiException {
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(exchangeRateBot);
        return api;
    }

    @Bean
    public OkHttpClient okHttpClient(){
        return new OkHttpClient();
    }


}
