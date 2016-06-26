package main.java.ru.andrey;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class SimpleEvolutionStrategyWithReplace {
    private int dimension;
    private int m;
    private int l;
    private double step = 1;
    private Oracle oracle;

    public SimpleEvolutionStrategyWithReplace(int dimension, int m, int l) {
        this.dimension = dimension;
        this.m = m;
        this.l = l;
    }

    public static void main(String[] args) {
        double acc = 0.0;
        SimpleEvolutionStrategyWithReplace s = new SimpleEvolutionStrategyWithReplace(10, 50, 800);
        for (int i = 0; i < 100; ++i) {
            double opt = s.searchOptimum();
            acc += opt;
            System.out.println("Optimum " + opt);
        }
        System.out.println("Average " + acc / 100.0);
    }

    public Double searchOptimum() {
        oracle = new Oracle(dimension);

        Set<Solution> population = new TreeSet<>((a, b) -> a.getQuality().compareTo(b.getQuality()));
        for (int i = 0; i < l; ++i) {
            population.add(new Solution(
                    oracle,
                    Arrays.stream(new double[dimension])
                            .map((x) -> Math.random() * 20 - 10) // generate vector of numbers from [-10, 10)
                            .toArray())
            );
        }

        Solution bestSolution = null;

        while (true) {
            for (Solution solution : population) {
                if (bestSolution == null || solution.getQuality() < bestSolution.getQuality()) {
                    bestSolution = new Solution(solution);
                }
            }

            Set<Solution> individualsForMutation = population.stream().limit(m).collect(Collectors.toSet());
            population = new TreeSet<>((a, b) -> a.getQuality().compareTo(b.getQuality()));
            for (Solution s : individualsForMutation) {
                for (int i = 0; i < (l / m); ++i) {
                    s.mutate(step, true);
                    if (s.isBroken()) {
                        return bestSolution.getQuality();
                    }
                    population.add(s); // mutate
                }
            }
        }
    }
}
