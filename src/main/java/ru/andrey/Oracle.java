package ru.andrey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.pow;

public class Oracle {
    private int dimension;
    private int counter;

    public Oracle(int dimension) {
        this.dimension = dimension;
        this.counter = (int) 10E4 * (int) pow(dimension, 2);
    }

    public Double quality(final double x) {
        return quality(new double[]{x});
    }

    public Double quality(final double[] args) {
        if (counter-- == 0) {
            return null;
        }

        List<Double> values = new ArrayList<>();

        int a = 1;
        int b = 1;
        double g = 20;

        for (int i = 0; i < 5; ++i) {
            double summ = 0;

            for (int j = 0; j < dimension; ++j) {
                summ += pow((args[j] - a), 2);
            }
            values.add(b + (g / 10) * summ); // g / 10 avoid problems with float numbers

            b++;
            g -= 2;
            a++;
        }

        return Collections.min(values);
    }
}
