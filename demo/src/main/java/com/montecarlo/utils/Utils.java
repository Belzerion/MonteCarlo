package com.montecarlo.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class Utils {
    
    public static List<Double> getRand(final int samples)
    {
        Random random = new Random();

        List<Double> randomNumbers = new ArrayList<>();

        for (int i = 0; i < samples; i++)
        {
            randomNumbers.add(random.nextDouble());
        }
        return randomNumbers;
    }
}
