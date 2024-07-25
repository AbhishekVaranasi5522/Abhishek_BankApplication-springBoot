package com.example.banking.utils;

import java.util.*;
import java.util.stream.Collectors;

public class Test implements Runnable{

    public void run(){
        for(int i=0;i<5;i++)
        {
            System.out.println("I am "+i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("ENd of run");
    }

    public static void main(String args[])
    {
        Test t = new Test();
        Thread thread = new Thread(t,"Test");
        System.out.println(thread.getName());
        thread.start();
        System.out.println("Main end");
//        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 1, 2, 6, 7, 8, 2, 3, 9);
//
//        List<Integer> res = numbers.stream().collect(Collectors.groupingBy(i->i,Collectors.counting()))
//                .entrySet()
//                .stream()
//                .filter(entry -> entry.getValue() > 1)
//                .map(Map.Entry::getKey)
//                .toList();
//
//        Set<Integer> set1 = new HashSet<>();
//        Set<Character> set2 = new LinkedHashSet<>();
//
//        numbers.stream().collect(Collectors.partitioningBy(i -> i%2 ==0)).entrySet().stream().filter(Map.Entry::getKey).distinct().forEach(System.out::println);

   //     numbers.stream().filter(x->!set1.add(x)).collect(Collectors.toSet()).forEach(System.out::println);

//        String s = "aaaabbcccckkk";
//
//        OptionalInt t = s.chars().filter(x->!set2.add((char)x)).findFirst();
//        System.out.println((char)t.getAsInt());



//        Map<Integer,Long> map = numbers.stream().collect(Collectors.groupingBy(i->i,Collectors.counting()));
//
//        map.forEach((key, value) -> System.out.println(key + " " + value));

//        Employee e1 = new Employee(1,"Abhi", 100L);
//        Employee e2 = new Employee(2,"Nithu",200L);
//
//        List<Employee> list = Arrays.asList(e1,e2);

    }

}