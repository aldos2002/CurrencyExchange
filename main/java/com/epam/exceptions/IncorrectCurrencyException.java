package com.epam.exceptions;

import com.epam.entity.Currency;

/**
 * Created by Almas_Doskozhin
 * on 2/21/2016.
 */
public class IncorrectCurrencyException extends BusinessException{
    private final Currency currency;

    public IncorrectCurrencyException(Currency currency) {
        this.currency = currency;
    }

   @Override
    public String getMessage() {
        return "Currency "+currency.toString()+" does not exist.";
    }
}
