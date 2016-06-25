package main.java.ru.andrey;

import java.util.Arrays;

public class RandomSearch {
    private int dimension;
    private double step = 0.001;
    private Oracle oracle;

    public static void main(String[] args) {
        double acc = 0.0;
        RandomSearch s = new RandomSearch(1);
        for (int i = 0; i < 100; ++i) {
            double opt = s.searchOptimum();
            acc += opt;
            System.out.println("Optimum " + opt);
        }
        System.out.println("Average " + acc / 100.0);
    }

    public RandomSearch(int dimension) {
        this.dimension = dimension;
    }

    public Double searchOptimum() {
        oracle = new Oracle(dimension);
        Solution globalOptimum = null;

        while (true) {
            Solution currentSolution = new Solution(
                    oracle,
                    Arrays.stream(new double[dimension])
                        .map((x) -> Math.random() * 20 - 10) // generate vector of numbers from [-10, 10)
                        .toArray()
            );

            Solution pretendentSolution = new Solution(currentSolution);
            pretendentSolution.mutation(step, false);

            if (currentSolution.isBroken()) {
                return globalOptimum.getQuality();
            }

            if (pretendentSolution.getQuality() > currentSolution.getQuality()) {
                step *= -1;
            }

            while (true) {
                pretendentSolution = new Solution(currentSolution);
                pretendentSolution.mutation(step, false);

                if (pretendentSolution.isBroken()) {
                    return globalOptimum.getQuality();
                }

                if (pretendentSolution.getQuality() < currentSolution.getQuality()) { // we are looking for minimum
//                    System.out.println("CurSolution: " + currentSolution + " CurQuality: " + currentSolution.getQuality());
//                    System.out.println("----------------------------------------------------------");
                    currentSolution = new Solution(pretendentSolution);
                } else {
                    if (globalOptimum == null || currentSolution.getQuality() < globalOptimum.getQuality()) {
                        globalOptimum = new Solution(currentSolution);
                    }
                    break;
                }
            }
        }
    }
}
