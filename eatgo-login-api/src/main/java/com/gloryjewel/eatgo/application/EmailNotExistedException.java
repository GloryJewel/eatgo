package com.gloryjewel.eatgo.application;

public class EmailNotExistedException extends RuntimeException{

    EmailNotExistedException(){
        super("email is not registered");
    }
}
