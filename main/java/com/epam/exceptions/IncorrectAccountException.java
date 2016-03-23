package com.epam.exceptions;

/**
 * Created by Almas_Doskozhin
 * on 2/21/2016.
 */
public class IncorrectAccountException extends BusinessException{
    private final String userLogin;


  public IncorrectAccountException(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public String getMessage() {
        return "User " +userLogin+" does not exist";
    }
}
