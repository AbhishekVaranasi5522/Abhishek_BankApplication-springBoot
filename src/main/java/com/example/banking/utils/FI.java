package com.example.banking.utils;

@FunctionalInterface
public  interface FI {

    public abstract int sum(int a, int b);

     default void show(int a, int b,int sum)
    {
        System.out.println("a:"+a+" b:"+b+" sum:"+ sum);
    }

}
