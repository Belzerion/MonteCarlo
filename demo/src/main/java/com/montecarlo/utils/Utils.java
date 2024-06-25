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

    public static List<List<Double>> getFCoordinates()
    {
        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();
        List<List<Double>> coordinates = new ArrayList<>();
        for (int i = 0; i < 800; i++)
        {
            double temp = (double)i/800.0;
            x.add(temp);
            y.add(Math.sqrt(1- Math.pow(temp, 2.0)));
        }
        coordinates.add(x);
        coordinates.add(y);
        return coordinates;
    }
}
