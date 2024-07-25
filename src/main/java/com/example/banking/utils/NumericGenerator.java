package com.example.banking.utils;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.Random;

public class NumericGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        Random random = new Random();
        // Generate a random 10-digit number
        long randomNumber = 1000000000L + random.nextLong() % 9000000000L;
        return randomNumber;
    }
}
