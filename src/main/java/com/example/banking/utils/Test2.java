package com.example.banking.utils;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Test2 {

    public void rotate(int[] nums, int k) {
        int n = nums.length;
        for(int i=0;i<k;i++)
        {
            swap(nums,n-k+i,i);
        }
    }

    public void swap(int[] nums,int a ,int b)
    {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }

    public static void main(String[] args) {

        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        int k = 3;
        Test2 test2 = new Test2();
        test2.rotate(arr,k);
        Arrays.stream(arr).forEach(System.out::print);
    }
}