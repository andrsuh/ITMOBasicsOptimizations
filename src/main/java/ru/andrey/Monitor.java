package ru.andrey;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Monitor {
    public static FileWriter file;

    public static void main(String[] args) throws IOException {
        file = new FileWriter("result.txt");

        for (int dimension = 1; dimension <= 10; ++dimension) {
            file.write("Dimension: " + dimension + "\n");

            List<OptimizationMethod> methods = Arrays.asList(
                    new RandomRestarts(dimension),
                    new SimulatedAnnealing(dimension),
                    new IteratedLocalSearch(dimension),
                    new EvolutionStrategyWithReplacement(dimension, 5, 25),
                    new EvolutionStrategyWithMerging(dimension, 50, 200),
                    new EvolutionStrategyWithCrossing(dimension, 50, 250)
            );

            for (OptimizationMethod method : methods) {
                run(method, 100);
            }
        }

        file.close();
    }

    public static void run(OptimizationMethod method, int times) throws IOException {
        double acc = 0.0;
        for (int i = 0; i < times; ++i) {
            double diff = method.getDifference();
            acc += diff;
        }
        file.write("Method: " + method.getClass() + " Average difference: " + acc / (double) times + "\n");
    }
}
