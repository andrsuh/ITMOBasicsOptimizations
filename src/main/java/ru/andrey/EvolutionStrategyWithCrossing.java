package ru.andrey;

import java.util.*;
import java.util.stream.Collectors;

public class EvolutionStrategyWithCrossing extends OptimizationMethod {
    private int m;
    private int l;
    private double step = 1;

    public EvolutionStrategyWithCrossing(int dimension, int m, int l) {
        super(dimension);
        this.m = m;
        this.l = l;
    }

    public static void main(String[] args) {
        double acc = 0.0;
        EvolutionStrategyWithCrossing s = new EvolutionStrategyWithCrossing(1, 50, 250);
        for (int i = 0; i < 10; ++i) {
            double diff = s.getDifference();
            acc += diff;
            System.out.println("Optimum " + diff);
        }
        System.out.println("Average " + acc / 10.0);
    }

    @Override
    public Solution searchOptimum() {
        oracle = new Oracle(dimension);

        List<Solution> population = new ArrayList<>();
        for (int i = 0; i < l; ++i) {
            population.add(new Solution(oracle, randomGeneratedSolution()));
        }
        Solution bestSolution = null;

        while (true) {
            for (Solution solution : population) {
                if (bestSolution == null || solution.getQuality() < bestSolution.getQuality()) {
                    bestSolution = Solution.copyOf(solution);
                }
            }
            Set<Solution> individualsForMutation = population.stream()
                    .sorted((a, b) -> a.getQuality().compareTo(b.getQuality()))
                    .limit(m)
                    .collect(Collectors.toSet());

            population = new ArrayList<>(individualsForMutation);

            List<Solution> parentArray = new ArrayList<>(individualsForMutation);

            for (int i = 0; i <= (l / 2); ++i) {
                Solution parent1 = Solution.copyOf(parentArray.get((int) (Math.random() * m)));
                Solution parent2 = Solution.copyOf(parentArray.get((int) (Math.random() * m)));
                double[] parent1Array = parent1.getSolution();
                double[] parent2Array = parent2.getSolution();
                double[] childArray1 = new double[dimension];
                double[] childArray2 = new double[dimension];

                for (int a = 0; a < dimension; ++a) {
                    int random = Math.random() > 0.5 ? 1 : 0;
                    childArray1[a] = (parent1Array[a] * random) + ((1 - random) * parent2Array[a]);
                    childArray2[a] = (parent2Array[a] * random) + ((1 - random) * parent1Array[a]);
                }

                Solution child1 = new Solution(oracle, childArray1);
                Solution child2 = new Solution(oracle, childArray2);

                if (child1.broken() || child2.broken()) {
                    System.out.println();
                    return bestSolution;
                }
                population.add(child1);
                population.add(child2);
            }

        }
    }
}