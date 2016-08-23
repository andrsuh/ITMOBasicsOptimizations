package ru.andrey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DifferentialEvolution extends OptimizationMethod {
    private int populationSize;

    public static void main(String[] args) {
        double acc = 0.0;
        DifferentialEvolution s = new DifferentialEvolution(10, 10000);
        for (int i = 0; i < 10; ++i) {
            double diff = s.getDifference();
            acc += diff;
            System.out.println("Difference " + diff);
        }
        System.out.println("Average " + acc / 10.0);
    }

    public DifferentialEvolution(int dimension, int populationSize) {
        super(dimension);
        this.populationSize = populationSize;
    }

    @Override
    public Solution searchOptimum() {
        oracle = new Oracle(dimension);
//        int count = 0;

        List<Solution> population = new ArrayList<>();
        for (int i = 0; i < populationSize; ++i) {
            population.add(new Solution(oracle, randomGeneratedSolution()));
        }

        Solution bestSolution = Solution.copyOf(population.get(0));

        while(true) {
            for (int i = 0; i < populationSize; i++) {
                Solution current = population.get(i);

                int aIndex = randomIndexFromPopulation(population, populationSize, Arrays.asList(i));
                int bIndex = randomIndexFromPopulation(population, populationSize, Arrays.asList(i, aIndex));
                int cIndex = randomIndexFromPopulation(population, populationSize, Arrays.asList(i, aIndex, bIndex));

                double[] a = population.get(aIndex).getSolution();
                double[] b = population.get(bIndex).getSolution();
                double[] c = population.get(cIndex).getSolution();

                int k = (int) (Math.random() * dimension);

                double[] newSolution = current.getSolution();

                double F = 0.001;

                for (int j = 0; j < dimension; j++) {
//                    if (b[j] - c[j] > 0.1) {
//                        System.out.println(b[j] - c[j]);
//                    }

                    if (i == k || Math.random() > 0.3) {
                        newSolution[j] = a[j] + F * (b[j] - c[j]);
                    }
                }

                Solution descendant = new Solution(oracle, newSolution);

                if (descendant.broken()) {
//                    System.out.println(count);
                    return bestSolution;
                }
//                } else {
//                    ++count;
//                }

                if (descendant.getQuality() < bestSolution.getQuality()) {
                    bestSolution = Solution.copyOf(descendant);
                }

                if (descendant.getQuality() < current.getQuality()) {
                    population.set(i, descendant);
                }
            }
        }
    }

    private int randomIndexFromPopulation(List<Solution> population, int to, List<Integer> except) {
        int index = except.get(0);

        while(except.contains(index)) {
            index = (int) (Math.random() * to);
        }

        return index;
    }

    @Override
    public double getDifference() {
        return super.getDifference();
    }
}
