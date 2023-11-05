package com.example.exchenges_bot.bot;

import com.example.exchenges_bot.exception.ServiceException;
import com.example.exchenges_bot.service.ExchangeRateService;
import com.example.exchenges_bot.service.impl.ExchangeRatesServicesImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;

@Component
public class ExchangeRateBot extends TelegramLongPollingBot {

    private static final Logger LOG = LoggerFactory.getLogger(ExchangeRateBot.class);

    private static final String START = "/start";
    private static final String USD = "/usd";
    private static final String EUR = "/eur";
    private static final String HELP = "/help";

    @Autowired
    ExchangeRateService exchangeRateService;

    public ExchangeRateBot(@Value("${bot.token}") String botToken){
        super(botToken);
    }
    @Override
    public void onUpdateReceived(Update update) {
         if(!update.hasMessage() || !update.getMessage().hasText()){
             return;
         }
         var message = update.getMessage().getText();
         var chatID = update.getMessage().getChatId();
         switch (message){
             case START -> {
                 String userName = update.getMessage().getChat().getUserName();
                 startCommand(chatID,userName);
                 LOG.info("Пользователь нажал комманду start");
             }
             case USD -> {
                 usdCommand(chatID);
                 LOG.info("Пользователь нажал комманду USD");
             }
             case EUR -> {
                 eurCommand(chatID);
                 LOG.info("Пользователь нажал комманду EUR");
             }
             case HELP -> {helpCommand(chatID);}
             default -> unknowCommand(chatID);

         }
    }
    @Override
    public String getBotUsername() {
        return "valute_parser_for_java_bot";
    }
    public void sendMessage(Long chatID, String text){
          var chatIdStr = String.valueOf(chatID);
          var sendMessage = new SendMessage(chatIdStr,text);
          try{
               execute(sendMessage);
          }catch (TelegramApiException e){
              LOG.error("Ошибка отправки сообщения",e);
          }
    }

    private void unknowCommand(Long chatID){
        var text = "Не удалось распознать комманду";
        sendMessage(chatID, text);
    }


    private void startCommand(Long chatID, String userName){

        var text = "Привет %s, в этом боте можно посмотреть актуальные курсы валют, " +
                "Для начала работы воспользуйся коммандами " +
                "/usd - для получения курса доллара."+
                 "/eur - для получения курса евро." +
                "/help - получение справки.";

        var formattedText = String.format(text,userName);
        sendMessage(chatID,formattedText);
    }


    private void usdCommand(Long chatID){
        String formatedText;
        try{
           var usd = exchangeRateService.getUSDExchangeRate();
           var text = "Курс доллара на %s составляет %s рублей.";
           formatedText = String.format(text, LocalDate.now(), usd);

        }catch (ServiceException e){
            LOG.error("Ошибка получения курса доллара ", e);
            formatedText = "Не удалось получить текущий курс доллара.";
        }
        sendMessage(chatID,formatedText);
    }

    private void eurCommand(Long chatID){
        String formatedText;
        try{
            var usd = exchangeRateService.getUSDExchangeRate();
            var text = "Курс евро на %s составляет %s рублей.";
            formatedText = String.format(text, LocalDate.now(), usd);

        }catch (ServiceException e){
            LOG.error("Ошибка получения курса евро ", e);
            formatedText = "Не удалось получить текущий курс евро.";
        }
        sendMessage(chatID,formatedText);
    }


    private void helpCommand(Long chatID){
        String text = "Для получения курса доллара воспользуйтесь коммандой /usd /n"+
                      "Для получения курса евро воспользуйтесь коммандой /eur";
        sendMessage(chatID,text);
    }
}
