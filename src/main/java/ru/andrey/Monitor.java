package main.java.ru.andrey;

import java.util.ArrayList;
import java.util.List;

public class Monitor {
    public static void main(String[] args) {
        for (int dimension = 1; dimension <= 10; ++dimension) {
            System.out.println("Dimension: " + dimension);

            List<OptimizationMethod> methods = new ArrayList<>();

            methods.add(new RandomSearch(dimension));
            methods.add(new SimulatedAnnealing(dimension));
            methods.add(new IteratedLocalSearch(dimension));
            methods.add(new EvolutionStrategyWithReplacement(dimension, 5, 25));
            methods.add(new EvolutionStrategyWithMerging(dimension, 50, 200));

            for (OptimizationMethod method: methods) {
                run(method, 5);
            }
        }
    }

    public static void run(OptimizationMethod method, int times) {
        double acc = 0.0;
        for (int i = 0; i < times; ++i) {
            Solution opt = method.searchOptimum();
            acc += opt.getQuality();
//            System.out.println("Optimum " + opt.getQuality());
        }
        System.out.println("Method: " + method.getClass() + " Average: " + acc / (double)times);
    }
}
