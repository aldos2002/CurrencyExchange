package com.epam.dao;

import com.epam.entity.Currency;
import com.epam.entity.UserAccount;
import com.epam.exceptions.IncorrectAccountException;
import com.epam.exceptions.IncorrectCurrencyException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Properties;

/**
 * Created by Almas_Doskozhin
 * on 2/22/2016.
 */
public class EntityDao {
    private static final String USER_NAME = "userName";

    public UserAccount readUserAccount(String login) throws IOException, IncorrectAccountException {
        Properties prop = new Properties();
        String propFileName = login+".properties";

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        if(inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new IncorrectAccountException(login);
        }

        String userName = prop.getProperty(USER_NAME);
        BigDecimal dollarAmount =  new BigDecimal(prop.getProperty(Currency.USD.toString()));
        BigDecimal euroAmount =  new BigDecimal(prop.getProperty(Currency.EUR.toString()));
        BigDecimal rubleAmount =  new BigDecimal(prop.getProperty(Currency.RUBLE.toString()));
        BigDecimal KZTAmount =  new BigDecimal(prop.getProperty(Currency.KZT.toString()));
        return new UserAccount(userName, dollarAmount, euroAmount, rubleAmount, KZTAmount);
    }

    public void saveUserAccount(UserAccount account) throws IOException {
        Properties properties = new Properties();
        properties.put(USER_NAME, "" + account.getLogin());
        properties.put(Currency.USD.toString(), account.getDollarAmount().toString());
        properties.put(Currency.EUR.toString(), account.getEuroAmount().toString());
        properties.put(Currency.RUBLE.toString(), account.getRubleAmount().toString());
        properties.put(Currency.KZT.toString(), account.getKZTAmount().toString());

        String propFileName = account.getLogin()+".properties";
        String path = EntityDao.class.getClassLoader().getResource(propFileName).getFile();
        OutputStream os = new FileOutputStream(new File(path));
        properties.store(os, null);
        os.close();
    }

    public BigDecimal getExchangeRate(Currency currency) throws IOException, IncorrectCurrencyException {
        Properties prop = new Properties();
        String propFileName = "exchangeRates.properties";

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        if(inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propFileName + "'not found it the classpath");
        }

        String exchRate = prop.getProperty(currency.toString());
        if(exchRate == null){
            throw new IncorrectCurrencyException(currency);
        }
        return new BigDecimal(exchRate);
    }
}
