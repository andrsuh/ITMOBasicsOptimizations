package ru.andrey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.pow;
import static java.lang.Math.random;
import static jdk.nashorn.internal.objects.NativeMath.min;

public class Oracle {
    private int dimension;
    private int counter;
    private boolean noisy;

    private double[][] alpha;
    private double[] gamma = new double[5];
    private double[] beta = new double[5];

    private double optimum;

    public Oracle(int dimension) {
        this.dimension = dimension;
        this.counter = (int) 1E4 * (int) pow(dimension, 2);

        alpha = new double[5][dimension];
        for (int i = 0; i < 5; ++i) {
            beta[i] = random() * 20 - 10;
            gamma[i] = random() + 1;

            for (int j = 0; j < dimension; ++j) {
                alpha[i][j] = random() * 20 - 10;
            }
        }

        double[] mins = new double[] {Double.POSITIVE_INFINITY};
        Arrays.stream(beta).forEach(x -> {
            if (x < mins[0]) {
                mins[0] = x;
            }
        });

        optimum = mins[0];
    }

    public Double quality(final double[] args) {
        if (counter-- <= 0) {
            return null;
        }

        List<Double> values = new ArrayList<>();

        for (int i = 0; i < 5; ++i) {
            double summ = 0;

            for (int j = 0; j < dimension; ++j) {
                summ += pow((args[j] - alpha[i][j]), 2);
            }
            values.add(beta[i] + (gamma[i] / 10) * summ); // g / 10 avoid problems with float numbers
        }

        if (noisy) {
            return (random() - 0.5) + Collections.min(values);
        }
        return Collections.min(values);

    }

    public double getOptimum() {
        return optimum;
    }
}
