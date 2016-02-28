package com.epam.service;

import com.epam.dao.EntityDao;
import com.epam.entity.Currency;
import com.epam.entity.UserAccount;
import com.epam.exceptions.BusinessException;
import com.epam.exceptions.FundsOverdrawnException;
import com.epam.exceptions.IncorrectCurrencyException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Almas_Doskozhin
 * on 2/22/2016.
 */
public class ExchangeService {
    private static final Logger LOGGER = Logger.getLogger(ExchangeService.class);

    public synchronized void convert(Currency from, Currency to, BigDecimal amount, String userName)
            throws IOException, BusinessException{
        LOGGER.debug("Convert "+amount+" "+from.toString()+" to "+to.toString());
        EntityDao entityDao = new EntityDao();
        BigDecimal fromRate = entityDao.getExchangeRate(from);
        BigDecimal toRate =  entityDao.getExchangeRate(to);
        BigDecimal rate = fromRate.divide(toRate, 4, RoundingMode.CEILING);
        BigDecimal convertedAmount = amount.multiply(rate);
        LOGGER.debug(amount+" "+from.toString()+" converted to "+convertedAmount+" "+to.toString()+".");
        UserAccount user = entityDao.readUserAccount(userName);
        switch(from){
            case USD:
                user.setDollarAmount(withdraw(user.getDollarAmount(),amount, from, userName));
                break;
            case EUR:
                user.setEuroAmount(withdraw(user.getEuroAmount(),amount, from, userName));
                break;
            case RUBLE:
                user.setRubleAmount(withdraw(user.getRubleAmount(),amount, from, userName));
                break;
            case KZT:
                user.setKZTAmount(withdraw(user.getKZTAmount(),amount, from, userName));
                break;
            default:
                throw new IncorrectCurrencyException(from);
        }

        switch(to){
            case USD:
                user.setDollarAmount(user.getDollarAmount().add(convertedAmount));
                break;
            case EUR:
                user.setEuroAmount(user.getEuroAmount().add(convertedAmount));
                break;
            case RUBLE:
                user.setRubleAmount(user.getRubleAmount().add(convertedAmount));
                break;
            case KZT:
                user.setKZTAmount(user.getKZTAmount().add(convertedAmount));
                break;
            default:
                throw new IncorrectCurrencyException(to);
        }
        entityDao.saveUserAccount(user);
    }

    private BigDecimal withdraw(BigDecimal currencyAmount, BigDecimal withdrawAmount, Currency currency, String user)
            throws FundsOverdrawnException{
        BigDecimal resultAmount = currencyAmount.subtract(withdrawAmount);
        if(resultAmount.compareTo(BigDecimal.ZERO) < 0){
            throw new FundsOverdrawnException(currencyAmount, user, currency);
        } else {
            return resultAmount;
        }
    }
}
