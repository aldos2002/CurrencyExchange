package com.epam.dao;

import com.epam.entity.Currency;
import com.epam.entity.UserAccount;
import com.epam.exceptions.BusinessException;
import com.epam.exceptions.IncorrectCurrencyException;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Properties;

/**
 * Created by Almas_Doskozhin
 * on 2/22/2016.
 */
public class EntityDao {
    private static final Logger LOG = Logger.getLogger(EntityDao.class);
    private static final String USER_NAME = "userName";

    public UserAccount readUserAccount(String login) throws BusinessException {
        Properties prop = getPropertiesByName(login);
        String userName = prop.getProperty(USER_NAME);
        BigDecimal dollarAmount = getCurrencyAmount(prop, Currency.USD);
        BigDecimal euroAmount = getCurrencyAmount(prop, Currency.EUR);
        BigDecimal rubleAmount = getCurrencyAmount(prop, Currency.RUBLE);
        BigDecimal tengeAmount = getCurrencyAmount(prop, Currency.KZT);
        return new UserAccount(userName, dollarAmount, euroAmount, rubleAmount, tengeAmount);
    }

    public void saveUserAccount(UserAccount account) throws BusinessException {
        Properties properties = new Properties();
        properties.put(USER_NAME, account.getLogin());
        properties.put(Currency.USD.toString(), account.getDollarAmount().toString());
        properties.put(Currency.EUR.toString(), account.getEuroAmount().toString());
        properties.put(Currency.RUBLE.toString(), account.getRubleAmount().toString());
        properties.put(Currency.KZT.toString(), account.getTengeAmount().toString());
        updatePropertiesByName(account.getLogin(), properties);
    }

    public BigDecimal getExchangeRate(Currency currency) throws BusinessException {
        String propFileName = "exchangeRates";
        Properties prop = getPropertiesByName(propFileName);
        String exchRate = prop.getProperty(currency.toString());
        if (exchRate == null) {
            throw new IncorrectCurrencyException(currency);
        }
        return new BigDecimal(exchRate);
    }

    private Properties getPropertiesByName(String name) throws BusinessException {
        Properties prop = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(name + ".properties")) {
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                LOG.error("Property file '" + name + "'not found it the classpath");
            }
        } catch (IOException e) {
            throw new BusinessException("getPropertiesByName threw exception: ", e);
        }
        return prop;
    }

    private void updatePropertiesByName(String name, Properties prop) throws BusinessException {
        String propFileName = name + ".properties";
        URL url = EntityDao.class.getClassLoader().getResource(propFileName);
        if (url != null) {
            String path = url.getFile();
            try (OutputStream os = new FileOutputStream(new File(path))) {
                prop.store(os, null);
            } catch (IOException e) {
                throw new BusinessException("saveUserAccount threw exception: ", e);
            }
        } else {
            LOG.error("Property file '" + name + "'not found it the classpath");
        }
    }

    private BigDecimal getCurrencyAmount(Properties prop, Currency currency) {
        return new BigDecimal(prop.getProperty(currency.toString()));
    }
}
