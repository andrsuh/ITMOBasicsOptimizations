package ru.andrey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DifferentialEvolution extends OptimizationMethod {
    private static final double DIFFEVOLUTION_PARAMETER = 1.0E-4;
    private static final double MUTATION_PROBABILITY = 0.9;
    private static final int POPULATION_SIZE = 1000;

    private int populationSize;

    public DifferentialEvolution(int dimension, int populationSize) {
        super(dimension);
        this.populationSize = populationSize;
    }

    public static void main(String[] args) {
        double acc = 0.0;
        DifferentialEvolution s = new DifferentialEvolution(10, POPULATION_SIZE);
        for (int i = 0; i < 10; ++i) {
            double diff = s.getDifference();
            acc += diff;
            System.out.println("Difference " + diff);
        }
        System.out.println("Average = " + acc / 10.0);
    }

    @Override
    public Solution searchOptimum() {
        oracle = new Oracle(dimension);

        List<Solution> population = new ArrayList<>();
        for (int i = 0; i < populationSize; ++i) {
            population.add(new Solution(oracle, randomGeneratedSolution()));
        }

        Solution bestSolution = Solution.copyOf(population.get(0));

        while (true) {
            for (int i = 0; i < populationSize; i++) {
                Solution current = population.get(i);

                int aIndex = randomIndexFromPopulation(populationSize, Arrays.asList(i));
                int bIndex = randomIndexFromPopulation(populationSize, Arrays.asList(i, aIndex));
                int cIndex = randomIndexFromPopulation(populationSize, Arrays.asList(i, aIndex, bIndex));

                double[] a = population.get(aIndex).getSolution();
                double[] b = population.get(bIndex).getSolution();
                double[] c = population.get(cIndex).getSolution();

                int k = (int) (Math.random() * dimension);

                double[] newSolution = current.getSolution();

                for (int j = 0; j < dimension; j++) {
                    if (j == k || Math.random() < MUTATION_PROBABILITY) {
                        newSolution[j] = a[j] +  DIFFEVOLUTION_PARAMETER * (b[j] - c[j]);
                    }
                }

                Solution descendant = new Solution(oracle, newSolution);

                if (descendant.broken()) {
                    return bestSolution;
                }

                if (descendant.getQuality() < bestSolution.getQuality()) {
                    bestSolution = Solution.copyOf(descendant);
                }

                if (descendant.getQuality() < current.getQuality()) {
                    population.set(i, descendant);
                }
            }
        }
    }

    private int randomIndexFromPopulation(int to, List<Integer> except) {
        int index = except.get(0);

        while (except.contains(index)) {
            index = (int) (Math.random() * to);
        }

        return index;
    }
}
