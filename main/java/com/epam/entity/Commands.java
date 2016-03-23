package com.epam.entity;

/**
 * Created by Almas_Doskozhin on 2/28/2016.
 */
public enum Commands {
    TEST("test"),
    RESET("reset"),
    DIAG("diag"),
    QUIT("quit");

   private String name;

    private Commands(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
