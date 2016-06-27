package main.java.ru.andrey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.pow;

public class Oracle {
    private int dimension;
    private int counter;

    public static void main(String[] args) {
        Oracle oracle = new Oracle(5);

        Double minValue = null;
        Double argValue = null;

        for (double i = -10; i < 10; i += 0.00001) {
            double next = oracle.quality(new double[] {i, i, i, i, i});
            System.out.println("F(x) = " + next + " x = " + i);

            if (minValue == null || next < minValue) {
                minValue = next;
                argValue = (double) i;
            }
        }

        System.out.println("Function min == " + minValue);
        System.out.println("Argument min == " + argValue);
    }

    public Oracle() {
        this(1);
    }

    public Oracle(int dimension) {
        this.dimension = dimension;
        this.counter = (int)10E4 * (int)pow(dimension, 2);
    }

    public Double quality(final double x) {
        return quality(new double[] {x});
    }

    public Double quality(final double[] args) {
        if (counter-- == 0) {
            return null;
        }

        List<Double> values = new ArrayList<Double>();

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
