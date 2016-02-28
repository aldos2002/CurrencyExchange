package com.epam.utils;

import com.epam.dao.EntityDao;
import com.epam.entity.UserAccount;
import com.epam.exceptions.BusinessException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by Almas_Doskozhin
 * on 2/21/2016.
 */
public class Utils {
    private static final Logger LOG = Logger.getLogger(Utils.class);

    private Utils(){}

    public static boolean checkUsers(){
        try {
            EntityDao entityDao = new EntityDao();
            UserAccount user1 = entityDao.readUserAccount("user1");
            UserAccount user2 = entityDao.readUserAccount("user2");

            boolean user1Correct = user1.getDollarAmount().intValue()==30 &&
                    user1.getEuroAmount().intValue()==136 &&
                    user1.getRubleAmount().intValue()==1520 &&
                    user1.getKZTAmount().intValue()==10000;
            boolean user2Correct = user2.getDollarAmount().intValue()==126 &&
                    user2.getEuroAmount().intValue()==130 &&
                    user2.getRubleAmount().intValue()==780 &&
                    user2.getKZTAmount().intValue()==25500;
            return user1Correct && user2Correct;
        }catch (IOException|BusinessException ex){
            LOG.error(ex.getMessage(), ex);
            return false;
        }
    }

    public static void resetUsers() {
        try {
            UserAccount user1 = new UserAccount("user1", new BigDecimal(50), new BigDecimal(100),
                    new BigDecimal(2000), new BigDecimal(15000));
            UserAccount user2 = new UserAccount("user2", new BigDecimal(70), new BigDecimal(200),
                    new BigDecimal(3000), new BigDecimal(7000));
            EntityDao entityDao = new EntityDao();
            entityDao.saveUserAccount(user1);
            entityDao.saveUserAccount(user2);
        } catch (IOException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        LOG.debug("Reset was successful");
    }
}
