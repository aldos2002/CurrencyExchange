package com.epam.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by admin on 21.02.2016.
 */
public class UserAccount {
    private List<Currency> currency;
    private String login;
    private BigDecimal dollarAmount;
    private BigDecimal euroAmount;
    private BigDecimal rubleAmount;
    private BigDecimal KZTAmount;

    public UserAccount(String login, BigDecimal dollarAmount, BigDecimal euroAmount, BigDecimal rubleAmount, BigDecimal KZTAmount) {
        this.login = login;
        this.dollarAmount = dollarAmount;
        this.euroAmount = euroAmount;
        this.rubleAmount = rubleAmount;
        this.KZTAmount = KZTAmount;
    }

    public List<Currency> getCurrency() {
        return currency;
    }

    public void setCurrency(List<Currency> currency) {
        this.currency = currency;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public BigDecimal getDollarAmount() {
        return dollarAmount;
    }

    public void setDollarAmount(BigDecimal dollarAmount) {
        this.dollarAmount = dollarAmount;
    }

    public BigDecimal getEuroAmount() {
        return euroAmount;
    }

    public void setEuroAmount(BigDecimal euroAmount) {
        this.euroAmount = euroAmount;
    }

    public BigDecimal getKZTAmount() {
        return KZTAmount;
    }

    public void setKZTAmount(BigDecimal KZTAmount) {
        this.KZTAmount = KZTAmount;
    }

    public BigDecimal getRubleAmount() {
        return rubleAmount;
    }

    public void setRubleAmount(BigDecimal rubleAmount) {
        this.rubleAmount = rubleAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount)) return false;
        UserAccount that = (UserAccount) o;
        return com.google.common.base.Objects.equal(currency, that.currency) &&
                com.google.common.base.Objects.equal(login, that.login) &&
                com.google.common.base.Objects.equal(dollarAmount, that.dollarAmount) &&
                com.google.common.base.Objects.equal(euroAmount, that.euroAmount) &&
                com.google.common.base.Objects.equal(rubleAmount, that.rubleAmount) &&
                com.google.common.base.Objects.equal(KZTAmount, that.KZTAmount);
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(currency, login, dollarAmount, euroAmount, rubleAmount, KZTAmount);
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "currency=" + currency +
                ", login='" + login + '\'' +
                ", dollarAmount=" + dollarAmount +
                ", euroAmount=" + euroAmount +
                ", rubleAmount=" + rubleAmount +
                ", KZTAmount=" + KZTAmount +
                '}';
    }
}
