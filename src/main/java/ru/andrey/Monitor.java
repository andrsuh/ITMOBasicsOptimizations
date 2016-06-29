package ru.andrey;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Monitor {
    public static FileWriter file;

    public static void main(String[] args) throws IOException {
        file = new FileWriter("result.txt");

        for (int dimension = 1; dimension <= 10; ++dimension) {
            file.write("Dimension: " + dimension + "\n");

            List<OptimizationMethod> methods = new ArrayList<>();

            methods.add(new RandomRestarts(dimension));
            methods.add(new SimulatedAnnealing(dimension));
            methods.add(new IteratedLocalSearch(dimension));
            methods.add(new EvolutionStrategyWithReplacement(dimension, 5, 25));
            methods.add(new EvolutionStrategyWithMerging(dimension, 50, 200));

            for (OptimizationMethod method : methods) {
                run(method, 100);
            }
        }

        file.close();
    }

    public static void run(OptimizationMethod method, int times) throws IOException {
        double acc = 0.0;
        for (int i = 0; i < times; ++i) {
            Solution opt = method.searchOptimum();
            acc += opt.getQuality();
        }
        file.write("Method: " + method.getClass() + " Average: " + acc / (double) times + "\n");
    }
}
