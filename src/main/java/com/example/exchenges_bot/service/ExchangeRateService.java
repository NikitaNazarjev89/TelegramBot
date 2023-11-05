package com.example.exchenges_bot.service;

import com.example.exchenges_bot.exception.ServiceException;

public interface ExchangeRateService {

    String getUSDExchangeRate() throws ServiceException;

    String getEURExchangeRate() throws ServiceException;
}
