package ru.andrey;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class EvolutionStrategyWithMerging extends OptimizationMethod {
    private int m;
    private int l;
    private double step = 1;

    public EvolutionStrategyWithMerging(int dimension, int m, int l) {
        super(dimension);
        this.m = m;
        this.l = l;
    }

    public static void main(String[] args) {
        double acc = 0.0;
        EvolutionStrategyWithMerging s = new EvolutionStrategyWithMerging(10,50, 200);
        for (int i = 0; i < 100; ++i) {
            double diff = s.getDifference();
            acc += diff;
            System.out.println("Optimum " + diff);
        }
        System.out.println("Average " + acc / 100.0);
    }

    @Override
    public Solution searchOptimum() {
        oracle = new Oracle(dimension);

        Set<Solution> population = new TreeSet<>((a, b) -> a.getQuality().compareTo(b.getQuality()));
        for (int i = 0; i < l; ++i) {
            population.add(new Solution(oracle, randomGeneratedSolution()));
        }

        Solution bestSolution = null;

        while (true) {
            for (Solution s : population) {
                if (bestSolution == null || bestSolution.getQuality() > s.getQuality()) {
                    bestSolution = Solution.copyOf(s);
                }
            }

            Set<Solution> individualsForMutation = population.stream().limit(m).collect(Collectors.toSet());

            population = new TreeSet<>((a, b) -> a.getQuality().compareTo(b.getQuality()));
            for (Solution s : individualsForMutation) {
                population.add(s);
                for (int i = 0; i < (l / m); ++i) {
                    Solution solution = Solution.copyOf(s);
                    solution.mutate(step);
                    if (solution.broken()) {
                        return bestSolution;
                    }
                    population.add(solution);
                }
            }
        }
    }
}
