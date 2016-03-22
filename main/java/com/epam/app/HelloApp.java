package com.epam.app;

import com.epam.entity.Commands;
import com.epam.utils.Utils;
import com.epam.entity.Currency;
import com.epam.exceptions.BusinessException;
import com.epam.service.ExchangeService;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HelloApp {
    private static final ExchangeService EXCHANGE_SERVICE = new ExchangeService();
    private static final Logger LOG = Logger.getLogger(HelloApp.class);
    private static final String USER_1 = "User1";
    private static final String USER_2 = "User2";
    private static ExecutorService executorService =  Executors.newCachedThreadPool();
    private static int counter = 1;

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        boolean exit = false;
        while (!exit) {
            System.out.println("Run cmd (test/reset/diag/quit):");
            String mode = scanner.nextLine();
            Commands cmd = Commands.valueOf(mode.toUpperCase());
            switch (cmd) {
                case TEST:
                    runTests();
                    break;
                case RESET:
                    Utils.resetUsers();
                    break;
                case DIAG:
                    System.out.println("Please choose your user(User1, User2): ");
                    String input = scanner.nextLine();
                    String user = input;
                    System.out.println("User chosen: " + input);
                    System.out.println("Input exchange values");

                    System.out.println("Input From curreny(USD, EUR, KZT, RUBLE): ");
                    Currency fromCurrency = Currency.valueOf(scanner.nextLine());
                    System.out.println("Input To curreny (USD, EUR, KZT, RUBLE): ");
                    Currency toCurrency = Currency.valueOf(scanner.nextLine());
                    System.out.println("Input currency ammount: ");
                    BigDecimal amount = scanner.nextBigDecimal();
                    scanner.nextLine();
                    exchange(user, fromCurrency, toCurrency, amount);
                    break;
                case QUIT:
                    exit = true;
                    executorService.shutdown();
                    break;
                default:
                    System.out.println("Incorrect cmd.");
            }
            Thread.sleep(5000L);
        }
    }

    private static void runTests() throws InterruptedException {
        exchange(USER_1, Currency.USD, Currency.EUR, new BigDecimal(10));
        exchange(USER_1, Currency.KZT, Currency.RUBLE, new BigDecimal(1000));

        exchange(USER_2, Currency.EUR, Currency.USD, new BigDecimal(10));

        exchange(USER_1, Currency.USD, Currency.EUR, new BigDecimal(20));
        exchange(USER_1, Currency.KZT, Currency.RUBLE, new BigDecimal(500));

        exchange(USER_2, Currency.RUBLE, Currency.KZT, new BigDecimal(2000));
        exchange(USER_2, Currency.EUR, Currency.USD, new BigDecimal(50));
        exchange(USER_2, Currency.RUBLE, Currency.KZT, new BigDecimal(1000));

        exchange(USER_1, Currency.KZT, Currency.USD, new BigDecimal(3500));
        exchange(USER_1, Currency.RUBLE, Currency.EUR, new BigDecimal(780));

        exchange(USER_2, Currency.EUR, Currency.RUBLE, new BigDecimal(10));
        exchange(USER_2, Currency.USD, Currency.KZT, new BigDecimal(10));

        Thread.sleep(5000L);
        if(Utils.checkUsers()){
            System.out.println("Tests passed.");
        }else{
            System.out.println("Tests failed.");
        }
    }

    private static void exchange(final String user, final Currency fromCurrency, final Currency toCurrency, final BigDecimal amount) {
        final int threadCounter = counter++;
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                LOG.debug("Thread "+threadCounter+" started");
                try {
                    EXCHANGE_SERVICE.convert(fromCurrency, toCurrency, amount, user);
                } catch (IOException |BusinessException e) {
                    LOG.error(e.getMessage(), e);
                }
                LOG.debug("Thread "+threadCounter+" finished");
            }
        });
    }
}
