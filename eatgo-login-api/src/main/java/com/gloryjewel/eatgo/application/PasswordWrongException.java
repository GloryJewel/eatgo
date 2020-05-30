package com.gloryjewel.eatgo.application;

public class PasswordWrongException extends RuntimeException{
    PasswordWrongException(){
        super("password is wrong");
    }
}
