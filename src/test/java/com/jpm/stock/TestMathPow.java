package com.jpm.stock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nava on 30/05/2016.
 */
public class TestMathPow {

    public static void main(String[] args) {

        List<Integer> data = new ArrayList<>();
        data.add(2);
        data.add(32);


        double sum = 1.0;

        for (int i = 0; i < data.size(); i++) {
            sum = sum * data.get(i);
        }

        double geoMean = StrictMath.pow(sum, 1.0 / data.size());
        System.out.println(geoMean);
    }
}
