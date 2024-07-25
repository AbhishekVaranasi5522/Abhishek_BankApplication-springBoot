package com.example.banking.utils;

public class Singleton {
    // Static variable to hold the single instance
    private static Singleton instance;
    int i;
    // Private constructor to prevent instantiation
    private Singleton() {
        // Initialization code here
        i=10;
    }

    // Public method to provide access to the instance
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    // Example method to demonstrate functionality
    public void showMessage() {
        System.out.println("Hello from the Singleton instance!  "+i);
    }

    public static void main(String[] args) {
        // Get the single instance of the Singleton class
        Singleton singleton = Singleton.getInstance();

        // Call a method on the Singleton instance
        singleton.showMessage();
    }
}
