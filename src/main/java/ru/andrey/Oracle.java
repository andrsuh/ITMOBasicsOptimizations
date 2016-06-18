package ru.andrey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.pow;

public class Oracle {
    private int counter = (int) 10E4;

    public static void main(String[] args) {
        Oracle oracle = new Oracle();

        Double minValue = null;
        Double argValue = null;

        for (double i = -10; i < 10; i += 0.1) {
            double next = oracle.quality(i);
            System.out.println("F(x) = " + next + " x = " + i);

            if (minValue == null || next < minValue) {
                minValue = next;
                argValue = (double) i;
            }
        }

        System.out.println("Function min == " + minValue);
        System.out.println("Argument min == " + argValue);
    }

    public Double quality(final double x) {
        if (counter-- == 0) {
            return null;
        }

        List<Double> values = new ArrayList<Double>() {{
            add(1 + 2 * pow((x - 1), 2));
            add(2 + 1.8 * pow((x - 2), 2));
            add(3 + 1.6 * pow((x - 3), 2));
            add(4 + 1.4 * pow((x - 4), 2));
            add(5 + 1.2 * pow((x - 5), 2));
        }};

        return Collections.min(values);
    }
}
