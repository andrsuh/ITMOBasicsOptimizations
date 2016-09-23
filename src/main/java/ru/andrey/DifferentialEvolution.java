package ru.andrey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javafx.scene.input.KeyCode.F;

public class DifferentialEvolution extends OptimizationMethod {
    private static double DIFFEVOLUTION_PARAMETER;
    private static double MUTATION_PROBABILITY;


    private int populationSize;

    public static void main(String[] args) {
        double acc = 0.0;
        DifferentialEvolution s = new DifferentialEvolution(10, 1000);

        Double bestMutationProbability = null;
        Double bestDiffEvParameter = null;
        Double bestAverage = null;

        for (double diffParam = 0.05; diffParam < 2.0; diffParam += 0.2) {
            DIFFEVOLUTION_PARAMETER = diffParam;
            for (double prob = 0.0; prob < 1.0; prob += 0.1) {
                for (int i = 0; i < 10; ++i) {
                    double diff = s.getDifference();
                    acc += diff;
                    System.out.println("Difference " + diff);
                }
                if (bestAverage == null || acc / 10.0 < bestAverage) {
                    bestAverage = acc / 10.0;
                    bestMutationProbability = prob;
                    bestDiffEvParameter = diffParam;
                }
            }
        }
        System.out.println("best param = " + bestDiffEvParameter + " best prob = " + bestMutationProbability);
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

                int aIndex = randomIndexFromPopulation( populationSize, Arrays.asList(i));
                int bIndex = randomIndexFromPopulation(populationSize, Arrays.asList(i, aIndex));
                int cIndex = randomIndexFromPopulation(populationSize, Arrays.asList(i, aIndex, bIndex));

                double[] a = population.get(aIndex).getSolution();
                double[] b = population.get(bIndex).getSolution();
                double[] c = population.get(cIndex).getSolution();

                int k = (int) (Math.random() * dimension);

                double[] newSolution = current.getSolution();

//                double F = (Math.random() > 0.1) ? 0.5 : 0.005;

                for (int j = 0; j < dimension; j++) {
//                    if (b[j] - c[j] > 0.1) {
//                        System.out.println(b[j] - c[j]);
//                    }

                    if (j == k || Math.random() > MUTATION_PROBABILITY) {
                        newSolution[j] = a[j] + DIFFEVOLUTION_PARAMETER * (b[j] - c[j]);
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

        while(except.contains(index)) {
            index = (int) (Math.random() * to);
        }

        return index;
    }
}
