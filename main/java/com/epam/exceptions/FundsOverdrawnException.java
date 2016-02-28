package com.epam.exceptions;

import com.epam.entity.Currency;

import java.math.BigDecimal;

/**
 * Created by Almas_Doskozhin
 * on 2/21/2016.
 */
public class FundsOverdrawnException extends BusinessException {
    private final BigDecimal amount;
    private final String user;
    private final Currency currency;

    public FundsOverdrawnException(BigDecimal amount, String user, Currency currency) {
        this.amount = amount;
        this.user = user;
        this.currency = currency;
    }

    @Override
    public String getMessage() {
        return "Not enough money on " + currency.toString() + "account for user: " + user + ". Available "
                +currency +" amount: " + amount;
    }
}
