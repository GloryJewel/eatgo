package com.gloryjewel.eatgo.justTests;

import org.junit.jupiter.api.Test;

public class GenericClassTests {


    @Test
    public void genericCreate(){


        Number n = 1;
        Generic<Integer> object = new Generic(1);
        Generic<String> object2 = new Generic(1);

        object.make3(object2);

    }
}

class Generic<T>{

    T object;

    public Generic(T object){
        this.object = object;
    }

    public void make2(T object){
        this.object = object;
    }

    public <T,V> void make(T object1,V object2){

        System.out.println(object);
    }

    public void make3(Generic<? extends String> o){


    }
}
