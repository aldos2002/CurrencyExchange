package com.epam.utils;

import com.epam.dao.EntityDao;
import com.epam.entity.UserAccount;
import com.epam.exceptions.BusinessException;
import org.apache.log4j.Logger;

import java.math.BigDecimal;

/**
 * Created by Almas_Doskozhin
 * on 2/21/2016.
 */
public class Utils {
    private static final Logger LOG = Logger.getLogger(Utils.class);

    private Utils() {
    }

    public static boolean checkUsers() {
        try {
            EntityDao entityDao = new EntityDao();
            UserAccount user1 = entityDao.readUserAccount("user1");
            UserAccount user2 = entityDao.readUserAccount("user2");
            UserAccount checkUser1 = entityDao.readUserAccount("checkUser1");
            UserAccount checkUser2 = entityDao.readUserAccount("checkUser2");

            boolean user1Correct = user1.equals(checkUser1);
            boolean user2Correct = user2.equals(checkUser2);
            return user1Correct && user2Correct;
        } catch (BusinessException ex) {
            LOG.error("checkUsers() threw an exception", ex);
            return false;
        }
    }

    public static void resetUsers() {
        try {
            BigDecimal firstUserDollarAmount = new BigDecimal(50);
            BigDecimal firstUserEuroAmount = new BigDecimal(100);
            BigDecimal firstUserRubleAmount = new BigDecimal(2000);
            BigDecimal firstUserTengeAmount = new BigDecimal(15000);
            BigDecimal secondUserDollarAmount = new BigDecimal(70);
            BigDecimal secondUserEuroAmount = new BigDecimal(200);
            BigDecimal secondUserRubleAmount = new BigDecimal(3000);
            BigDecimal secondUserTengeAmount = new BigDecimal(7000);

            UserAccount user1 = new UserAccount("user1", firstUserDollarAmount, firstUserEuroAmount,
                    firstUserRubleAmount, firstUserTengeAmount);
            UserAccount user2 = new UserAccount("user2", secondUserDollarAmount, secondUserEuroAmount,
                    secondUserRubleAmount, secondUserTengeAmount);
            EntityDao entityDao = new EntityDao();
            entityDao.saveUserAccount(user1);
            entityDao.saveUserAccount(user2);
        } catch (BusinessException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        LOG.debug("Reset was successful");
    }
}
